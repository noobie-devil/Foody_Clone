package hcmute.edu.vn.foody_18.Fragment;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import hcmute.edu.vn.foody_18.R;
import hcmute.edu.vn.foody_18.SignInActivity;
import hcmute.edu.vn.foody_18.UpdateInfoActivity;

public class AccountFragment extends Fragment {

    Button buttonSetting;
    TextView accountName;

    SharedPreferences sharedPreferences;

    public AccountFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        mapping(view);

        accountName.setText(sharedPreferences.getString("userName", "Đăng nhập"));
        if (accountName.getText().toString().equals("Đăng nhập")) {
            buttonSetting.setVisibility(View.GONE);
        }

        accountName.setOnClickListener(view1 -> {
            if (accountName.getText().toString().equals("Đăng nhập")) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });
        buttonSetting.setOnClickListener(view2 -> {
            Intent intent = new Intent(getActivity(), UpdateInfoActivity.class);
            startActivity(intent);
        });

        return view;
    }

    public void mapping(View view) {
        buttonSetting   = view.findViewById(R.id.accountFragment_button_setting);
        accountName     = view.findViewById(R.id.accountFragment_textview_accountName);

        sharedPreferences = requireContext().getSharedPreferences("currentUser", Context.MODE_PRIVATE);
    }
}