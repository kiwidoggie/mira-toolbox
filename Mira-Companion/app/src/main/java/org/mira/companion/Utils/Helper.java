package org.mira.companion.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.text.TextUtils;

import org.mira.companion.R;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Helper {

    static ProgressDialog pDialog;
    private static SharedPreferences mSharedPreferences;


    public static final String md5(final String ss) {
        final String toHash = ss+"";
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(toHash.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }


   // Open a browser window to the URL specified.
    public static Intent openLink(String url) {
        // if protocol isn't defined use http by default
        if (!TextUtils.isEmpty(url) && !url.contains("://")) {
            url = "http://" + url;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        return intent;
    }

    public static Intent openLink(URL url) {
        return openLink(url.toString());
    }


    public static  String randomString( int len ){
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        SecureRandom rnd = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


    public static void showLoadingDialog(Context mContext) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(mContext.getString(R.string.please_wait));
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    public static void showLoadingDialog(Context mContext, String message) {
        pDialog = new ProgressDialog(mContext);
        pDialog.setMessage(message);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();
    }
    public static void hideLoadingDialog() {
        if (pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener1
     *            the listener1
     * @param listener2
     *            the listener2
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg, String btn1,
                                         String btn2, DialogInterface.OnClickListener listener1,
                                         DialogInterface.OnClickListener listener2)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(ctx, R.style.MiraCustomDialogs));
//        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
         builder.setTitle(R.string.app_name);
        builder.setMessage(msg).setCancelable(false)
                .setPositiveButton(btn1, listener1);
        if (btn2 != null && listener2 != null)
            builder.setNegativeButton(btn2, listener2);

        AlertDialog alert = builder.create();
        alert.show();
        return alert;

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener1
     *            the listener1
     * @param listener2
     *            the listener2
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg, int btn1,
                                         int btn2, DialogInterface.OnClickListener listener1,
                                         DialogInterface.OnClickListener listener2)
    {

        return showDialog(ctx, ctx.getString(msg), ctx.getString(btn1),
                ctx.getString(btn2), listener1, listener2);

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg, String btn1,
                                         String btn2, DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, msg, btn1, btn2, listener,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param btn1
     *            the btn1
     * @param btn2
     *            the btn2
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg, int btn1,
                                         int btn2, DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, ctx.getString(msg), ctx.getString(btn1),
                ctx.getString(btn2), listener);

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg,
                                         DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, msg, ctx.getString(android.R.string.ok), null,
                listener, null);
    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @param listener
     *            the listener
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg,
                                         DialogInterface.OnClickListener listener)
    {

        return showDialog(ctx, ctx.getString(msg),
                ctx.getString(android.R.string.ok), null, listener, null);
    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, String msg)
    {

        return showDialog(ctx, msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {

                dialog.dismiss();
            }
        });

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param msg
     *            the msg
     * @return the alert dialog
     */
    public static AlertDialog showDialog(Context ctx, int msg)
    {

        return showDialog(ctx, ctx.getString(msg));

    }

    /**
     * Show dialog.
     *
     * @param ctx
     *            the ctx
     * @param title
     *            the title
     * @param msg
     *            the msg
     * @param listener
     *            the listener
     */
    public static void showDialog(Context ctx, int title, int msg,
                                  DialogInterface.OnClickListener listener)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg).setCancelable(false)
                .setPositiveButton(android.R.string.ok, listener);
        builder.setTitle(title);
        AlertDialog alert = builder.create();
        alert.show();
    }


}
