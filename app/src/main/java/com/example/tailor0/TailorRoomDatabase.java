package com.example.tailor0;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.tailor0.dao.CustomerDao;
import com.example.tailor0.dao.OrderDao;
import com.example.tailor0.entity.Customer;
import com.example.tailor0.entity.Order;

import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = {Order.class, Customer.class}, version = 3, exportSchema = false)
public abstract class TailorRoomDatabase extends RoomDatabase {
    public abstract OrderDao orderDao();
    public abstract CustomerDao customerDao();

    private static volatile TailorRoomDatabase INSTANCE;

    static TailorRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TailorRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TailorRoomDatabase.class, "tailor_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sTailorDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sTailorDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final OrderDao orderDao;
        private final CustomerDao customerDao;

        PopulateDbAsync(TailorRoomDatabase db) {
            orderDao = db.orderDao();
            customerDao = db.customerDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
            orderDao.deleteAll();

            Order order = new Order();

            order.cost = 100;
            order.dateStart = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
            order.note = "несколько слов о заказе";
            orderDao.insert(order);

//            customerDao.deleteAll();
//            Customer customer = new Customer();
//            customer.fio = "Петров";
//            customerDao.insert(customer);
//
//            customer = new Customer();
//            customer.fio = "Иванов";
//            customerDao.insert(customer);

            return null;
        }
    }
}
