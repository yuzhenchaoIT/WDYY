package com.baidu.wdyy.presenter;



import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import io.reactivex.Observable;

public class RegPresenter extends BasePresenter {


    public RegPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        return requestInterFace.register((String) args[0], (String) args[1], (String) args[2],
                (String) args[3], (int) args[4], (String) args[5], (String) args[6],
                (String) args[7], (String) args[8], (String) args[9], (String) args[10]);
    }
}
