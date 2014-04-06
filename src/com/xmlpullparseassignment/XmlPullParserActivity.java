
package com.xmlpullparseassignment;

import com.xmlpullparseassignment.model.NewsItem;
import com.xmlpullparseassignment.tasks.NewsTask;
import com.xmlpullparseassignment.tasks.NewsTask.NewsResult;
import com.xmlpullparseassignment.utilities.Constants;
import com.xmlpullparseassignment.viewadapters.NewsListAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * XmlPullParserActivity
 * @author Nilesh Jinde
 */
public class XmlPullParserActivity extends Activity {

    private ListView mListView;
    private TextView mTextViewEmptyList;
    private NewsListAdapter mListAdapter;
    List<NewsItem> mNewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_pull_parse);

        initializeView();
        initializeOnClickHandlers();
        getNewsData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.xml_pull_parse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method used to initialise ui components
     */
    private void initializeView() {
        mListView = (ListView)findViewById(R.id.newsListView);
        mTextViewEmptyList = (TextView)findViewById(R.id.txtEmpty);
    }

    /**
     * This method used to set onclick handlers to ui components
     */
    private void initializeOnClickHandlers() {
        mListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent webViewIntent = new Intent(XmlPullParserActivity.this, WebViewActivity.class);
                webViewIntent.putExtra(Constants.INTENT_EXTRA_LINK, mNewsList.get(position).getLinks());
                startActivity(webViewIntent);
            }
        });
    }

    /**
     * This method used to get data from RSS feed
     */
    private void getNewsData() {
        String strNewsUrl = "http://timesofindia.feedsportal.com/c/33039/f/533917/index.rss"; // world news
        new NewsTask(this,new NewsResult() {

            @Override
            public void onReceiveResult(List<NewsItem> mList) {
                mNewsList = mList;
                if(mNewsList!= null && mNewsList.size()> 0){
                    showNewsList(mNewsList);
                }else{
                    mListView.setVisibility(View.GONE);
                    mTextViewEmptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onReceiveError() {

            }
        }).execute(new String[]{strNewsUrl});
    }

    /**
     * This method used to show rss feed data in list
     * @param mNewsList  list of news items
     */
    private void showNewsList(List<NewsItem> mNewsList) {
        mListAdapter = new NewsListAdapter(this, mNewsList);
        mListView.setAdapter(mListAdapter);
        mListView.setVisibility(View.VISIBLE);
        mTextViewEmptyList.setVisibility(View.GONE);
    }
}
