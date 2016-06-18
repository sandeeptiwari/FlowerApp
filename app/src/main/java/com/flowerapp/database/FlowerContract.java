package com.flowerapp.database;

import android.net.Uri;

/**
 * Created by Sandeep Tiwari on 6/17/2016.
 */

public class FlowerContract {

    public static final String 	CONTENT_AUTHORITY 	= "com.flowerapp.content";
    private static final Uri BASE_CONTENT_URI 	= Uri.parse("content://" + CONTENT_AUTHORITY);

    public interface FlowerColumns{
        String ID               = "id";
        String AUTHOR 			= "author";
        String IMAGE_SRC 		= "image_src";
        String COLOR 			= "color";
        String DATE 			= "date";
        String MODIFIED_DATE    = "modified_date";
        String WIDTH            = "width";
        String HEIGHT           = "height";
        String RATIO            = "ratio";
        String FEATURED         = "featured";
        String CATEGORY         = "user_id";
        String CORRUPT          = "corrupt";
        String BOOKMARK         = "bookmark";
    }

    private static final String PATH_FLOWER_APP = "flower";

    public static class FlowerAppUri implements FlowerColumns{
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FLOWER_APP).build();
    }
}
