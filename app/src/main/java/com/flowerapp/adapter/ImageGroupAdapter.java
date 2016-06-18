package com.flowerapp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.flowerapp.R;
import com.flowerapp.bean.Flower;
import com.flowerapp.database.FlowerDataHandler;
import com.flowerapp.others.OnItemClickListener;
import com.flowerapp.others.PaletteTransformation;
import com.flowerapp.others.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Sandeep Tiwari on 6/16/2016.
 */

public class ImageGroupAdapter extends RecyclerView.Adapter<ImageGroupAdapter.ImagesViewHolder> {

    private Context mContext;
    private List<Flower> mDatas;
    private int mScreenWidth;

    private int mDefaultTextColor;
    private int mDefaultBackgroundColor;


    public ImageGroupAdapter() {
    }

    public ImageGroupAdapter(List<Flower> datas){
        mDatas = datas;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ImagesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View rowView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_group_item, parent, false);
        mContext = parent.getContext();

        // Return a new holder instance
        ImagesViewHolder viewHolder = new ImagesViewHolder(rowView, onItemClickListener);

        //get the colors
        mDefaultTextColor = ContextCompat.getColor(mContext, R.color.text_without_palette);
        mDefaultBackgroundColor = ContextCompat.getColor(mContext, R.color.text_without_palette);

        //get the screenWidth :D optimize everything :D
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ImagesViewHolder imagesViewHolder, final int position) {
        Flower flower = mDatas.get(position);

        imagesViewHolder.imageAuthor.setText(flower.getAuthor());
        imagesViewHolder.imageDate.setText(flower.getModifiedDate());
        //imagesViewHolder.imageView.setDrawingCacheEnabled(true);
        imagesViewHolder.imageView.setImageBitmap(null);

        //reset colors so we prevent crazy flashes :D
        imagesViewHolder.imageAuthor.setTextColor(mDefaultTextColor);
        imagesViewHolder.imageDate.setTextColor(mDefaultTextColor);
        imagesViewHolder.imageTextContainer.setBackgroundColor(mDefaultBackgroundColor);

        int isBookMark = flower.getBookmark();
        Log.i("RESPONSE", "bookmark value "+isBookMark);
        if(isBookMark == 1){
            imagesViewHolder.btnBookmark.setBackgroundResource(R.drawable.ic_bookmark);
        }else{
            imagesViewHolder.btnBookmark.setBackgroundResource(R.drawable.ic_bookmark_border);
        }

        //cancel any loading images on this view
        Picasso.with(mContext).cancelRequest(imagesViewHolder.imageView);
        //load the image
        Picasso.with(mContext).load(mDatas.get(position).getImageSrc(mScreenWidth))
                .transform(PaletteTransformation.instance()).into(imagesViewHolder.imageView, new Callback.EmptyCallback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) imagesViewHolder.imageView.getDrawable()).getBitmap(); // Ew!

                if (bitmap != null && !bitmap.isRecycled()) {

                    Palette palette = PaletteTransformation.getPalette(bitmap);

                    if (palette != null) {
                        Palette.Swatch s = palette.getVibrantSwatch();
                        if (s == null) {
                            s = palette.getDarkVibrantSwatch();
                        }
                        if (s == null) {
                            s = palette.getLightVibrantSwatch();
                        }
                        if (s == null) {
                            s = palette.getMutedSwatch();
                        }

                        if (s != null && position >= 0 && position < mDatas.size()) {
                            if (mDatas.get(position) != null) {
                                mDatas.get(position).setSwatch(s);
                            }

                            imagesViewHolder.imageAuthor.setTextColor(s.getTitleTextColor());
                            imagesViewHolder.imageDate.setTextColor(s.getTitleTextColor());
                            Util.animateViewColor(imagesViewHolder.imageTextContainer, mDefaultBackgroundColor, s.getRgb());
                        }
                    }
                }

                // just delete the reference again.
                bitmap = null;

                if (Build.VERSION.SDK_INT >= 21) {
                    imagesViewHolder.imageView.setTransitionName("cover" + position);
                }
                imagesViewHolder.imageTextContainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onClick(v, position);
                    }
                });
            }
        });

        //calculate height of the list-item so we don't have jumps in the view
        DisplayMetrics displaymetrics = mContext.getResources().getDisplayMetrics();
        //image.width .... image.height
        //device.width ... device
        int finalHeight = (int) (displaymetrics.widthPixels / flower.getRatio());
        imagesViewHolder.imageView.setMinimumHeight(finalHeight);

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    // Used to cache the views within the item layout for fast access
    public static class ImagesViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        protected final FrameLayout imageTextContainer;
        protected final ImageView imageView;
        protected final TextView imageAuthor;
        protected final TextView imageDate;
        protected final ImageButton btnBookmark, btnShare;
        OnItemClickListener onItemClickListener;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ImagesViewHolder(View itemView, final OnItemClickListener onItemClickListener){
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            this.onItemClickListener = onItemClickListener;

            imageTextContainer = (FrameLayout) itemView.findViewById(R.id.item_image_text_container);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_img);
            imageAuthor = (TextView) itemView.findViewById(R.id.item_image_author);
            imageDate = (TextView) itemView.findViewById(R.id.item_image_date);
            btnBookmark = (ImageButton)itemView.findViewById(R.id.btn_bookmark);
            btnShare = (ImageButton)itemView.findViewById(R.id.btn_share);

            btnBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnBookmark.setBackgroundResource(R.drawable.ic_bookmark);
                    onItemClickListener.onClickBookMark(v, getAdapterPosition());
                }
            });

            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickShare(v, getAdapterPosition());
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, getAdapterPosition());
                }
            });

        }

    }
}
