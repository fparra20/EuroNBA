package com.example.euronba.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.euronba.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class StandingsPOActivity extends AppCompatActivity {

    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standings_poactivity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView nv = (NavigationView) findViewById(R.id.navigation_view);

        setSupportActionBar(toolbar);

        nv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.menu_players) {
                            Intent intent = new Intent(StandingsPOActivity.this, PlayerListActivity.class);
                            startActivity(intent);
                        }
                        if (id == R.id.menu_home) {
                            Intent intent = new Intent(StandingsPOActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        if (id == R.id.menu_teams) {
                            Intent intent = new Intent(StandingsPOActivity.this, TeamListActivity.class);
                            startActivity(intent);
                        }

                        if (id == R.id.menu_standings) {
                            Intent intent = new Intent(StandingsPOActivity.this, StandingsPOActivity.class);
                            startActivity(intent);
                        }

                        if (id == R.id.menu_playoffs) {
                            Intent intent = new Intent(StandingsPOActivity.this, PlayOffsActivity.class);
                            startActivity(intent);
                        }
                        finish();
                        return true;
                    }
                });

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Creamos el adaptador para el viewpager
        SectionsPagerAdapter pagerAdapter =
                new SectionsPagerAdapter(getSupportFragmentManager());

        // Creamos el viewpager
        ViewPager pager = (ViewPager) findViewById(R.id.pager);

        // Vinculamos el viewpager con el adaptador
        pager.setAdapter(pagerAdapter);

        // Creamos un tablayout
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(pager);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // Crearemos las dos pestañas y y que cada una muestre un contenido fragment
    private class SectionsPagerAdapter extends FragmentPagerAdapter{

        public SectionsPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new EastStandingsFragment();
                case 1:
                    return new WestStandingsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "EAST";
                case 1:
                    return "WEST";
            }
            return null;
        }
    }


}