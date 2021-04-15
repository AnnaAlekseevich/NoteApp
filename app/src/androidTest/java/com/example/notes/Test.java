package com.example.notes;

import android.app.Instrumentation;
import android.content.Context;
import android.util.Log;

import androidx.test.platform.app.InstrumentationRegistry;

import com.example.notes.db.DataBaseManager;
import com.example.notes.models.UserModel;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static org.junit.Assert.assertTrue;

public class Test extends Instrumentation {

    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    DataBaseManager dataBaseManager = new DataBaseManager(context);

    /*@Before
    public void setUp() {
        final Context context = InstrumentationRegistry.getInstrumentation().getContext();
        rootView = LayoutInflater.from(context).inflate(R.layout.attrributes, null, false);
    }*/

    /*@org.junit.Test
    public void dbCreationTest() {

        dataBaseManager.getAllNotes().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Note>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NotNull List<Note> notes) {
                        assertTrue(true);
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        assertFalse(e instanceof DataBaseManager.RoomDataBaseIsNotCreatedExeption);
                    }
                });
    }*/

    @org.junit.Test
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
//                .onErrorResumeNext(NotesApp.getInstance().getDatabaseManager().insertUser(new UserModel(name, pass))
//                        .andThen(NotesApp.getInstance().getDatabaseManager().getUserWithName(name)))
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



    @org.junit.Test
    public void dbGetUserWithNameTest() {

        String name = "Anton";

        dataBaseManager.getUserWithName(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<UserModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(UserModel userModel) {

                        assertTrue(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        assertTrue(false);
                    }
                });
    }



}
