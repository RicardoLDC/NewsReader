package com.zxsoft.example;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.library.permission.AndPermission;
import com.example.library.permission.PermissionListener;
import com.example.library.permission.Rationale;
import com.example.library.permission.RationaleListener;
import com.example.library.utils.AppACache;
import com.example.library.utils.SmartToast;
import com.example.library.widget.SmartProgressDialog;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @class describe:描述
 * @anthor chenyx
 * @time 2017/09/11 17:13
 * @chang 2017/09/11 17:13
 */
public abstract class BaseFragment extends Fragment {
    private View view;
    protected Context mContext;
    private AppACache mCache;
    private NetworkChangeReceiver networkChangeReceiver;
    //    public static final int TIME_CLICK_IGNORE = 500;
    Unbinder unbinder;
    private int reqCode;

    protected boolean isViewInitiated;
    protected boolean isVisibleToUser;
    protected boolean isDataInitiated;

    protected static final int REQUEST_CODE_CAREMA = 0;
    protected static final int REQUEST_CODE_PHOTO = 1;
    protected static final int REQUEST_CODE_CUT = 2;

    protected enum NetState {
        WIFI, MOBILE
    }

    private SmartProgressDialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getExtra();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = LayoutInflater.from(this.getActivity()).inflate(getLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        mContext = getActivity();
        mCache = AppACache.get(mContext);
        //注册网络监听广播
        networkChangeReceiver = new NetworkChangeReceiver();
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction("ConnectivityManager.CONNECTIVITY_ACTION");
        mContext.registerReceiver(networkChangeReceiver, networkFilter);

        init(savedInstanceState);
        setListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareFetchData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    public boolean prepareFetchData() {
        return prepareFetchData(false);
    }

    public boolean prepareFetchData(boolean forceUpdate) {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData();
            isDataInitiated = true;
            return true;
        }
        return false;
    }


//    /**
//     * 默认空白页面
//     * @param inflater
//     * @return
//     */
//    public View createEmptyView(LayoutInflater inflater) {
//        View view = inflater.inflate(R.layout.emptyview_default, null, false);
//        return view;
//    }
//
//
//    /**
//     * 带文字的空白页面
//     * @param inflater
//     * @param text
//     * @return
//     */
//    public View createEmptyView(LayoutInflater inflater,String text) {
//        View view = inflater.inflate(R.layout.emptyview_default, null, false);
//        if (text != null) {
//            TextView textView= (TextView) view.findViewById(R.id.tvWords);
//            textView.setText(text);}
//        return view;
//    }


    public void getExtra() {
    }

    protected <V extends View> V findView(int resId) {
        return (V) view.findViewById(resId);
    }

    public abstract int getLayoutId();

    public abstract void fetchData();

    public abstract void init(Bundle savedInstanceState);

    public abstract void setListener();

    /**
     * 网络已连接
     *
     * @param type 1:数据 2.无线
     */
    protected void onNetworkConnect(BaseFragment.NetState type) {
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

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 携带数据的页面跳转
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(mContext, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 含有Bundle通过Class打开编辑界面
     *
     * @param cls
     * @param bundle
     * @param requestCode
     */
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void cachePutBitmap(String key, Bitmap bitmap) {
        mCache.put(key, bitmap);
    }

    public Bitmap getCacheBitmap(String key) {
        return mCache.getAsBitmap(key);
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
                    onNetworkConnect(BaseFragment.NetState.MOBILE);
                } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED == wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                    onNetworkConnect(BaseFragment.NetState.WIFI);
                } else if (wifiState != null && mobileState != null && NetworkInfo.State.CONNECTED != wifiState && NetworkInfo.State.CONNECTED != mobileState) {
                    onNetworkInterrupt();
                }
            }
        }
    }

    /**
     * toast
     *
     * @param text
     */
    public void toast(String text) {
        SmartToast.show(mContext, text);
    }

    /**
     * toast
     *
     * @param resId
     */
    public void toast(int resId) {
        SmartToast.show(mContext, getString(resId));
    }

    public void showLoading() {
        if (dialog == null) {
            dialog = new SmartProgressDialog(mContext);
        }
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
        AndPermission.with(this).requestCode(reqCode).permission(permissions)
                .rationale(rationaleListener).callback(listener).start();
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener() {
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale) {
            new AlertDialog.Builder(mContext)
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

                if (AndPermission.hasPermission(mContext, grantedPermissions)) {
                    // TODO 执行拥有权限时的下一步。
                    hasPermissionCallback(requestCode);
                } else {
                    // TODO 执行无权限时的下一步。为了适配国产手机
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(mContext, grantedPermissions)) {
                        AndPermission.defaultSettingDialog(getActivity(), 400).show();
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
                if (AndPermission.hasPermission(mContext, deniedPermissions)) {
                    // TODO 执行拥有权限时的下一步。为了适配国产手机
                    hasPermissionCallback(requestCode);
                } else {
                    // TODO 执行无权限时的下一步。
                    // 是否有不再提示并拒绝的权限。
                    if (AndPermission.hasAlwaysDeniedPermission(mContext, deniedPermissions)) {
                        AndPermission.defaultSettingDialog(getActivity(), 400).show();
                    }
//                    } else {
//                        noPermissionCallback(requestCode);
//                    }
                }
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mContext.unregisterReceiver(networkChangeReceiver);
    }

}
