package com.payu.payuui.Widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.payu.payuui.R;

/**
 * Created by Varun Verma on 11/22/2017.
 */

public class SdkUtils {
    private static ProgressDialog mProgressDialog;
    private static Toast toast;
    private static AlertDialog dialog;
    public static Dialog showProgressDialog(Context context,
                                            boolean isCancelable, String message) {

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
        mProgressDialog.setCancelable(isCancelable);
        return mProgressDialog;
    }

    /**
     * Static method to pause the progress dialog.
     */
    public static void pauseProgressDialog() {
        try {
            if (mProgressDialog != null) {
                mProgressDialog.cancel();
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }

    //For Long Period Toast Message
    public static void showLong(Context context, String message) {
        if (message == null) {
            return;
        }
        if (toast == null && context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        }
        if (toast != null) {
            toast.setText(message);
            toast.show();
        }
    }


    public static void showAlertDialogWithCancel(Context context, String title,
                                                 String msg, String btnText,
                                                 DialogInterface.OnClickListener listener, String cancelTxt, DialogInterface.OnClickListener cancelListenr) {

        if (listener == null)
            listener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramDialogInterface,
                                    int paramInt) {
                    paramDialogInterface.dismiss();
                }
            };
        if (cancelListenr == null) {
            cancelListenr = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setIcon(context.getResources().getDrawable(R.mipmap.ic_launcher));
        builder.setPositiveButton(btnText, listener);
        builder.setNegativeButton(cancelTxt, cancelListenr);
        dialog = builder.create();
        dialog.setCancelable(false);
        try {
            dialog.show();
        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
