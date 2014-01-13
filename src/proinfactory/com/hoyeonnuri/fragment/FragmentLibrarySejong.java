package proinfactory.com.hoyeonnuri.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockFragment;
import com.proinlab.hoyeonnuri.adapter.list.LibraryInfoCustomAdapter;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.parser.LibraryParser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PROIN on 13. 5. 17..
 */
public class FragmentLibrarySejong extends SherlockFragment {

    private ListView listView;
    private LibraryInfoCustomAdapter adapter;
    private ArrayList<HashMap<Integer, String>> datalist = new ArrayList<HashMap<Integer, String>>();
    private ClientManager clientManager = new ClientManager();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        listView = new ListView(getActivity());

        loadThread.start();

        return listView;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            adapter = new LibraryInfoCustomAdapter(getActivity(), datalist);
            listView.setAdapter(adapter);
        }
    };

    private Thread loadThread = new Thread(new Runnable() {
        @Override
        public void run() {
            String htmlSource = clientManager.HtmlToString("http://163.152.221.20/domian5.asp");
            datalist = LibraryParser.parsing_process(htmlSource);
            mHandler.post(new Runnable() {
                public void run() {
                    mHandler.sendEmptyMessage(0);
                }
            });
        }
    });

}

