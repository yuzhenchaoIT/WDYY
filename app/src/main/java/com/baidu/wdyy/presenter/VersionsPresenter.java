package com.baidu.wdyy.presenter;

import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import io.reactivex.Observable;


public class VersionsPresenter extends BasePresenter{
    public VersionsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace iRequest = NetWorkManager.getInstance().create(RequestInterFace.class);
        return iRequest.versions((int)args[0],(String)args[1],(String)args[2]);
    }
}
