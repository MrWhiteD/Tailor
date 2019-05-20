package com.example.tailor0;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.tailor0.entity.Customer;
import com.example.tailor0.fragments.Customers;
import com.example.tailor0.fragments.Orders;
import com.example.tailor0.fragments.Sketches;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Orders forders;
    Customers fcustomers;
    Sketches fsketches;

    public static final int NEW_CUSTOMER_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPD_CUSTOMER_ACTIVITY_REQUEST_CODE = 2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
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
            fragmentTransaction.replace(R.id.container,forders);
        } else if (id == R.id.nav_customers) {
            fragmentTransaction.replace(R.id.container,fcustomers);
        } else if (id == R.id.nav_sketchers) {
            fragmentTransaction.replace(R.id.container,fsketches);
        } fragmentTransaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == NEW_CUSTOMER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
//            Customer customer = new Customer();
//            customer.fio = data.getStringExtra("FIO");
//            customer.phone = data.getStringExtra("Phone");
//            customer.notes = data.getStringExtra("Notes");
//            customer.email = data.getStringExtra("Email");
//            customerViewModel.insert(customer);
//        }
//    }

}
