package org.mira.companion.MiraAPIs;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class MiraNetwork {

    private static  String TAG = "MiraNetwork";

    public static boolean isUserConnectedOnWifi(Context mContext)
    {
                    WifiManager wifiMgr = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
                    if (wifiMgr.isWifiEnabled()) { // Wi-Fi adapter is ON
                        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
                        Log.e(TAG, "Wifi Adapter is on");
                        if( wifiInfo.getNetworkId() == -1 ){
                            Log.e(TAG, "Is Not connected to Wifi");
                            return false; // Not connected to an access point
                        }
                        Log.e(TAG, "Is connected to Wifi : "+wifiInfo.getSSID());
                        return true; // Connected to an access point
                    }
                    else {
                        Log.e(TAG, "Wifi Adapter is off");
                        return false; // Wi-Fi adapter is OFF
                    }

    }




}
