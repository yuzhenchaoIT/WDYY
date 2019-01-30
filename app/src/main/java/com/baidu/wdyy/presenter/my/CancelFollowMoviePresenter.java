package com.baidu.wdyy.presenter.my;

import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;
import com.baidu.wdyy.presenter.BasePresenter;

import io.reactivex.Observable;

public class CancelFollowMoviePresenter extends BasePresenter {


    public CancelFollowMoviePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        return requestInterFace.cancelFollowMovie((int) args[0], (String) args[1], (int) args[2]);
    }
}
