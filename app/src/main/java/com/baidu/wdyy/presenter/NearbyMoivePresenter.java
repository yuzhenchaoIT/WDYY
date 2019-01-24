package com.baidu.wdyy.presenter;

import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;


import io.reactivex.Observable;

public class NearbyMoivePresenter extends BasePresenter{
    public NearbyMoivePresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace iBaseView = NetWorkManager.getInstance().create(RequestInterFace.class);
        return iBaseView.Nearby((int) args[0],(String) args[1],(String) args[2],(String) args[3],(int) args[4],(int) args[5]);
    }
}
