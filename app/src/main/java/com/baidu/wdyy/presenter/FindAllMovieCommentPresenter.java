package com.baidu.wdyy.presenter;




import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.http.NetWorkManager;
import com.baidu.wdyy.http.RequestInterFace;

import io.reactivex.Observable;


public class FindAllMovieCommentPresenter extends BasePresenter {
    private int page=1;
    public FindAllMovieCommentPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        RequestInterFace iRequest = NetWorkManager.getInstance().create(RequestInterFace.class);
        boolean flag= (boolean) args[3];
        if (flag){
            page++;
        }else {
            page=1;
        }
        return iRequest.findAllMovieComment((int) args[0],(String) args[1],(int) args[2],page,(int) args[4]);
    }


}

