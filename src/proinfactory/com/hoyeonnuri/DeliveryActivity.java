package proinfactory.com.hoyeonnuri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.proinlab.hoyeonnuri.adapter.list.DeliveryCustomAdapter;
import com.proinlab.hoyeonnuri.adapter.viewpager.BoardViewPagerAdapter;
import com.proinlab.hoyeonnuri.adapter.viewpager.ViewPagerAdapter;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.manager.LoginManager;
import com.proinlab.hoyeonnuri.parser.DeliveryParser;

import java.util.ArrayList;

public class DeliveryActivity extends SlidingDrawerActivity {

    private DeliveryCustomAdapter adapter;
    private ListView listView;
    private ClientManager clientManager = new ClientManager();
    private ArrayList<String[]> listData;

    private AutoCompleteTextView SearchTextView;
    private Button SearchBtn;

    private AlertDialog.Builder dialog;

    private boolean isSearching = false;

    @Override
    public void onCreate(Bundle inState) {
        super.onCreate(inState);
        setContentView(R.layout.delivery_list);
        setOnClick();

        listView = (ListView) findViewById(R.id.delivery_listview);

        SearchTextView = (AutoCompleteTextView) findViewById(R.id.delivery_autosearch);
        SearchBtn = (Button) findViewById(R.id.delivery_searchbtn);

        mainThread();

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            adapter = new DeliveryCustomAdapter(DeliveryActivity.this, listData);
            listView.setAdapter(adapter);

            String[] col = new String[listData.size()];
            for (int i = 0; i < listData.size(); i++)
                col[i] = listData.get(i)[DeliveryParser.NAME];
            ArrayAdapter sAdapter = new ArrayAdapter(DeliveryActivity.this, android.R.layout.simple_dropdown_item_1line, col);
            SearchTextView.setAdapter(sAdapter);
            SearchTextView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            SearchTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId,
                                              KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        String Searching_name = SearchTextView.getText().toString();
                        adapter = new DeliveryCustomAdapter(DeliveryActivity.this, findDataByName(Searching_name));
                        listView.setAdapter(adapter);
                        SearchTextView.setText("");
                        isSearching = true;
                        SearchTextView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                keyboard.hideSoftInputFromWindow(SearchTextView.getWindowToken(), 0);
                            }
                        }, 200);
                        return true;
                    }
                    return false;
                }
            });
            SearchTextView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    keyboard.hideSoftInputFromWindow(SearchTextView.getWindowToken(), 0);
                }
            }, 200);

            SearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSearching = true;
                    String Searching_name = SearchTextView.getText().toString();
                    adapter = new DeliveryCustomAdapter(DeliveryActivity.this, findDataByName(Searching_name));
                    listView.setAdapter(adapter);
                    SearchTextView.setText("");
                    SearchTextView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager keyboard = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            keyboard.hideSoftInputFromWindow( SearchTextView.getWindowToken(), 0);
                        }
                    }, 200);
                }
            });
        }
    };

    private ArrayList<String[]> findDataByName(String name) {
        ArrayList<String[]> result = new ArrayList<String[]>();
        for(int i=0;i<listData.size();i++)
            if(listData.get(i)[DeliveryParser.NAME].contains(name))
                result.add(listData.get(i));
        return result;
    }

    private void mainThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String xml = clientManager.HtmlToString("http://mob.korea.ac.kr/hoyeonnuri/delivery/delivery_db.xml", "utf-8");
                listData = DeliveryParser.DataBaseXMLParse(xml);
                Log.i("TAG", Integer.toString(listData.size()) + " - data size");
                listData = sortData(listData);

                mHandler.post(new Runnable() {
                    public void run() {
                        mHandler.sendEmptyMessage(0);
                    }
                });
            }
        }).start();
    }

    private ArrayList<String[]> sortData(ArrayList<String[]> data) {
        ArrayList<String[]> returnDATA = data;
        String[] tmp;

        for (int i = 0; i < returnDATA.size(); i++) {
            for (int j = i + 1; j < returnDATA.size(); j++) {
                if (Integer.parseInt(returnDATA.get(i)[DeliveryParser.SPONSER]) < Integer.parseInt(returnDATA.get(j)[DeliveryParser.SPONSER])) {
                    tmp = returnDATA.get(i);
                    returnDATA.set(i, returnDATA.get(j));
                    returnDATA.set(j, tmp);
                }
            }
        }
        return returnDATA;
    }

    private void setOnClick() {
        findViewById(R.id.drawer_home).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_devinfo).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_board_free).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_board_notice).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_board_qna).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_board_repair).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_restaurant_notice).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_restaurant_1).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_restaurant_2).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_restaurant_3).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_info_bus).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_info_sejong_library).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_info_anam_library).setOnClickListener(onClickListener);
        findViewById(R.id.drawer_delivery).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.drawer_home:
                    finish();
                    break;
                case R.id.drawer_devinfo:
                    finish();
                    break;
                case R.id.drawer_board_notice:
                    intent = new Intent(DeliveryActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_NOTICE);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_board_free:
                    intent = new Intent(DeliveryActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_FREE);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_board_repair:
                    intent = new Intent(DeliveryActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_REPAIR);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_board_qna:
                    intent = new Intent(DeliveryActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_QNA);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_notice:
                    intent = new Intent(DeliveryActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_NOTICE);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_1:
                    intent = new Intent(DeliveryActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_JINLI);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_2:
                    intent = new Intent(DeliveryActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_HOYEON);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_3:
                    intent = new Intent(DeliveryActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_STUDENT);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_info_bus:
                    intent = new Intent(DeliveryActivity.this, InfoActivity.class);
                    intent.putExtra(InfoActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_INFO_SUTTLE);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_info_sejong_library:
                    intent = new Intent(DeliveryActivity.this, InfoActivity.class);
                    intent.putExtra(InfoActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_INFO_LIBRARY_SEJONG);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_info_anam_library:
                    intent = new Intent(DeliveryActivity.this, InfoActivity.class);
                    intent.putExtra(InfoActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_INFO_LIBRARY_ANAM);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_delivery:
                    break;
            }
            mMenuDrawer.closeMenu();
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent msg) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isSearching) {
                adapter = new DeliveryCustomAdapter(DeliveryActivity.this, listData);
                listView.setAdapter(adapter);
                isSearching = false;
            } else {
                finish();
            }
            return false;
        }
        return super.onKeyDown(keyCode, msg);
    }

    private boolean loginStat;
    private boolean isLoginIng;

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(isSearching) {
                    adapter = new DeliveryCustomAdapter(DeliveryActivity.this, listData);
                    listView.setAdapter(adapter);
                    isSearching = false;
                } else {
                    mMenuDrawer.toggleMenu();
                }
                return true;
            case R.id.menu_settings:
                if(isLoginIng) {
                    Toast.makeText(this, "로그인이 진행중입니다.", Toast.LENGTH_LONG).show();
                    return true;
                }

                new Thread() {
                    @Override
                    public void run() {
                        isLoginIng = true;
                        loginStat = new LoginManager().checkLogin();
                        handler.post(new Runnable() {
                            public void run() {
                                handler.sendEmptyMessage(3);
                            }
                        });
                    }
                }.start();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private EditText id;
    private EditText pw;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    isLoginIng = false;
                    break;
                case 1:
                    Toast.makeText(DeliveryActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                    isLoginIng = false;
                    break;
                case 2:
                    Toast.makeText(DeliveryActivity.this, "로그아웃", Toast.LENGTH_LONG).show();
                    isLoginIng = false;
                    break;
                case 3:
                    loginDialog(loginStat);
                    break;
            }

        }
    };

    private void loginDialog(boolean bool) {
        if(!bool) {
            dialog = new AlertDialog.Builder(this);

            dialog.setTitle("로그인");

            LayoutInflater inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.login_dialog, null);

            id = (EditText) layout.findViewById(R.id.login_id);
            pw = (EditText) layout.findViewById(R.id.login_pw);

            CheckBox auto = (CheckBox) layout.findViewById(R.id.login_autologin);
            auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b) {
                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("SAVED_ID", id.getText().toString());
                        editor.putString("SAVED_PW", pw.getText().toString());
                        editor.putBoolean("AUTO_LOGIN", b);
                        editor.commit();
                    } else {
                        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("SAVED_ID", "");
                        editor.putString("SAVED_PW", "");
                        editor.putBoolean("AUTO_LOGIN", b);
                        editor.commit();
                    }
                }
            });

            dialog.setPositiveButton("로그인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    loginThread(id.getText().toString(), pw.getText().toString());
                }
            });

            dialog.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    isLoginIng = false;
                }
            });

            dialog.setView(layout);
            dialog.show();
        } else {
            dialog = new AlertDialog.Builder(this);

            dialog.setTitle("로그아웃");
            dialog.setMessage("로그아웃 하시겠습니까?");
            dialog.setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();

                    SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("SAVED_ID", "");
                    editor.putString("SAVED_PW", "");
                    editor.putBoolean("AUTO_LOGIN", false);
                    editor.commit();

                    new Thread() {
                        @Override
                        public void run() {
                            new ClientManager().HtmlToString("http://dormitel.korea.ac.kr/pub/process/member_logout.php");
                            handler.post(new Runnable() {
                                public void run() {
                                    handler.sendEmptyMessage(2);
                                }
                            });
                        }
                    }.start();
                }
            });
            dialog.show();
        }
    }

    private void loginThread(final String id, final String passwd) {
        new Thread() {
            @Override
            public void run() {
                LoginManager loginManager = new LoginManager();
                loginManager.loginProcess(id, passwd);
                if(loginManager.checkLogin()) {
                    handler.post(new Runnable() {
                        public void run() {
                            handler.sendEmptyMessage(0);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        public void run() {
                            handler.sendEmptyMessage(1);
                        }
                    });
                }
            }
        }.start();
    }
}
