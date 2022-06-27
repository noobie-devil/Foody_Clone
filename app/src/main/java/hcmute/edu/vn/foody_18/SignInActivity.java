package hcmute.edu.vn.foody_18;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.foody_18.Database.DatabaseHandler;
import hcmute.edu.vn.foody_18.Model.User;

public class SignInActivity extends AppCompatActivity {

    Button buttonLogin;
    EditText editEmail, editPass;
    TextView textBack, textRegister;

    DatabaseHandler db;
    SharedPreferences sharedPreferences;
    User user;

    private static final String ARG_BACK_TO_PREVIOUS = "backToPrevious";
    boolean backToPrevious;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mapping();
        Bundle bundle = getIntent().getExtras();
        backToPrevious = false;
        if (bundle != null) {
            backToPrevious = bundle.getBoolean(ARG_BACK_TO_PREVIOUS);
        }

        textBack.setOnClickListener(view -> finish());
        buttonLogin.setOnClickListener(view -> {
            String email = editEmail.getText().toString().trim();
            String pass = editPass.getText().toString().trim();

            if (email.equals("") || pass.equals("")) {
                Toast.makeText(SignInActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_LONG).show();
            } else {
                Boolean login = db.loginUser(email, pass);
                if (login) {
                    Toast.makeText(SignInActivity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    user = db.getInfoUser(email);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt(    "userId",   user.getUserId());
                    editor.putString( "userName", user.getUserName());
                    editor.putString( "email",    user.getEmail());
                    editor.putString( "address",  user.getAddress());
                    editor.putString( "phone",    user.getPhone());
                    editor.apply();

                    if (!backToPrevious) {
                        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent();
                        intent.putExtra("logged", true);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                }
            }
        });
        textRegister.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    public void mapping() {
        buttonLogin     = findViewById(R.id.signInActivity_button_login);
        editEmail       = findViewById(R.id.signInActivity_edittext_email);
        editPass        = findViewById(R.id.signInActivity_edittext_password);
        textBack        = findViewById(R.id.signInActivity_textview_back);
        textRegister    = findViewById(R.id.signInActivity_textview_register);

        db = DatabaseHandler.getInstance(this);
        sharedPreferences = getSharedPreferences("currentUser", MODE_PRIVATE);
        user = new User();
    }
}