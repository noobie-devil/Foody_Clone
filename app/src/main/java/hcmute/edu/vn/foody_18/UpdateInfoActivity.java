package hcmute.edu.vn.foody_18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.User;

public class UpdateInfoActivity extends AppCompatActivity {

    Button buttonChange, buttonLogout, buttonSave;
    EditText editAddress, editEmail, editName, editPassConfirm, editPassNew, editPassOld, editPhone;
    TextView textBack;

    DatabaseHandler db;
    SharedPreferences sharedPreferences;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        mapping();

        editAddress.setText(sharedPreferences.getString("address",  ""));
        editEmail.setText(sharedPreferences.getString(  "email",    ""));
        editName.setText(sharedPreferences.getString(   "userName", ""));
        editPhone.setText(sharedPreferences.getString(  "phone",    ""));
        String email = editEmail.getText().toString();

        textBack.setOnClickListener(view -> finish());
        buttonChange.setOnClickListener(view -> {
            String passOld = editPassOld.getText().toString().trim();
            String passNew = editPassNew.getText().toString().trim();
            String passCon = editPassConfirm.getText().toString().trim();

            if (passOld.equals("") || passNew.equals("") || passCon.equals("")) {
                Toast.makeText(UpdateInfoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
            } else if (passNew.equals(passCon)) {
                Boolean check = db.loginUser(email, passOld);
                if (check) {
                    user.setEmail(email);
                    user.setPassword(passNew);
                    db.changePassword(user);
                    Toast.makeText(UpdateInfoActivity.this, "Đã thay đổi mật khẩu", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(UpdateInfoActivity.this, "Mật khẩu không đúng", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(UpdateInfoActivity.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_LONG).show();
            }
        });
        buttonLogout.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();

            Toast.makeText(UpdateInfoActivity.this, "     Đã đăng xuất\nVui lòng đăng nhập lại", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(UpdateInfoActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
        buttonSave.setOnClickListener(view -> {
            String name = editName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String address = editAddress.getText().toString().trim();

            if (name.equals("") || phone.equals("") || address.equals("")) {
                Toast.makeText(UpdateInfoActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
            } else {
                user.setAddress(address);
                user.setEmail(email);
                user.setPhone(phone);
                user.setUserName(name);
                db.updateInfoUser(user);
                Toast.makeText(UpdateInfoActivity.this, "Cập nhật thành công", Toast.LENGTH_LONG).show();

                user = db.getInfoUser(email);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(    "userId",   user.getUserId());
                editor.putString( "userName", user.getUserName());
                editor.putString( "email",    user.getEmail());
                editor.putString( "address",  user.getAddress());
                editor.putString( "phone",    user.getPhone());
                editor.apply();

                Intent intent = new Intent(UpdateInfoActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    public void mapping() {
        buttonChange    = findViewById(R.id.updateInfoActivity_button_change);
        buttonLogout    = findViewById(R.id.updateInfoActivity_button_logout);
        buttonSave      = findViewById(R.id.updateInfoActivity_button_save);
        editAddress     = findViewById(R.id.updateInfoActivity_edittext_address);
        editEmail       = findViewById(R.id.updateInfoActivity_edittext_email);
        editName        = findViewById(R.id.updateInfoActivity_edittext_name);
        editPassConfirm = findViewById(R.id.updateInfoActivity_edittext_password_confirm);
        editPassNew     = findViewById(R.id.updateInfoActivity_edittext_password_new);
        editPassOld     = findViewById(R.id.updateInfoActivity_edittext_password_old);
        editPhone       = findViewById(R.id.updateInfoActivity_edittext_phone);
        textBack        = findViewById(R.id.updateInfoActivity_textview_back);

        db = DatabaseHandler.getInstance(this);
        sharedPreferences = getSharedPreferences("currentUser", Context.MODE_PRIVATE);
        user = new User();
    }
}