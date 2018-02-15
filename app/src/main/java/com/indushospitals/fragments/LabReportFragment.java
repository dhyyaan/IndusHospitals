package com.indushospitals.fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indushospitals.R;
import com.indushospitals.activities.MoreActivity;


public class LabReportFragment extends Fragment {
    private  ProgressDialog progressDialog;
    public LabReportFragment() {
        // Required empty public constructor
    }

    public static LabReportFragment newInstance() {

        return new LabReportFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     final View v =  inflater.inflate(R.layout.fragment_lab_report, container, false);
        MoreActivity.self.moreActivityBinding. back.setVisibility(View.INVISIBLE);
        WebView myWebView = (WebView)v. findViewById(R.id.webview);
        progressDialog = new ProgressDialog(MoreActivity.self);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        myWebView.setWebViewClient(new MyBrowser());
        myWebView.setInitialScale( 100);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDisplayZoomControls(false);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl("http://lab.indusafrica.org/");


        SpannableString s_text_log_report = new SpannableString(getResources().getString(R.string.text_log_report));

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                LinearLayout  llGoWeb = (LinearLayout) v.findViewById(R.id.llGoWeb);
                llGoWeb.setVisibility(View.GONE);

            }
            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };
        s_text_log_report.setSpan(clickableSpan, 0, 10, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(84,180,84));
        s_text_log_report.setSpan(fcs, 0, 10, Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        //For UnderLine
        s_text_log_report.setSpan(new UnderlineSpan(),0,10,0);
        TextView textView = (TextView) v.findViewById(R.id.textViewGoWeb);
        textView.setText(s_text_log_report);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);


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
    @Override
    public void onResume() {
        MoreActivity.self.moreActivityBinding.title.setText(getResources().getString(R.string.text_lab_report));
        super.onResume();
    }

}
