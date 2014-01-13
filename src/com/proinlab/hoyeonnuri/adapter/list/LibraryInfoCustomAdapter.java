package com.proinlab.hoyeonnuri.adapter.list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.proinlab.hoyeonnuri.parser.LibraryParser;
import proinfactory.com.hoyeonnuri.LibrarySejongWebView;
import proinfactory.com.hoyeonnuri.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by PROIN on 13. 5. 17..
 */
public class LibraryInfoCustomAdapter extends BaseAdapter implements View.OnClickListener {
    Context maincon;
    LayoutInflater Inflater;
    ArrayList<HashMap<Integer, String>>  arSrc;
    int layout;

    public LibraryInfoCustomAdapter(Context context, ArrayList<HashMap<Integer, String>> aarSrc) {
        maincon = context;
        Inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        arSrc = aarSrc;
        layout = R.layout.library_list_contents;
    }

    public int getCount() {
        return arSrc.size();
    }

    public HashMap<Integer, String> getItem(int position) {
        return arSrc.get(position);
    }

    public long getItemId(int position) {
        return position;
    }


    // 각 항목의 뷰 생성
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = Inflater.inflate(layout, parent, false);
        }
        TextView title = (TextView)convertView.findViewById(R.id.listview_contents_title);
        title.setText(arSrc.get(position).get(LibraryParser.KEY2_LIBRARY_NAME));

        TextView using = (TextView)convertView.findViewById(R.id.listview_contents_using);
        using.setText("사용 좌석수 : "+arSrc.get(position).get(LibraryParser.KEY4_USEING_SPACE));

        TextView all = (TextView)convertView.findViewById(R.id.listview_contents_all);
        all.setText("전체 좌석수 : "+arSrc.get(position).get(LibraryParser.KEY3_ENTIRE_SPACE));

        TextView rest = (TextView)convertView.findViewById(R.id.listview_contents_rest);
        rest.setText("잔여 좌석수 : "+arSrc.get(position).get(LibraryParser.KEY5_REMAIN_SPACE));

        TextView rate = (TextView)convertView.findViewById(R.id.listview_contents_rate);
        rate.setText("이용률 : "+arSrc.get(position).get(LibraryParser.KEY6_RATE)+"%");

        convertView.setTag(position);
        convertView.setOnClickListener(this);

        return convertView;
    }


    public void onClick(View v)
    {
        int position = (Integer) v.getTag();
        HashMap<Integer, String> data = getItem(position);
        Bundle extras = new Bundle();
        String liburl = "http://163.152.221.20/"+data.get(LibraryParser.KEY1_BOARD_URL);
        extras.putString("Intent_URL", liburl);
        extras.putString("Intent_Title", data.get(LibraryParser.KEY2_LIBRARY_NAME));
        Intent intent = new Intent(maincon, LibrarySejongWebView.class);
        intent.putExtras(extras);
        maincon.startActivity(intent);

    }
}