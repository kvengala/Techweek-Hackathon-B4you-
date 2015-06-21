//============================================================================
//
//   Zebra Technologies  - MPACT
//
//  Copyright (c) 2015  Zebra Technologies . All Rights Reserved.
//
//  All information contained herein is the property of Zebra Technologies.  All software
//  within this document, of the software, is distributed on an "AS IS" BASIS, WITHOUT
//  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//
//============================================================================
package com.zebra.mpact.mpactnotify;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zebra.mpact.mpactclient.MPactClient;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       final   Button button;




              ///  Intent intent = new Intent(context, WebViewActivity.class);
                WebView webView;
                webView = (WebView) findViewById(R.id.webView1);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://b4-u.co");
                //startActivity(intent);




        setLabelText(R.id.textViewSDKVersion, MPactClient.getVersion());

        RegionNotifier.getInstanceForApplication(getApplicationContext()).setMainActivity(this);
    }

    @Override
    protected void onDestroy() {
        RegionNotifier.getInstanceForApplication(getApplicationContext()).setMainActivity(null);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*
     * This section holds GUI specific code.
     */

    public void setLabelText(final int textViewId, final String displayString) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView view = (TextView) findViewById(textViewId);
                view.setText(displayString);
            }
        });

    }
}
