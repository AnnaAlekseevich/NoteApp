package com.example.notes.ui.activities.createnotefragment.textfragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.notes.R;

import org.jetbrains.annotations.NotNull;

public class DialogImageSetting extends DialogFragment implements View.OnClickListener {
    String[] data = {"12", "14", "16", "18", "20"};
    //public BitmapUtils bitmapUtils;
    DialogBitmapSettingListener dialogBitmapSettingListener;
    int chosenBackgroundColor;
    int chosenTextSize;
    public void setDialogBitmapSettingListener(DialogBitmapSettingListener dialogBitmapSettingListener){
        this.dialogBitmapSettingListener = dialogBitmapSettingListener;
    }

    public static DialogImageSetting newInstance(String title) {
        DialogImageSetting frag = new DialogImageSetting();
        Bundle args = new Bundle();
        args.putString("Настройки картинки", title);
        frag.setArguments(args);
        return frag;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
                             @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        View v = inflater.inflate(R.layout.dialog_image_setting, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View v, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = v.findViewById(R.id.dialog_spinner_text_size);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Размер текста");
        // выделяем элемент
        spinner.setSelection(2);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Log.d("Size", "Size " + data[position]);
                switch (data[position]) {
                    case "12":
                        chosenTextSize = 12;
                        break;
                    case "14":
                        chosenTextSize = 14;
                        break;
                    case "16":
                        Log.d("SettingDIS", "Size " + 16);
                        chosenTextSize = 16;
                        break;
                    case "18":
                        chosenTextSize = 18;
                        break;
                    case "20":
                        chosenTextSize = 20;
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        v.findViewById(R.id.dialog_radioGroupColour).setOnClickListener(this);


        RadioButton redRadioButton = v.findViewById(R.id.radio_red);
        redRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton greenRadioButton = v.findViewById(R.id.radio_green);
        greenRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton blueRadioButton = v.findViewById(R.id.radio_blue);
        blueRadioButton.setOnClickListener(radioButtonClickListener);

        RadioButton grayRadioButton = v.findViewById(R.id.radio_white);
        grayRadioButton.setOnClickListener(radioButtonClickListener);
        Button btnApplySettings = v.findViewById(R.id.btn_apply_settings);
        btnApplySettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBitmapSettingListener.onApplySettingData(chosenBackgroundColor, chosenTextSize);
                Log.d("SettingDIS", " onApplySettingData color = " + chosenBackgroundColor
                + "size = " + chosenTextSize);
                onDestroyView();
            }
        });
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    View.OnClickListener radioButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RadioButton rb = (RadioButton) v;
            switch (rb.getId()) {
                case R.id.radio_red:
                    //bitmapUtils.setСolor(Color.RED);
                    chosenBackgroundColor = Color.RED;
                    Log.d("SettingDIS", "Color RED");
                    break;
                case R.id.radio_green:
                    //bitmapUtils.setСolor(Color.GREEN);
                    chosenBackgroundColor = Color.GREEN;
                    break;
                case R.id.radio_blue:
                    //bitmapUtils.setСolor(Color.BLUE);
                    chosenBackgroundColor = Color.BLUE;
                    break;
                case R.id.radio_white:
                    //bitmapUtils.setСolor(Color.WHITE);
                    chosenBackgroundColor = Color.WHITE;
                    break;

                default:
                    break;
            }
        }
    };

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(@NonNull @NotNull DialogInterface dialog) {
        super.onCancel(dialog);
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }



}
