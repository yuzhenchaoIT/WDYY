package com.baidu.wdyy.presenter.my;

import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;
import com.baidu.wdyy.presenter.BasePresenter;

import io.reactivex.Observable;

public class FindMoviePresenter extends BasePresenter {


    public FindMoviePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace iBaseView = NetWorkManager.getInstance().create(RequestInterFace.class);
        return iBaseView.findMovie((int) args[0], (String) args[1], (int) args[2], (int) args[3]);

    }
}
