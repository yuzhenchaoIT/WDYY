package com.baidu.wdyy.presenter.my;

import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;
import com.baidu.wdyy.presenter.BasePresenter;

import io.reactivex.Observable;

public class ResetPwdPresenter extends BasePresenter {

    public ResetPwdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        Observable<Result> resetPwd = requestInterFace.resetPwd((int) args[0], (String) args[1], (String) args[2], (String) args[3], (String) args[4]);
        return resetPwd;
    }
}
