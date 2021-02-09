package com.example.system.oms.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.system.oms.R;
import com.example.system.oms.greendao.gen.DaoManager;
import com.example.system.oms.greendao.gen.OrderDao;
import com.example.system.oms.greendao.gen.UserDao;
import com.example.system.oms.greendao.gen.entity.Order;
import com.example.system.oms.greendao.gen.entity.User;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Attributes;


public class AddOrderActivity extends Activity implements View.OnClickListener {

    TextView idText;
    EditText orderNameText;
    EditText numberText;
    EditText priceText;
    EditText totalPriceText;
    EditText customerText;
    EditText customerPhoneText;
    EditText dataTimeText;
    Button saveButton;
    Button resetButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_order_info);
        initView();
        initListener();
    }

    private void initView() {
        idText = (TextView) findViewById(R.id.tv_add_id1);
        orderNameText = (EditText) findViewById(R.id.et_add_name);
        numberText = (EditText) findViewById(R.id.et_add_number);
        priceText = findViewById(R.id.et_add_price);
        totalPriceText = findViewById(R.id.et_add_totalprice);
        customerText = findViewById(R.id.et_add_customer);
        customerPhoneText = findViewById(R.id.et_add_customphone);
        dataTimeText = (EditText) findViewById(R.id.et_add_datatime);
        saveButton = (Button) findViewById(R.id.btn_add_save);
        resetButton = (Button) findViewById(R.id.btn_add_reset);
    }

    private void initListener() {
        saveButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    //  * 得到当前的日期时间
    private String getCurrentDateTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    //  清空界面的数据176
    private void clearUIData() {
        orderNameText.setText("");
        numberText.setText("");
        priceText.setText("");
        totalPriceText.setText("");
        customerText.setText("");
        customerPhoneText.setText("");
        dataTimeText.setText("");
    }

    public void onClick(View v) {
        if (v == resetButton) {
            clearUIData();
        } else if (v == saveButton) {

            //String id=idText.getText().toString().trim();
            String name = orderNameText.getText().toString().trim();
            String number = numberText.getText().toString().trim();
            String price = priceText.getText().toString().trim();
            String totalPrice = totalPriceText.getText().toString().trim();
            String customer = customerText.getText().toString().trim();
            String customerPhone = customerPhoneText.getText().toString().trim();
            String dataTime = getCurrentDateTime();


            if (name.length() == 0) {
                Toast.makeText(this, "名字不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (number.length() == 0) {
                Toast.makeText(this, "数量不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (price.length() == 0) {
                Toast.makeText(this, "价格不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (totalPrice.length() == 0) {
                Toast.makeText(this, "总价格不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (customer.length() == 0) {
                Toast.makeText(this, "客户不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (customerPhone.length() == 0) {
                Toast.makeText(this, "联系电话不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            Order order = new Order(name, number, price, totalPrice, customer, customerPhone, dataTime);
            order.setOrderName(name);
            order.setNumber(number);
            order.setPrice(Integer.parseInt(price));
            order.setTotalPrice(Integer.parseInt(totalPrice));
            order.setCustomer(customer);
            order.setCustomPhone(Long.valueOf(customerPhone));
            order.setDataTime(dataTime);

            //插入数据库
            long insertId = DaoManager.getTable().getOrderDao().insertOrReplace(order);
            if (insertId != 0) {
                Toast.makeText(AddOrderActivity.this, "新增订单信息成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("isAddNewOrderSuccess", true);
                setResult(1001, intent);
                finish();
            } else {
                Toast.makeText(AddOrderActivity.this, "新增订单信息失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
