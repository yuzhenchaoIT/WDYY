package com.baidu.wdyy.presenter;



import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfoOut;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import io.reactivex.Observable;

public class LoginPresenter extends BasePresenter {
    public LoginPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace requestInterFace = NetWorkManager.getInstance().create(RequestInterFace.class);
        Observable<Result<UserInfoOut>> login = requestInterFace.login((String) args[0], (String) args[1]);
        return login;
    }
}
