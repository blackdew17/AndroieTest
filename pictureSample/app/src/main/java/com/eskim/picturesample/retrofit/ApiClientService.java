package com.eskim.picturesample.retrofit;

import com.eskim.picturesample.api.PictureInfo;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiClientService {

    @GET("v2/list")
    Observable<List<PictureInfo>> getPictureUrlList();
}
