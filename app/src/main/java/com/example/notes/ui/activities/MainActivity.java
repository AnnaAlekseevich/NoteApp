package com.example.notes.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.notes.R;
import com.example.notes.models.NoteType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import static com.example.notes.ui.activities.NoteActivity.ARG_NoteType;

public class MainActivity extends AppCompatActivity {

    private String[] names = new String[] {"first", "second"};

    private DrawerLayout drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        drawerLayout = findViewById(R.id.drawerLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),getLifecycle());
        ViewPager2    viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //for PopupMenu



        findViewById(R.id.floatingActionButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // create notes
                //Intent intent = new Intent(MainActivity.this, NoteActivity.class);
                //startActivity(intent);//fixme startActivityForResult
                showPopupMenu(view);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = "";
        switch (item.getItemId()){
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                break;
            case R.id.search:
                message = "search";
                break;
            case R.id.sort:
                message = "sort";
                break;
            case R.id.view_headline:
                item.setChecked(!item.isChecked());
                item.setIcon(item.isChecked()? R.drawable.ic_view_module_black_24dp :  R.drawable.ic_view_headline_black_24dp);
                break;

        }
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDrawerItemClick(View v){
        String message = "";
        switch (v.getId()){
            case R.id.id_Notes:
                message = "Notes";
                break;
            case R.id.idLists:
                message = "Lists";
            break;
            case R.id.idReminder:
                message = "Reminder";
            break;
        }
        if (!message.isEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };

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
                Toast.makeText(getApplicationContext(), "onDismiss",
                        Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

}
