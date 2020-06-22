package com.desaco.fundroid.webviewP;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.desaco.fundroid.R;

/**
 * @author dengwen
 * @date 2020/4/28.
 *
 *
 */
public class WebViewActivity extends Activity {

    private WebView webView;
    	private static final String mHomeUrl = "http://app.html5.qq.com/navi/index";
//	private static final String mHomeUrl = "file:///android_asset/ad/fun_html/ad_bg_video.html";
//    private static final String mHomeUrl = "file:///android_asset/index.html";
//    	private static final String mHomeUrl = "http://clarins.myprd.cn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.net_page_wv);
        init();
        webView.loadUrl(mHomeUrl);
    }


    private void init() {
        WebSettings webSettings = webView.getSettings();
        // 设置WebView支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        // 支持自动适配
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);  //支持放大缩小
        webSettings.setBuiltInZoomControls(false); //显示缩放按钮
        webSettings.setBlockNetworkImage(false);// 把图片加载放在最后来加载渲染
        webSettings.setAllowFileAccess(true); // 允许访问文件
        webSettings.setSaveFormData(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);/// 支持通过JS打开新窗口
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 自动播放h5里的video视频
        webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        // 设置UserAgent
        webView.getSettings().setUserAgentString("tv_shopping");
        // 设置不让其跳转浏览器
        webView.setBackgroundColor(Color.TRANSPARENT);
//        webView.getBackground().setAlpha(0);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                mLoadingPbar.setVisibility(View.VISIBLE);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                mLoadingPbar.setVisibility(View.GONE);

            }
        });
        // 添加客户端支持
        webView.setWebChromeClient(new WebChromeClient());
//        mWebView.loadUrl(TEXTURL);
        // 不加这个图片显示不出来
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

//        RelativeLayout mRootLayout = (RelativeLayout) findViewById(R.id.webview_root_layout);
//        mRootLayout.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (keyEvent.getAction() == KeyEvent.ACTION_UP) {
//                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 返回键
//                        finish();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
    }
}
