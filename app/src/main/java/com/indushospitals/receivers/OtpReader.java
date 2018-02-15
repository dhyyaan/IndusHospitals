package com.indushospitals.receivers;

/**
 * Created by think360 on 12/04/17.
 */


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;


import com.indushospitals.interfaces.listener.OTPListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class OtpReader extends BroadcastReceiver {

    /**
     * Constant TAG for logging key.
     */
    private static final String TAG = "OtpReader";

    /**
     * The bound OTP Listener that will be trigerred on receiving message.
     */
    private static OTPListener otpListener;

    /**
     * The Sender number string.
     */
    private static String receiverString;

    /**
     * Binds the sender string and listener for callback.
     *
     * @param listener
     * @param sender
     */
    public static void bind(OTPListener listener, String sender) {
        otpListener = listener;
        receiverString = sender;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {

            final Object[] pdusArr = (Object[]) bundle.get("pdus");

            for (int i = 0; i < pdusArr.length; i++) {
                SmsMessage currentMessage=null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = bundle.getString("format");
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusArr[i], format);
                } else {
                    currentMessage = SmsMessage.createFromPdu((byte[]) pdusArr[i]);
                }
             //   SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusArr[i]);
                String senderNum = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();
              //  Log.i(TAG, "senderNum: " + senderNum + " message: " + message);

                if (!TextUtils.isEmpty(receiverString) && senderNum.contains(receiverString)) { //If message received is from required number.
                    //If bound a listener interface, callback the overriden method.
                    if (otpListener != null) {
                        Pattern p = Pattern.compile("\\d+");
                        Matcher m = p.matcher(message);
                        while (m.find())
                        otpListener.otpReceived(m.group());
                    }
                }
            }
        }
    }
}