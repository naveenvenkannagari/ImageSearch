package com.example.temp.imagesearch.ui.imagesearch;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.temp.imagesearch.R;
import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.utils.Constants;
import com.example.temp.imagesearch.utils.Utility;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by temp on 11/11/17.
 */

public class ImageSearchAdapter extends RecyclerView.Adapter<ImageSearchAdapter.CustomViewHolder> {

    private List<Photo> mImageList;
    private Context context;
    private boolean misGridTypeSelected;
    private Photo mSelectedPhoto;
    private ImageSelectedListener mImageSelectedListener;

    public ImageSearchAdapter(Context context, ImageSelectedListener mImageSelectedListener) {
        this.context = context;
        this.mImageSelectedListener = mImageSelectedListener;
    }

    public void updateDataSet(List<Photo> imageList) {
        this.mImageList = imageList;
        notifyDataSetChanged();
    }

    public void setIsGridTypeSelected(boolean isGridTypeSelected) {
        this.misGridTypeSelected = isGridTypeSelected;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.image_search_grid_item, parent, false);
        return new CustomViewHolder(view);
    }


    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        if (misGridTypeSelected) {
            holder.imageView.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.grid_item_height);
            holder.label.setVisibility(View.GONE);
        } else {
            holder.imageView.getLayoutParams().height = (int) context.getResources().getDimension(R.dimen.list_item_height);
            holder.label.setVisibility(View.VISIBLE);
        }
        Photo photo = mImageList.get(position);
        String imageUrl = Utility.getImageUrl(photo);
        photo.setImageUrl(imageUrl);

        Picasso.with(context).load(imageUrl.toString()).into(holder.imageView);
        holder.imageView.setTag(photo);
        holder.label.setText(photo.getTitle());

        // to detect double tap on the image
        final GestureDetector gestureDetector = new GestureDetector(new MyGestureListener());
        holder.imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent event) {
                mSelectedPhoto = (Photo) view.getTag();
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }


    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_view)
        ImageView imageView;

        @BindView(R.id.image_title)
        TextView label;

        public CustomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


    public interface ImageSelectedListener {
        void onImageSelected(Photo photo);
    }


    private void addSelectedImageToFavourites() {
        if (mSelectedPhoto != null) {
            mImageSelectedListener.onImageSelected(mSelectedPhoto);
            Toast.makeText(context, context.getString(R.string.message_favourite_message), Toast.LENGTH_SHORT).show();
            Picasso.with(context).load(mSelectedPhoto.getImageUrl()).into(picassoImageTarget(context, Constants.IMAGE_DIRECTORY_NAME, mSelectedPhoto.getId() + ".jpeg"));
        }
    }


    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDoubleTap(MotionEvent event) {
            addSelectedImageToFavourites();
            return true;
        }

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }
    }

    /*
        Caching the Favourite Image in the Application Memory
     */
    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE);
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(() -> {
                    final File myImageFile = new File(directory, imageName);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(myImageFile);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                //NA
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                //NA
            }
        };
    }


}
