package com.flowerapp.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.flowerapp.R;
import com.flowerapp.adapter.ImageGroupAdapter;
import com.flowerapp.bean.Flower;
import com.flowerapp.bean.Result;
import com.flowerapp.database.FlowerDataHandler;
import com.flowerapp.others.DetailsTransition;
import com.flowerapp.others.OnItemClickListener;

import java.io.File;
import java.util.List;

/**
 * Created by Sandeep Tiwari on 6/16/2016.
 */

public class ImageGroupFragment extends Fragment {

    private Bundle bundle;
    private ImageGroupAdapter mImageAdapter;
    private RecyclerView mImageRecycler;
    private ProgressBar mImagesProgress;
    private List<Flower> flowers;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bundle = getArguments();
        Log.i("RESPONCE", "ImageGroupFragment " + bundle);
        View rootView = inflater.inflate(R.layout.frag_image_group, container, false);
        mImageRecycler = (RecyclerView) rootView.findViewById(R.id.fragment_last_images_recycler);
        mImagesProgress = (ProgressBar) rootView.findViewById(R.id.fragment_images_progress);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        flowers = (List<Flower>) bundle.getSerializable("data");
        mImageAdapter = new ImageGroupAdapter(flowers);
        mImageAdapter.setOnItemClickListener(recyclerRowClickListener);
        mImageRecycler.setAdapter(mImageAdapter);
        mImageRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private OnItemClickListener recyclerRowClickListener = new OnItemClickListener() {

        @Override
        public void onClick(View v, int position) {
            Log.i("RESPONCE", "onclick image");
            Flower flower = flowers.get(position);
            DetailsFragment imageDetails = DetailsFragment.newInstance(flower);

            FragmentTransaction transaction = getActivity().getFragmentManager()
                    .beginTransaction();
            // Note that we need the API version check here because the actual transition classes (e.g. Fade)
            // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
            // ARE available in the support library (though they don't do anything on API < 21)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                imageDetails.setSharedElementEnterTransition(new DetailsTransition());
                imageDetails.setEnterTransition(new Fade());
                setExitTransition(new Fade());
                imageDetails.setSharedElementReturnTransition(new DetailsTransition());
                transaction.addSharedElement(v, "kittenImage");
            }

             transaction.replace(R.id.ui_container, imageDetails)
                    .addToBackStack(null)
                    .commit();
        }

        @Override
        public void onClickShare(View v, int position) {
            Log.i("RESPONCE", "onClickShare");
            Intent intent = new Intent(Intent.ACTION_SEND);

        // Always use string resources for UI text.
        // This says something like "Share this photo with"
            String title = "Share Via";
        // Create intent to show chooser
            Intent chooser = Intent.createChooser(intent, title);

        // Verify the intent will resolve to at least one activity
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(chooser);
            }

        }

        @Override
        public void onClickBookMark(View v, int position) {
            Log.i("RESPONCE", "onClickBookMark");
            Flower flower = flowers.get(position);
            flower.setBookmark(1);
            FlowerDataHandler.bookMarkImage(getActivity(), flower.getId());
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void initShareIntent(String type) {
        boolean found = false;
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("image/jpeg");

        // gets the list of intents that can be loaded.
        List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(share, 0);
        if (!resInfo.isEmpty()) {
            for (ResolveInfo info : resInfo) {
                if (info.activityInfo.packageName.toLowerCase().contains(type) ||
                        info.activityInfo.name.toLowerCase().contains(type)) {
                    share.putExtra(Intent.EXTRA_SUBJECT, "subject");
                    share.putExtra(Intent.EXTRA_TEXT, "your text");
                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(""))); // Optional, just if you wanna share an image.
                    share.setPackage(info.activityInfo.packageName);
                    found = true;
                    break;
                }
            }
            if (!found)
                return;

            startActivity(Intent.createChooser(share, "Select"));
        }
    }
}
