package proinfactory.com.hoyeonnuri.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.actionbarsherlock.app.SherlockFragment;
import proinfactory.com.hoyeonnuri.R;

/**
 * Created by PROIN on 13. 5. 17..
 */
public class FragmentLibraryAnam extends SherlockFragment {
    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webView = new WebView(getActivity());

        webView.setWebViewClient(new MyWebClient());
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        WebSettings set = webView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        set.setDomStorageEnabled(true);
        set.setDefaultZoom(WebSettings.ZoomDensity.FAR);

        webView.loadUrl("http://library.korea.ac.kr/html/ko/readingroom.html");

        return webView;
    }

    class MyWebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}