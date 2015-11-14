package com.example.vijsu.smsreply;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    /*public MyReceiver() {
    }*/
    String senderNumber;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        throw new UnsupportedOperationException("Not yet implemented");
        String action = intent.getAction();
        Bundle extras = intent.getExtras();

        if (action.equals("android.provider.Telephony.SMS_RECEIVED")){
//            displayToast(context,"Incoming SMS");
            Object[] pdus = (Object[]) extras.get("pdus");
            SmsMessage[] smsMessage = new SmsMessage[pdus.length];
            for (int i = 0; i < smsMessage.length; i++){

                    smsMessage[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                senderNumber = smsMessage[i].getOriginatingAddress();
                String incomingMsg = smsMessage[i].getMessageBody();
                SmsManager smsManager = SmsManager.getDefault();
                if (incomingMsg.equalsIgnoreCase("bus"))
                {
                    smsManager.sendTextMessage(senderNumber, null, "Bus No is AP091164", null, null);
                }
                else if (incomingMsg.equalsIgnoreCase("car"))
                {
                    smsManager.sendTextMessage(senderNumber, null, "Car No is AP091164", null, null);
                }
                else
                {
                    smsManager.sendTextMessage(senderNumber,null,"The format u have sent is wrong. Plese use bus or car keyword in ur sms",null,null);
                }

                displayToast(context,"Incoming message from "+senderNumber);
            }
        }
}

    private void displayToast(Context context, String msg) {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
    private void sendAutoReply(String number){
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, "Sorry i am busy", null, null);
    }
}
