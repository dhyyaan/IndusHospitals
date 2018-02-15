package com.indushospitals.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;


/**
 * Created by think360 on 19/07/17.
 */

public class SocialMediaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private  String social_media;
    private  ProgressDialog progressDialog;

    public SocialMediaFragment() {
        // Required empty public constructor
    }

    public static SocialMediaFragment newInstance(String param1) {
        SocialMediaFragment fragment = new SocialMediaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            social_media = getArguments().getString(ARG_PARAM1);
        }
       MoreActivity.self .selMore=false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_social_media, container, false);

        MoreActivity.self.moreActivityBinding.back.setVisibility(View.VISIBLE);
        MoreActivity.self.moreActivityBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoreActivity.self.onBackPressed();

            }
        });

        progressDialog = new ProgressDialog(MoreActivity.self);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        WebView myWebView = (WebView) v.findViewById(R.id.webview);
        myWebView.setWebViewClient(new MyBrowser());
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl(social_media);

        return v;
    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(MoreActivity.self, "Error:" + description, Toast.LENGTH_SHORT).show();

        }
    }

}