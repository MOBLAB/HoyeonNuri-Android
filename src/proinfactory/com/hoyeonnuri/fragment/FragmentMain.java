package proinfactory.com.hoyeonnuri.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.proinlab.hoyeonnuri.adapter.viewpager.ViewPagerAdapter;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.parser.NoticeParser;
import proinfactory.com.hoyeonnuri.*;

public class FragmentMain extends SherlockFragment {
	private ImageView Menu1, Menu2, Menu3, Menu4;
	private TextView Notice;
	private ScrollView NoticeScroll;

	private AlertDialog.Builder dialog;

	private int displayWidth, displayHeight;

	private String NoticeStr = "";

	private boolean main_popup_bool = false;
	private boolean main_text_bool = false;

	private ClientManager clientManager = new ClientManager();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.main, container, false);
		InitUIRate(v);
		Notice.setText("Loading...");

		try {
			mainThread();
		} catch (Exception e) {
			Notice.setText("네트워크 상태가 원활하지 않습니다.");
		}

		return v;
	}

	private void InitUIRate(View v) {
		RelativeLayout.LayoutParams rlp;

		displayHeight = MainActivity.displayHeight;
		displayWidth = MainActivity.displayWidth;

		Menu1 = (ImageView) v.findViewById(R.id.main_menu1);
		Menu2 = (ImageView) v.findViewById(R.id.main_menu2);
		Menu3 = (ImageView) v.findViewById(R.id.main_menu3);
		Menu4 = (ImageView) v.findViewById(R.id.main_menu4);
		Notice = (TextView) v.findViewById(R.id.main_notice_txt);
		NoticeScroll = (ScrollView) v.findViewById(R.id.main_notice_scroll);

		rlp = new RelativeLayout.LayoutParams(432 * displayWidth / 1000, 263 * displayHeight / 1000);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rlp.setMargins(0, 490 * displayHeight / 1000, 0, 124 * displayHeight / 1000);
		Menu1.setLayoutParams(rlp);

		rlp = new RelativeLayout.LayoutParams(270 * displayWidth / 1000, 323 * displayHeight / 1000);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rlp.setMargins(350 * displayWidth / 1000, 0, 0, 32 * displayHeight / 1000);
		Menu2.setLayoutParams(rlp);

		rlp = new RelativeLayout.LayoutParams(242 * displayWidth / 1000, 323 * displayHeight / 1000);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rlp.setMargins(581 * displayWidth / 1000, 0, 0, 32 * displayHeight / 1000);
		Menu3.setLayoutParams(rlp);

		rlp = new RelativeLayout.LayoutParams(210 * displayWidth / 1000, 323 * displayHeight / 1000);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rlp.setMargins(790 * displayWidth / 1000, 0, 0, 32 * displayHeight / 1000);
		Menu4.setLayoutParams(rlp);

		rlp = new RelativeLayout.LayoutParams(796 * displayWidth / 1000, 333 * displayHeight / 1000);
		rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		rlp.setMargins(113 * displayWidth / 1000, 0, 0, 470 * displayHeight / 1000);
		NoticeScroll.setLayoutParams(rlp);

		Menu1.setOnClickListener(menuOnClickListener);
		Menu2.setOnClickListener(menuOnClickListener);
		Menu3.setOnClickListener(menuOnClickListener);
		Menu4.setOnClickListener(menuOnClickListener);
	}

	private android.view.View.OnClickListener menuOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
            Intent intent;
			switch (v.getId()) {
			case R.id.main_menu1:
                intent = new Intent(getActivity(), BoardActivity.class);
                intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_NOTICE);
                startActivity(intent);
				break;
			case R.id.main_menu2:
                intent = new Intent(getActivity(), RestaurantActivity.class);
                intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_JINLI);
                startActivity(intent);
				break;
			case R.id.main_menu3:
                intent = new Intent(getActivity(), InfoActivity.class);
                intent.putExtra(InfoActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_INFO_SUTTLE);
                startActivity(intent);
				break;
			case R.id.main_menu4:
                intent = new Intent(getActivity(), DeliveryActivity.class);
                startActivity(intent);
				break;
			}
		}
	};

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
			Dialog_Web.loadUrl("http://mob.korea.ac.kr/hoyeonnuri/popup_notice/notice_main_html.html");
			dialog.setView(layout);
			dialog.show();
		}
	}

	private class MyWebClient extends WebViewClient {
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			Notice.setText(NoticeStr);
			if (MainActivity.dialogCount == 0)
				noticeDialog(main_popup_bool);
			MainActivity.dialogCount++;
		}
	};

	private void mainThread() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				String noticeBoolSource = clientManager.HtmlToString("http://mob.korea.ac.kr/hoyeonnuri/popup_notice/notice_bool.txt", "UTF-8");
				boolean b[] = NoticeParser.checkNotice(noticeBoolSource);
				main_popup_bool = b[NoticeParser.NOTICE_MAIN];
				main_text_bool = b[NoticeParser.NOTICE_MAINTXT];

				NoticeStr = "noSource";
				if (!main_text_bool) {
					while (NoticeStr.equals("noSource"))
						NoticeStr = clientManager.HtmlToString("http://dormitel.korea.ac.kr/main.html", "EUC-KR");
					NoticeStr = NoticeParser.getNoticeText(NoticeStr);
				} else {
					while (NoticeStr.equals("noSource"))
						NoticeStr = clientManager.HtmlToString("http://mob.korea.ac.kr/hoyeonnuri/popup_notice/notice_main_text.txt", "UTF-8");
				}

				mHandler.post(new Runnable() {
					public void run() {
						mHandler.sendEmptyMessage(0);
					}
				});
			}
		}).start();
	}

}
