package com.example.system.oms.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.system.oms.R;
import com.example.system.oms.greendao.gen.DaoManager;
import com.example.system.oms.greendao.gen.DaoSession;
import com.example.system.oms.greendao.gen.OrderDao;
import com.example.system.oms.greendao.gen.entity.Order;

import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderSearch extends Activity implements OnClickListener {
    private static final Object TAG = "OrderSearch";
    private EditText nameText;
    private Button reButton;
    private Cursor cursor;
    private ListAdapter listAdapter;
    private ListView listView;
    private Order dao;
    private Button returnButton;
    private LinearLayout layout;
    private Button searchButton;
    private Executable query;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        initView();
//        initListiner();
//        Cursor myCursor = getContentResolver().query(Uri.parse(OrderDao.TABLENAME), null, null, null, null);
//        startManagingCursor(myCursor);


    }

    public void initView() {
        layout = findViewById(R.id.linersearch);
        nameText = findViewById(R.id.et_srarch);
        searchButton = findViewById(R.id.bn_sure_search);
        reButton = findViewById(R.id.bn_return);
        listView = findViewById(R.id.searchListView);
        returnButton = findViewById(R.id.return_id);
        reButton.setOnClickListener(this);
        returnButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        layout.setVisibility(View.GONE);
        returnButton.setVisibility(View.GONE);


    }


    //    public void initListiner() {
//        searchButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (view == searchButton) {
            reButton.setVisibility(View.GONE);
            searchButton.setVisibility(View.GONE);
            nameText.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            returnButton.setVisibility(View.VISIBLE);


            String name = nameText.getText().toString().trim();
//
//            Order order = new Order();
//            order.setOrderName(name);


            List<Order> oldOrders = DaoManager.getTable().getOrderDao().queryBuilder().
                    where(OrderDao.Properties.OrderName.like(name)).list();

//            Cursor cursor = DaoManager.getInstance().getDaoSession()
//                    .getOrderDao().getDatabase().rawQuery("select * from ", null);

            if (oldOrders == null || oldOrders.isEmpty()) {
                Toast.makeText(OrderSearch.this, "检索不到结果", Toast.LENGTH_SHORT).show();
                return;
            } else if (oldOrders != null) {
                //如果有所查询的信息，则将查询结果显示出来
                List<Map<String, String>> result = new ArrayList<>();
                for (int i = 0; i < oldOrders.size(); i++) {
                    Order od = oldOrders.get(i);
                    Map<String, String> map = new HashMap<>();
                    map.put("Id", od.getId().toString());
                    map.put("OrderName", od.getOrderName());
                    map.put("Number", od.getNumber());
                    map.put("Price", od.getPrice().toString());
                    map.put("TotalPrice", od.getTotalPrice().toString());
                    map.put("Customer", od.getCustomer());
                    map.put("CustomPhone", od.getCustomPhone().toString());
                    map.put("DataTime", od.getDateTime());
                    result.add(map);
                }


                String[] tags = new String[]{
                        "Id",
                        "OrderName",
                        "Number",
                        "Price",
                        "TotalPrice",
                        "Customer",
                        "CustomPhone",
                        "DataTime"
                };
                int[] ids = new int[]{
                        R.id.tv_order_id_find,
                        R.id.tv_order_name_find,
                        R.id.tv_order_number_find,
                        R.id.tv_order_price_find,
                        R.id.tv_order_total_find,
                        R.id.tv_order_customer_find,
                        R.id.tv_order_customer_phone_find,
                        R.id.tv_order_datetime_find};
                listAdapter = new SimpleAdapter(this, result, R.layout.find_order_list_item,
                        tags, ids);
                listView.setAdapter(listAdapter);
            }
        } else if (view == reButton | view == returnButton) {
            finish();
        }
    }

}


//    @Override
//    public void onClick(View v) {
//        if (v == searchButton) {
//            reButton.setVisibility(View.GONE);
//            searchButton.setVisibility(View.GONE);
//            nameText.setVisibility(View.GONE);
//            layout.setVisibility(View.VISIBLE);
//            String name = nameText.getText().toString().trim();
//            cursor =  DaoManager.getTable().getOrderDao().queryBuilder().where(OrderDao.Properties.OrderName.like(name));
//            if (!cursor.moveToFirst()) {
//                Toast.makeText(this, "没有查询到订单信息！", Toast.LENGTH_SHORT).show();
//            } else
////                如果有所查询的信息，则将查询结果显示出来
//                adapter = new SimpleCursorAdapter(this, R.layout.find_order_list_item,
//                        cursor, new String[] {"Id),
//                        "OrderName),
//                        "Number),
//                        "Price),
//                        "TotalPrice),
//                        "Customer),
//                        "CustomPhone),
//                        "DataTime)
//                },
//                        new int[] {
//                                R.id.tv_order_id_find,
//                                R.id.tv_order_name_find,
//                                R.id.tv_order_number_find,
//                                R.id.tv_order_price_find,
//                                R.id.tv_order_total_find,
//                                R.id.tv_order_customer_find,
//                                R.id.tv_order_customer_phone_find,
//                                R.id.tv_order_datetime_find
//                });
//            listView.setAdapter(adapter);







