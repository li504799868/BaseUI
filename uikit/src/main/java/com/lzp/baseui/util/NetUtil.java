package com.lzp.baseui.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by li.zhipeng on 2018/08.23
 *
 *      网络相关工具类
 */
public class NetUtil {

    /**
     * 网络是否可用
     * true false
     * @return
     */
    public static boolean isNetConnected(Context context) {
        boolean isNetConnected;
        // 获得网络连接服务
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connManager != null;
        NetworkInfo info = connManager.getActiveNetworkInfo();
        isNetConnected = info != null && info.isAvailable();
        return isNetConnected;
    }

}

