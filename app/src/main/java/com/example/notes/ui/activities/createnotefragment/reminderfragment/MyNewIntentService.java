package com.example.notes.ui.activities.createnotefragment.reminderfragment;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.useractivities.LoginActivity;

import org.jetbrains.annotations.NotNull;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MyNewIntentService extends IntentService {
    public final static String ARG_NOTE_ID = "arg_note_id";

    public MyNewIntentService() {
        super("MyNewIntentService");
    }
    private static final String IntentService = "IntentService";

    private void showNotification(Note note){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), "notifyLemubit");
        builder.setContentTitle(note.getName());
//        builder.setContentText(note.getText()); security problem
        builder.setSmallIcon(R.drawable.ic_priority_high_black_24dp);
        Intent notifyIntent = new Intent(this, LoginActivity.class);


        notifyIntent.putExtra(LoginActivity.ARG_NOTE_ID, note.id);
        Log.d("noteIdNotification", "MyNewIntentService note.id " + note.id);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Long longId = new Long(note.id);
        int intId = longId.intValue();
        Log.d("noteIdNotification", "MyNewIntentService intId " + intId);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, intId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT );
        builder.setContentIntent(pendingIntent);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        //notificationCompat.setLatestEventInfo(context, title, message, intent);
        notificationCompat.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(intId, notificationCompat);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        NotesApp.getInstance().getDatabaseManager().getNoteByIdWithoutUserId(intent.getLongExtra(ARG_NOTE_ID, -1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Note>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull Note note) {
                        showNotification(note);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {

                    }
                });

    }
}
