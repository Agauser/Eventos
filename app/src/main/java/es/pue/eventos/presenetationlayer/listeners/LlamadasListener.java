package es.pue.eventos.presenetationlayer.listeners;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;


public class LlamadasListener extends PhoneStateListener {

    private Context context;

    public LlamadasListener(Context context){
        this.context=context;
    }


    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        super.onCallStateChanged(state, incomingNumber);

        switch (state)
        {
            case TelephonyManager.CALL_STATE_IDLE:
                Log.i("LlamadasListener","He colgado! "+incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                Log.i("LlamadasListener","Has Descolgado "+incomingNumber);
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                Log.i("LlamadasListener","Te estan llamando! "+incomingNumber);
                getSMS();
                break;
        }

    }
    private String getSMS(){

        StringBuilder sbSms = new StringBuilder();

        Cursor cursor= context.getContentResolver().query(
                Telephony.Sms.Inbox.CONTENT_URI,
         //       Uri.parse("content://sms/inbox"),
                null,
                null,
                null,
                null);

        while (cursor.moveToNext())
        {
            sbSms.append("From:");
            sbSms.append(cursor.getString(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.ADDRESS)));
            sbSms.append(" : ");
            sbSms.append(cursor.getString(cursor.getColumnIndex(Telephony.TextBasedSmsColumns.BODY )));
            sbSms.append("\n");
        }

        cursor.close();

        Log.d("SMS.",sbSms.toString());

        return  sbSms.toString();

    }



}
