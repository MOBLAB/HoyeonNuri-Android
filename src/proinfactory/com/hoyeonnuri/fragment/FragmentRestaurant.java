package proinfactory.com.hoyeonnuri.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockFragment;
import com.proinlab.hoyeonnuri.adapter.viewpager.RestaurantViewPagerAdapter;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.parser.NoticeParser;
import com.proinlab.hoyeonnuri.parser.RestaurantMenuParser;
import proinfactory.com.hoyeonnuri.MainActivity;
import proinfactory.com.hoyeonnuri.R;

public class FragmentRestaurant extends SherlockFragment {
    private static final int RES_THIS_WEEK = 0;
    private static final int RES_NEXT_WEEK = 1;
    private static final int RES_NOTICE = 2;

    public static final String ARG_URL = "ARG_URL";
    private ClientManager clientManager = new ClientManager();

    private WebView restaurantWebView;
    private WebView restaurantBar;
    private ProgressBar mPBar;
    private String timetableUrl = "http://mob.korea.ac.kr/hoyeonnuri/popup_notice/restaurant_timetable.html";
    private String resUrl[];
    private String htmlSource;
    private View prePage;
    private  View v;

    private String currentRestaurant;

    private Button resBtn1;
    private Button resBtn2;
    private Button resBtn3;

    private Builder dialog;
    private boolean noticeBoolean;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String arr[][] = {
                { "http://sejong.welstory.com/sejong/menu/menu_01.jsp?beforeWeek=&nextWeek=&beforeWeek2=&nextWeek2=&cate=b&hall_no=E53O",
                        "http://sejong.welstory.com/sejong/menu/menu_01.jsp?beforeWeek=&nextWeek=&beforeWeek2=&nextWeek2=&cate=c&hall_no=E53O",
                        "" },
                { "http://sejong.welstory.com/sejong/menu/menu_01.jsp?beforeWeek=&nextWeek=&beforeWeek2=&nextWeek2=&cate=b&hall_no=E53R",
                        "http://sejong.welstory.com/sejong/menu/menu_01.jsp?beforeWeek=&nextWeek=&beforeWeek2=&nextWeek2=&cate=c&hall_no=E53R",
                        "http://mob.korea.ac.kr/hoyeonnuri/popup_notice/notice_rest1_html.html" },
                { "http://sejong.welstory.com/sejong/menu/menu_01.jsp?beforeWeek=&nextWeek=&beforeWeek2=&nextWeek2=&cate=b&hall_no=E53S",
                        "http://sejong.welstory.com/sejong/menu/menu_01.jsp?beforeWeek=&nextWeek=&beforeWeek2=&nextWeek2=&cate=c&hall_no=E53S",
                        "" }
        };

        switch (getArguments().getInt(ARG_URL)) {
            case RestaurantViewPagerAdapter.FRAGMENT_RESTAURANT_JINLI:
                resUrl = arr[0];
                currentRestaurant = "진리관";
                break;
            case RestaurantViewPagerAdapter.FRAGMENT_RESTAURANT_HOYEON:
                resUrl = arr[1];
                currentRestaurant = "호연4관";
                break;
            case RestaurantViewPagerAdapter.FRAGMENT_RESTAURANT_STUDENT:
                currentRestaurant = "학생회관";
                resUrl = arr[2];
                break;
        }
        noticeThread();

        v = inflater.inflate(R.layout.restaurant, container, false);
        resBtn1 = (Button) v.findViewById(R.id.res_this);
        resBtn2 = (Button) v.findViewById(R.id.res_next);
        resBtn3 = (Button) v.findViewById(R.id.restaurant_time);

        resBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resBtnClick(view);
            }
        });

        resBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resBtnClick(view);
            }
        });

        resBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resBtnClick(view);
            }
        });

        prePage = v.findViewById(R.id.Res_prepage);

        restaurantWebView = (WebView) v.findViewById(R.id.webviewctrl_web);
        restaurantWebView.setWebViewClient(new MyWebClient());
        WebSettings set = restaurantWebView.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(false);

        restaurantBar = (WebView) v.findViewById(R.id.restaurant_bar);
        restaurantBar.setWebViewClient(new MyWebClient());

        String res_title = "<html> <head> <meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=euc-kr\" /> " +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"http://mob.korea.ac.kr/hoyeonnuri/style_title.css\" title=\"web\" /> " +
                "</head> <body><div class=\"menu_box\"><p class=\"date\">날짜</p><div class=\"menu\"><p class=\"breakfast\">아침</p>" +
                "<p class=\"lunch\">점심</p><p class=\"dinner\">저녁</p></div></body></html>";
        restaurantBar.loadDataWithBaseURL(null, res_title, "text/html", "utf-8", null);

        mPBar = (ProgressBar) v.findViewById(R.id.webviewctrl_pBar);
        restaurantWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    mPBar.setVisibility(ProgressBar.VISIBLE);
                } else if (progress == 100) {
                    mPBar.setVisibility(ProgressBar.GONE);
                }
                mPBar.setProgress(progress);
            }
        });

        menuThread(resUrl[RES_THIS_WEEK]);

        return v;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private Handler noticeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (currentRestaurant.equals("진리관")) {
                if (MainActivity.resDialogJinliCount == 0)
                    noticeDialog(noticeBoolean);
                MainActivity.resDialogJinliCount++;
            } else if (currentRestaurant.equals("호연4관")) {
                if (MainActivity.resDialogHoyeonCount == 0)
                    noticeDialog(noticeBoolean);
                MainActivity.resDialogHoyeonCount++;
            } else if (currentRestaurant.equals("학생회관")) {
                if (MainActivity.resDialogStudentCount == 0)
                    noticeDialog(noticeBoolean);
                MainActivity.resDialogStudentCount++;
            }
        }
    };

    private boolean noticeThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String noticeBoolSource = clientManager.HtmlToString("http://mob.korea.ac.kr/hoyeonnuri/popup_notice/notice_bool.txt", "UTF-8");
                boolean b[] = NoticeParser.checkNotice(noticeBoolSource);
                if (currentRestaurant.equals("진리관"))
                    noticeBoolean = b[NoticeParser.NOTICE_JINLI];
                else if (currentRestaurant.equals("호연4관"))
                    noticeBoolean = b[NoticeParser.NOTICE_HOYEON];
                else if (currentRestaurant.equals("학생회관"))
                    noticeBoolean = b[NoticeParser.NOTICE_REST4];

                noticeHandler.post(new Runnable() {
                    public void run() {
                        noticeHandler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
        return true;
    }

    @SuppressWarnings("static-access")
    private void noticeDialog(boolean bool) {
        if (bool) {
            dialog = new AlertDialog.Builder(getActivity());
            dialog.setPositiveButton("확인", new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.setTitle("공지");

            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup_dialog, null);

            final WebView Dialog_Web = (WebView) layout.findViewById(R.id.dialog_webv);
            Dialog_Web.setWebViewClient(new MyWebClient());
            WebSettings dia_set = Dialog_Web.getSettings();
            dia_set.setBuiltInZoomControls(true);
            Dialog_Web.loadUrl(resUrl[RES_NOTICE]);
            dialog.setView(layout);
            dialog.show();
        }
    }

    private Handler menuHandler = new Handler() {
        public void handleMessage(Message msg) {
            restaurantWebView.loadDataWithBaseURL(null, htmlSource, "text/html", "utf-8", null);
            prePage.setVisibility(View.INVISIBLE);
            restaurantWebView.setVisibility(View.VISIBLE);
        }
    };

    private boolean menuThread(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                htmlSource = "noSource";
                while (htmlSource.equals("noSource")) {
                    htmlSource = clientManager.HtmlToString(url, "EUC-KR");
                }

                try {
                    htmlSource = RestaurantMenuParser.getData(htmlSource);
                } catch (Exception e) {

                }

                menuHandler.post(new Runnable() {
                    public void run() {
                        menuHandler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
        return true;
    }

    public void resBtnClick(View v) {
        switch (v.getId()) {
            case R.id.res_this:
                Log.i("TAG","THIS");
                prePage.setVisibility(View.VISIBLE);
                menuThread(resUrl[RES_THIS_WEEK]);
                resBtn1.setBackgroundResource(R.drawable.listrowbg);
                resBtn2.setBackgroundResource(R.drawable.listview_focus);
                resBtn3.setBackgroundResource(R.drawable.listview_focus);
                restaurantBar.setVisibility(View.VISIBLE);
                break;
            case R.id.res_next:
                Log.i("TAG","NEXT");
                prePage.setVisibility(View.VISIBLE);
                menuThread(resUrl[RES_NEXT_WEEK]);
                resBtn1.setBackgroundResource(R.drawable.listview_focus);
                resBtn2.setBackgroundResource(R.drawable.listrowbg);
                resBtn3.setBackgroundResource(R.drawable.listview_focus);
                restaurantBar.setVisibility(View.VISIBLE);
                break;
            case R.id.restaurant_time:
                Log.i("TAG","TIME");
                restaurantWebView.loadUrl(timetableUrl);
                resBtn1.setBackgroundResource(R.drawable.listview_focus);
                resBtn2.setBackgroundResource(R.drawable.listview_focus);
                resBtn3.setBackgroundResource(R.drawable.listrowbg);
                restaurantBar.setVisibility(View.GONE);
                break;
        }
    }

    class MyWebClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
