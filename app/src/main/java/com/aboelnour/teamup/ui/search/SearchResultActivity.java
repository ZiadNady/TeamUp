package com.aboelnour.teamup.ui.search;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.aboelnour.teamup.Adapters.postAdapter;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.Search;
import com.aboelnour.teamup.module.User;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class SearchResultActivity extends AppCompatActivity {

    private static final String TAG = "SearchResultActivity";
    private Bundle bundle;
    private Search searchData;
    private RecyclerView recyclerView;
    private postAdapter adapter;
    private FirebaseFirestore firebaseFirestore ;
    private CollectionReference collectionReference;
    private ArrayList<DataPostModel> arrayList = new ArrayList<>();
    private int limit = 5;
    private int totalItem = 0, lastVisibleItem;
    private LinearLayoutManager layoutManager;
    private boolean isLoading = false;
    private final int itemLoadCount = 5;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        bundle = getIntent().getExtras();
        searchData = bundle.getParcelable("search");
        recyclerView = findViewById(R.id.recyclerView);


        firebaseFirestore = FirebaseFirestore.getInstance();
        queryFiltering();
        firstQuery();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        onScroll();

    }

    private void onScroll() {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                totalItem = layoutManager.getItemCount();
                lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!isLoading && totalItem <= (lastVisibleItem + itemLoadCount)){
                    isLoading = true;
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
//        Log.d(TAG, "loadMore: "+startAfter);
//        CollectionReference collectionReference = db.collection("Posts");
//        Query query = collectionReference.orderBy("postID").startAfter(startAfter).limit(itemLoadCount);
//        getdata(root, query);
        int size = arrayList.size() -1;
        query.orderBy("projectCategory").startAfter(arrayList.get(size).getPostID()).limit(itemLoadCount)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: SUCCESSFUL");
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                DataPostModel model = queryDocumentSnapshot.toObject(DataPostModel.class);

                                Log.d(TAG, "onComplete: "+queryDocumentSnapshot.getData());
                                if (model.getNumberOfAcceptedPartners()<model.getNumberOfPartners()&&!arrayList.contains(model)){
                                    arrayList.add(model);
                                    Recyclerview();
                                }
                                else {
//                                    queryDocumentSnapshot.getRef().child(model.getPostID()).removeValue();
//                                    mRef.child("completedPosts").child(model.getPostID()).setValue(model);
                                }
                            }
                        }
                        else
                            Log.d(TAG, "onComplete: ERROR");
                    }
                });
    }

    private void firstQuery(){
        //Query query = db.collection("Posts").limit(itemLoadCount);
        query.limit(itemLoadCount)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "onComplete: SUCCESSFUL");
                            for (QueryDocumentSnapshot queryDocumentSnapshot : task.getResult()){
                                DataPostModel model = queryDocumentSnapshot.toObject(DataPostModel.class);

                                Log.d(TAG, "onComplete: "+queryDocumentSnapshot.getData());
                                if (model.getNumberOfAcceptedPartners()<model.getNumberOfPartners()&&!arrayList.contains(model)){
                                    arrayList.add(model);
                                    Recyclerview();
                                }
                                else {
//                                    queryDocumentSnapshot.getRef().child(model.getPostID()).removeValue();
//                                    mRef.child("completedPosts").child(model.getPostID()).setValue(model);
                                }
                            }
                        }
                        else
                            Log.d(TAG, "onComplete: ERROR");
                    }
                });
        //getdata(root, query);
    }

    private void Recyclerview(){

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new postAdapter(arrayList,this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter.notifyDataSetChanged();
    }

//    private void getdata(View root, Query query) {
//
//
//        FirestoreRecyclerOptions<DataPostModel> options = new FirestoreRecyclerOptions.Builder<DataPostModel>()
//                .setQuery(query, DataPostModel.class)
//                .build();
//
//        isLoading = false;
//
//            adapter = new FirestoreRecyclerAdapter<DataPostModel, PostsViewHolder>(options) {
//                @NonNull
//                @Override
//                public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
//                    return new PostsViewHolder(view);
//                }
//
//                @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
//                @Override
//                protected void onBindViewHolder(@NonNull PostsViewHolder holder, int position, @NonNull DataPostModel model) {
//                     holder.mRef = FirebaseDatabase.getInstance().getReference().child("users").child(model.getPublisherID());
//                    arrayList.add(model);
//
//                    holder.mRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            holder.user = dataSnapshot.getValue(User.class);
//
//                            holder.cardView.setTag(position);
//                            holder.userName.setText(holder.user.getFirstName()+" "+holder.user.getLastName());
//                            holder.userCountry.setText(holder.user.getCountry());
//                            holder.userTeratory.setText(holder.user.getTeratory());
//                            Picasso.get().load(holder.user.getImageURL()).into(holder.ownerImageInPost);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//
//                    holder.date.setText(model.getDay()+"/"+model.getMonth()+"/"+model.getYear());
//                    holder.projectName.setText(model.getProjectName());
//                    holder.projectCategory.setText(model.getProjectCategory());
//                    holder.projectPlaceCountry.setText(model.getProjectCountry());
//                    holder.projectPlaceTeratory.setText(model.getProjectGovernorate());
//                    holder.projectdiscription.setText(model.getProjectDescription());
//                    holder.numberOfPartnesr.setText(Integer.toString(model.getNumberOfPartners()));
//                    holder.numberOfPartnesrRemainingInPost.setText(Integer.toString(model.getNumberOfPartners()-model.getNumberOfAcceptedPartners()));
//
//                    holder.likeBtn.setTag("unlike");
//
//                    FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
//                    holder.callbackManager = CallbackManager.Factory.create();
//                    holder.shareDialog = new ShareDialog((Activity) getActivity());
//
//
//                    holder.likeRer = FirebaseDatabase.getInstance().getReference().child("Likes").child(model.getPostID());
//                    holder.likeRer.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            if (dataSnapshot.child(holder.liker.getUid()).exists()) {
//                                holder.likeBtn.setBackgroundColor(Color.parseColor("#2AB0B9"));
//                                holder.likeBtn.setTag("liked");
//                            }
//                            else
//                                holder.likeBtn.setTag("unliked");
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
////                    holder.cardView.setOnClickListener(this);
//
//                    holder.likeBtn.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            if(holder.likeBtn.getTag().equals("liked")) {
//                                holder.likeRer.child(holder.liker.getUid()).removeValue();
//                                holder.likeBtn.setBackgroundResource(R.color.white);
//                                holder.likeBtn.setTag("unlike");
//                            }
//                            else{
//                                holder.likeRer.child(holder.liker.getUid()).setValue(true);
//                                holder.likeBtn.setBackgroundResource(R.color.liked);
//                                holder.likeBtn.setTag("liked");
//                            }
//                        }
//                    });
//
//
//
//                    holder.shareBtn = holder.itemView.findViewById(R.id.share);
//                    holder.shareBtn.setOnTouchListener(new View.OnTouchListener() {
//                        @Override
//                        public boolean onTouch(View v, MotionEvent event) {
//
//                            if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                                holder.shareBtn.setBackgroundColor(Color.parseColor("#2AB0B9"));
//                            } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                                holder.shareBtn.setBackgroundColor(Color.parseColor("#ffffff"));
//
//                                String message = "Text I want to share.";
//                                Intent share = new Intent(Intent.ACTION_SEND);
//                                share.setType("text/plain");
//                                share.putExtra(Intent.EXTRA_TEXT, message);
//                                startActivity(Intent.createChooser(share, "Title of the dialog the system will open"));
//
////                        ShareLinkContent linkContent = new ShareLinkContent.Builder()
////                                .setQuote("this is test")
////                                .setContentUrl(Uri.parse("https://youtube.com"))
////                                .build();
////
////                        if(ShareDialog.canShow(ShareLinkContent.class)){
////                            shareDialog.show(linkContent);
////                        }
//                            }
//
//                            return true;
//                        }
//                    });
//
//                    holder.details.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            DataPostModel data1 = new DataPostModel();
//                            data1 = model;
//                            Intent intent = new Intent(getActivity(), PostDetailsActivity.class);
//                            intent.putExtra("data",data1);
//                            intent.putExtra("user",holder.user);
//                            intent.putExtra("Adapter","postAdapter");
//                            startActivity(intent);
//                        }
//                    });
//                }
//            };
////        CollectionReference collectionReference = db.collection("Posts");
////
////        collectionReference.get()
////                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                    @Override
////                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
////
////                        if (task.isSuccessful()){
////
////                            for(QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
////                                data = queryDocumentSnapshot.toObject(DataPostModel.class);
////                                Log.d(TAG, "onComplete: "+data.getMonth());
////                                Log.d(TAG, "onComplete: "+ queryDocumentSnapshot.getData());
////                                if (data != null)
////                                    mArrayList.add(data);
//////                                Recyclerview(root);
////                            }
////
////                        }
////                        else{
////
////                            Log.e(TAG, "onComplete: ERROR: "+task.getException().getLocalizedMessage() );
////
////                        }
////                    }
////                });
//    }

    class PostsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        DatabaseReference mRef;
        CardView cardView;
        TextView userName, userCountry,userTeratory,projectName,
                projectPlaceCountry,projectPlaceTeratory,projectdiscription,
                numberOfPartnesr,projectCategory,date;
        ImageButton likeBtn,shareBtn;
        CallbackManager callbackManager;
        ShareDialog shareDialog;
        FirebaseUser liker ;
        DatabaseReference likeRer ;
        CircleImageView ownerImageInPost;
        User user;
        Button details;

        public PostsViewHolder(@NonNull View itemView) {
            super(itemView);

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
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
        }

    }



    private void queryFiltering() {
        String category = searchData.getCategory();
        collectionReference = firebaseFirestore.collection("Posts");
        query = collectionReference.whereEqualTo("projectCategory",category);
        if (!TextUtils.isEmpty(searchData.getProjectName())) {
            query = query.whereEqualTo("projectName", searchData.getProjectName());
            Log.d(TAG, "projectName: "+ searchData.getProjectName());
        }
        if (!TextUtils.isEmpty(searchData.getPartnersAgeFrom())) {
            query = query.whereGreaterThanOrEqualTo("partnersAgeFrom", Integer.parseInt(searchData.getPartnersAgeFrom()));
            Log.d(TAG, "partnersAgeFrom: "+searchData.getPartnersAgeFrom());
        }
        if (!TextUtils.isEmpty(searchData.getPartnersAgeTo())) {
            query = query.whereLessThanOrEqualTo("partnersAgeTo", Integer.parseInt(searchData.getPartnersAgeTo()));
            Log.d(TAG, "partnersAgeTo: " + searchData.getPartnersAgeTo());
        }
        if (!TextUtils.isEmpty(searchData.getPartnersNumFrom())) {
            query = query.whereGreaterThanOrEqualTo("numberOfPartners", Integer.parseInt(searchData.getPartnersNumFrom()));
            Log.d(TAG, "numberOfPartners: "+ searchData.getPartnersNumFrom());
        }
        if (!TextUtils.isEmpty(searchData.getPartnersNumTo())) {
            query = query.whereLessThanOrEqualTo("numberOfPartners", Integer.parseInt(searchData.getPartnersNumTo()));
            Log.d(TAG, "numberOfPartners: "+searchData.getPartnersNumTo());
        }
        if (!TextUtils.isEmpty(searchData.getProjectPlaceCountry())&&!searchData.getProjectPlaceCountry().contains("اختر الدولة")){
            query = query.whereEqualTo("projectCountry", searchData.getProjectPlaceCountry());
            Log.d(TAG, "queryFiltering: "+searchData.getProjectPlaceCountry());
        }
        if (!TextUtils.isEmpty(searchData.getProjectplaceTeratory())&&searchData.getProjectplaceTeratory().equals("اختر")){
            query = query.whereEqualTo("projectGovernorate",searchData.getProjectplaceTeratory());
            Log.d(TAG, "queryFiltering: "+searchData.getProjectplaceTeratory());
        }
        if (!TextUtils.isEmpty(searchData.getProjectCostFrom())) {
            query = query.whereGreaterThanOrEqualTo("projectCost", Integer.parseInt(searchData.getProjectCostFrom()));
            Log.d(TAG, "projectCost: "+ searchData.getProjectCostFrom());
        }
        if (!TextUtils.isEmpty(searchData.getProjectCostTo())) {
            query = query.whereLessThanOrEqualTo("projectCost", Integer.parseInt(searchData.getProjectCostTo()));
            Log.d(TAG, "projectCost: "+searchData.getProjectCostTo());
        }
        if (!TextUtils.isEmpty(searchData.getPartnersGender())){
            query = query.whereEqualTo("partnerGender", searchData.getPartnersGender());
            Log.d(TAG, "partnerGender: "+ searchData.getPartnersGender());
        }
        if (!TextUtils.isEmpty(searchData.getPartnersEducation())) {
            query = query.whereEqualTo("partnersEducation", searchData.getPartnersEducation());
            Log.d(TAG, "partnersEducation: "+ searchData.getPartnersEducation());
        }
        if (!TextUtils.isEmpty(searchData.getExperienceYearsFrom())) {
            query = query.whereGreaterThanOrEqualTo("yearsOfExperience", Integer.parseInt(searchData.getExperienceYearsFrom()));
            Log.d(TAG, "yearsOfExperience: "+searchData.getExperienceYearsFrom());
        }
        if (!TextUtils.isEmpty(searchData.getExperienceYearsTo())) {
            query = query.whereLessThanOrEqualTo("yearsOfExperience", Integer.parseInt(searchData.getExperienceYearsTo()));
            Log.d(TAG, "yearsOfExperience: "+searchData.getExperienceYearsTo());
        }
        if (!TextUtils.isEmpty(searchData.getExpectedProfitTo())){
            query = query.whereGreaterThanOrEqualTo("theExpectedProfitFrom", Integer.parseInt(searchData.getExpectedProfitFrom()));
            Log.d(TAG, "theExpectedProfitFrom: "+searchData.getExpectedProfitFrom());
        }
        if (!TextUtils.isEmpty(searchData.getExpectedProfitFrom())) {
            query = query.whereLessThanOrEqualTo("theExpectedProfitTo", Integer.parseInt(searchData.getExpectedProfitTo()));
            Log.d(TAG, "theExpectedProfitTo: "+searchData.getExpectedProfitTo());
        }
        if (!TextUtils.isEmpty(searchData.getProjectPublishMonth())&&!searchData.getProjectPublishMonth().equals(" ")){
            query = query.whereEqualTo("month", searchData.getProjectPublishMonth());
            Log.d(TAG, "queryFiltering: "+searchData.getProjectPublishMonth());
        }
        if (!TextUtils.isEmpty(searchData.getProjectPublishYear())&&!searchData.getProjectPublishYear().equals(" ")){
            query = query.whereEqualTo("year", searchData.getProjectPublishYear());
            Log.d(TAG, "queryFiltering: "+searchData.getProjectPublishYear());
        }
        if (!TextUtils.isEmpty(searchData.getProjectStartMonth())&&!searchData.getProjectStartMonth().equals(" ")){
            query = query.whereEqualTo("projectStartMonth", searchData.getProjectStartMonth());
            Log.d(TAG, "queryFiltering: "+searchData.getProjectStartMonth());
        }
        if (!TextUtils.isEmpty(searchData.getProjectStartYear())&&!searchData.getProjectStartYear().equals(" ")){
            query = query.whereEqualTo("projectStartYear", searchData.getProjectStartYear());
            Log.d(TAG, "queryFiltering: "+searchData.getProjectStartYear());
        }
        if (!TextUtils.isEmpty(searchData.getProjectPlaceCountry())&&!searchData.getCurrency().contains("--اختر--")){
            query = query.whereEqualTo("projectCurrency", searchData.getCurrency());
            Log.d(TAG, "queryFiltering: "+searchData.getCurrency());
        }

    }

}
