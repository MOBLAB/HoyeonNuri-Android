package com.proinlab.hoyeonnuri.adapter.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.proinlab.hoyeonnuri.parser.RestaurantBoardParser;
import com.proinlab.hoyeonnuri.parser.RestaurantBoardParser;
import proinfactory.com.hoyeonnuri.BoardWebView;
import proinfactory.com.hoyeonnuri.R;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantNoticeCustomAdapter extends BaseAdapter implements OnClickListener {
	Context maincon;
	LayoutInflater Inflater;
	ArrayList<HashMap<Integer, String>> arSrc;
	int layout;

	public RestaurantNoticeCustomAdapter(Context context, ArrayList<HashMap<Integer, String>> aarSrc) {
		maincon = context;
		Inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		arSrc = aarSrc;
		layout = R.layout.listview_contents;
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

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = Inflater.inflate(layout, parent, false);
		}
		TextView title = (TextView) convertView.findViewById(R.id.listview_contents_title);
		title.setText(arSrc.get(position).get(RestaurantBoardParser.KEY2_TITLE));

        TextView date = (TextView) convertView.findViewById(R.id.listview_contents_date);
		date.setText(arSrc.get(position).get(RestaurantBoardParser.KEY4_DATE));

		TextView writer = (TextView) convertView.findViewById(R.id.listview_contents_writer);
		writer.setText(arSrc.get(position).get(RestaurantBoardParser.KEY3_WRITER));

		TextView boardno = (TextView) convertView.findViewById(R.id.listview_contents_boardno);
		boardno.setText(arSrc.get(position).get(RestaurantBoardParser.KEY5_BOARDNO));

		TextView comment = (TextView) convertView.findViewById(R.id.listview_contents_comment);
        comment.setVisibility(View.GONE);

		ImageView next = (ImageView) convertView.findViewById(R.id.board_next);
		ImageView secretkey = (ImageView) convertView.findViewById(R.id.board_secretkey);
		if (arSrc.get(position).get(RestaurantBoardParser.KEY6_IS_SECRET).equals("비밀글")) {
			secretkey.setVisibility(View.VISIBLE);
			next.setVisibility(View.INVISIBLE);
		} else {
			next.setVisibility(View.VISIBLE);
			secretkey.setVisibility(View.INVISIBLE);
		}

		ImageView notice = (ImageView) convertView.findViewById(R.id.board_notice);
		if (arSrc.get(position).get(RestaurantBoardParser.KEY7_IS_NOTICE).equals("공지글")) {
			notice.setVisibility(View.VISIBLE);
			boardno.setVisibility(View.INVISIBLE);
		} else {
			boardno.setVisibility(View.VISIBLE);
			notice.setVisibility(View.INVISIBLE);
		}

		convertView.setTag(position);
		convertView.setOnClickListener(this);

		return convertView;
	}

	public void onClick(View v) {
		int position = (Integer) v.getTag();
		HashMap<Integer, String> data = getItem(position);
		Bundle extras = new Bundle();
		extras.putString("Intent_URL", data.get(RestaurantBoardParser.KEY1_BOARD_URL));
		extras.putString("Intent_Title", data.get(RestaurantBoardParser.KEY2_TITLE));
		extras.putString("Intent_Secret", data.get(RestaurantBoardParser.KEY6_IS_SECRET));
		extras.putString("Intent_writer", data.get(RestaurantBoardParser.KEY3_WRITER));
		extras.putString("Intent_date", data.get(RestaurantBoardParser.KEY4_DATE));
        extras.putString("BOARD_TYPE", "RES_BOARD");
		Intent intent = new Intent(maincon, BoardWebView.class);
		intent.putExtras(extras);
		maincon.startActivity(intent);

	}
}