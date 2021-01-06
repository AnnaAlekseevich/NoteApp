package com.example.notes.ui.activities.useractivities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.UserModel;
import com.example.notes.utils.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class RegistrationActivity extends AppCompatActivity {

    EditText etUserName;
    EditText etUserPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().hide();

        etUserName = findViewById(R.id.et_registration_name);
        etUserPass = findViewById(R.id.et_registration_pass);
        findViewById(R.id.btn_create).setOnClickListener(view -> {
            if (TextUtils.isEmpty(etUserName.getText().toString()) || TextUtils.isEmpty(etUserName.getText().toString())) {
                Toast.makeText(this, R.string.empty_data, Toast.LENGTH_SHORT).show();
            } else {
                registerUser(etUserName.getText().toString(), etUserPass.getText().toString());
            }
        });
    }

    private void registerUser(String name, String pass) {
        NotesApp.getInstance().getDatabaseManager().getUserWithName(name)
                .subscribeOn(Schedulers.io())//thread pool
                .flatMap(userModel -> {
                    if (userModel != null) {
                        throw new IllegalAccessException("Такой Пользователь существует");
                    } else {
                        Log.d("Registration_PROBLEM", "registerUser 111");
                        return NotesApp.getInstance().getDatabaseManager().insertUser(new UserModel(name, pass))
                                .andThen(NotesApp.getInstance().getDatabaseManager().getUserWithName(name));
                    }
                })
                .onErrorResumeNext( NotesApp.getInstance().getDatabaseManager().insertUser(new UserModel(name, pass))
                .andThen(NotesApp.getInstance().getDatabaseManager().getUserWithName(name)) )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserModel userModel) {
                        Log.d("Registration_PROBLEM", "User created id=" + userModel.id);
                        PreferenceManager.saveLastUserName(userModel.name);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(RegistrationActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
