package com.example.toki.merge_include;


import android.app.LauncherActivity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.media.CamcorderProfile.get;
import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by toki on 2017/12/16.
 */

public class AdapterRecycleView extends RecyclerView.Adapter<AdapterRecycleView.ViewHolder> {

    private List<AlbumRecycleView> albumRecycleView;
    private Context context;
    public static ImageLoader imageLoader;




    public AdapterRecycleView(List<AlbumRecycleView> albumRecycleView, Context context) {
        this.albumRecycleView = albumRecycleView;
        this.context = context;
    }





    @Override
    public AdapterRecycleView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_pic,parent,false);
        imageLoader= ImageLoader.getInstance();
        AdapterRecycleView.imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterRecycleView.ViewHolder holder, int position) {
         final AlbumRecycleView recycleView=albumRecycleView.get(position);


        try{
             holder.name.setText(recycleView.getName());
            holder.text.setText(recycleView.getText());
            holder.time.setText(recycleView.getTime());
            holder.cardtimekey.setText(recycleView.getTimekey());

            int defaultImage = context.getResources().getIdentifier("@drawable/image_failed",null,context.getPackageName());

            //create display options
            DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true)
                    .showImageForEmptyUri(defaultImage).showImageOnFail(defaultImage).showImageOnLoading(defaultImage).build();

            imageLoader.displayImage(recycleView.getProfileImage(), holder.profileImage, options);
            imageLoader.displayImage(recycleView.getPostImage(),holder.postPhoto,options);
            imageLoader.displayImage(recycleView.getrep1(),holder.rep1,options);
            imageLoader.displayImage(recycleView.getrep2(),holder.rep2,options);

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,ClickPost.class);
                    intent.putExtra("toki",recycleView.getTimekey());
                    Toast.makeText(context, "TimeKetIntent"+recycleView.getTimekey(), Toast.LENGTH_SHORT).show();
                    context.startActivity(intent);
                }
            });

         }catch (IllegalArgumentException e){
                  Log.e(TAG, "getView: IllegalArgumentException: " + e.getMessage() );

        }

    }


    @Override
    public int getItemCount() {
        return albumRecycleView.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView time, cardtimekey;
        public TextView text;
        public CircleImageView profileImage, rep1, rep2;
        public ImageView postPhoto;
        public LinearLayout linearLayout;

        public ClipData.Item currentItem;
        public View view;



        public ViewHolder(View itemView) {
            super(itemView);

            this.view=itemView;

            cardtimekey = itemView.findViewById(R.id.card_timekey);
            name = itemView.findViewById(R.id.Name);
            time = itemView.findViewById(R.id.time);
            text = itemView.findViewById(R.id.text);
            profileImage = itemView.findViewById(R.id.cardImage);
            postPhoto = itemView.findViewById(R.id.imageCard);
            rep1 = itemView.findViewById(R.id.cardRep1);
            rep2 = itemView.findViewById(R.id.cardRep2);
            linearLayout = itemView.findViewById(R.id.cardMain);

        }
    }

}


//    private void setupImageLoader(){
//        // UNIVERSAL IMAGE LOADER SETUP
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
//                .cacheOnDisc(true).cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .displayer(new FadeInBitmapDisplayer(300)).build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
//                .defaultDisplayImageOptions(defaultOptions)
//                .memoryCache(new WeakMemoryCache())
//                .discCacheSize(100 * 1024 * 1024).build();
//
//        ImageLoader.getInstance().init(config);
//        // END - UNIVERSAL IMAGE LOADER SETUP
//    }


