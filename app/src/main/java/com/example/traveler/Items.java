package com.example.traveler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Items extends AppCompatActivity implements RecyclerAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private ProgressBar mProgressBar;
    private FirebaseStorage mStorage;
    private DatabaseReference mDBRef;
    private ValueEventListener mDBListener;
    private List<imgData> mImage;

    private void openDetailActivity(String[] data){
        Intent intent = new Intent(this, activity_img_viewer.class);
        intent.putExtra("NAME_KEY", data[0]);
        intent.putExtra("LOCATION_KEY", data[1]);
        intent.putExtra("IMAGE_KEY", data[2]);

        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);

        mRecyclerView = findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mImage = new ArrayList<>();
        mAdapter = new RecyclerAdapter(Items.this, mImage);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(Items.this);
        mStorage = FirebaseStorage.getInstance();
        mDBRef = FirebaseDatabase.getInstance().getReference("imgData");

        mDBListener = mDBRef.addValueEventListener(new ValueEventListener(){
           public void onDataChange(DataSnapshot dataSnapshot){
               mImage.clear();
               for(DataSnapshot imageSnapshot : dataSnapshot.getChildren()){
                   imgData upload = imageSnapshot.getValue(imgData.class);
                   upload.setImgId(imageSnapshot.getKey());
                   mImage.add(upload);
               }
               mAdapter.notifyDataSetChanged();
           }
           public void onCancelled(DatabaseError databaseError){
               Toast.makeText(Items.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
           }
        });
    }
    public void onItemClick(int position){
        imgData clickedImage = mImage.get(position);
        String[] imageData = {clickedImage.getTitle(),clickedImage.getLocation(),clickedImage.getImgId()};
        openDetailActivity(imageData);
    }
    public void onShowItemClick(int position){
        imgData clickedImage = mImage.get(position);
        String[] imageData = {clickedImage.getTitle(),clickedImage.getLocation(),clickedImage.getImgId()};
        openDetailActivity(imageData);
    }
    public void onDeleteItemClick(int position){
        imgData selectedItem =  mImage.get(position);
        final String selectedKey = selectedItem.getImgId();

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getImgId());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mDBRef.child(selectedKey).removeValue();
                Toast.makeText(Items.this,"Item deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        mDBRef.removeEventListener(mDBListener);
    }
}
