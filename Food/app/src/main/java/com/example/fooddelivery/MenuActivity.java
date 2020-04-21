package com.example.fooddelivery;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button order;
    private Button viewOrderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        order = (Button) findViewById(R.id.order);
        viewOrderList = (Button) findViewById(R.id.view_order_list);
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        viewOrderList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this, ViewOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}
