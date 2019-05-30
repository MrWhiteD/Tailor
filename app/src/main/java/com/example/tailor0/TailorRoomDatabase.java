package com.example.tailor0;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.tailor0.dao.CustomerDao;
import com.example.tailor0.dao.HandbkM2PDao;
import com.example.tailor0.dao.HandbkMerkiDao;
import com.example.tailor0.dao.OrderDao;
import com.example.tailor0.dao.ProductTypeDao;
import com.example.tailor0.entity.Customer;
import com.example.tailor0.entity.HandbkM2P;
import com.example.tailor0.entity.HandbkMerki;
import com.example.tailor0.entity.Order;
import com.example.tailor0.entity.ProductType;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

@Database(entities = {Order.class, Customer.class, ProductType.class, HandbkMerki.class, HandbkM2P.class}, version = 7, exportSchema = false)
public abstract class TailorRoomDatabase extends RoomDatabase {
    public abstract OrderDao orderDao();
    public abstract CustomerDao customerDao();
    public abstract ProductTypeDao productTypeDao();
    public abstract HandbkMerkiDao handbkMerkiDao();
    public abstract HandbkM2PDao handbkM2PDao();

    private static volatile TailorRoomDatabase INSTANCE;
    static Context mContext;

    public static TailorRoomDatabase getDatabase(final Context context) {
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
        mContext = context;
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
        private final ProductTypeDao productTypeDao;
        private final HandbkMerkiDao handbkMerkiDao;
        private final HandbkM2PDao handbkM2PDao;

        PopulateDbAsync(TailorRoomDatabase db) {
            orderDao = db.orderDao();
            customerDao = db.customerDao();
            productTypeDao = db.productTypeDao();
            handbkMerkiDao = db.handbkMerkiDao();
            handbkM2PDao = db.handbkM2PDao();
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

            // TODO: 27.05.2019 сделать проверку наличия записей в существующей базе и не удалять все

            /*Первоначальное заполнение таблицы типовых изделий*/
            productTypeDao.deleteAll();
            ProductType productType = new ProductType();
            productType.id = 1;
            productType.name = "Юбка";
            productType.notes = "Юбка короткая, длинная, юбка-пояс";
            productTypeDao.insert(productType);

            productType = new ProductType();
            productType.id = 2;
            productType.name = "Брюки";
            productType.notes = "Брюки, бриджи ла лала ";
            productTypeDao.insert(productType);

            /*Первоначальное заполнение таблицы клиентов*/
            customerDao.deleteAll();
            Customer customer = new Customer();
            customer.id = 1;
            customer.fio = "Петров";
            customerDao.insert(customer);

            customer = new Customer();
            customer.id = 2;
            customer.fio = "Иванов";
            customerDao.insert(customer);

            /*Первоначальное заполнение таблицы мерок*/
            handbkMerkiDao.deleteAll();
            Resources res = mContext.getResources();
            TypedArray ta = res.obtainTypedArray(R.array.wmerki);
            String[] array = new String[ta.length()];
            for (int i = 0; i < ta.length(); i++) {
                HandbkMerki handbkMerki = new HandbkMerki();
                try {
                    JSONObject jsonObject = new JSONObject(ta.getString(i));
                    handbkMerki.id = jsonObject.getLong("id");
                    handbkMerki.name = jsonObject.getString("name");
                    handbkMerki.short_name = jsonObject.getString("short_name");
                    handbkMerki.description = jsonObject.getString("description");
                    handbkMerkiDao.insert(handbkMerki);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            /*Первоначальное заполнение таблицы м2п (продукт-мерки)*/
            handbkM2PDao.deleteAll();
            ta = res.obtainTypedArray(R.array.m2p);
            array = new String[ta.length()];
            for (int i = 0; i < ta.length(); i++) {
                HandbkM2P handbkM2P = new HandbkM2P();
                try {
                    JSONObject jsonObject = new JSONObject(ta.getString(i));
                    handbkM2P.prod_id = jsonObject.getLong("prod_id");
                    handbkM2P.merka_id = jsonObject.getLong("merka_id");
                    handbkM2PDao.insert(handbkM2P);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}
