package com.staremisto.smsnet.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;

public class IncomingSMS extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                ArrayList<SmsMessage> messages = new ArrayList<SmsMessage>();
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (Object aPdusObj : pdusObj) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                    messages.add(currentMessage);
                }

                //append multi parts sms 
                StringBuilder bodyText = new StringBuilder();
                int messagesSize = messages.size();
                for (int i = 0; i < messagesSize; i++) {
                    bodyText.append(messages.get(i).getMessageBody());
                }

                String sms_body = bodyText.toString();
                String sender_num = messages.get(0).getDisplayOriginatingAddress();

                sms_body = "5*45 13:24 Snyatie 100.00UAH (CASH PAYMENTS UAH) Komissia 1.00UAH. Bal:52.75UAH Kak bystro i nezametno nakopit dengi? pb.ua/kopi";
                //sender_num = "+380675475520 +380";

                //parse in new Thread
//                SmsProcess smsProcess = new SmsProcess(context, sender_num, sms_body);
//                Thread t = new Thread(smsProcess);
//                t.start();
            } // bundle is null

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" + e);
        }
    }

//    class SmsProcess implements Runnable {
//        private Context context;
//        private String number;
//        private String body;
//        private String convertFrom;
//
//        public SmsProcess(Context context, String sender_num, String sms_body) {
//            this.context = context;
//            number = sender_num;
//            body = sms_body;
//        }
//
//        public void run() {
//            //parse SMS
//            final DatabaseHelper db = DatabaseHelper.getInstance(context);
//            SmsParser smsParser = SmsParser.getInstance(context);
//
//            final ParsedMessage mes = smsParser.parseSMS(number, body);
//            //  UtilsLib.LogO("SMS", number + " " + body + "\r\n");
//            if (mes != null) {
//                //        Log.i("SMS", "parsed: " + body);
//                IApplication.getInstance().userNullChecker();
//
//                //       Log.d("Bank", mes.bank_id + "");
//                //Log.d("MESSAGE Bank", SmsParser.getInstance().bank.toString());
//                if (mes.bank_id != 1 || mes.bank_id != 2) {
//                    mes.bank_id = 1;
//                }
//
//                //check card num, add if not exist
//                if (!db.checkCard(mes.card, IApplication.user.getId())) {
//                    Log.d("checkCard no find", mes.card);
//                    String colors[] = context.getResources().getStringArray(R.array.cards_colors);
//                    Random rand = new Random();
//                    int randomColor = rand.nextInt(colors.length);
//                    CardName card = new CardName(mes.card, "", Color.parseColor(colors[randomColor]), db.getBankNameByID(mes.bank_id), IApplication.user.getId(), mes.bank_id);
//                    //first of all need save into db
//                    db.addCardName(card);
//                    //save new card to server
//                    SynchronizeEngine.getInstance().updateCard(card, null);
//                }
//
//                mes.userId = IApplication.user.getId();
//                String convertFrom = mes.sum_curr;
//                final String convertTo = db.getCurrencyShortById(IApplication.user.getDefaultCurrency());
//
//                //convert tu user default currency
//                if (!convertFrom.equals(convertTo)) {
//                    final double rate = ExchangeRate.getRate(convertFrom, convertTo);
//                    mes.sum = mes.sum * (float) rate;
//                }
//
//                convertFrom = mes.komissia_curr;
//                if (!convertFrom.equals(convertTo)) {
//                    final double rate = ExchangeRate.getRate(convertFrom, convertTo);
//                    mes.komissia = mes.komissia * (float) rate;
//                }
//
//                convertFrom = mes.balance_curr;
//                if (!convertFrom.equals(convertTo)) {
//                    final double rate = ExchangeRate.getRate(convertFrom, convertTo);
//                    mes.balance = mes.balance * (float) rate;
//                }
//
//                //save message to DB
//                final int mes_id = db.addParsedSMS(mes);
//
//                //send broadcast notify unread message added
//                Intent intent_mes = new Intent(Main.BROADCAST_ACTION);
//                intent_mes.putExtra("message_id", mes_id);
//                context.sendBroadcast(intent_mes);
//                Utility.showNotification(context, "Новая транзакция", number);
//            }
//        }
//    }

}
