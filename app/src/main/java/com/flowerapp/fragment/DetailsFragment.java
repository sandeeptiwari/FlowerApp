package com.flowerapp.fragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.flowerapp.R;
import com.flowerapp.bean.Flower;
import com.flowerapp.others.PaletteTransformation;
import com.flowerapp.others.Util;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Sandeep Tiwari on 6/18/2016.
 */

public class DetailsFragment extends Fragment {

    private static final String ARG_FLOWER = "flower";
    private ImageView mImageFlower;
    private TextView mAuther, mDate;
    private ImageButton mBtnBookMark, mBtnShare;
    private FrameLayout mPlatteLayout;
    private ProgressBar progressBar;

    /**
     * Create a new DetailsFragment
     *
     * @param flower The selected flower from the list
     */
    public static DetailsFragment newInstance(Flower flower) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_FLOWER, flower);
        DetailsFragment fragment = new DetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.details_fragment, container, false);
        mImageFlower = (ImageView) rootView.findViewById(R.id.flowerimage);
        mAuther = (TextView) rootView.findViewById(R.id.item_image_author);
        mDate = (TextView) rootView.findViewById(R.id.item_image_date);
        mBtnBookMark = (ImageButton) rootView.findViewById(R.id.btn_bookmark);
        mBtnShare = (ImageButton) rootView.findViewById(R.id.btn_share);
        mPlatteLayout = (FrameLayout) rootView.findViewById(R.id.item_image_text_container);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("Palet", "onViewCreated");
        Bundle args = getArguments();
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        final Flower flower = (Flower) args.getSerializable(ARG_FLOWER);
        //get the colors
        final int defaultTextColor = ContextCompat.getColor(getActivity(), R.color.text_without_palette);
        mAuther.setText(flower.getAuthor());
        mDate.setText(flower.getDate());
        mAuther.setTextColor(defaultTextColor);
        mDate.setTextColor(defaultTextColor);
        final int defaultBackgroundColor = ContextCompat.getColor(getActivity(), R.color.text_without_palette);
        Log.i("Palet", "call");
        Picasso.with(getActivity()).load(flower.getHighResImage(screenWidth, screenHeight)).into(mImageFlower, new Callback() {
            @Override
            public void onSuccess() {
                Log.i("Palet", "onSuccess");
                Bitmap bitmap = ((BitmapDrawable) mImageFlower.getDrawable()).getBitmap();
                if (bitmap != null && !bitmap.isRecycled()) {
                    Log.i("Palet", "bitmap");
                    Palette palette = Palette.from(bitmap).generate();
                    Log.i("Palet", "palette "+palette);

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
                        Log.i("Palet", "s rgb" + s.getRgb());
                        flower.setSwatch(s);
                        mAuther.setTextColor(s.getTitleTextColor());
                        mDate.setTextColor(s.getTitleTextColor());
                        Util.animateViewColor(mPlatteLayout, defaultBackgroundColor, s.getRgb());
                    }
                }
                bitmap = null;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                Log.i("Palet", "onError");
            }
        });

        Log.i("Palet", "end");


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("Palet", "onActivityCreated");
    }
}
