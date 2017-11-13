package com.example.temp.imagesearch.data.source.remote;

import com.example.temp.imagesearch.data.models.ImageSearchResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by temp on 10/11/17.
 */

public interface ImageSearchApiService {

    @GET("?method=flickr.photos.search")
    Observable<ImageSearchResponse> getImagesBasedonSearchText(@Query("api_key") String apiKey, @Query("text") String searchText,
                                                               @Query("format") String apiResponseFormat, @Query("nojsoncallback") int callbackStatus);

}
