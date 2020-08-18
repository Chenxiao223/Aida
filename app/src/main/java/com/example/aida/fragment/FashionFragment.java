package com.example.aida.fragment;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.aida.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.webkit.WebSettings.LOAD_NO_CACHE;

/**
 * 时尚
 */
public class FashionFragment extends BaseFragment {
    @BindView(R.id.layout_web)
    LinearLayout layoutWeb;
    @BindView(R.id.view)
    View mView;
    private WebView webview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fashion, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        initImmersionBar(mView);
        webview = new WebView(getActivity());
        layoutWeb.addView(webview);
        initWebView(webview);
        webview.loadUrl("https://www.vogue.com.cn/");
    }

    void initWebView(WebView webBase) {

        WebSettings webSettings = webBase.getSettings();
        webSettings.setCacheMode(LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= 19) {
            webSettings.setLoadsImagesAutomatically(true);
        } else {
            webSettings.setLoadsImagesAutomatically(false);
        }

        webBase.setLayerType(View.LAYER_TYPE_NONE, null);
        webSettings.setJavaScriptEnabled(true);
        /*webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);*/
        webSettings.setDatabaseEnabled(true);
        webSettings.setSavePassword(true);
        webSettings.setDomStorageEnabled(true);
        webBase.setSaveEnabled(true);

        //webBase.setEnabled(false);

        webBase.setWebViewClient(new WebViewClient() {

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String title = view.getTitle();
            }
        });
    }


    @Override
    public void onDestroy() {
        if (webview != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = webview.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webview);
            }

            webview.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            webview.getSettings().setJavaScriptEnabled(false);
            webview.clearHistory();
            webview.clearView();
            webview.removeAllViews();
            webview.destroy();
        }
        super.onDestroy();
    }
}