package com.crocussativus.wallpaper;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.crocussativus.wallpaper.widgets.LogService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<String> imageArrayList;
    public static ArrayList<String> imageArrayList2;
    public static TextView tvName;
    public static TextView tvEmail;
    private static Context mContext;
    Intent mServiceIntent;
    private AppBarConfiguration mAppBarConfiguration;
    private LogService mLogService;

    public static Context getMainContext() {
        return MainActivity.mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.mContext = getApplicationContext();
        imageArrayList = new ArrayList<String>();
        imageArrayList2 = new ArrayList<String>();

        mLogService = new LogService();
        mServiceIntent = new Intent(this, mLogService.getClass());
        if (!isMyServiceRunning(mLogService.getClass())) {
            startService(mServiceIntent);
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Image");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.d("Data", "Data is change: " + dataSnapshot);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                 Log.w("Data", "Failed to read value.", databaseError.toException());
            }


        });
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                imageArrayList.add(dataSnapshot.getValue().toString());
                Log.i("DataAdd", "Data Add: "+ dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        for (int i = 14; i <= 16; i++) {
//            myRef.child(i+"").setValue("Value " + i);
//        }
        DatabaseReference myRef2 = database.getReference("Users");
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Log.d("Data", "Data is change: " + dataSnapshot);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                 Log.w("Data", "Failed to read value.", databaseError.toException());
            }


        });
        myRef2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                imageArrayList2.add(dataSnapshot.getValue().toString());
                Log.i("DataAdd", "Data Add: "+ dataSnapshot.getValue());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_featured,
                R.id.nav_donate, R.id.nav_infor, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        tvName = headerView.findViewById(R.id.yourName);
        tvEmail = headerView.findViewById(R.id.yourEmail);

        changeInforNavHeader();
    }

    public void changeInforNavHeader() {

        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                // Stuff that updates the UI
                //Log.i("Count", "=====Settings=====  ");

                String name = SettingsActivity.AppFragment.getYourName();
                if (name != null) {
                    //Log.d("Setting",name);
                    tvName.setText(name);
                } else {
                    tvName.setText("Meow");
                }
                String mail = SettingsActivity.AppFragment.getYourEmail();
                if (mail != null) {
                    //Log.d("Setting",mail);
                    tvEmail.setText(mail);
                } else {
                    tvEmail.setText("Change at settings screen!");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                //Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i("Service status", "Not running");
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        super.onDestroy();
    }

}
