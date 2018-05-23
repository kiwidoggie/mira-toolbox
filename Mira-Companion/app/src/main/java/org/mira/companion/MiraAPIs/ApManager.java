package org.mira.companion.MiraAPIs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.Method;

/*App manger class*/
public class ApManager {
    public static final String TAG = "ApManager";
    private final WifiManager mWifiManager;
    private Context context;

    public ApManager(Context context) {
        this.context = context;
        mWifiManager = (WifiManager) this.context.getSystemService(Context.WIFI_SERVICE);
    }
    public void showWritePermissionSettings(boolean force) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (force || !Settings.System.canWrite(this.context)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.context.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.context.startActivity(intent);
            }
        }
    }
    //check whether wifi hotspot on or off
    public boolean isApOn() {
        try {
            Method method = mWifiManager.getClass().getDeclaredMethod("isWifiApEnabled");
            method.setAccessible(true);
            return (Boolean) method.invoke(mWifiManager);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // Turn wifiAp hotspot on
    public boolean turnWifiApOn() {
        WifiConfiguration wificonfiguration = null;
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(mWifiManager, wificonfiguration, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Turn wifiAp hotspot off
    public boolean turnWifiApOff() {
        WifiConfiguration wificonfiguration = null;
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            method.invoke(mWifiManager, null, false);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean createNewNetwork(String ssid, String password) {
        mWifiManager.setWifiEnabled(false); // turn off Wifi
        if (isApOn()) {
            turnWifiApOff();
        } else {
            Log.e(TAG, "WifiAp is turned off");

        }
// creating new wifi configuration
        WifiConfiguration myConfig = new WifiConfiguration();
        myConfig.SSID = ssid; // SSID name of netwok
        myConfig.preSharedKey = password; // password for network
        myConfig.allowedKeyManagement.set(4); // 4 is for KeyMgmt.WPA2_PSK which is not exposed by android KeyMgmt class
        myConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN); // Set Auth Algorithms to open
        try {
            Method method = mWifiManager.getClass().getMethod("setWifiApEnabled", WifiConfiguration.class, boolean.class);
            return (Boolean) method.invoke(mWifiManager, myConfig, true);  // setting and turing on android wifiap with new configrations
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

}
