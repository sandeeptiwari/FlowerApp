package com.flowerapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.flowerapp.bean.Flower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sandeep Tiwari on 6/17/2016.
 */

public class FlowerDataHandler {

    private static final String TAG = FlowerDataHandler.class.getSimpleName();

    public static void saveFlower(Context ctx, Flower flower){

        ContentValues values = new ContentValues();

        int id = flower.getId();
        values.put(FlowerContract.FlowerColumns.ID, id);
        values.put(FlowerContract.FlowerColumns.AUTHOR, flower.getAuthor());
        values.put(FlowerContract.FlowerColumns.IMAGE_SRC, flower.getImageSrc());
        values.put(FlowerContract.FlowerColumns.COLOR, flower.getColor());
        values.put(FlowerContract.FlowerColumns.DATE, flower.getDate());
        values.put(FlowerContract.FlowerColumns.MODIFIED_DATE, flower.getModifiedDate());
        values.put(FlowerContract.FlowerColumns.WIDTH, flower.getWidth());
        values.put(FlowerContract.FlowerColumns.HEIGHT, flower.getHeight());
        values.put(FlowerContract.FlowerColumns.RATIO, flower.getRatio());
        values.put(FlowerContract.FlowerColumns.FEATURED, flower.getFeatured());
        values.put(FlowerContract.FlowerColumns.CATEGORY, flower.getCategory());
        values.put(FlowerContract.FlowerColumns.CORRUPT, flower.getCorrupt());
        values.put(FlowerContract.FlowerColumns.BOOKMARK, flower.getBookmark());
        ctx.getContentResolver().insert(FlowerContract.FlowerAppUri.CONTENT_URI, values);
        Log.i(TAG, "insertion is complete");
    }

    public static Flower getFlowerById(Context ctx, String flowerId)
    {
        Flower flower = new Flower();
        String where = FlowerContract.FlowerColumns.ID + "==" + flowerId;
        Log.i(TAG, "check where clause in UserDataHelper :->"+where);

        Cursor cursor = ctx.getContentResolver().query(FlowerContract.FlowerAppUri.CONTENT_URI,
                null, where, null, null);

        int count = cursor.getCount();
        Log.i(TAG, "cursor count :->"+count);

        if(count == 0)
            return null;

        while(cursor.moveToNext())
        {
            int id = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.ID));
            flower.setId(id);

            String auther = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.AUTHOR));
            flower.setAuthor(auther);

            String imageSrc = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.IMAGE_SRC));
            flower.setImageSrc(imageSrc);

            String color = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.COLOR));
            flower.setColor(color);

            String date = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.DATE));
            flower.setDate(date);

            String modifyDate = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.MODIFIED_DATE));
            flower.setModifiedDate(modifyDate);

            int width = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.WIDTH));
            flower.setWidth(width);

            int height = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.HEIGHT));
            flower.setHeight(height);

            double ratio = cursor.getDouble(cursor.getColumnIndex(FlowerContract.FlowerColumns.RATIO));
            flower.setRatio(ratio);

            int feature = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.FEATURED));
            flower.setFeatured(feature);

            int cat = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.CATEGORY));
            flower.setCategory(cat);

            int cor = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.CORRUPT));
            flower.setCorrupt(cor);

            int bookMark = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.BOOKMARK));
            flower.setBookmark(bookMark);
        }

        return flower;
    }

    public static List<Flower> getAllFlowers(Context ctx) {

        if (ctx == null)
            return null;

        List<Flower> flowers = new ArrayList<Flower>();
        String where = "";

        Log.i(TAG, "check where clause in PostDataHelper :->" + where);

        Cursor cursor = ctx.getContentResolver().query(FlowerContract.FlowerAppUri.CONTENT_URI,
                null, null, null, null);

        int count = cursor.getCount();
        Log.i(TAG, "cursor count :->" + count);

        if (count == 0)
            return null;

        while (cursor.moveToNext()) {
            Flower flower = new Flower();
            int id = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.ID));
            flower.setId(id);

            String auther = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.AUTHOR));
            flower.setAuthor(auther);

            String imageSrc = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.IMAGE_SRC));
            flower.setImageSrc(imageSrc);

            String color = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.COLOR));
            flower.setColor(color);

            String date = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.DATE));
            flower.setDate(date);

            String modifyDate = cursor.getString(cursor.getColumnIndex(FlowerContract.FlowerColumns.MODIFIED_DATE));
            flower.setModifiedDate(modifyDate);

            int width = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.WIDTH));
            flower.setWidth(width);

            int height = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.HEIGHT));
            flower.setHeight(height);

            double ratio = cursor.getDouble(cursor.getColumnIndex(FlowerContract.FlowerColumns.RATIO));
            flower.setRatio(ratio);

            int feature = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.FEATURED));
            flower.setFeatured(feature);

            int cat = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.CATEGORY));
            flower.setCategory(cat);

            int cor = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.CORRUPT));
            flower.setCorrupt(cor);

            int bookMark = cursor.getInt(cursor.getColumnIndex(FlowerContract.FlowerColumns.BOOKMARK));
            flower.setBookmark(bookMark);

            flowers.add(flower);
        }
        Log.i(TAG, "flowers size" +flowers.size());
        return flowers;
    }

    public static void bookMarkImage(Context ctx, int id) {
        ContentValues values = new ContentValues();
        String where = FlowerContract.FlowerColumns.ID + "==" + id;
        values.put(FlowerContract.FlowerColumns.BOOKMARK, 1);
        ctx.getContentResolver().update(FlowerContract.FlowerAppUri.CONTENT_URI, values, where, null);
        Log.i(TAG, "bookmarkupdated");
    }

}
