package proinfactory.com.hoyeonnuri;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.ActionBar;
import com.proinlab.hoyeonnuri.adapter.viewpager.BoardViewPagerAdapter;
import com.proinlab.hoyeonnuri.adapter.viewpager.InfoViewPagerAdapter;
import com.proinlab.hoyeonnuri.adapter.viewpager.ViewPagerAdapter;
import com.proinlab.hoyeonnuri.manager.ClientManager;
import com.proinlab.hoyeonnuri.manager.LoginManager;

public class InfoActivity extends SlidingDrawerActivity {

    public static final String INTENT_KEY = "INFOKEY";

    private ViewPagerAdapter viewPagerAdapter;
    public static ViewPager mViewPager;

    private AlertDialog.Builder dialog;

    @Override
    public void onCreate(Bundle inState) {
        super.onCreate(inState);
        setContentView(R.layout.fragment_main);
        setOnClick();

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowCustomEnabled(true);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        viewPagerAdapter = new InfoViewPagerAdapter(getSupportFragmentManager(), this);
        mViewPager.setAdapter(viewPagerAdapter);

        for (int i = 0; i < viewPagerAdapter.getCount(); i++)
            actionBar.addTab(actionBar.newTab().setText(viewPagerAdapter.getPageTitle(i)).setTabListener(tabListener));

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        mViewPager.setCurrentItem(getIntent().getIntExtra(INTENT_KEY, 0));

    }

    private ActionBar.TabListener tabListener = new ActionBar.TabListener() {
        @Override
        public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        }

        @Override
        public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        }
    };

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
                    intent = new Intent(InfoActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_NOTICE);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_board_free:
                    intent = new Intent(InfoActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_FREE);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_board_repair:
                    intent = new Intent(InfoActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_REPAIR);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_board_qna:
                    intent = new Intent(InfoActivity.this, BoardActivity.class);
                    intent.putExtra(BoardActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_BOARD_QNA);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_notice:
                    intent = new Intent(InfoActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_NOTICE);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_1:
                    intent = new Intent(InfoActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_JINLI);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_2:
                    intent = new Intent(InfoActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_HOYEON);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_restaurant_3:
                    intent = new Intent(InfoActivity.this, RestaurantActivity.class);
                    intent.putExtra(RestaurantActivity.INTENT_KEY, ViewPagerAdapter.FRAGMENT_RESTAURANT_STUDENT);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.drawer_info_bus:
                    mViewPager.setCurrentItem(ViewPagerAdapter.FRAGMENT_INFO_SUTTLE);
                    break;
                case R.id.drawer_info_sejong_library:
                    mViewPager.setCurrentItem(ViewPagerAdapter.FRAGMENT_INFO_LIBRARY_SEJONG);
                    break;
                case R.id.drawer_info_anam_library:
                    mViewPager.setCurrentItem(ViewPagerAdapter.FRAGMENT_INFO_LIBRARY_ANAM);
                    break;
                case R.id.drawer_delivery:
                    intent = new Intent(InfoActivity.this, DeliveryActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            mMenuDrawer.closeMenu();
        }
    };

    private boolean loginStat;
    private boolean isLoginIng;

    @Override
    public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenuDrawer.toggleMenu();
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
                    Toast.makeText(InfoActivity.this, "로그인 실패", Toast.LENGTH_LONG).show();
                    isLoginIng = false;
                    break;
                case 2:
                    Toast.makeText(InfoActivity.this, "로그아웃", Toast.LENGTH_LONG).show();
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
