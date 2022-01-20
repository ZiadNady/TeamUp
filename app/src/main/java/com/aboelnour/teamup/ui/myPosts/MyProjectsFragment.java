package com.aboelnour.teamup.ui.myPosts;

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


import com.aboelnour.teamup.Adapters.MyPostsAdapter;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MyProjectsFragment extends Fragment {

    private static final String TAG = "MyProjectsFragment";
    private DatabaseReference mRef;
    private RecyclerView recyclerView;
    private ArrayList<DataPostModel> dataPostModels;
    private MyPostsAdapter adapter;
    private FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View root = inflater.inflate(R.layout.fragment_my_projects, container, false);

        db = FirebaseFirestore.getInstance();

        dataPostModels = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference("userPosts").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("posts");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.exists()) {
                        DataPostModel model = snapshot.getValue(DataPostModel.class);
                        Log.d(TAG, "onDataChange: postID : " + model.getPostID());
                        if (!dataPostModels.contains(model)){
                            dataPostModels.add(model);
                            Recyclerview(root);
                        }
//                    DocumentReference docRef = db.collection("Posts").document(model.getPostID());
//                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                    if (task.isSuccessful()) {
//                                        DocumentSnapshot document = task.getResult();
//                                        if (document.exists()) {
//                                            if(dataPostModels.contains(document.toObject(DataPostModel.class))) {
//                                                dataPostModels.add(document.toObject(DataPostModel.class));
//                                                Log.d(TAG, "onComplete: added");
//                                                Recyclerview(root);
//                                            }
//                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                                        } else {
//                                            Log.d(TAG, "No such document");
//                                        }
//                                    } else {
//                                        Log.d(TAG, "get failed with ", task.getException());
//                                    }
//                                }
//                            });
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
        recyclerView = root.findViewById(R.id.MyPostsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyPostsAdapter(dataPostModels,getContext());

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
//        adapter.stopListening();
        if (isNetworkAvailable()){
            if (!dataPostModels.isEmpty()){
                adapter.notifyItemRangeRemoved(0 , dataPostModels.size()-1);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}