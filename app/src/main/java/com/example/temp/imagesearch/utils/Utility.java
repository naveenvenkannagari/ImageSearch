package com.example.temp.imagesearch.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import com.example.temp.imagesearch.R;
import com.example.temp.imagesearch.data.models.Photo;

/**
 * Created by temp on 13/11/17.
 */

public class Utility {

    private static ProgressDialog mProgressDialog;

    public static void showProgressDialog(Context activity, String message, boolean cancelable) {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                return;
            }

            mProgressDialog = new ProgressDialog(activity);
            if (message == null) {
                message = activity.getResources().getString(R.string.progress_dialog_message);
            }
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(cancelable);
            mProgressDialog.setCanceledOnTouchOutside(cancelable);
            mProgressDialog.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
                mProgressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getImageUrl(Photo photo) {
        StringBuilder imageUrl = new StringBuilder();
        imageUrl.append("https://farm");
        imageUrl.append(photo.getFarm());
        imageUrl.append(".staticflickr.com/");
        imageUrl.append(photo.getServer());
        imageUrl.append("/");
        imageUrl.append(photo.getId());
        imageUrl.append("_");
        imageUrl.append(photo.getSecret());
        imageUrl.append(".jpg");
        return imageUrl.toString();
    }

}
