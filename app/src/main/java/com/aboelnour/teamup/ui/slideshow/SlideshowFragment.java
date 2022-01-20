package com.aboelnour.teamup.ui.slideshow;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aboelnour.teamup.Adapters.requestAdapter;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SlideshowFragment extends Fragment {

    private static final String TAG = "SlideshowFragment";
    private DatabaseReference mRef;
    private RecyclerView recyclerView;
    private ArrayList<DataPostModel> dataPostModels;
    private requestAdapter adapter;
    private ArrayList<String> state;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        dataPostModels = new ArrayList<>();
        state = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("userRequests");
        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("posts").child("accept").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPostModel model = snapshot.getValue(DataPostModel.class);

                        if (snapshot.exists()) {
                            dataPostModels.add(model);
                            state.add("true");
                            Recyclerview(root);
                            Log.d(TAG, "onDataChange: accept");
                        }
                        else
                            mRef.child(model.getPostID()).removeValue();

                        Log.d(TAG, "onDataChange: exist");
                    }
                Log.d(TAG, "onDataChange: accept ref");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("posts").child("request").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                      DataPostModel model = snapshot.getValue(DataPostModel.class);

                      if (snapshot.exists()) {
                          dataPostModels.add(model);
                          state.add("false");
                          Recyclerview(root);
                          Log.d(TAG, "onDataChange: refuse");
                      }
                      else
                          mRef.child(model.getPostID()).removeValue();
                    Log.d(TAG, "onDataChange: exist");

                }
                Log.d(TAG, "onDataChange: refuse ref");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.keepSynced(true);

        return root;
    }

    private void Recyclerview(View root){
        recyclerView = root.findViewById(R.id.requestRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new requestAdapter(dataPostModels,getContext(),state);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
//        adapter.stopListening();
        if (isNetworkAvailable()) {
            if (!dataPostModels.isEmpty()) {
                adapter = new requestAdapter(dataPostModels,getContext(),state);
                adapter.notifyItemRangeRemoved(0, dataPostModels.size() - 1);
                recyclerView.setAdapter(null);
                dataPostModels.clear();
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}