package com.example.system.oms.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.system.oms.R;
import com.example.system.oms.greendao.gen.DaoManager;
import com.example.system.oms.greendao.gen.UserDao;
import com.example.system.oms.greendao.gen.entity.User;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    EditText account;
    EditText password;
    EditText age;
    RadioGroup sex;
    Button register;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initListener();
    }

    private void initView() {
        account = (EditText) findViewById(R.id.et_register_account);
        password = (EditText) findViewById(R.id.et_register_password);
        age = (EditText) findViewById(R.id.et_register_age);
        sex = (RadioGroup) findViewById(R.id.rg_register_sex);
        register = (Button) findViewById(R.id.bt_register_register);
    }

    private void initListener() {
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = account.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String agestr = age.getText().toString().trim();
                String sexstr = ((RadioButton) RegisterActivity.this.findViewById(sex.getCheckedRadioButtonId())).getText().toString();

                User user = new User();
                user.setAccount(name);
                user.setPassword(pass);
                user.setAge(Integer.parseInt(agestr));
                user.setSex(sexstr);

                List<User> oldUsers = DaoManager.getTable().getUserDao().queryBuilder().where(UserDao.Properties.Account.eq(name)).list();

                if(oldUsers!=null&&oldUsers.size()>0){
                    Toast.makeText(RegisterActivity.this, "用户已存在", Toast.LENGTH_LONG).show();
                    return;
                }

                long userId = DaoManager.getTable().getUserDao().insert(user);
                if(userId!=0) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}