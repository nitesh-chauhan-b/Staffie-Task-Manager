package com.managmnet.staffie.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.managmnet.staffie.R;

import java.io.File;
import java.net.URLEncoder;
import java.security.cert.Extension;

import javax.security.auth.callback.Callback;

public class Uploaded_PDF_View extends AppCompatActivity {
    private static final String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";
    WebView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_pdf_view);

        pdfView = findViewById(R.id.uploaded_pdf_view);

        pdfView.getSettings().setJavaScriptEnabled(true);
        pdfView.getSettings().setPluginState(WebSettings.PluginState.ON);

        String filename = getIntent().getStringExtra("file_name");
        String fileUrl = getIntent().getStringExtra("file_url");

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(filename);
        progressDialog.setMessage("Opening...");


//


        pdfView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.hide();
            }
        });


        String url = "";
        String encodedURL="";
        try {
            url = URLEncoder.encode(fileUrl, "utf-8");
        } catch (Exception ex) {

            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
//        pdfView.setWebViewClient(new Callback());

        pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);

//        pdfView.loadUrl("http://docs.google.com/viewer?url=\" + url + \"&embedded=true");

    }

//    private class Callback extends WebViewClient {
//        @Override
//        public boolean shouldOverrideUrlLoading(
//                WebView view, String url) {
//            return (false);
//        }
//    }

}
