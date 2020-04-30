package com.example.traveler;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> {
    private Context mContext;
    private List<imgData> imagers;
    private OnItemClickListener mListener;

    public RecyclerAdapter(Context context, List<imgData> uploads){
        mContext = context;
        imagers = uploads;
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.activity_row_model, parent, false);
        return new RecyclerViewHolder(v);
    }
    public void onBindViewHolder(RecyclerViewHolder holder, int position){
        imgData currentImage = imagers.get(position);
        holder.title.setText(currentImage.getTitle());
        holder.location.setText(currentImage.getLocation());
        Picasso.with(mContext)
                .load(currentImage.getImgId()).placeholder(R.drawable.placeholdere).fit().centerCrop().into(holder.ImgView);
    }
    public int getItemCount(){
        return imagers.size();
    }
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener{
        public TextView title, location;
        public ImageView ImgView;

        public RecyclerViewHolder(View itmeView){
            super(itmeView);
            title = itmeView.findViewById(R.id.title);
            location = itmeView.findViewById(R.id.location);
            ImgView = itmeView.findViewById(R.id.sImg);

            itmeView.setOnClickListener(this);
            itmeView.setOnCreateContextMenuListener(this);
        }

        public void onClick(View v){
            if (mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position);
                }
            }
        }

        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
            menu.setHeaderTitle("Select Action");
            MenuItem showItem = menu.add(Menu.NONE, 1,2, "Show");
            MenuItem deleteItem = menu.add(Menu.NONE, 2,2, "Delete");

            showItem.setOnMenuItemClickListener(this);
            deleteItem.setOnMenuItemClickListener(this);
        }

        public boolean onMenuItemClick(MenuItem item){
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    switch (item.getItemId()){
                        case 1:
                            mListener.onShowItemClick(position);
                            return true;
                        case 2:
                            mListener.onDeleteItemClick(position);
                            return true;
                    }
                }
            }
            return false;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onShowItemClick(int position);
        void onDeleteItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }
}
