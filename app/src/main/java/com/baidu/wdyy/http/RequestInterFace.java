package com.baidu.wdyy.http;


import com.baidu.wdyy.bean.CinemaBean;
import com.baidu.wdyy.bean.IDMoiveDetalisOne;
import com.baidu.wdyy.bean.MoiveBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfo;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * @author lmx
 * @date 2019/1/17
 */
public interface RequestInterFace {

    /**
     * 登录
     *
     * @param phone
     * @param pwd
     * @return
     */
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<Result<UserInfo>> login(@Field("phone") String phone,
                                       @Field("pwd") String pwd);


    /**
     * 注册
     *
     * @param nickName
     * @param phone
     * @param pwd
     * @param pwd2
     * @param sex
     * @param birthday
     * @param imei
     * @param ua
     * @param screenSize
     * @param os
     * @param email
     * @return
     */
    @FormUrlEncoded
    @POST("user/v1/registerUser")
    Observable<Result> register(@Field("nickName") String nickName,
                                @Field("phone") String phone,
                                @Field("pwd") String pwd,
                                @Field("pwd2") String pwd2,
                                @Field("sex") int sex,
                                @Field("birthday") String birthday,
                                @Field("imei") String imei,
                                @Field("ua") String ua,
                                @Field("screenSize") String screenSize,
                                @Field("os") String os,
                                @Field("email") String email);

    //热门影院列表
    @GET("movie/v1/findHotMovieList")
    Observable<Result<List<MoiveBean>>> Popular(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);

    //正在上映列表
    @GET("movie/v1/findReleaseMovieList")
    Observable<Result<List<MoiveBean>>> Being(@Header("userId") int userId,
                                              @Header("sessionId") String sessionId,
                                              @Query("page") int page,
                                              @Query("count") int count);

    //即将上映列表
    @GET("movie/v1/findComingSoonMovieList")
    Observable<Result<List<MoiveBean>>> Soon(@Header("userId") int userId,
                                             @Header("sessionId") String sessionId,
                                             @Query("page") int page,
                                             @Query("count") int count);

    //推荐影院列表
    @GET("cinema/v1/findRecommendCinemas")
    Observable<Result<List<CinemaBean>>> Cinema(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);

    //附近影院列表
    @GET("cinema/v1/findNearbyCinemas")
    Observable<Result<List<CinemaBean>>> Nearby(@Header("userId") int userId,
                                                @Header("sessionId") String sessionId,
                                                @Query("longitude") String longitude,
                                                @Query("latitude") String latitude,
                                                @Query("page") int page,
                                                @Query("count") int count);

    //根据电影id查询电影信息
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<IDMoiveDetalisOne>> IDMoivedetalis(@Header("userId") int userId,
                                                         @Header("sessionId") String sessionId,
                                                         @Query("movieId") int movieId);

    /**
     * 查询用户关注的影片列表
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("movie/v1/verify/findMoviePageList")
    Observable<Result<List<MoiveBean>>> findMovie(@Header("userId") int userId,
                                                  @Header("sessionId") String sessionId,
                                                  @Query("page") int page,
                                                  @Query("count") int count);

    /**
     * 查询用户关注的影片列表
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */
    @GET("cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<CinemaBean>>> findCinema(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId,
                                                    @Query("page") int page,
                                                    @Query("count") int count);


    /**
     * 影院关注
     *
     * @param userId
     * @param sessionId
     * @param cinemaId
     * @return
     */
    @GET("cinema/v1/verify/followCinema")
    Observable<Result> followCinema(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Query("cinemaId") int cinemaId);

    /**
     * 影院取消关注
     *
     * @param userId
     * @param sessionId
     * @param cinemaId
     * @return
     */
    @GET("cinema/v1/verify/cancelFollowCinema")
    Observable<Result> cancelFollowCinema(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("cinemaId") int cinemaId);

    /**
     * 上传头像
     *
     * @param userId
     * @param sessionId
     * @param image
     * @return
     */
    @Multipart
    @POST("user/v1/verify/uploadHeadPic")
    Observable<Result<UserInfo>> uploadHeadPic(
            @Header("userId") int userId,
            @Header("sessionId") String sessionId,
            @Part("image") File image);


}
