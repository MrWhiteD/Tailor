package com.example.tailor0;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.tailor0.fragments.Customers;
import com.example.tailor0.fragments.HandbkMerkiFragment;
import com.example.tailor0.fragments.HandbkProdFragment;
import com.example.tailor0.fragments.Orders;
import com.example.tailor0.fragments.Sketches;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Orders forders;
    Customers fcustomers;
    Sketches fsketches;
    HandbkProdFragment fhandbk_prod;
    HandbkMerkiFragment fhandbk_merki;

            Toolbar toolbar;
    public static final int NEW_CUSTOMER_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPD_CUSTOMER_ACTIVITY_REQUEST_CODE = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Заказы");

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        forders = new Orders();
        fcustomers = new Customers();
        fsketches = new Sketches();

//        fhandbk_merki = new HandbkProdFragment("merki");
//        fhandbk_prod = new HandbkProdFragment("prod");
//        fhandbk_merki = HandbkProdFragment.newInstance("merki");
        fhandbk_merki = new HandbkMerkiFragment();
        fhandbk_prod = HandbkProdFragment.newInstance("prod");

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container,forders);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (id == R.id.nav_orders) {
//            Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
            toolbar.setTitle("Заказы");
            fragmentTransaction.replace(R.id.container,forders);
        } else if (id == R.id.nav_customers) {
            toolbar.setTitle("Клиенты");
            fragmentTransaction.replace(R.id.container,fcustomers);
        } else if (id == R.id.nav_sketchers) {
            toolbar.setTitle("Эскизы");
            fragmentTransaction.replace(R.id.container,fsketches);
        } else if (id == R.id.nav_dictm) {
            toolbar.setTitle("Справочник мерок");
            fragmentTransaction.replace(R.id.container,fhandbk_merki);
        } else if (id == R.id.nav_dict_prod) {
            toolbar.setTitle("Справочник изделий");
            fragmentTransaction.replace(R.id.container,fhandbk_prod);
        }
        fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
