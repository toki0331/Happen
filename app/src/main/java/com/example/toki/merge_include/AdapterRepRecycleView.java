package com.example.toki.merge_include;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by toki on 2017/12/30.
 */

public class AdapterRepRecycleView extends RecyclerView.Adapter<AdapterRepRecycleView.MyViewHolder> {

    private Context context;
    ImageLoader imageLoader;
    private List<AlbumRepRecycleView> adapterList;


    public AdapterRepRecycleView(List<AlbumRepRecycleView> adapterList, Context context) {
        this.adapterList = adapterList;
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_click, parent, false);
        imageLoader = ImageLoader.getInstance();

        AdapterRecycleView.imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AlbumRepRecycleView recycleView = adapterList.get(position);
        try {
            holder.name.setText(recycleView.getName());
            holder.text.setText(recycleView.getText());
            holder.time.setText(recycleView.getTime());


            int defaultImage = context.getResources().getIdentifier("@drawable/image_failed", null, context.getPackageName());

            //create display options
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(defaultImage).showImageOnFail(defaultImage).showImageOnLoading(defaultImage).build();

            imageLoader.displayImage(recycleView.getProfileImage(), holder.profileImage, options);
            imageLoader.displayImage(recycleView.getPostImage(), holder.postPhoto, options);


        } catch (IllegalArgumentException e) {
            Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage());

        }
    }

    @Override
    public int getItemCount() {
        return adapterList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView time;
        public TextView text;
        public CircleImageView profileImage;
        public ImageView postPhoto;


        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.Name);
            time = itemView.findViewById(R.id.time);
            text = itemView.findViewById(R.id.text);
            profileImage = itemView.findViewById(R.id.cardImage);
            postPhoto = itemView.findViewById(R.id.imageCard);

        }
    }
}

