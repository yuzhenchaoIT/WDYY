package com.baidu.wdyy.http;


import com.baidu.wdyy.bean.CinemaBean;
import com.baidu.wdyy.bean.CinemaById;
import com.baidu.wdyy.bean.CinemaDetalisBean;
import com.baidu.wdyy.bean.CinemaRecy;
import com.baidu.wdyy.bean.FilmReviewBean;
import com.baidu.wdyy.bean.IDMoiveDetalisOne;
import com.baidu.wdyy.bean.IDMoiveDetalisTwo;
import com.baidu.wdyy.bean.MoiveBean;
import com.baidu.wdyy.bean.PurchaseBean;
import com.baidu.wdyy.bean.RemindBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfo;
import com.baidu.wdyy.bean.UserInfoBean;

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

    //查询电影信息明细
    @GET("cinema/v1/findCinemaInfo")
    Observable<Result<CinemaDetalisBean>> CinemaDetalis(@Header("userId") int userId,
                                                        @Header("sessionId") String sessionId,
                                                        @Query("cinemaId") int cinemaId);

    //查询电影影评
    @GET("movie/v1/findAllMovieComment")
    Observable<Result<List<FilmReviewBean>>> findAllMovieComment(@Header("userId") int userId,
                                                                 @Header("sessionId") String sessionId,
                                                                 @Query("movieId") int movieId,
                                                                 @Query("page") int page,
                                                                 @Query("count") int count);

    //查看影院排期
    @GET("movie/v1/findMovieScheduleList")
    Observable<Result<List<CinemaRecy>>> cinemaRecy(@Query("cinemasId") int cinemasId,
                                                    @Query("movieId") int movieId);

    //查看影院影片
    @GET("movie/v1/findMovieListByCinemaId")
    Observable<Result<List<CinemaById>>> cinemaById(@Query("cinemaId") int cinemaId);

    /**
     * 意见反馈
     *
     * @param userId
     * @param sessionId
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST("tool/v1/verify/recordFeedBack")
    Observable<Result> recordFeedBack(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("content") String content);


    //根据电影ID查询当前排片该电影的影院列表
    @GET("movie/v1/findCinemasListByMovieId")
    Observable<Result<List<PurchaseBean>>> Purchase(@Query("movieId") int movieId);

    @POST("movie/v1/verify/buyMovieTicket")
    @FormUrlEncoded
    Observable<Result> buyMovieTicket(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("scheduleId") int scheduleId,
                                      @Field("amount") int amount,
                                      @Field("sign") String sign);

    @POST("movie/v1/verify/pay")
    @FormUrlEncoded
    Observable<Result> buyMovieresult(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Field("payType") int payType,
                                      @Field("orderId") String orderId);

    @POST("user/v1/weChatBindingLogin")
    @FormUrlEncoded
    Observable<Result<UserInfo>> wxLogin(@Field("code") String code);

    /**
     * 用户签到
     *
     * @param userId
     * @param sessionId
     * @return
     */
    @GET("user/v1/verify/userSignIn")
    Observable<Result> userSignIn(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId);

    //根据电影Id查询电影信息2
    @GET("movie/v1/findMoviesDetail")
    Observable<Result<IDMoiveDetalisTwo>> IDMoivedetalisTwo(@Header("userId") int userId,
                                                            @Header("sessionId") String sessionId,
                                                            @Query("movieId") int movieId);


    /**
     * 电影关注
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/verify/followMovie")
    Observable<Result> followMovie(@Header("userId") int userId,
                                   @Header("sessionId") String sessionId,
                                   @Query("movieId") int movieId);

    /**
     * 电影取消关注
     *
     * @param userId
     * @param sessionId
     * @param movieId
     * @return
     */
    @GET("movie/v1/verify/cancelFollowMovie")
    Observable<Result> cancelFollowMovie(@Header("userId") int userId,
                                         @Header("sessionId") String sessionId,
                                         @Query("movieId") int movieId);

    /**
     * 查询系统消息列表
     *
     * @param userId
     * @param sessionId
     * @param page
     * @param count
     * @return
     */

    @GET("tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<RemindBean>>> findAllSysMsg(@Header("userId") int userId,
                                                       @Header("sessionId") String sessionId,
                                                       @Query("page") int page,
                                                       @Query("count") int count);

    /**
     * 重置密码
     *
     * @param userId
     * @param sessionId
     * @param oldPwd
     * @param newPwd
     * @param newPwd2
     * @return
     */
    @POST("user/v1/verify/modifyUserPwd")
    @FormUrlEncoded
    Observable<Result> resetPwd(@Header("userId") int userId,
                                @Header("sessionId") String sessionId,
                                @Field("oldPwd") String oldPwd,
                                @Field("newPwd") String newPwd,
                                @Field("newPwd2") String newPwd2);


}
