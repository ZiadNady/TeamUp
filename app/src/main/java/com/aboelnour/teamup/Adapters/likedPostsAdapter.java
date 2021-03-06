package com.aboelnour.teamup.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.User;
import com.aboelnour.teamup.module.functions;
import com.aboelnour.teamup.ui.home.PostDetailsActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class likedPostsAdapter extends RecyclerView.Adapter<likedPostsAdapter.ViewHolder> implements View.OnClickListener {

    private ArrayList<DataPostModel> dataPostModel;
    private Context context;
    private static final String TAG = "likedPostsAdapter";

    public likedPostsAdapter(ArrayList<DataPostModel> dataPostModel, Context context) {
        this.dataPostModel = dataPostModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post,parent,false);

        return new ViewHolder(layout);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        DatabaseReference mRef;
        CardView cardView;
        private DataPostModel data = new DataPostModel();
        TextView userName, userCountry,userTeratory,projectName,
                projectPlaceCountry,projectPlaceTeratory,projectdiscription,
                numberOfPartnesr,projectCategory,date;
        ImageButton likeBtn,shareBtn;
        CallbackManager callbackManager;
        ShareDialog shareDialog;
        FirebaseUser liker ;
        DatabaseReference likeRer ;
        CircleImageView ownerImageInPost;
        User model;
        Button details;
        LinearLayout layoutColor,location;

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.Locaion);
            cardView = itemView.findViewById(R.id.cardview);
            date = itemView.findViewById(R.id.PostReleaseDate);
            userName = itemView.findViewById(R.id.userName);
            userCountry = itemView.findViewById(R.id.UserCountryInPost);
            userTeratory = itemView.findViewById(R.id.UserTeratoryInPost);
            projectName = itemView.findViewById(R.id.ProjectNameInPost);
            projectCategory = itemView.findViewById(R.id.ProjectCategoryInPost);
            projectPlaceCountry = itemView.findViewById(R.id.ProjectPlaceCountryInPost);
            projectPlaceTeratory = itemView.findViewById(R.id.ProjectPlaceTeratoryInPost);
            projectdiscription = itemView.findViewById(R.id.ProjectDiscriptionInPost);
            numberOfPartnesr = itemView.findViewById(R.id.numberOfPartners);
            ownerImageInPost = itemView.findViewById(R.id.ownerImageInPost);
            details = itemView.findViewById(R.id.details);
            likeBtn = itemView.findViewById(R.id.like);
            liker = FirebaseAuth.getInstance().getCurrentUser();
            layoutColor = itemView.findViewById(R.id.layoutColor);

        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
        }
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.data = dataPostModel.get(position);

        functions func = new functions();
        func.changeColor(holder.layoutColor, context);

        holder.mRef = FirebaseDatabase.getInstance().getReference().child("users").child(holder.data.getPublisherID());

        holder.mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.model = dataSnapshot.getValue(User.class);

                holder.cardView.setTag(position);
                holder.userName.setText(holder.model.getFirstName()+" "+holder.model.getLastName());
                holder.userCountry.setText(holder.model.getCountry());
                holder.userTeratory.setText(holder.model.getTeratory());
                Picasso.get().load(holder.model.getImageURL()).into(holder.ownerImageInPost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (!TextUtils.isEmpty(holder.data.getProjectCategory())){
            holder.location.setVisibility(View.VISIBLE);
        }
        holder.date.setText(holder.data.getDay()+"/"+holder.data.getMonth()+"/"+holder.data.getYear());
        holder.projectName.setText(holder.data.getProjectName());
        holder.projectCategory.setText(holder.data.getProjectCategory());
        holder.projectPlaceCountry.setText(holder.data.getProjectCountry());
        holder.projectPlaceTeratory.setText(holder.data.getProjectGovernorate());
        holder.projectdiscription.setText(holder.data.getProjectDescription());
        holder.numberOfPartnesr.setText(" ?????????? " +holder.data.getNumberOfPartners()+" ?????????????? - ???????? "+holder.data.getNumberOfAcceptedPartners()+" ?????????????? - ???????? "+(holder.data.getNumberOfPartners()-holder.data.getNumberOfAcceptedPartners())+" ?????????????? ");

        holder.likeBtn.setTag("unlike");

        FacebookSdk.sdkInitialize(context.getApplicationContext());
        holder.callbackManager = CallbackManager.Factory.create();
        holder.shareDialog = new ShareDialog((Activity) context);


        holder.likeRer = FirebaseDatabase.getInstance().getReference().child("Likes").child(holder.liker.getUid()).child("Likes");

        holder.likeBtn.setBackgroundColor(Color.parseColor("#2AB0B9"));

        holder.cardView.setOnClickListener(this);

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.likeRer.child(holder.data.getPostID()).removeValue();
                dataPostModel.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataPostModel.size());
            }
        });



        holder.shareBtn = holder.itemView.findViewById(R.id.share);
        holder.shareBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.shareBtn.setBackgroundColor(Color.parseColor("#2AB0B9"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.shareBtn.setBackgroundColor(Color.parseColor("#ffffff"));

                    try {
                        String query = "";
                        try {
                            query = URLEncoder.encode(String.format("&%1s=%2s", "id", holder.data.getPostID()), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "it worked");
                        Log.d(TAG, "onTouch: share link "+ holder.data.getSharedImgLink());
                        if(TextUtils.isEmpty(holder.data.getSharedImgLink())){
                            ProgressDialog progressDialog = new ProgressDialog(context);
                            progressDialog.setMessage("please wait ...");
                            progressDialog.show();
                            Bitmap b = func.getBitmapFromView(holder.layoutColor);
                            b = func.BITMAP_RESIZER(b,550 , 300);
                            /////////////////////////////////
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            b.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();
                            FirebaseStorage storage = FirebaseStorage.getInstance();

                            StorageReference storageRef = storage.getReferenceFromUrl("gs://teamup-434b5.appspot.com");

                            StorageReference imagesRef = storageRef.child("shareImages/"+holder.data.getPostID());

                            UploadTask uploadTask = imagesRef.putBytes(data);
                            String finalQuery = query;
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                                    imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            holder.data.setSharedImgLink(uri.toString());
                                            DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
                                            mReference.child("userPosts").child(holder.data.getPublisherID()).child("posts").child(holder.data.getPostID()).setValue(holder.data);
                                            Log.d(TAG, "onTouch: image uploaded");
                                            progressDialog.dismiss();

                                            String firstData = "?????????? ?????????? :"+holder.data.getDay()+"/"+holder.data.getMonth()+"/"+holder.data.getYear()+"\n" +
                                                    "?????? ???????????????? : "+holder.model.getFirstName()+" "+holder.model.getLastName()+"\n"+
                                                    holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                                    "?????? ?????????????? : "+holder.data.getProjectName()+"\n"+
                                                    " ???????? ?????????????? : "+holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                                    "?????? ?????????????? :"+"\n"+
                                                    holder.data.getProjectDescription()+"\n"+
                                                    " ?????????? " +holder.data.getNumberOfPartners()+" ?????????????? - ???????? "+holder.data.getNumberOfAcceptedPartners()+
                                                    " ?????????????? - ???????? "+(holder.data.getNumberOfPartners()-holder.data.getNumberOfAcceptedPartners())+" ?????????????? ";

                                            /////////////////////////////////////////////////////////////////////////
                                            DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                                                    .setLink(Uri.parse("https://abouelnour.page.link/bjYi"))
                                                    .setDomainUriPrefix("https://abouelnour.page.link")
                                                    .setAndroidParameters(
                                                            new DynamicLink.AndroidParameters.Builder("com.aboelnour.teamup")
                                                                    .build())
                                                    .setSocialMetaTagParameters(
                                                            new DynamicLink.SocialMetaTagParameters.Builder()
                                                                    .setTitle("???? ???????????? ?????????? Team up ?? ???????? ?????? ???????? ????????????")
                                                                    .setDescription("???????? "+"\""+holder.model.getFirstName()+" "+holder.model.getLastName()+"\""+" ???? ?????????? ???????????? "+"\""+holder.data.getProjectName()+"\""+" ???????? ???????????? ????????????????.")
                                                                    .setImageUrl(Uri.parse(holder.data.getSharedImgLink()))
                                                                    .build())
                                                    .buildDynamicLink();  // Or buildShortDynamicLink()

                                            Log.d(TAG, "onTouch: "+"???? "+holder.model.getFirstName()+" "+holder.model.getLastName()+" ???????? ???? ?????????? ???????????? "+holder.data.getProjectName()+" ???????? ???????????? ????????????????.");

                                            Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                                                    .setLongLink(Uri.parse(dynamicLink.getUri()+ finalQuery))
                                                    .buildShortDynamicLink()
                                                    .addOnCompleteListener((Activity) context, new OnCompleteListener<ShortDynamicLink>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                                            if (task.isSuccessful()) {
                                                                // Short link created
                                                                Uri shortLink = task.getResult().getShortLink();
                                                                Uri flowchartLink = task.getResult().getPreviewLink();

                                                                ////////////////////////////////////////////////////////////////////////
                                                                shareIntent.putExtra(Intent.EXTRA_TEXT, firstData+"\n"+shortLink.toString());
                                                                context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                                                            } else {
                                                                // Error
                                                                // ...
                                                            }
                                                        }
                                                    });

                                            Log.d(TAG, "onTouch: "+ dynamicLink.getUri()+ finalQuery);
                                            ////////////////////////////////

                                        }
                                    });
                                }
                            });

                        }else{
                            Log.d(TAG, "onTouch: else c");
                            String firstData = "?????????? ?????????? :"+holder.data.getDay()+"/"+holder.data.getMonth()+"/"+holder.data.getYear()+"\n" +
                                    "?????? ???????????????? : "+holder.model.getFirstName()+" "+holder.model.getLastName()+"\n"+
                                    holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                    "?????? ?????????????? : "+holder.data.getProjectName()+"\n"+
                                    " ???????? ?????????????? : "+holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                    "?????? ?????????????? :"+"\n"+
                                    holder.data.getProjectDescription()+"\n"+
                                    " ?????????? " +holder.data.getNumberOfPartners()+" ?????????????? - ???????? "+holder.data.getNumberOfAcceptedPartners()+" ?????????????? - ???????? "+(holder.data.getNumberOfPartners()-holder.data.getNumberOfAcceptedPartners())+" ?????????????? ";

                            /////////////////////////////////////////////////////////////////////////
                            DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                                    .setLink(Uri.parse("https://abouelnour.page.link/bjYi"))
                                    .setDomainUriPrefix("https://abouelnour.page.link")
                                    .setAndroidParameters(
                                            new DynamicLink.AndroidParameters.Builder("com.aboelnour.teamup")
                                                    .build())
                                    .setSocialMetaTagParameters(
                                            new DynamicLink.SocialMetaTagParameters.Builder()
                                                    .setTitle("???? ???????????? ?????????? Team up ?? ???????? ?????? ???????? ????????????")
                                                    .setDescription("???????? "+"\""+holder.model.getFirstName()+" "+holder.model.getLastName()+"\""+" ???? ?????????? ???????????? "+"\""+holder.data.getProjectName()+"\""+" ???????? ???????????? ????????????????.")
                                                    .setImageUrl(Uri.parse(holder.data.getSharedImgLink()))
                                                    .build())
                                    .buildDynamicLink();  // Or buildShortDynamicLink()

                            Log.d(TAG, "onTouch: "+"???? "+holder.model.getFirstName()+" "+holder.model.getLastName()+" ???????? ???? ?????????? ???????????? "+holder.data.getProjectName()+" ???????? ???????????? ????????????????.");

                            Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                                    .setLongLink(Uri.parse(dynamicLink.getUri()+query))
                                    .buildShortDynamicLink()
                                    .addOnCompleteListener((Activity) context, new OnCompleteListener<ShortDynamicLink>() {
                                        @Override
                                        public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                            if (task.isSuccessful()) {
                                                // Short link created
                                                Uri shortLink = task.getResult().getShortLink();
                                                Uri flowchartLink = task.getResult().getPreviewLink();

                                                ////////////////////////////////////////////////////////////////////////
                                                shareIntent.putExtra(Intent.EXTRA_TEXT, firstData+"\n"+shortLink.toString());
                                                context.startActivity(Intent.createChooser(shareIntent, "choose one"));
                                            } else {
                                                // Error
                                                // ...
                                            }
                                        }
                                    });

                            Log.d(TAG, "onTouch: "+ dynamicLink.getUri()+query);
                        }



                    } catch(Exception e) { }
                }

                return true;
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataPostModel data1 = new DataPostModel();
                data1 = dataPostModel.get(position);
                Intent intent = new Intent(context, PostDetailsActivity.class);
                intent.putExtra("data",data1);
                intent.putExtra("user",holder.model);
                intent.putExtra("Adapter","postAdapter");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return dataPostModel.size(); }

    @Override
    public void onClick(View v) {

    }

}
