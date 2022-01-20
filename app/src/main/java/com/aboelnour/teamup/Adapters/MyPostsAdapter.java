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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.User;
import com.aboelnour.teamup.module.functions;
import com.aboelnour.teamup.ui.myPosts.PrevPartnersActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPostsAdapter extends RecyclerView.Adapter<MyPostsAdapter.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "MyPostsAdapter";
    private ArrayList<DataPostModel> dataPostModel;
    private Context context;



    public MyPostsAdapter(ArrayList<DataPostModel> dataPostModel, Context context) {
        this.dataPostModel = dataPostModel;
        this.context = context;
    }

    @NonNull
    @Override
    public MyPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_posts,parent,false);

        return new ViewHolder(layout);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String TAG = "ViewHolder";
        DatabaseReference mRef;
        CardView cardView;
        TextView userName, userCountry,userTeratory,projectName,
                projectPlaceCountry,projectPlaceTeratory,projectdiscription,
                numberOfPartnesr,projectCategory,date;
        ImageView remove, share;
        User model;
        CircleImageView ownerImageInPost;
        Button details;
        FirebaseFirestore db ;
        LinearLayout mainLayout,layoutColor,location;
        private DataPostModel data = new DataPostModel();

        @SuppressLint("ClickableViewAccessibility")
        ViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.Locaion);
            cardView = itemView.findViewById(R.id.cardview);
            date = itemView.findViewById(R.id.PostReleaseDateDetails);
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
            remove = itemView.findViewById(R.id.remove);
            share = itemView.findViewById(R.id.share);
            details = itemView.findViewById(R.id.details);
            db = FirebaseFirestore.getInstance();
            mainLayout = itemView.findViewById(R.id.layoutColor);
            cardView.setOnClickListener(this);
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
        func.changeColor(holder.mainLayout, context);

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
        holder.numberOfPartnesr.setText(" مطلوب " +holder.data.getNumberOfPartners()+" مشاركين - انضم "+holder.data.getNumberOfAcceptedPartners()+" مشاركين - باقي "+(holder.data.getNumberOfPartners()-holder.data.getNumberOfAcceptedPartners())+" مشاركين ");


        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.db.collection("Posts").document(holder.data.getPostID()).delete();
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                mRef.child("Posts").child(holder.data.getPostID()).removeValue();
                mRef.child("userPosts").child(holder.data.getPublisherID()).child("posts").child(holder.data.getPostID()).removeValue();
                mRef.child("removedPosts").child(holder.data.getPublisherID()).child("posts").child("accept").child(holder.data.getPostID()).setValue(holder.data);
                mRef.child("removedPosts").child(holder.data.getPublisherID()).child("posts").child("request").child(holder.data.getPostID()).setValue(holder.data);
                dataPostModel.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataPostModel.size());

            }
        });

        holder.share.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.share.setBackgroundColor(Color.parseColor("#2AB0B9"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.share.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                return true;
            }
        });

//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String message = "Text I want to share.";
//                Intent share = new Intent(Intent.ACTION_SEND);
//                share.setType("text/plain");
//                share.putExtra(Intent.EXTRA_TEXT, message);
//
//                context.startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
//            }
//        });

        holder.share.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    holder.share.setBackgroundColor(Color.parseColor("#2AB0B9"));
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    holder.share.setBackgroundColor(Color.parseColor("#ffffff"));

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

                                            String firstData = "تاريخ النشر :"+holder.data.getDay()+"/"+holder.data.getMonth()+"/"+holder.data.getYear()+"\n" +
                                                    "اسم المستخدم : "+holder.model.getFirstName()+" "+holder.model.getLastName()+"\n"+
                                                    holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                                    "اسم المشروع : "+holder.data.getProjectName()+"\n"+
                                                    " مكان المشروع : "+holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                                    "وصف المشروع :"+"\n"+
                                                    holder.data.getProjectDescription()+"\n"+
                                                    " مطلوب " +holder.data.getNumberOfPartners()+" مشاركين - انضم "+holder.data.getNumberOfAcceptedPartners()+
                                                    " مشاركين - باقي "+(holder.data.getNumberOfPartners()-holder.data.getNumberOfAcceptedPartners())+" مشاركين ";

                                            /////////////////////////////////////////////////////////////////////////
                                            DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                                                    .setLink(Uri.parse("https://abouelnour.page.link/bjYi"))
                                                    .setDomainUriPrefix("https://abouelnour.page.link")
                                                    .setAndroidParameters(
                                                            new DynamicLink.AndroidParameters.Builder("com.aboelnour.teamup")
                                                                    .build())
                                                    .setSocialMetaTagParameters(
                                                            new DynamicLink.SocialMetaTagParameters.Builder()
                                                                    .setTitle("قم بتنزيل تطبيق Team up و أعثر علي شريك اعمالك")
                                                                    .setDescription("يبحث "+"\""+holder.model.getFirstName()+" "+holder.model.getLastName()+"\""+" عن شركاء لمشروع "+"\""+holder.data.getProjectName()+"\""+" إضغط لمعرفة التفاصيل.")
                                                                    .setImageUrl(Uri.parse(holder.data.getSharedImgLink()))
                                                                    .build())
                                                    .buildDynamicLink();  // Or buildShortDynamicLink()

                                            Log.d(TAG, "onTouch: "+"إن "+holder.model.getFirstName()+" "+holder.model.getLastName()+" يبحث عن شركاء لمشروع "+holder.data.getProjectName()+" إضغط لمعرفة التفاصيل.");

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
                            String firstData = "تاريخ النشر :"+holder.data.getDay()+"/"+holder.data.getMonth()+"/"+holder.data.getYear()+"\n" +
                                    "اسم المستخدم : "+holder.model.getFirstName()+" "+holder.model.getLastName()+"\n"+
                                    holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                    "اسم المشروع : "+holder.data.getProjectName()+"\n"+
                                    " مكان المشروع : "+holder.data.getProjectCountry()+" - "+holder.data.getProjectGovernorate()+"\n"+
                                    "وصف المشروع :"+"\n"+
                                    holder.data.getProjectDescription()+"\n"+
                                    " مطلوب " +holder.data.getNumberOfPartners()+" مشاركين - انضم "+holder.data.getNumberOfAcceptedPartners()+" مشاركين - باقي "+(holder.data.getNumberOfPartners()-holder.data.getNumberOfAcceptedPartners())+" مشاركين ";

                            /////////////////////////////////////////////////////////////////////////
                            DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                                    .setLink(Uri.parse("https://abouelnour.page.link/bjYi"))
                                    .setDomainUriPrefix("https://abouelnour.page.link")
                                    .setAndroidParameters(
                                            new DynamicLink.AndroidParameters.Builder("com.aboelnour.teamup")
                                                    .build())
                                    .setSocialMetaTagParameters(
                                            new DynamicLink.SocialMetaTagParameters.Builder()
                                                    .setTitle("قم بتنزيل تطبيق Team up و أعثر علي شريك اعمالك")
                                                    .setDescription("يبحث "+"\""+holder.model.getFirstName()+" "+holder.model.getLastName()+"\""+" عن شركاء لمشروع "+"\""+holder.data.getProjectName()+"\""+" إضغط لمعرفة التفاصيل.")
                                                    .setImageUrl(Uri.parse(holder.data.getSharedImgLink()))
                                                    .build())
                                    .buildDynamicLink();  // Or buildShortDynamicLink()

                            Log.d(TAG, "onTouch: "+"إن "+holder.model.getFirstName()+" "+holder.model.getLastName()+" يبحث عن شركاء لمشروع "+holder.data.getProjectName()+" إضغط لمعرفة التفاصيل.");

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
                holder.data = dataPostModel.get(position);

                Intent intent = new Intent(context, PrevPartnersActivity.class);

                intent.putExtra("data",holder.data);
                intent.putExtra("user",holder.model);

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
