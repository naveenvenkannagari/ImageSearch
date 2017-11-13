package com.example.temp.imagesearch.data.source.local;

import com.example.temp.imagesearch.data.models.Photo;
import com.example.temp.imagesearch.data.realmmodels.RealmPhoto;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by temp on 11/11/17.
 */

public class ImageLocalDataService {

    private Realm mRealm = null;

    public ImageLocalDataService(final Realm realm) {
        mRealm = realm;
    }


    public void addFavouritePhoto(Photo photo) {
        mRealm.executeTransactionAsync(bgRealm -> {
            RealmPhoto realmPhoto = bgRealm.createObject(RealmPhoto.class, photo.getId());
            realmPhoto.setOwner(photo.getOwner());
            realmPhoto.setSecret(photo.getSecret());
            realmPhoto.setServer(photo.getServer());
            realmPhoto.setFarm(photo.getFarm());
            realmPhoto.setTitle(photo.getTitle());
            realmPhoto.setIspublic(photo.getIspublic());
            realmPhoto.setIsfriend(photo.getIsfriend());
            realmPhoto.setIsfamily(photo.getIsfamily());
        }, () -> {

        }, error -> error.printStackTrace());


    }


    public void removeFavouritePhoto(String photoId) {
        mRealm.executeTransaction(realm -> {
            RealmResults<RealmPhoto> result = realm.where(RealmPhoto.class).
                    equalTo(RealmPhoto.ID, photoId).findAll();
            result.deleteAllFromRealm();
        });
    }

    public List<Photo> getAllFavourites() {
        RealmResults<RealmPhoto> result = mRealm.where(RealmPhoto.class).findAll();
        return convertToDisplayModels(mRealm.copyFromRealm(result));
    }

    private List<Photo> convertToDisplayModels(List<RealmPhoto> dataList) {
        List<Photo> realmList = new ArrayList<>();
        for (RealmPhoto realmPhoto : dataList) {
            Photo photo = new Photo();
            photo.setId(realmPhoto.getId());
            photo.setFarm(realmPhoto.getIsfriend());
            photo.setIsfamily(realmPhoto.getIsfamily());
            photo.setTitle(realmPhoto.getTitle());
            photo.setServer(realmPhoto.getServer());
            photo.setSecret(realmPhoto.getSecret());
            photo.setOwner(realmPhoto.getOwner());
            photo.setIspublic(realmPhoto.getIspublic());
            photo.setIsfriend(realmPhoto.getIsfriend());
            realmList.add(photo);
        }
        return realmList;
    }


}
