package com.example.system.oms.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.system.oms.R;
import com.example.system.oms.greendao.gen.DaoManager;
import com.example.system.oms.greendao.gen.OrderDao;
import com.example.system.oms.greendao.gen.entity.Order;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.greendao.annotation.Id;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView listView;
    private static final String TAG = "OrderActivity";
    TextView orderNameText;
    TextView numberText;
    private OrderDao dao;
    private List<Long> list;
    private Button addOrder;
    private LinearLayout layout;
    private Button searchButton;
    private Button selectButton;
    private Button deleteButton;
    private Button selectAllButton;
    private Button canleButton;
    private RelativeLayout relativeLayout;
    private SimpleAdapter adapter;
    private Boolean isDeleteList = false;
    private Order order;
    private Button lookButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_order);
        Log.e(TAG, "onCreate");
        list = new ArrayList<Long>();
        initAdapter();
        initView();
        initListener();
        updateList();
        order = new Order();


    }


    private void initView() {

        addOrder = findViewById(R.id.btn_add_order);
        orderNameText = findViewById(R.id.tv_order_name);
        layout = findViewById(R.id.showLiner);
        searchButton = findViewById(R.id.btn_search_order);
        selectButton = findViewById(R.id.btn_select_order);
        deleteButton = findViewById(R.id.bn_delete);
        selectAllButton = findViewById(R.id.bn_selectall);
        canleButton = findViewById(R.id.bn_canel);
        lookButton = findViewById(R.id.bt_order_look);
        listView = findViewById(R.id.list_view);
        listView.setAdapter(adapter);
    }

    private List<Order> orders;
    private List<Map<String, String>> ordersMap = new ArrayList<>();

    private void updateList() {
        orders = DaoManager.getTable().getOrderDao().loadAll();
        ordersMap.clear();
        if (orders != null) {
            for (int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                Map<String, String> map = new HashMap<>();
                map.put("id", order.getId().toString());
                map.put("name", order.getOrderName());
                map.put("number", order.getNumber());
                map.put("price", String.valueOf(order.getPrice()));
                map.put("totalPrice", String.valueOf(order.getTotalPrice()));
                map.put("customer", order.getCustomer());
                map.put("customerPhone", String.valueOf(order.getCustomPhone()));
                map.put("date", order.getDateTime());
                ordersMap.add(map);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        //主页面显示订单列表
        String[] keys = new String[]{"id", "name", "number", "date"};
        int[] ress = new int[]{R.id.tv_order_id, R.id.tv_order_name, R.id.tv_order_number, R.id.tv_order_date};
        adapter = new SimpleAdapter(this, ordersMap, R.layout.list_order_item, keys, ress) {
            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                lookButton = view.findViewById(R.id.bt_order_look);
                if (lookButton != null) {
                    lookButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (orders != null) {
                                Long id = orders.get(position).getId();
                                Intent intent = new Intent(OrderActivity.this, ShowOrderActivity.class);
                                intent.putExtra("id", id);
                                startActivity(intent);
                            }
                        }
                    });
                }
                return view;
            }
        };
    }

    private void initListener() {
        addOrder.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        selectButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        canleButton.setOnClickListener(this);
        selectAllButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // 跳转到添加信息的界面
        if (v == addOrder) {
            startActivityForResult(new Intent(OrderActivity.this, AddOrderActivity.class), 101);
        } else if (v == searchButton) {
            // 跳转到查询界面
            startActivity(new Intent(OrderActivity.this, OrderSearch.class));
        } else if (v == selectButton) {
            //跳转到选择界面
            isDeleteList = !isDeleteList;
            if (isDeleteList) {
                checkOrAllCheckboxs(true);
            } else {
                showOrHiddenCheckBoxs(false);
            }
        } else if (v == deleteButton) {
            // 删除数据
            if (list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    long id = list.get(i);
                    Log.e(TAG, "delete id=" + id);
                    DaoManager.getTable().getOrderDao().deleteByKey(id);
                }
                DaoManager.getInstance().closeHelper();
                updateList();
            }
        } else if (v == canleButton) {
            // 点击取消，回到初始界面
            updateList();
            layout.setVisibility(GONE);
            isDeleteList = !isDeleteList;
        } else if (v == selectAllButton) {
            // 全选，如果当前全选按钮显示是全选，则在点击后变为取消全选，如果当前为取消全选，则在点击后变为全选
            selectAllMethods();
        }

    }

    // 创建菜单
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this); //getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
    }


    // 对菜单中的Item按钮添加响应事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int item_id = item.getItemId();
        order = (Order) listView.getTag();
        Log.v(TAG, "TestSQLite++++order+" + listView.getTag() + "");
        final long order_id = order.getId();
        Intent intent = new Intent();
        Log.v(TAG, "TestSQLite+++++++id" + order_id);
        switch (item_id) {
   /* 添加
   case R.id.add:
    startActivity(new Intent(this, AddStudentActivity.class));
    break;*/
            // 删除
            case R.id.delete:
                deleteStudentInformation(order_id);
                break;
            case R.id.look:
                // 查看学生信息
                Log.v(TAG, "TestSQLite+++++++look" + order + "");
                intent.putExtra("order", String.valueOf(order));
                intent.setClass(this, ShowOrderActivity.class);
                this.startActivity(intent);
                break;
            case R.id.write:
                // 修改学生信息
                intent.putExtra("order", String.valueOf(order));
                intent.setClass(this, AddOrderActivity.class);
                this.startActivity(intent);
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Order order = getOrderFromView(view, id);
        listView.setTag(order);
        registerForContextMenu(listView);
        return false;
    }

    // 点击一条记录是触发的事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        if (!isDeleteList) {
            order = getOrderFromView(view, id);
            Log.e(TAG, "student*****" + getOrderFromView(view, id));
            Intent intent = new Intent();
            intent.putExtra("order", String.valueOf(order));
            intent.setClass(this, ShowOrderActivity.class);
            this.startActivity(intent);
        } else {
            CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
            box.setChecked(!box.isChecked());
            list.add(id);
            deleteButton.setEnabled(box.isChecked());
        }
    }


    //全选或取消全选
    private void checkOrAllCheckboxs(boolean b) {
        int childCount = listView.getChildCount();
        Log.e(TAG, "list child size=" + childCount);
        for (int i = 0; i < childCount; i++) {
            View view = listView.getChildAt(i);
            if (view != null) {
                CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
                box.setChecked(!b);
            }
        }
        showOrHiddenCheckBoxs(true);
    }


    //显示或隐藏菜单
    private void showOrHiddenCheckBoxs(boolean b) {
        int childCount = listView.getChildCount();
        Log.e(TAG, "list child size=" + childCount);
        for (int i = 0; i < childCount; i++) {
            View view = listView.getChildAt(i);
            if (view != null) {
                CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
                int visible = b ? View.VISIBLE : GONE;
                box.setVisibility(visible);
                layout.setVisibility(visible);
                deleteButton.setEnabled(false);
            }
        }

    }

    // 自定义一个利用对话框形式进行数据的删除
    private void deleteStudentInformation(final long delete_id) {
        // 利用对话框的形式删除数据
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("学员信息删除")
                .setMessage("确定删除所选记录?")
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DaoManager.getTable().getOrderDao().deleteByKey(delete_id);
                        layout.setVisibility(View.GONE);
                        isDeleteList = !isDeleteList;
                        updateList();
                        Toast.makeText(OrderActivity.this, "删除成功!",
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


    // 点击全选事件时所触发的响应
    private void selectAllMethods() {
        // 全选，如果当前全选按钮显示是全选，则在点击后变为取消全选，如果当前为取消全选，则在点击后变为全选
        if (selectAllButton.getText().toString().equals("全选")) {
            int childCount = listView.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View view = listView.getChildAt(i);
                if (view != null) {
                    CheckBox box = (CheckBox) view.findViewById(R.id.cb_box);
                    box.setChecked(true);
                    deleteButton.setEnabled(true);
                    selectAllButton.setText("取消全选");
                }
            }
        } else if (selectAllButton.getText().toString().equals("取消全选")) {
            checkOrAllCheckboxs(true);
            deleteButton.setEnabled(false);
            selectAllButton.setText("全选");
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 1001) {
            Boolean isAddNewOrderSuccess = data.getBooleanExtra("isAddNewOrderSuccess", false);
            if (isAddNewOrderSuccess) {
                updateList();
            }
        }
    }

    public Order getOrderFromView(View view, long id) {
        TextView nameView = (TextView) view.findViewById(R.id.tv_order_name);
        TextView numberView = (TextView) view.findViewById(R.id.tv_order_number);
        TextView priceView = (TextView) view.findViewById(R.id.tv_order_price);
        TextView totalView = (TextView) view.findViewById(R.id.tv_order_totalPrice);
        TextView customerView = (TextView) view.findViewById(R.id.tv_order_customer);
        TextView customerPhoneView = (TextView) view.findViewById(R.id.tv_order_customerPhone);
        TextView dataView = (TextView) view.findViewById(R.id.tv_order_date);

        String name = nameView.getText().toString();
        String number = numberView.getText().toString();
        Long price = Long.valueOf(priceView.getText().toString());
        Long totalPrice = Long.valueOf(totalView.getText().toString());
        String customer = customerView.getText().toString();
        Long customerPhone = Long.valueOf(customerPhoneView.getText().toString());
        String data = dataView.getText().toString();
        Order order = new Order(id, name, number, price, totalPrice, customer, customerPhone, data);
        return order;
    }


}
