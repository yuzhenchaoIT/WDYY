package com.baidu.wdyy.presenter;

import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import io.reactivex.Observable;

public class MovieCommentPresenter extends BasePresenter {
    public MovieCommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        return requestInterFace.movieComment((int) args[0], (String) args[1], (int) args[2], (String) args[3]);
    }
}
