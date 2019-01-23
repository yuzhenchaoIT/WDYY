package com.baidu.wdyy.presenter;




import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.core.CustomException;
import com.baidu.wdyy.core.ResponseTransformer;
import com.baidu.wdyy.http.DataCall;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresenter {


    private DataCall dataCall;

    private boolean running;

    public BasePresenter(DataCall dataCall) {
        this.dataCall = dataCall;
    }


    protected abstract Observable observable(Object... args);


    public void request(Object... args) {
        if (running) {
            return;
        }
        running = true;
        observable(args)
                .compose(ResponseTransformer.handleResult())
                .compose(new ObservableTransformer() {
                    @Override
                    public ObservableSource apply(Observable upstream) {
                        return upstream.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread());
                    }
                })
                .subscribe(new Consumer<Result>() {
                    @Override
                    public void accept(Result result) throws Exception {
                        running = false;
                        dataCall.success(result);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        running = false;
                        //处理异常
                        dataCall.fail(CustomException.handleException(throwable));
                    }
                });

    }


    //暴露一个运行的方法
    public boolean isRunning() {
        return running;
    }


    public void unBind() {
        dataCall = null;
    }


}
