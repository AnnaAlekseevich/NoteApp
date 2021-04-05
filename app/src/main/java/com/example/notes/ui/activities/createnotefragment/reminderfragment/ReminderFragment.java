package com.example.notes.ui.activities.createnotefragment.reminderfragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;

import java.util.Calendar;

public class ReminderFragment extends BaseNoteFragment {

    TextView tv_description_date;
    EditText et_description_note;
    static Calendar dateAndTime = Calendar.getInstance();
    private static final String Time = "ReminderTime";
    private static final String NotificationReminderFragment = "NotificationReminderFragment";

    Button btActivate;


    public static ReminderFragment newInstance() {
        ReminderFragment fragment = new ReminderFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();
    }

    @Override
    protected void drawNote(Note note) {
        dateAndTime.setTimeInMillis(note.getReminder().getReminderDate());
        et_description_note.setText(note.getText());
        setInitialDateTime();
        changeActivateButtonText();
    }

    @Override
    public Note getFilledNote() {
        note.setText(et_description_note.getText().toString());
        note.getReminder().setReminderDate(dateAndTime.getTimeInMillis());
        return note;
    }

    private void changeActivateButtonText() {
        btActivate.setText(note.getReminder().isActive() ? R.string.deactivate_reminder : R.string.activate_reminder);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_reminder, container, false);
        setHasOptionsMenu(true);

        tv_description_date = view.findViewById(R.id.tv_description_date);
        et_description_note = view.findViewById(R.id.et_description_note);
        setInitialDateTime();

        view.findViewById(R.id.dateButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setDate(view);
            }
        });

        view.findViewById(R.id.timeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime(view);
            }
        });

        btActivate = view.findViewById(R.id.setReminder);
        btActivate.setOnClickListener(view1 -> {

            //todo it's working

           if(!note.getReminder().isActive()) {
               Toast.makeText(getContext(), "Напоминание установлено!",
                       Toast.LENGTH_SHORT).show();
               Intent notifyIntent = new Intent(getContext(), MyReceiver.class);
               notifyIntent.setAction(""+getFilledNote().id);

               Log.d("noteIdNotification","Напоминание activate getFilledNote.id = " +getFilledNote().id);
               Log.d("noteIdNotification","Напоминание activate note.id = " +note.id);


               PendingIntent pendingIntent = PendingIntent.getBroadcast
                       (getContext(), 1, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

               AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
               alarmManager.set(AlarmManager.RTC_WAKEUP, getFilledNote().getReminder().getReminderDate(),
                       pendingIntent);

               Log.d(Time, "Reminder time = " + getFilledNote().getReminder().getReminderDate());
               Log.d(Time, "System time = " + System.currentTimeMillis());
               note.getReminder().setActive(!note.getReminder().isActive());
               changeActivateButtonText();
           } else if (note.getReminder().isActive()) {
               Toast.makeText(getContext(), "Напоминание отменено!",
                       Toast.LENGTH_SHORT).show();
               AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
               Intent myIntent = new Intent(getContext(), MyReceiver.class);
               PendingIntent pendingIntent = PendingIntent.getBroadcast(
                       getContext(), 1, myIntent,
                       0);

               alarmManager.cancel(pendingIntent);
               note.getReminder().setActive(!note.getReminder().isActive());
               changeActivateButtonText();
           }




        });

        return view;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "LemubitReminderChannel";
            String description = "Channel for Lemubit Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notifyLemubit", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager =
                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }


    // отображаем диалоговое окно для выбора даты
    public void setDate(View v) {
        new DatePickerDialog(getContext(), d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    public void setTime(View v) {
        new TimePickerDialog(getContext(), t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDateTime() {

        tv_description_date.setText(DateUtils.formatDateTime(getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR
                        | DateUtils.FORMAT_SHOW_TIME));
    }

    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialDateTime();
        }
    };

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };


}
