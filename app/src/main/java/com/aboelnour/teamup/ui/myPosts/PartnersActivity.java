package com.aboelnour.teamup.ui.myPosts;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aboelnour.teamup.Adapters.partnersAdapter;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.PartnersForm;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PartnersActivity extends AppCompatActivity {

    private static final String TAG = "PartnersActivity";
    private DatabaseReference mRef;
    private Bundle bundle;
    private ArrayList<PartnersForm> partnersForms;
    private RecyclerView recyclerView;
    private partnersAdapter adapter;
    private DataPostModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partners);

        bundle = getIntent().getExtras();
        data = bundle.getParcelable("data");
        partnersForms = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference().child("userPosts").child(data.getPublisherID()).child("posts").child(data.getPostID()).child("Partners");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        PartnersForm model = snapshot.getValue(PartnersForm.class);
                        partnersForms.add(model);
                        Recyclerview();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void Recyclerview(){
        recyclerView = findViewById(R.id.postsRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new partnersAdapter(partnersForms,data,this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == RESULT_FIRST_USER){
                bundle = getIntent().getExtras();
                partnersForms.remove(bundle.getInt("position"));
                adapter.notifyItemRemoved(bundle.getInt("position"));
            }
        }
    }
}
