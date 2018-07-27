package com.payu.payuui.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.webkit.WebView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.payu.custombrowser.Bank;
import com.payu.custombrowser.CustomBrowser;
import com.payu.custombrowser.PayUCustomBrowserCallback;
import com.payu.custombrowser.PayUWebChromeClient;
import com.payu.custombrowser.PayUWebViewClient;
import com.payu.custombrowser.bean.CustomBrowserConfig;
import com.payu.india.Model.PayuConfig;
import com.payu.india.Payu.PayuConstants;
import com.payu.magicretry.MagicRetryFragment;
import com.payu.payuui.R;
import com.payu.payuui.SdkuiUtil.SdkUIConstants;
import com.payu.payuui.Widget.SdkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentsActivity extends FragmentActivity {
    private Bundle bundle;
    private String url;
    private PayuConfig payuConfig;
    private String UTF = "UTF-8";
    private boolean viewPortWide = false;
    private String merchantHash;
    private String txnId = null;
    private String merchantKey;
    private String invoice_id, user_id, coupon_code, final_amount, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            bundle = getIntent().getExtras();

            if (bundle != null)
                payuConfig = bundle.getParcelable(PayuConstants.PAYU_CONFIG);

            email = bundle.getString(SdkUIConstants.EMAIL_ID);
            invoice_id = bundle.getString(SdkUIConstants.INVOICE_ID);
            user_id = bundle.getString(SdkUIConstants.USER_ID);
            coupon_code = bundle.getString(SdkUIConstants.COUPON_CODE);
            final_amount = bundle.getString(SdkUIConstants.FINAL_AMOUNT);


            if (payuConfig != null) {
                url = payuConfig.getEnvironment() == PayuConstants.PRODUCTION_ENV ? PayuConstants.PRODUCTION_PAYMENT_URL : PayuConstants.TEST_PAYMENT_URL;

                String[] list = null;
                if (payuConfig.getData() != null)
                    list = payuConfig.getData().split("&");

                if (list != null) {
                    for (String item : list) {
                        String[] items = item.split("=");
                        if (items.length >= 2) {
                            String id = items[0];
                            switch (id) {
                                case "txnid":
                                    txnId = items[1];
                                    break;
                                case "key":
                                    merchantKey = items[1];
                                    break;
                                case "pg":
                                    if (items[1].contentEquals("NB")) {
                                        viewPortWide = true;
                                    }
                                    break;

                            }
                        }
                    }
                }

                //set callback to track important events
                PayUCustomBrowserCallback payUCustomBrowserCallback = new PayUCustomBrowserCallback() {

                    /**
                     * This method will be called after a failed transaction.
                     *
                     * @param payuResponse     response sent by PayU in App
                     * @param merchantResponse response received from Furl
                     */
                    @Override
                    public void onPaymentFailure(String payuResponse, String merchantResponse) {
                        Intent intent = new Intent();
                        intent.putExtra(getString(R.string.cb_result), merchantResponse);
                        intent.putExtra(getString(R.string.cb_payu_response), payuResponse);
                        Log.e("onPaymentFailure", "onPaymentFailure");
                        sendPaymentConfirm(0);
                        if (null != merchantHash) {
                            intent.putExtra(PayuConstants.MERCHANT_HASH, merchantHash);
                        }

                        setResult(Activity.RESULT_CANCELED, intent);

                    }

                    @Override
                    public void onPaymentTerminate() {
                        //  sendPaymentConfirm(false);
                        finish();
                    }

                    /**
                     * This method will be called after a successful transaction.
                     *
                     * @param payuResponse     response sent by PayU in App
                     * @param merchantResponse response received from Furl
                     */
                    @Override
                    public void onPaymentSuccess(String payuResponse, String merchantResponse) {
                        Intent intent = new Intent();
                        intent.putExtra(getString(R.string.cb_result), merchantResponse);
                        intent.putExtra(getString(R.string.cb_payu_response), payuResponse);
                        Log.e("onPaymentSuccess", "onPaymentSuccess");
                        sendPaymentConfirm(1);
                        if (null != merchantHash) {
                            intent.putExtra(PayuConstants.MERCHANT_HASH, merchantHash);
                        }
                        setResult(Activity.RESULT_OK, intent);

                    }

                    @Override
                    public void onCBErrorReceived(int code, String errormsg) {
                        Log.e("onCBErrorReceived", "onCBErrorReceived");
                    }

                    @Override
                    public void setCBProperties(WebView webview, Bank payUCustomBrowser) {
                        webview.setWebChromeClient(new PayUWebChromeClient(payUCustomBrowser));
                        webview.setWebViewClient(new PayUWebViewClient(payUCustomBrowser, merchantKey));
                    }

                    @Override
                    public void onBackApprove() {
                        Log.e("onBackApprove", "onBackApprove");
                        PaymentsActivity.this.finish();
                    }

                    @Override
                    public void onBackDismiss() {
                        Log.e("onBackDismiss", "onBackDismiss");
                        super.onBackDismiss();
                    }

                    /**
                     * This callback method will be invoked when setDisableBackButtonDialog is set to true.
                     *
                     * @param alertDialogBuilder a reference of AlertDialog.Builder to customize the dialog
                     */
                    @Override
                    public void onBackButton(AlertDialog.Builder alertDialogBuilder) {
                        super.onBackButton(alertDialogBuilder);
                    }

                    @Override
                    public void initializeMagicRetry(Bank payUCustomBrowser, WebView webview, MagicRetryFragment magicRetryFragment) {
                        webview.setWebViewClient(new PayUWebViewClient(payUCustomBrowser, magicRetryFragment, merchantKey));
                        Map<String, String> urlList = new HashMap<String, String>();
                        if (payuConfig != null)
                            urlList.put(url, payuConfig.getData());
                        payUCustomBrowser.setMagicRetry(urlList);
                    }
                };

                //Sets the configuration of custom browser
                CustomBrowserConfig customBrowserConfig = new CustomBrowserConfig(merchantKey, txnId);
                customBrowserConfig.setViewPortWideEnable(viewPortWide);

                //TODO don't forgot to set AutoApprove and AutoSelectOTP to true for One Tap payments
                customBrowserConfig.setAutoApprove(false);  // set true to automatically approve the OTP
                customBrowserConfig.setAutoSelectOTP(false); // set true to automatically select the OTP flow

                //Set below flag to true to disable the default alert dialog of Custom Browser and use your custom dialog
                customBrowserConfig.setDisableBackButtonDialog(false);

                //Below flag is used for One Click Payments. It should always be set to CustomBrowserConfig.STOREONECLICKHASH_MODE_SERVER
                customBrowserConfig.setStoreOneClickHash(CustomBrowserConfig.STOREONECLICKHASH_MODE_SERVER);

                //Set it to true to enable run time permission dialog to appear for all Android 6.0 and above devices
                customBrowserConfig.setMerchantSMSPermission(false);

                //Set it to true to enable Magic retry
                customBrowserConfig.setmagicRetry(true);

                //Set the first url to open in WebView
                customBrowserConfig.setPostURL(url);

                if (payuConfig != null)
                    customBrowserConfig.setPayuPostData(payuConfig.getData());

                new CustomBrowser().addCustomBrowser(PaymentsActivity.this, customBrowserConfig, payUCustomBrowserCallback);
            }
        }
    }



    public void sendPaymentConfirm(int status) {
        SdkUtils.showProgressDialog(PaymentsActivity.this, true, "Please wait...");
        AndroidNetworking.post(SdkUIConstants.BASE_URL_APPS + SdkUIConstants.CONFIRM_SUBSCRIPTION_API)
                .setTag("test")
                .addBodyParameter(getParms(status))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        SdkUtils.pauseProgressDialog();
                        Log.e("sendPaymentConfirm_PAYU", response.toString());
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String status = jObj.getString("status");
                            String msg = jObj.getString("message");
                            if (status.equalsIgnoreCase("1")) {

                                finish();

                            } else {
                                SdkUtils.showLong(PaymentsActivity.this, msg);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        SdkUtils.pauseProgressDialog();
                        finish();

                        Log.e("sendPaymentConfirm", anError.getErrorBody() + " " + anError.getErrorCode());
                    }
                });

    }


    public Map<String, String> getParms(int status) {

        HashMap<String, String> params = new HashMap<>();
        params.put(SdkUIConstants.INVOICE_ID, invoice_id);
        params.put(SdkUIConstants.USER_ID, user_id);
        params.put(SdkUIConstants.COUPON_CODE, coupon_code);
        params.put(SdkUIConstants.FINAL_AMOUNT, final_amount);
        params.put(SdkUIConstants.PAYMENT_STATUS, status+"");

        Log.e("sendPaymentConfirm", params.toString());
        return params;
    }


}
