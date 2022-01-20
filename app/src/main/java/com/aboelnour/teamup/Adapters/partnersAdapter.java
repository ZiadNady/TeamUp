package com.aboelnour.teamup.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.PartnersForm;
import com.aboelnour.teamup.module.User;
import com.aboelnour.teamup.ui.myPosts.PartnerDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class partnersAdapter extends RecyclerView.Adapter<partnersAdapter.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "partnersAdapter";
    private ArrayList<PartnersForm> partnersForms;
    private Context context;
    DataPostModel dataPostModel;



    public partnersAdapter(ArrayList<PartnersForm> partnersForms, DataPostModel dataPostModel, Context context) {
        this.partnersForms = partnersForms;
        this.context = context;
        this.dataPostModel = dataPostModel;
    }

    @NonNull
    @Override
    public partnersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_partners_card,parent,false);

        return new ViewHolder(layout);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView userName, userCountry,userTeratory;
        DatabaseReference mRef;
        User model;
        ImageButton accept,refuse;
        Button details;
        FirebaseFirestore db;
        private PartnersForm data = new PartnersForm();

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardview);
            userName = itemView.findViewById(R.id.UserNameInPartners);
            userCountry = itemView.findViewById(R.id.UserCountryInPartners);
            userTeratory = itemView.findViewById(R.id.UserTeratoryInPartners);
            accept = itemView.findViewById(R.id.accept);
            refuse = itemView.findViewById(R.id.refuse);
            details = itemView.findViewById(R.id.details);
            db = FirebaseFirestore.getInstance();
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();


        }
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.data = partnersForms.get(position);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("userPosts").child(dataPostModel.getPublisherID()).child("posts").child(dataPostModel.getPostID());
        holder.mRef = FirebaseDatabase.getInstance().getReference().child("users").child(holder.data.getPartnerId());

        holder.mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.model = dataSnapshot.getValue(User.class);

                holder.cardView.setTag(position);
                holder.userName.setText(holder.model.getFirstName()+" "+holder.model.getLastName());
                holder.userCountry.setText(holder.model.getCountry());
                holder.userTeratory.setText(holder.model.getTeratory());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.mRef.keepSynced(true);

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PartnerDetailActivity.class);
                intent.putExtra("data",holder.data);
                intent.putExtra("user",holder.model);
                intent.putExtra("model",dataPostModel);
                intent.putExtra("position",position);
                intent.putExtra("state","requests");
                ((Activity)context).startActivityForResult(intent,1);
            }
        });

        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(context).inflate(R.layout.remove_post_popup_window_layout, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button btnDismiss = popupView.findViewById(R.id.closePopupBtn);
                Button accept = popupView.findViewById(R.id.remove);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                        mRef.child("userRequests").child(holder.data.getPartnerId()).child("posts").child("request").child(dataPostModel.getPostID()).removeValue();
                        mRef.child("userRequests").child(holder.data.getPartnerId()).child("posts").child("accept").child(dataPostModel.getPostID()).setValue(dataPostModel);
                        databaseReference.child("Partners").child(holder.data.getPartnerId()).removeValue();
                        databaseReference.child("accepted").child(holder.data.getPartnerId()).setValue(holder.data);
                        holder.db.collection("Posts").document(dataPostModel.getPostID()).update("numberOfAcceptedPartners", dataPostModel.getNumberOfAcceptedPartners() + 1);
//                mRef.child("Posts").child(dataPostModel.getPostID()).child("numberOfAcceptedPartners").setValue(dataPostModel.getNumberOfAcceptedPartners()+1);
                        mRef.child("userPosts").child(dataPostModel.getPublisherID()).child("posts").child(dataPostModel.getPostID()).child("numberOfAcceptedPartners").setValue(dataPostModel.getNumberOfAcceptedPartners()+1);
                        partnersForms.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, partnersForms.size());
                        popupWindow.dismiss();
                    }
                });
            }
        });

        holder.refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View popupView = LayoutInflater.from(context).inflate(R.layout.remove_post_popup_window_layout, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button btnDismiss = popupView.findViewById(R.id.closePopupBtn);
                Button refuse = popupView.findViewById(R.id.remove);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                refuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                        mRef.child("userRequests").child(holder.data.getPartnerId()).child("posts").child("request").child(dataPostModel.getPostID()).removeValue();
                        databaseReference.child("Partners").child(holder.data.getPartnerId()).removeValue();
                        partnersForms.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, partnersForms.size());
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() { return partnersForms.size(); }

    @Override
    public void onClick(View v) {

    }

}
