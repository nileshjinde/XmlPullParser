package com.xmlpullparseassignment.viewadapters;

import com.xmlpullparseassignment.R;
import com.xmlpullparseassignment.model.NewsItem;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * This is a NewsList adapter used to show list news feeds in list
 * @author Nilesh Jinde
 *
 */
public class NewsListAdapter extends BaseAdapter{

    private final List<NewsItem> mNewsList;
    private final Context context;

    public NewsListAdapter(Context context, List<NewsItem> items) {
        this.mNewsList = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.news_list_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.description = (TextView) convertView.findViewById(R.id.txtDesc);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(Html.fromHtml(mNewsList.get(position).getTitle()));
        holder.description.setText(Html.fromHtml(mNewsList.get(position).getDescription()));
        return convertView;
    }

    static class ViewHolder {
        TextView title;
        TextView description;
    }
}
