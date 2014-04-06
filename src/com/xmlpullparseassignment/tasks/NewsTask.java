package com.xmlpullparseassignment.tasks;

import com.xmlpullparseassignment.connection.HTTPSConnection;
import com.xmlpullparseassignment.model.NewsItem;
import com.xmlpullparseassignment.parser.NewsParser;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;
import java.util.List;

/**
 * This is a async task, used to call api to fetch news list
 * @author Nilesh Jinde
 *
 */
public class NewsTask extends AsyncTask<String, String, List<NewsItem>> {

    private Context mContext;
    private ProgressDialog mProgressDialog;
    private NewsResult mResult;
    private boolean isTaskCancled = false;

    public NewsTask (Context context, NewsResult mResult){
        this.mResult = mResult;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog = ProgressDialog.show(mContext, "Please wait...", "Your patience will be appreciated !!!");
        super.onPreExecute();
    }

    @Override
    protected List<NewsItem> doInBackground(String... mParams) {
        if (isTaskCancled) {
            return null;
        } else {
            String webServiceUrl =  mParams[0];
            Log.i("Api url :", mParams[0]);
            HTTPSConnection httpsConnection = new HTTPSConnection();
            InputStream mInputStream = httpsConnection.getDataFromServer(webServiceUrl);

            NewsParser mNewsParser = new NewsParser();
            List<NewsItem> mNewsList = mNewsParser.parseNewsData(mInputStream);
            return mNewsList;
        }
    }

    @Override
    protected void onPostExecute(List<NewsItem> result) {
        if (result != null) {
            mResult.onReceiveResult(result);
        } else {
            mResult.onReceiveError();
        }
        mProgressDialog.cancel();
        super.onPostExecute(result);
    }

    @Override
    protected void onCancelled() {
        mProgressDialog.dismiss();
        isTaskCancled = true;
        super.onCancelled();
    }

    public interface NewsResult {
        public void onReceiveResult(List<NewsItem> result);
        public void onReceiveError();
    }
}
