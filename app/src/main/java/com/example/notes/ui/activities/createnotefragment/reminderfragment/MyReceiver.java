package com.example.notes.ui.activities.createnotefragment.reminderfragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MyNewIntentService.class);
        intent1.putExtra(MyNewIntentService.ARG_NOTE_ID, Long.parseLong(intent.getAction()));
        context.startService(intent1);
    }

}
