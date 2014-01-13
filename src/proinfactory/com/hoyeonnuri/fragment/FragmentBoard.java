package proinfactory.com.hoyeonnuri.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.proinlab.hoyeonnuri.adapter.list.BoardListCustomAdapter;
import com.proinlab.hoyeonnuri.adapter.viewpager.BoardViewPagerAdapter;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.parser.BoardParser;
import proinfactory.com.hoyeonnuri.R;

public class FragmentBoard extends SherlockFragment {
    public static final String ARG_URL = "ARG_URL";
    private ClientManager clientManager = new ClientManager();
    private ArrayList<HashMap<Integer, String>> listData;
    private String siteUrl = "http://dormitel.korea.ac.kr/pub/board/bbs_free.html?cboardID=part060301&page=";
    private int currentPage = 1;

    private ListView listView;
    private BoardListCustomAdapter adapter;
    private boolean mLockListView;
    private boolean isThreadActive;
    private LayoutInflater mInflater;
    private View footer;
    private String HtmlString;

    private boolean threadShutDown = false;

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = 1;
        String arr[] = { "http://dormitel.korea.ac.kr/pub/board/bbs_free.html?cboardID=know050101&page=",
                "http://dormitel.korea.ac.kr/pub/board/bbs_free.html?cboardID=part060301&page=",
                "http://dormitel.korea.ac.kr/pub/board/bbs_free.html?cboardID=part060101&page=",
                "http://dormitel.korea.ac.kr/pub/board/bbs_free.html?cboardID=part060201&page=" };
        switch (getArguments().getInt(ARG_URL)) {
            case BoardViewPagerAdapter.FRAGMENT_BOARD_NOTICE:
                siteUrl = arr[0];
                break;
            case BoardViewPagerAdapter.FRAGMENT_BOARD_FREE:
                siteUrl = arr[1];
                break;
            case BoardViewPagerAdapter.FRAGMENT_BOARD_REPAIR:
                siteUrl = arr[2];
                break;
            case BoardViewPagerAdapter.FRAGMENT_BOARD_QNA:
                siteUrl = arr[3];
                break;
        }

        listView = new ListView(getActivity());
        listData = new ArrayList<HashMap<Integer, String>>();

        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = mInflater.inflate(R.layout.footer_ready, null);
        listView.addFooterView(footer);

        adapter = new BoardListCustomAdapter(getActivity(), listData);
        listView.setAdapter(adapter);

        mainThread();

        mLockListView = true;

        listView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int count = totalItemCount - visibleItemCount;
                if (firstVisibleItem >= count && adapter.getCount() != 0 && mLockListView == false) {
                    mLockListView = true;
                    mainThread();
                }
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });

        return listView;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (adapter.getCount() == 0)
                listView.removeFooterView(footer);
            adapter.notifyDataSetChanged();
            isThreadActive = false;
            mLockListView = false;
            currentPage++;
        }
    };

    private boolean mainThread() {
        if (isThreadActive) {
            return false;
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HtmlString = "noSource";
                    while (HtmlString.equals("noSource")) {
                        HtmlString = clientManager.HtmlToString(siteUrl + currentPage, "EUC-KR");
                    }
                    ArrayList<HashMap<Integer, String>> arr = BoardParser.getData(HtmlString, currentPage);
                    for (int i = 0; i < arr.size(); i++)
                        listData.add(arr.get(i));
                    mHandler.post(new Runnable() {
                        public void run() {
                            mHandler.sendEmptyMessage(0);
                        }
                    });
                }
            }).start();
        }
        return true;
    }

}
