package com.xmlpullparseassignment;

import com.xmlpullparseassignment.utilities.Constants;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Webview Activity to show link in webview
 * @author Nilesh Jinde
 */
@SuppressWarnings("deprecation")
@SuppressLint("SetJavaScriptEnabled")
public class WebViewActivity extends Activity{

    private WebView mWebView;
    private WebChromeClient mWebChromeClient;
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        setUpWebView();

        Bundle bundle  = getIntent().getExtras();
        if (bundle!=null) {
            String link = getIntent().getExtras().getString(Constants.INTENT_EXTRA_LINK);
            if(link != null)
                mWebView.loadUrl(link);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            mWebView.onPause();
        CookieSyncManager.getInstance().sync();
    }

    @Override
    public void onResume() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            if (mWebView == null) {
                setUpWebView();
            }
        mWebView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mWebView.saveState(outState);
    }

    private void setUpWebView() {
        mWebView = (WebView)findViewById(R.id.webView);
        mWebChromeClient = new MyWebChromeClient();
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setPluginState(PluginState.ON);
        settings.setBuiltInZoomControls(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        mWebView.setWebChromeClient(mWebChromeClient);
        mWebView.setWebViewClient(mWebViewClient);
    }

    private class MyWebChromeClient extends WebChromeClient{
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            String progress = newProgress+"%";
            if (toast == null) {
                toast = Toast.makeText(WebViewActivity.this, progress, Toast.LENGTH_SHORT);
                toast.setText(progress);
                toast.show();
            } else {
                toast.setText(progress);
                toast.show();
            }
        }
    }

    WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelToast();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelToast();
        if (mWebView != null) {
            mWebView.stopLoading();
        }
        mWebView = null;
    }

    private void cancelToast(){
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }
}
