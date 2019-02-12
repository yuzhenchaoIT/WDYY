package com.baidu.wdyy.presenter.my;

import com.baidu.wdyy.bean.RemindBean;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;
import com.baidu.wdyy.presenter.BasePresenter;

import java.util.List;

import io.reactivex.Observable;

public class MyRemindPresenter extends BasePresenter {

    public MyRemindPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        Observable<Result<List<RemindBean>>> allSysMsg = requestInterFace.findAllSysMsg((int) args[0], (String) args[1], (int) args[2], (int) args[3]);
        return allSysMsg;
    }
}
