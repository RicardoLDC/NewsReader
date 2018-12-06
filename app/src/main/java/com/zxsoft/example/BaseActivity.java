package com.zxsoft.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.permission.AndPermission;
import com.example.library.permission.PermissionListener;
import com.example.library.permission.Rationale;
import com.example.library.permission.RationaleListener;
import com.example.library.widget.SmartProgressDialog;
import com.lzy.okgo.OkGo;
import com.zxsoft.example.Activity.SearchViewActivity;
import com.zxsoft.example.fragment.HistoryFragment;
import com.zxsoft.example.fragment.HomeFragment;
import com.zxsoft.example.fragment.UserFragment;
import com.zxsoft.example.model.data.home.HomeLocalDataSource;
import com.zxsoft.example.model.data.home.HomeRemoteDataSource;
import com.zxsoft.example.model.data.home.HomeRepository;
import com.zxsoft.example.presenter.HomePresenter;

//import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * @class describe:描述
 * @anthor chenyx
 * @time 2017/08/23 11:03
 * @chang 2017/08/23 11:03
 */
public abstract class BaseActivity extends AppCompatActivity {

    private int hashcode;
    private View contentView;
    private TextView tvTitle;
    private TextView tvRightBtn;
    private TextView tvR2Btn;
    protected ImageView ivRight;
    private ImageView ivCenter;
    private Toolbar toolbar;
    private NetworkChangeReceiver networkChangeReceiver;
    protected Context mActivity;
    private SmartProgressDialog dialog;
    private int reqCode;
    protected Bundle mSavedInstanceState;
    protected boolean isBack = true;
    private HomePresenter homePresenter;
    protected static final int REQUEST_CODE_CAREMA = 0;
    protected static final int REQUEST_CODE_PHOTO = 1;
    protected static final int REQUEST_CODE_CUT = 2;
    //自定义
    @BindView(R.id.fl_container)
    FrameLayout flcontainer;
    @BindView(R.id.homepage)
    RadioButton rbHome;
    @BindView(R.id.history)
    RadioButton rbHistory;
    @BindView(R.id.mine)
    RadioButton rbUser;
    @BindView(R.id.rg_bottom)
    RadioGroup rgBottom;
    private FragmentManager mFragmentManager;
    HomeFragment home;
    HistoryFragment history;
    UserFragment user;
    public static final int HOMEFRAGMENT=1;
    public static final int HISTORYFRAGMENT=2;
    public static final int USERFRAGMETN=3;
    protected enum NetState {
        WIFI, MOBILE
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSavedInstanceState = savedInstanceState;
        //hashcode = this.hashCode();
        initStatus();
        initScreen();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //自定义
        //兼容的fragment管理器
        mFragmentManager=getSupportFragmentManager();
        //默认让主页被选中
        showFragment(1);
        //RedioGroup点击事件
        rgBottom.setOnCheckedChangeListener(mOnCheckedChangeListener);
        init();
        setListener();
//        LitePal.initialize(this);

    }

    //自定义
    RadioGroup.OnCheckedChangeListener mOnCheckedChangeListener=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.homepage://主页
                    showFragment(1);
                    break;
                case R.id.history://历史
                    showFragment(2);
                    break;
                case R.id.mine://我的
                    showFragment(3);
                    break;
                default:
                    break;
            }
        }
    };
//
//    private void switchFragment(Fragment fragment) {
//        android.support.v4.app.FragmentTransaction transaction= mFragmentManager.beginTransaction();
//        //transaction.add(R.id.fl_container,fragment);
//        transaction.replace(R.id.fl_container,fragment);
//        transaction.commit();
//    }
    public void showFragment(int index) {
        FragmentTransaction ft=mFragmentManager.beginTransaction();
        hideFragment(ft);
        switch(index) {
            case HOMEFRAGMENT:
                if(home==null) {
                    home=new HomeFragment();
                    ft.add(R.id.fl_container,home);
                } else {
                    ft.show(home);
                }
//                ft.hide(history);
//                ft.hide(user);
                break;
            case HISTORYFRAGMENT:
                if(history==null) {
                    history=new HistoryFragment();
                    ft.add(R.id.fl_container,history);
                } else {
                    ft.show(history);
                }
//                ft.hide(home);
//                ft.hide(user);
                break;
            case USERFRAGMETN:
                if(user==null) {
                    user=new UserFragment();
                    ft.add(R.id.fl_container,user);
                } else {
                    ft.show(user);
                }
//                ft.hide(home);
//                ft.hide(history);
                break;
            default:
                break;
        }
        ft.commit();
    }

    public void hideFragment(FragmentTransaction ft) {
        if(home!=null) {
            ft.hide(home);
        }
        if(history!=null) {
            ft.hide(history);
        }
        if(user!=null) {
            ft.hide(user);
        }
    }




    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//        this.mSavedInstanceState = savedInstanceState;
//        hashcode = this.hashCode();
//        initStatus();
//        initScreen();
//        contentView = LayoutInflater.from(this).inflate(getLayoutId(), null);
////        contentView.setFitsSystemWindows(true);
//        setContentView(contentView);
//        ButterKnife.bind(this);
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        tvTitle = (TextView) findViewById(R.id.tv_activity_title);
//        tvRightBtn = (TextView) findViewById(R.id.tv_activity_right_btn);
//        tvR2Btn = (TextView) findViewById(R.id.tv_activity_right2_btn);
//        ivRight = (ImageView) findViewById(R.id.iv_activity_right);
//        ivCenter = (ImageView) findViewById(R.id.iv_activity_center);
//        setSupportActionBar(toolbar);
//        //隐藏toolbar自带标题显示
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        //点击箭头返回
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackClick();
//                if (isBack) {
//                    finish();
//                }
//            }
//        });
//
//
//        //注册网络监听广播
//        networkChangeReceiver = new NetworkChangeReceiver();
//        IntentFilter networkFilter = new IntentFilter();
//        networkFilter.addAction("ConnectivityManager.CONNECTIVITY_ACTION");
//        registerReceiver(networkChangeReceiver, networkFilter);
//
//        mActivity = this;
//
//        tvRightBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onR1BtnClick(view);
//            }
//        });
//        tvR2Btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onR2BtnClick(view);
//            }
//        });
//        ivRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onR1ImageClick();
//            }
//        });
//        ivCenter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onCenterImageClick();
//            }
//        });
//
//        init();
//        setListener();
//
//    }
    @Override
    public void setContentView(View contentView) {
        /*
        ViewGroup superContentView = (ViewGroup) LayoutInflater.from(this)
                .inflate(R.layout.activity_main, null);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout rootView = (RelativeLayout) superContentView.findViewById(R.id.content);
        rootView.addView(contentView, params);
//        rootView.setFitsSystemWindows(true);
//        rootView.setLayoutParams(params);
//        StatusBarCompat.compat(this, rootView, getStatusBar());
        super.setContentView(superContentView);
        */
    }
    /*
    public void setNavigationClick(View.OnClickListener listener) {
        //点击箭头返回
        toolbar.setNavigationOnClickListener(listener);
    }
    */

    /**
     * 隐藏返回按钮
     */
    protected void hideBackButton() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    /**
     * 隐藏toolbar
     */

    protected void hideToolbar() {
        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);
        }
    }

    public void showToolbar() {
        if (toolbar != null) {
            toolbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置标题（居中标题）
     *
     * @param title
     */
    public void setCenterTitle(String title) {

        if (tvTitle == null) {
            tvTitle = (TextView) findViewById(R.id.tv_activity_title);
        }
        tvTitle.setText(title);

    }

    protected void setShowCenterIv() {
        ivCenter.setVisibility(View.VISIBLE);
    }

    /**
     * 设置右侧按钮文字
     *
     * @param text
     */
    protected void setR1BtnText(String text) {
        if (text == null || text.isEmpty()) {
            tvRightBtn.setVisibility(View.GONE);
        } else {
            tvRightBtn.setVisibility(View.VISIBLE);
            tvRightBtn.setText(text);
        }
    }

    /**
     * 设置右侧按钮文字
     *
     * @param text
     */
    protected void setR2BtnText(String text) {
        if (text == null || text.isEmpty()) {
            tvR2Btn.setVisibility(View.GONE);
        } else {
            tvR2Btn.setVisibility(View.VISIBLE);
            tvR2Btn.setText(text);
        }
    }

    /**
     * 设置右侧图片
     *
     * @param resid
     */
    protected void setR1Image(int resid) {
        if (resid == 0) {
            ivRight.setVisibility(View.GONE);
        } else {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setBackgroundResource(resid);
        }
    }

    protected void initStatus() {
    }

    protected void initScreen() {
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public SmartProgressDialog getDialog() {
        return dialog;
    }

    protected TextView getR1BtnText() {
        return tvRightBtn;
    }

    protected abstract int getLayoutId();

    protected abstract void init();

    protected abstract void setListener();

    protected void onR1BtnClick(View view) {
    }

    protected void onR2BtnClick(View view) {
    }

    protected void onR1ImageClick() {
    }

    protected void onCenterImageClick() {
    }

    protected void onBackClick() {
    }

    /**
     * 网络已连接
     *
     * @param type 1:数据 2.无线
     */
    protected void onNetworkConnect(NetState type) {
    }

    /**
     * 网络中断
     */
    protected void onNetworkInterrupt() {
    }

    /**
     * 获取权限
     */
    protected void hasPermissionCallback(int code) {

    }

    /**
     * 无权限
     */
    protected void noPermissionCallback(int code) {

    }

    /**
     * 网络状态监听器
     */
    private class NetworkChangeReceiver extends BroadcastReceiver {
        NetworkInfo.State wifiState = null;
        NetworkInfo.State mobileState = null;

        @Override
        public void onReceive(Context context, Intent intent) {
            if ("ConnectivityManager.CONNECTIVITY_ACTION".equals(intent.getAction())) {
                //获取手机的连接服务管理器，这里是连接管理器类
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                wifiState = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
                mobileState = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

                if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED == mobileState) {
                    onNetworkConnect(NetState.MOBILE);
                } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                    onNetworkConnect(NetState.WIFI);
                } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                    onNetworkInterrupt();
                }
            }
        }
    }

    protected void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    protected void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected void setResults(Bundle bundle) {
        Intent intent = new Intent();
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        setResult(-1, intent);
    }


    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    protected void startActivityForResult(Class<?> cls, Bundle bundle,
                                          int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 通过Uri传递图像信息以供裁剪
     *
     * @param uri
     */
    protected void startImageZoom(Uri uri) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //裁剪之后的数据是通过Intent返回
        intent.putExtra("return-data", true);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CODE_CUT);
    }

    /**
     * toast
     *
     * @param text
     */
    public void toast(String text) {
        Toast.makeText(mActivity, text, Toast.LENGTH_LONG).show();
    }

    /**
     * toast
     *
     * @param resId
     */
    public void toast(int resId) {
        Toast.makeText(mActivity, getString(resId), Toast.LENGTH_LONG).show();
    }

    public void showLoading() {
        dialog = new SmartProgressDialog(mActivity);
        dialog.show();
    }

    public void dismissLoading() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 动态申请权限
     *
     * @param permissions
     */
    protected void requestPermission(int reqCode, String... permissions) {
        this.reqCode = reqCode;
        AndPermission.with(this).requestCode(reqCode).permission(permissions).callback(listener).start();
//                .rationale(rationaleListener)
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(mActivity)
                    .setTitle("友好提醒")
                    .setMessage("你已拒绝过定位权限，沒有定位权限无法使用地图定位功能！")
                    .setPositiveButton("好，给你", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("我拒绝", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            rationale.cancel();
                        }
                    })
                    .show();
        }
    };

    /**
     * 申请权限回调
     */
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if (requestCode == reqCode) {

                if (AndPermission.hasPermission(mActivity, grantedPermissions)) {
                    // TODO 执行拥有权限时的下一步。
                    hasPermissionCallback(requestCode);
                } else {
                    // TODO 执行无权限时的下一步。为了适配国产手机
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(mActivity, grantedPermissions)) {
                        AndPermission.defaultSettingDialog(BaseActivity.this, 400).show();
                    } else {
                        noPermissionCallback(requestCode);
                    }
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if (requestCode == reqCode) {
                if (AndPermission.hasPermission(mActivity, deniedPermissions)) {
                    // TODO 执行拥有权限时的下一步。为了适配国产手机
                    hasPermissionCallback(requestCode);
                } else {
                    // TODO 执行无权限时的下一步。
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(mActivity, deniedPermissions)) {
                        AndPermission.defaultSettingDialog(BaseActivity.this, 400).show();
                    }
//                    } else {
//                        noPermissionCallback(requestCode);
//                    }
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
      //  unregisterReceiver(networkChangeReceiver);
        //根据 Tag 取消请求
        try {
            cancelTag(String.valueOf(hashcode), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void cancelTag(String tag, int aa) {
        for (Call call : OkGo.getInstance().getOkHttpClient().dispatcher().queuedCalls()) {
            String tags = String.valueOf(call.request().tag());
            if (tags.startsWith(tag.substring(0, tag.length() - 2))) {
                call.cancel();
            }
        }
        for (Call call : OkGo.getInstance().getOkHttpClient().dispatcher().runningCalls()) {
            String tags = String.valueOf(call.request().tag());
            if (tags.startsWith(tag.substring(0, tag.length() - 2))) {
                call.cancel();
            }
        }
    }

}


