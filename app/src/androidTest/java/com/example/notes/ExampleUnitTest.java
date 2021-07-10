package com.example.notes;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.notes.db.DataBaseManager;
import com.example.notes.models.UserModel;

import org.junit.Test;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertTrue;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest extends Instrumentation {


    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DataBaseManager dataBaseManager = new DataBaseManager(context);


    @Test
    public void createUserTestUniqueName() {
        String name = "" + System.currentTimeMillis();
        String pass = "1111";


        dataBaseManager.getUserWithName(name)
                .subscribeOn(Schedulers.io())//thread pool
                .flatMap(userModel -> {
                    if (userModel != null) {
                        assertTrue(false);
                        throw new IllegalAccessException("Такой Пользователь существует");
                    } else {
                        Log.d("Registration_PROBLEM", "registerUser 111");
                        return dataBaseManager.insertUser(new UserModel(name, pass))
                                .andThen(dataBaseManager.getUserWithName(name));
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserModel userModel) {
                        assertTrue(userModel.id > 0 && userModel.name.equals(name));
                    }

                    @Override
                    public void onError(Throwable e) {
                        assertTrue(false);
                    }
                });

    }


}