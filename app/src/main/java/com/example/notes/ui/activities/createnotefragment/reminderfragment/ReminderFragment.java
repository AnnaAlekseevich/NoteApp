package com.example.notes.ui.activities.createnotefragment.reminderfragment;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.ui.activities.createnotefragment.BaseNoteFragment;

import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class ReminderFragment extends BaseNoteFragment {

    TextView tv_description_date;
    EditText et_description_note;
    static Calendar dateAndTime = Calendar.getInstance();
    Button btActivate;
    public static String create_reminder = "SKOVORODA";
    String reminder = "У Вас напоминание";


    public static ReminderFragment newInstance() {
        ReminderFragment fragment = new ReminderFragment();
        return fragment;
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
            note.getReminder().setActive(!note.getReminder().isActive());
            changeActivateButtonText();
            addNotification();
        });

        return view;
    }

    private void addNotification() {

        NotificationManager notificationManager =
                (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(create_reminder, "My channel",
                    NotificationManager.IMPORTANCE_HIGH);

            channel.setDescription("My channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), create_reminder)
                .setSmallIcon(R.drawable.ic_priority_high_black_24dp)
                .setContentTitle(reminder)
                .setContentText(note.getName())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        /*Intent notificationIntent = new Intent(this, ReminderFragment.class);
        notificationIntent.setAction(ACTION_SNOOZE);
        notificationIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, notificationIntent, 0);*/


        Notification notification = builder.build();

        notificationManager.notify(1, notification);

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
