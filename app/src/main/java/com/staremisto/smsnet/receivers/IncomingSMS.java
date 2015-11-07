package com.staremisto.smsnet.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.staremisto.smsnet.Constants;
import com.staremisto.smsnet.activity.BBCActivity;
import com.staremisto.smsnet.activity.DirectionActivity;
import com.staremisto.smsnet.activity.WikipediaActivity;
import com.staremisto.smsnet.activity.FoursquareActivity;
import com.staremisto.smsnet.activity.WeatherActivity;


public class IncomingSMS extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        try {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();
                SmsMessage[] msgs = null;
                String msg_from;
                if (bundle != null) {
                    try {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < msgs.length; i++) {
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            msg_from = msgs[i].getOriginatingAddress();
                            String msgBody = msgs[i].getMessageBody();
                            Log.d("SMSHandler", "msgBody " + msgBody);
                            stringBuilder.append(msgBody);
                        }

                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
                        int result = prefs.getInt(Constants.SHARED_PREF_ACTIVITY_TYPE_KEY, -1);

                        switch (result) {
                            case Constants.SHARED_PREF_WIKIPEDIA_KEY:
                                WikipediaActivity.getInstance().updateInfoFromSMS(stringBuilder.toString());
                                break;
                            case Constants.SHARED_PREF_BBC_KEY:
                                BBCActivity.getInstance().updateInfoFromSMS(stringBuilder.toString());
                                break;
                            case Constants.SHARED_PREF_DIRECTION_KEY:
                                DirectionActivity.getInstance().updateInfoFromSMS(stringBuilder.toString());

                            case Constants.SHARED_PREF_WEATHER_KEY:
                                WeatherActivity.getInstance().updateInfoFromSMS(stringBuilder.toString());
                                break;
                            case Constants.SHARED_PREF_FOURSQUARE_KEY:
                                FoursquareActivity.getInstance().updateInfoFromSMS(stringBuilder.toString());
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        Log.d("SMSHandler", "Excpetion " + e);
                    }
                }
            }



        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }

}
