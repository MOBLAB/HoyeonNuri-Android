package proinfactory.com.hoyeonnuri;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * Created by PROIN on 13. 5. 17..
 */
public class LibrarySejongWebView extends SherlockActivity {

    private WebView WebViewController;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_webview);

        String top_title = getIntent().getStringExtra("Intent_Title");
        getSupportActionBar().setTitle(top_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.main_topbar));

        String url = getIntent().getStringExtra("Intent_URL");
        Log.i("TAG", url);

        WebViewController = (WebView)findViewById(R.id.info_webView);
        WebViewController.setWebViewClient(new MyWebClient());
        WebViewController.setHorizontalScrollBarEnabled(false);
        WebViewController.setVerticalScrollBarEnabled(false);

        WebSettings set = WebViewController.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        set.setDomStorageEnabled(true);
        set.setDefaultFontSize(12);

        WebViewController.loadUrl(url);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }

    class MyWebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}