package hcmute.edu.vn.foody_18;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.User;

public class SignUpActivity extends AppCompatActivity {

    Button buttonRegister;
    EditText editName, editPhone, editAddress, editEmail, editPass, editRePass;
    TextView textBack, textLogin;

    DatabaseHandler db;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mapping();

        textBack.setOnClickListener(view -> finish());
        buttonRegister.setOnClickListener(view -> {
            String name = editName.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String address = editAddress.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String pass = editPass.getText().toString().trim();
            String repass = editRePass.getText().toString().trim();

            if (name.equals("") || phone.equals("") || address.equals("") || email.equals("") || pass.equals("") || repass.equals("")) {
                Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
            } else if (pass.equals(repass)) {
                Boolean check = db.checkExistUser(email);
                if (!check) {
                    user.setUserName(name);
                    user.setPhone(phone);
                    user.setAddress(address);
                    user.setEmail(email);
                    user.setPassword(pass);

                    Boolean result = db.addUser(user);
                    if (result) {
                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Đăng ký thất bại", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(SignUpActivity.this, "Email đã được đăng ký\n Vui lòng đăng nhập", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(SignUpActivity.this, "Mật khẩu xác nhận không trùng khớp", Toast.LENGTH_LONG).show();
            }
        });
        textLogin.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    public void mapping() {
        buttonRegister  = findViewById(R.id.signUpActivity_button_register);
        editName        = findViewById(R.id.signUpActivity_edittext_name);
        editPhone       = findViewById(R.id.signUpActivity_edittext_phone);
        editAddress     = findViewById(R.id.signUpActivity_edittext_address);
        editEmail       = findViewById(R.id.signUpActivity_edittext_email);
        editPass        = findViewById(R.id.signUpActivity_edittext_password);
        editRePass      = findViewById(R.id.signUpActivity_edittext_confirm_password);
        textBack        = findViewById(R.id.signUpActivity_textview_back);
        textLogin       = findViewById(R.id.signUpActivity_textview_login);

        db = DatabaseHandler.getInstance(this);
        user = new User();
    }
}