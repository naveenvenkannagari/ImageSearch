package com.example.temp.imagesearch.ui.imagefavourite;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.temp.imagesearch.R;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.utils.Constants;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by temp on 12/11/17.
 */

public class ImageFavouriteAdapter extends RecyclerView.Adapter<ImageFavouriteAdapter.CustomViewHolder> {

    private Context context;
    private List<Photo> photoList;
    private ImageSelectedListener imageSelectedListener;
    private Photo selectedPhoto;

    public ImageFavouriteAdapter(List<Photo> photosList, Context context, ImageSelectedListener imageSelectedListener) {
        this.photoList = photosList;
        this.context = context;
        this.imageSelectedListener = imageSelectedListener;
    }

    @Override
    public ImageFavouriteAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_search_grid_item, parent, false);
        return new ImageFavouriteAdapter.CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageFavouriteAdapter.CustomViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(Constants.IMAGE_DIRECTORY_NAME, Context.MODE_PRIVATE);
        File myImageFile = new File(directory, photo.getId() + ".jpeg");
        Picasso.with(context).load(myImageFile).config(Bitmap.Config.RGB_565).into(holder.imageView);
        holder.imageView.setTag(photo);
        holder.imageView.setOnLongClickListener(v -> {
            selectedPhoto = (Photo) v.getTag();
            showAlertDialog();
            return false;
        });
    }

    private void removePhotoFromList(Photo photo) {
        photoList.remove(photoList.indexOf(photo));
        notifyDataSetChanged();
    }

    private void showAlertDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle(context.getString(R.string.delete_favourite_label))
                .setMessage(context.getString(R.string.delete_favorite_message))
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    if (selectedPhoto != null) {
                        removePhotoFromList(selectedPhoto);
                        deleteImageFromCache(selectedPhoto.getId());
                        imageSelectedListener.onImageLongPressClick(selectedPhoto);
                    }
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                    // do nothing
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteImageFromCache(String id) {
        ContextWrapper cw = new ContextWrapper(context);
        File directory = cw.getDir(Constants.IMAGE_DIRECTORY_NAME, Context.MODE_PRIVATE);
        File image = new File(directory, id + ".jpeg");
        image.delete();
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view)
        ImageView imageView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ImageSelectedListener {
        void onImageLongPressClick(Photo photo);
    }


}
