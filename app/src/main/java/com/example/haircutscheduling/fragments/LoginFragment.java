package com.example.haircutscheduling.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    MainActivity mainActivity;
    public FirebaseDatabase database;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoginFragment.
     */
    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        Button signin = view.findViewById(R.id.buttonSignin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity = (MainActivity) getActivity();
                mainActivity.setFragment(new SigninFragment());
            }
        });

        Button login = view.findViewById(R.id.buttonLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView userNameTextView = view.findViewById(R.id.editTextTextEmailAddressLogin);
                TextView passwordTextView = view.findViewById(R.id.editTextTextPasswordLogin);

                String userName = userNameTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                mainActivity = (MainActivity) getActivity();

                if(userName.isEmpty() || password.isEmpty()) {
                    Toast.makeText(mainActivity, "\n" +
                            "Vui lòng nhập tên tài khoản và mật khẩu", Toast.LENGTH_LONG).show();
                }
                else {
                    mainActivity.Login(userName,password);
                }
            }
        });

        return view;
    }
}