package com.example.haircutscheduling.fragments;

import android.graphics.ImageDecoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.haircutscheduling.R;
import com.example.haircutscheduling.activities.MainActivity;
import com.example.haircutscheduling.classes.DataModels.UpdateDataModel;
import com.example.haircutscheduling.classes.CustomAdapters.UpdatesBoardCustomAdapter;
import com.example.haircutscheduling.classes.Data.UpdatesData;
import com.example.haircutscheduling.classes.Settings;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatesBoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatesBoardFragment extends Fragment {

    MainActivity mainActivity;
    private FirebaseDatabase database;

    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<UpdateDataModel> updatesData;
    private static UpdatesBoardCustomAdapter adapter;

    public UpdatesBoardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UpdatesBoardFragment.
     */
    public static UpdatesBoardFragment newInstance() {
        UpdatesBoardFragment fragment = new UpdatesBoardFragment();
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
        View view = inflater.inflate(R.layout.fragment_updates_board, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.updatesRecycleView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainActivity = (MainActivity) getActivity();

        DatabaseReference myRef = database.getReference().child("updates");

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                updatesData = new ArrayList<UpdateDataModel>();

                if (!task.isSuccessful()) {
                }
                else {
                    if (task.getResult().hasChildren()) {
                        UpdatesData updates = task.getResult().getValue(UpdatesData.class);
                        for (String key : updates.updatesList.keySet()) {
                            UpdateDataModel update = updates.updatesList.get(key);
                            updatesData.add(update);
                        }
                    }
                    else {
                        Toast.makeText(mainActivity,"No updates",Toast.LENGTH_LONG).show();
                    }
                }

                adapter = new UpdatesBoardCustomAdapter(updatesData);
                recyclerView.setAdapter(adapter);
            }
        });

        return view;
    }
}