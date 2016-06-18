package com.flowerapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.flowerapp.database.FlowerContract.FlowerColumns;

/**
 * Created by Sandeep Tiwari on 6/17/2016.
 */

public class FlowerDatabase extends SQLiteOpenHelper {

    private static final String TAG = FlowerDatabase.class.getSimpleName();
    public static final String DB_NAME = "flower.db";
    public static final int DB_VERSION = 1;
    private Context mContext;

    public interface TABLE {
        String FLOWER_APP = "flower";

    }

    public FlowerDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String flowerTableCreateQuery =
                "CREATE TABLE " + TABLE.FLOWER_APP + " ("
                        + FlowerColumns.ID + " INTEGER PRIMARY KEY, "
                        + FlowerColumns.AUTHOR + " TEXT, "
                        + FlowerColumns.IMAGE_SRC + " TEXT, "
                        + FlowerColumns.COLOR + " TEXT, "
                        + FlowerColumns.DATE + " TEXT, "
                        + FlowerColumns.MODIFIED_DATE + " TEXT, "
                        + FlowerColumns.WIDTH + " INTEGER, "
                        + FlowerColumns.HEIGHT + " INTEGER, "
                        + FlowerColumns.RATIO + " REAL, "
                        + FlowerColumns.FEATURED + " INTEGER, "
                        + FlowerColumns.CATEGORY + " INTEGER, "
                        + FlowerColumns.CORRUPT + " INTEGER, "
                        + FlowerColumns.BOOKMARK
                        + " INTEGER,  UNIQUE(" + FlowerColumns.ID + ") ON CONFLICT REPLACE);";
          db.execSQL(flowerTableCreateQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}
