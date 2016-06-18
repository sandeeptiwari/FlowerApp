package com.flowerapp.others;

import android.view.View;

/**
 * Created by Sandeep Tiwari on 6/16/2016.
 */

public interface OnItemClickListener {
    void onClick(View v, int position);
    void onClickShare(View v, int position);
    void onClickBookMark(View v, int position);

}
