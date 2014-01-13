package proinfactory.com.hoyeonnuri;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.parser.HtmlParser;

import java.io.IOException;

/**
 * Created by PROIN on 13. 5. 17..
 */
public class BoardWebView extends SherlockActivity {
    private ClientManager clientManager = new ClientManager();
    private WebView WebViewController;
    private ProgressBar mPBar;
    private View Prepage;
    private String HtmlSource;
    private String board_no;
    private String board_writer;
    private String board_date;
    private int secret_test;
    private String currentType = "BOARD";

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Update();
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_webview);

        Prepage = (View) findViewById(R.id.board_prepage);

        WebViewController = (WebView) findViewById(R.id.board_webviewctrl_web);
        WebViewController.setWebViewClient(new MyWebClient());
        WebViewController.setHorizontalScrollBarEnabled(false);
        WebViewController.setVerticalScrollBarEnabled(false);

        WebSettings set = WebViewController.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(true);
        set.setDomStorageEnabled(true);
        set.setDefaultZoom(WebSettings.ZoomDensity.FAR);

        mPBar = (ProgressBar) findViewById(R.id.webviewctrl_pBar);

        WebViewController.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    mPBar.setVisibility(ProgressBar.VISIBLE);
                } else if (progress == 100) {
                    mPBar.setVisibility(ProgressBar.GONE);
                }
                mPBar.setProgress(progress);
            }
        });

        String top_title = getIntent().getStringExtra("Intent_Title");
        getSupportActionBar().setTitle(top_title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.main_topbar));

        board_no = getIntent().getStringExtra("Intent_URL");
        board_date = getIntent().getStringExtra("Intent_date");
        TextView sub_date = (TextView) findViewById(R.id.subbar_date);
        sub_date.setText(board_date);
        board_writer = getIntent().getStringExtra("Intent_writer");
        TextView sub_writer = (TextView) findViewById(R.id.subbar_writer);
        sub_writer.setText(board_writer);

        currentType = getIntent().getStringExtra("BOARD_TYPE");

        open();
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

    private void Update() {
        // 비밀글일경우
        if (secret_test != -1) {
            WebViewController.loadDataWithBaseURL(null, HtmlSource, "text/html", "utf-8", null);
            Prepage.setVisibility(View.INVISIBLE);
            WebViewController.setVisibility(View.VISIBLE);
        }
        // 일반글일경우
        else {
            WebViewController.loadDataWithBaseURL(null, HtmlSource, "text/html", "utf-8", null);
            Prepage.setVisibility(View.INVISIBLE);
            WebViewController.setVisibility(View.VISIBLE);
        }
    }

    void open() {
        try {
            process();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void process() throws IOException {
        final Handler mHandler = new Handler();
        new Thread() {
            @Override
            public void run() {
                mHandler.post(new Runnable() {
                    public void run() {
                    }
                });

                if(currentType.equals("BOARD"))
                    parseBoardType();
                else
                    parseResType();



                mHandler.post(new Runnable() {
                    public void run() {
                        handler.sendEmptyMessage(0);
                    }
                });
            }
        }.start();
    }

    private void parseBoardType() {
        // 데이터 로딩
        HtmlSource = clientManager.HtmlToString("http://dormitel.korea.ac.kr/pub/board/bbs_free_read.html?" + board_no);
        String board_data = clientManager.HtmlToString("http://dormitel.korea.ac.kr/pub/board/read.html?" + board_no);

        secret_test = 0;
        secret_test = HtmlSource.indexOf("이글은 비밀글입니다");

        // 비밀글 필터링
        if (secret_test != -1) {
            HtmlSource = "<br><br><br>이글은 비밀글입니다.<br>작성자와 관리자만 열람가능합니다.<br>로그인을 해주세요.";
            HtmlSource = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
                    + HtmlSource;
        }

        else {
            // 댓글 없을때 실행
            if (HtmlSource.indexOf("<font color=bc8d3c><b>") == -1)
                HtmlSource = board_data;
                // 댓글 있을때 실행
            else {
                String TempSource = HtmlSource;
                String comment_writer = null, comment_data = null, comment_date = null;
                // 댓글 갯수
                int comment_count = 1;
                while (TempSource.indexOf("<font color=bc8d3c><b>") < TempSource.lastIndexOf("<font color=bc8d3c><b>")) {
                    comment_count++;
                    TempSource = HtmlParser.getStartLocation(TempSource, "<font color=bc8d3c><b>");
                }

                // 댓글 내용 파싱
                TempSource = HtmlSource;
                HtmlSource = "<html> <head> <link rel=\"stylesheet\" type=\"text/css\" href=\"http://115.88.201.43/hoyeonnuri/style.css\" title=\"web\" /> <body> board_data <table width=\"100%c\" border=\"0\" align=\"left\" cellspacing=\"0\"> <tr> <td> <br>";
                HtmlSource = "<meta http-equiv='Content-Type' content='text/html; charset=utf-8' />"
                        + HtmlSource.replace("board_data", board_data);
                for (int i = 0; i < comment_count; i++) {
                    TempSource = HtmlParser.getStartLocation(TempSource, "<font color=bc8d3c><b>");
                    comment_writer = HtmlParser.getParsedData(TempSource, "<b>", "</b>");
                    TempSource = HtmlParser.getStartLocation(TempSource, "<td class=\"text_small2\"");
                    comment_data = HtmlParser.getParsedData(TempSource, "valign=\"middle\">", "</td>");
                    comment_date = HtmlParser.getParsedData(TempSource, "<font color=\"333333\">", "</font>");

                    String comment_html = "<b>&nbsp;&nbsp;"
                            + comment_writer + "</b>&nbsp;&nbsp;"
                            + comment_date
                            + "<br><div class=\"comment\">"
                            + comment_data + "</div><br>";

                    HtmlSource = HtmlSource + comment_html;
                }
                HtmlSource = HtmlSource
                        + "</td> <tr> </table> </body> </html>";
            }
        }
    }

    private void parseResType() {
        // 데이터 로딩
        HtmlSource = clientManager.HtmlToString(board_no);
        String board_data = clientManager.HtmlToString(board_no);

        HtmlSource = board_data;
        HtmlSource = HtmlParser.getParsedData(HtmlSource,"<!-- 내용 -->","<!-- [s]이전글/다음글 -->");
        HtmlSource = HtmlParser.getParsedData(HtmlSource,"<HTML>","</HTML>");
    }
}