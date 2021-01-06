package com.example.notes.ui.activities.useractivities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.UserModel;
import com.example.notes.ui.activities.MainActivity;
import com.example.notes.utils.PreferenceManager;
import com.example.notes.utils.UserProviderSingleton;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {

    EditText etUserName;
    EditText etUserPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        etUserName = findViewById(R.id.et_login_name);
        etUserPass = findViewById(R.id.et_login_pass);
        findViewById(R.id.btn_create).setOnClickListener(view -> {
            if (TextUtils.isEmpty(etUserName.getText().toString()) || TextUtils.isEmpty(etUserName.getText().toString())) {
                Toast.makeText(this, R.string.empty_data, Toast.LENGTH_SHORT).show();
            } else {
                doLogin(etUserName.getText().toString(), etUserPass.getText().toString());
            }
        });

        findViewById(R.id.tv_register).setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistrationActivity.class);
            startActivity(intent);
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!TextUtils.isEmpty(PreferenceManager.getLastUserName())){
            etUserName.setText(PreferenceManager.getLastUserName());
        }
    }

    private void doLogin(String userName, String pass) {
        NotesApp.getInstance().getDatabaseManager().getUserWithName(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserModel userModel) {
                        Log.d("REGISTRATION_SUCCESS", "User by name =" + userModel);
                        Log.d("REGISTRATION_SUCCESS", "User pass =" + pass);
                        if (userModel.pass.equals(pass)) {
                            onLoginSuccessful(userModel);
                        }else{
                            Toast.makeText(LoginActivity.this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                        }
                        Log.d("REGISTRATION_SUCCESS", "User created id=" + userModel.id);

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Registration_PROBLEM", "login error =" + e);
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void onLoginSuccessful(UserModel userModel) {
        UserProviderSingleton.getInstance().updateCurrentUser(userModel);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}