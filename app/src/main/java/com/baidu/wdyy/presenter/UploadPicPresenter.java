package com.baidu.wdyy.presenter;

import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfo;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import java.io.File;

import io.reactivex.Observable;

public class UploadPicPresenter extends BasePresenter {
    public UploadPicPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        Observable<Result<UserInfo>> uploadHeadPic = requestInterFace.uploadHeadPic((int) args[0], (String) args[1], (File) args[2]);
        return uploadHeadPic;
    }
}
