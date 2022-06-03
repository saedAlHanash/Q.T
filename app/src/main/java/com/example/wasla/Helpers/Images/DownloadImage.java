package com.example.wasla.Helpers.Images;

import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wasla.R;

import static com.example.wasla.ApiClint.ApiClint.BASE_URL;

public class DownloadImage {
    /**
     * to get image from api and set it with image view <br>
     * how it work :  get BASE_URL {@link com.example.wasla.ApiClint.ApiClint#BASE_URL}<br>
     * then add api link then add id for get image <br>
     * if downloading field set an icon from res
     *
     * @param activity  for get context
     * @param id        id to get specific image from api
     * @param imageView image view to init image with it
     */
    public static void GET_AND_SET_USER_IMAGE(Activity activity, int id, ImageView imageView) {

        String link = BASE_URL + "api/services/app/UserInformation/DownloadImage/" + id;

        Glide.with(activity)
                .load(link)
                .placeholder(R.drawable.ic_user_avatar_filled)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

    public static void SET_IMAGE_WITHOUT_PLACEHOLDER(Activity activity, int id, ImageView imageView) {

        String link = BASE_URL + "api/services/app/UserInformation/DownloadImage/" + id;

        Glide.with(activity)
                .load(link)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView);
    }

}
