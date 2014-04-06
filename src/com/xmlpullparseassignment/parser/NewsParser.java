package com.xmlpullparseassignment.parser;

import com.xmlpullparseassignment.model.NewsItem;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
/**
 * This is parser, which parses XML news feed using XmlPullParser
 * @author Nilesh Jinde
 *
 */
public class NewsParser {

    public NewsParser() {
    }

    public List<NewsItem> parseNewsData(InputStream mInputStream){
        XmlPullParserFactory xmlFactoryObject;
        List<NewsItem> mNewsList = new ArrayList<NewsItem>();

        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            XmlPullParser mParser = xmlFactoryObject.newPullParser();
            mParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            mParser.setInput(mInputStream,null);

            int eventType = mParser.getEventType();
            NewsItem mItem = new NewsItem();
            String text = "", tagname;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                tagname = mParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            mItem = new NewsItem();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = mParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase("item")) {
                            mNewsList.add(mItem);
                        }else if (tagname.equalsIgnoreCase("title")){
                            mItem.setTitle(text);
                        }else if (tagname.equalsIgnoreCase("description")){
                            mItem.setDescription(text);
                        }else if (tagname.equalsIgnoreCase("link")){
                            mItem.setLinks(text);
                        }else if (tagname.equalsIgnoreCase("pubDate")){
                            mItem.setPubDate(text);
                        }
                        break;
                    default:
                        break;
                }
                eventType = mParser.next();
            }
        }catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return mNewsList;
    }
}
