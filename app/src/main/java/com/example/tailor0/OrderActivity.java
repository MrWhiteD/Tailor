package com.example.tailor0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderActivity extends AppCompatActivity {
    private static final String EXTRA_ORDER_ID = "order_id";

    private Long orderId;

    public static Intent newIntent(Context context, Long orderId){
        Intent i = new Intent(context, OrderActivity.class);
        i.putExtra(EXTRA_ORDER_ID, orderId);
        return i;
    }

    private TextView orderName, orderSurname, orderType, orderNumber, orderStartDate, orderFirstExample, orderSecondExample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderId = getIntent().getLongExtra(EXTRA_ORDER_ID, -1);

        orderName = findViewById(R.id.order_name);
        orderSurname = findViewById(R.id.order_surname);
        orderType = findViewById(R.id.order_type);
        orderNumber = findViewById(R.id.order_number);
        orderStartDate = findViewById(R.id.order_number);
        orderFirstExample = findViewById(R.id.order_first_example);
        orderSecondExample = findViewById(R.id.order_second_example);

        Toolbar tool = findViewById(R.id.order_toolbar);
        if(orderId == -1){
            tool.setTitle("Новый заказ");
        } else {
            tool.setTitle("Редактируем заказ");
        }

        Button button = findViewById(R.id.order_save_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSave();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Вот здесь нужно из базы вытащить этот заказ и по нему заполнить поля, если, конечно, это не создание нового заказа
        if(orderId != null){
            // Вытаскиваем и заполняем
        }
    }

    private void onSave() {
        // Так как при возврате к фрагменту будет вызван метод onResume фрагмента, и снова будет выборка из базы
        // то я обычно сохранение в БД делаю в активити работы с конкретным объектом
        // Т.е. здесь нужно создать новый объект или обновить существующий и просто завершаем активити
        // стек активити восстановится автоматом

        finish();
    }


}
