package com.aboelnour.teamup.ui.likedPosts;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aboelnour.teamup.Adapters.likedPostsAdapter;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class likedPosts extends Fragment {

    private static final String TAG = "likedPosts";
    private DatabaseReference mRef;
    private RecyclerView recyclerView;
    private ArrayList<DataPostModel> dataPostModels;
    private likedPostsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_liked_posts, container, false);

        dataPostModels = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference().child("Likes").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Likes");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        DataPostModel model = snapshot.getValue(DataPostModel.class);

                        if (snapshot.exists()) {
                            if(!dataPostModels.contains(model)) {
                                dataPostModels.add(model);
                                Recyclerview(root);
                            }

                        }
                        else{
                            mRef.child(model.getPostID()).removeValue();
                            Toast.makeText(getActivity(),"hi", Toast.LENGTH_SHORT).show();
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRef.keepSynced(true);

        return root;
    }

    private void Recyclerview(View root){
        Log.d(TAG, "Recyclerview: yup");

        recyclerView = root.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new likedPostsAdapter(dataPostModels,getContext());

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
                adapter = new likedPostsAdapter(dataPostModels,getContext());
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