package com.example.notes.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.notes.NotesApp;
import com.example.notes.R;
import com.example.notes.models.Note;
import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.useractivities.LoginActivity;
import com.example.notes.utils.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.notes.ui.activities.NoteActivity.ARG_NoteType;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    ViewPager2 viewPager;
    private TextView tvUser;
    private ImageView logout;
    public final static String ARG_NOTE_ID = "arg_note_intent_main";
    public ViewPagerAdapter adapter;

    private static final int QUERY_KEY = 1000;
    private static final long DELAY_TIME_FOR_START_SEARCH = 2000;

    private Handler searchHandler = new Handler() {

        @Override
        public void handleMessage(@NonNull @NotNull Message msg) {
            super.handleMessage(msg);
            Log.d("SearchHandle", "Message = " + msg);
            adapter.updateQuery((String) msg.obj);
            //adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        drawerLayout = findViewById(R.id.drawerLayout);
        tvUser = drawerLayout.findViewById(R.id.idUnknown);
        tvUser.setText(PreferenceManager.getLastUserName());

        logout = drawerLayout.findViewById(R.id.idLogout);

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view);

            }
        });

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(ViewPagerAdapter.getPageTitle(position))
        ).attach();

        openNoteActivityIfNotificationExistForCurrentUser(getIntent().getLongExtra(MainActivity.ARG_NOTE_ID, -1));

        Log.d("noteIdNotification", "noteIdNotification in MainActivity = "
                + getIntent().getLongExtra(MainActivity.ARG_NOTE_ID, -1));


    }

    private void openNoteActivityIfNotificationExistForCurrentUser(long noteId) {
        if (noteId > 0) {
            NotesApp.getInstance().getDatabaseManager().getNoteById(noteId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new SingleObserver<Note>() {
                        @Override
                        public void onSubscribe(@NotNull Disposable d) {

                        }

                        @Override
                        public void onSuccess(@NotNull Note note) {
                            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                            intent.putExtra(NoteActivity.ARG_NOTE, note);
                            startActivity(intent);
                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                        }
                    });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.view_search).getActionView();

// Здесь можно указать будет ли строка поиска изначально развернута или свернута в значок
        searchView.setIconifiedByDefault(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearhQueryUprate(newText);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    private void handleSearhQueryUprate(String query) {
        Log.d("Search", "updated. new query= " + query);
        searchHandler.removeMessages(QUERY_KEY);
        searchHandler.sendMessageDelayed(Message.obtain(searchHandler, QUERY_KEY, query), DELAY_TIME_FOR_START_SEARCH);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = "";
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.view_headline:
                item.setChecked(!item.isChecked());
                item.setIcon(!item.isChecked() ? R.drawable.ic_view_module_black_24dp : R.drawable.ic_view_headline_black_24dp);
                break;
        }
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDrawerItemClick(View v) {
        String message = "";
        switch (v.getId()) {
            case R.id.idLogout:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case R.id.idAllNotes:
                viewPager.setCurrentItem(0);
                message = "All Notes";
                break;
            case R.id.id_Notes:
                viewPager.setCurrentItem(1);
                message = "Notes";
                break;
            case R.id.idLists:
                viewPager.setCurrentItem(2);
                message = "Lists";
                break;
            case R.id.idReminder:
                viewPager.setCurrentItem(3);
                message = "Reminder";
                break;
            case R.id.idBasket:
                //viewPager.setCurrentItem(4);
                //message = "Basket";
                //
                Intent intentBasket = new Intent(MainActivity.this, BasketActivity.class);
                intentBasket.putExtra(ARG_NoteType, NoteType.Text);
                startActivity(intentBasket);
                break;
            case R.id.idFavorites:
                Intent intentFavorite = new Intent(MainActivity.this, FavoriteActivity.class);
                intentFavorite.putExtra(ARG_NoteType, NoteType.Text);
                startActivity(intentFavorite);
                break;
        }
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    View.OnClickListener viewClickListener = v -> showPopupMenu(v);

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popup_menu);

        popupMenu
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent = new Intent(MainActivity.this, NoteActivity.class);

                        switch (item.getItemId()) {
                            case R.id.menu1:
                                intent.putExtra(ARG_NoteType, NoteType.Text);
                                startActivity(intent);//fixme startActivityForResult
                                /*getSupportFragmentManager().beginTransaction()
                                        .add(R.id.fragment_note, MainActivity.class, null)
                                        .commit();*/
                                return true;
                            case R.id.menu2:
                                intent.putExtra(ARG_NoteType, NoteType.List);
                                startActivity(intent);//fixme startActivityForResult
                                return true;
                            case R.id.menu3:
                                intent.putExtra(ARG_NoteType, NoteType.Reminder);
                                startActivity(intent);//fixme startActivityForResult
                                return true;
                            default:
                                return false;
                        }
                    }
                });

        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                //Toast.makeText(getApplicationContext(), "onDismiss",
                //Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

}
