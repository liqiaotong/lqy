package com.example.system.oms.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.system.oms.R;
import com.example.system.oms.greendao.gen.DaoManager;
import com.example.system.oms.greendao.gen.OrderDao;
import com.example.system.oms.greendao.gen.entity.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowOrderActivity extends AppCompatActivity {
    private static final String TAG = "ShowOrderActivity";
    TextView orderId;
    TextView orderName;
    TextView number;
    TextView price;
    TextView totalPrice;
    TextView customer;
    TextView customerPhone;
    TextView dateTime;
    private Button returnButton;
    private SimpleAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_order);
        Log.e(TAG, "onCreate");
        initView();
        getData();
//        initListener();
//        updateList();
//        initAdapter();


    }

    private void initView() {

        orderId = findViewById(R.id.tv_info_id1);
        orderName = findViewById(R.id.tv_info_ordername1);
        number = findViewById(R.id.tv_info_number1);
        price = findViewById(R.id.tv_info_price1);
        totalPrice = findViewById(R.id.tv_info_totalprice1);
        customer = findViewById(R.id.tv_info_customer1);
        customerPhone = findViewById(R.id.tv_info_customerphone1);
        dateTime = findViewById(R.id.tv_info_datetime1);
        returnButton = findViewById(R.id.back_to_list_id);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getData() {
        Intent intent = getIntent();
        Long id = intent.getLongExtra("id", 0);
        if (id == 0) {
            Toast.makeText(this, "获取Id失败", Toast.LENGTH_SHORT).show();
        } else {
            Order order = DaoManager.getTable().getOrderDao().load(id);
            orderId.setText(order.getId().toString());
            orderName.setText(order.getOrderName());
            number.setText(order.getNumber());
            price.setText(order.getPrice().toString());
            totalPrice.setText(order.getTotalPrice().toString());
            customer.setText(order.getCustomer());
            customerPhone.setText(order.getCustomPhone().toString());
            dateTime.setText(order.getDataTime());
        }
    }


//    private List<Map<String, String>> ordersMap = new ArrayList<>();
//
//    private void updateList() {
//        List<Order> orders = DaoManager.getTable().getOrderDao().loadAll();
//        ordersMap.clear();
//        if (orders != null) {
//            for (int i = 0; i < orders.size(); i++) {
//                Order order = orders.get(i);
//                Map<String, String> map = new HashMap<>();
//                map.put("id", order.getId().toString());
//                map.put("name", order.getOrderName());
//                map.put("number", order.getNumber());
//                map.put("price", order.getPrice().toString());
//                map.put("totalPrice", order.getTotalPrice().toString());
//                map.put("customer", order.getCustomer());
//                map.put("customerPhone", order.getCustomPhone().toString());
//                map.put("date", order.getDateTime());
//                ordersMap.add(map);
//            }
//        }
//
//    }
//
//    private void initAdapter() {
//        String[] keys = new String[]{"id", "name", "number", "price", "totalPrice", "customer", "customerPhone", "date"};
//        int[] ress = new int[]{R.id.tv_info_id1, R.id.tv_info_ordername1,
//                R.id.tv_info_number1, R.id.tv_info_price1, R.id.tv_info_totalprice1, R.id.tv_info_customer1
//                , R.id.tv_info_customerphone1, R.id.tv_info_datetime1};
//        adapter = new SimpleAdapter(this, ordersMap, R.layout.info_order, keys, ress);
//    }
//
//    public void initListener() {
//        returnButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ShowOrderActivity.this, OrderActivity.class);
//                Toast.makeText(ShowOrderActivity.this, "跳转成功", Toast.LENGTH_SHORT).show();
//                startActivity(intent);
//            }
//        });
//
//    }

}



