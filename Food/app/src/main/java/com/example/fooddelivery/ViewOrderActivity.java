package com.example.fooddelivery;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Food;
import model.Order;

public class ViewOrderActivity extends AppCompatActivity {

    private ListView orderList;
    private ArrayList<Order> orders;
    private ArrayList<Map<String, String>> mylist;
    private SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);
        orderList = (ListView) findViewById(R.id.orders_list);
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (Client.socket.isConnected()) {
                    mylist = new ArrayList<>();
                    orders = LoginActivity.client.queryOrder();
                    for (Order order : orders) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("order_id", "Order ID: " + order.getID());
                        map.put("status", "Status: " + order.getStatus());
                        StringBuilder orderedFoods = new StringBuilder();
                        orderedFoods.append("Ordered Foods: \n");
                        for (Map.Entry<Food, Integer> food : order.getIngredients().entrySet()) {
                            orderedFoods.append(food.getKey().getName() + "    $");
                            orderedFoods.append(food.getKey().getPrice() + "    x");
                            orderedFoods.append(food.getValue() + "\n");
                        }
                        map.put("ordered_foods", orderedFoods.toString());
                        map.put("total", "Total Price: $" + new DecimalFormat("#.00").format(order.getTotal()));
                        mylist.add(map);
                        adapter = new SimpleAdapter(ViewOrderActivity.this,
                                mylist,
                                R.layout.order_list_layout,
                                new String[]{"order_id", "status", "ordered_foods", "total"},
                                new int[]{R.id.order_id, R.id.status, R.id.ordered_foods, R.id.total});
                        Message message = new Message();
                        message.what = 0;
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    orderList.setAdapter(adapter);
                    break;
                default:
                    break;
            }
        }
    };
}
