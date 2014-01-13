package proinfactory.com.hoyeonnuri.fragment;

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
import com.proinlab.hoyeonnuri.adapter.list.RestaurantNoticeCustomAdapter;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.parser.RestaurantBoardParser;
import proinfactory.com.hoyeonnuri.R;

import java.util.ArrayList;
import java.util.HashMap;

public class FragmentRestaurantBoard extends SherlockFragment {
    public static final String ARG_URL = "ARG_URL";
    private ClientManager clientManager = new ClientManager();
    private ArrayList<HashMap<Integer, String>> listData;
    private String siteUrl = "http://sejong.welstory.com/sejong/notice/notice_list.jsp?pg=";
    private int currentPage = 1;

    private ListView listView;
    private RestaurantNoticeCustomAdapter adapter;
    private boolean mLockListView;
    private boolean isThreadActive;
    private LayoutInflater mInflater;
    private View footer;
    private String HtmlString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentPage = 1;

        listView = new ListView(getActivity());
        listData = new ArrayList<HashMap<Integer, String>>();

        mInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footer = mInflater.inflate(R.layout.footer_ready, null);
        listView.addFooterView(footer);

        adapter = new RestaurantNoticeCustomAdapter(getActivity(), listData);

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
            listView.removeFooterView(footer);
            adapter.notifyDataSetChanged();
            isThreadActive = true;
            mLockListView = true;
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
                    ArrayList<HashMap<Integer, String>> arr = RestaurantBoardParser.getData(HtmlString, currentPage);
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
