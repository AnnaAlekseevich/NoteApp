package com.example.notes.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.notes.R;
import com.example.notes.models.NoteType;
import com.example.notes.ui.activities.useractivities.LoginActivity;
import com.example.notes.utils.PreferenceManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import static com.example.notes.ui.activities.NoteActivity.ARG_NoteType;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    ViewPager2 viewPager;
    private TextView tvUser;
    private ImageView logout;
    final String MENU_CONTEXT = "DELETE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        drawerLayout = findViewById(R.id.drawerLayout);
        tvUser = drawerLayout.findViewById(R.id.idUnknown);
        tvUser.setText(PreferenceManager.getLastUserName());

        logout = drawerLayout.findViewById(R.id.idLogout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager = findViewById(R.id.view_pager);
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

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(ViewPagerAdapter.getPageTitle(position))
        ).attach();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
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
                viewPager.setCurrentItem(4);
                message = "Basket";
                //
                break;
            case R.id.idFavorites:
                message = "Favorites";
                viewPager.setCurrentItem(5);
                Toast.makeText(this, "Favorites", Toast.LENGTH_SHORT).show();
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


    /*View.OnClickListener viewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupMenu(v);
        }
    };*/

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
                Toast.makeText(getApplicationContext(), "onDismiss",
                        Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

}
