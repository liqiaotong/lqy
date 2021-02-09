package com.example.system.oms.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.system.oms.R;
import com.example.system.oms.greendao.gen.DaoManager;
import com.example.system.oms.greendao.gen.UserDao;
import com.example.system.oms.greendao.gen.entity.User;

public class LoginActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;
    private Button login;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
    }

    public void initView() {
        account = (EditText) findViewById(R.id.et_login_account);
        password = (EditText) findViewById(R.id.et_login_password);
        login = (Button) findViewById(R.id.bt_login_login);
        register = findViewById(R.id.bt_login_register);
    }

    public void initListener() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = account.getText().toString();
                String pass = password.getText().toString();

                User user = DaoManager.getTable().getUserDao().queryBuilder().where
                        (UserDao.Properties.Account.eq(name),UserDao.Properties.Password.eq(pass)).unique();

                boolean flag = user!=null;

                if (flag) {
                    Log.i("TAG", "登录成功");
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this,OrderActivity.class);
                    startActivity(intent);
                } else {
                    Log.i("TAG", "登录失败");
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


}