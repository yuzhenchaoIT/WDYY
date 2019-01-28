package com.bw.movie.wxapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.baidu.wdyy.ShowActivity;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfoBean;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.WxLoginPresenter;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.reactivex.annotations.Nullable;

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private WxLoginPresenter wxLoginPresenter = new WxLoginPresenter(new WxDataCall());
    private IWXAPI mWxApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, "wxb3852e6a6b7d9516", false);
        //如果没回调onResp，八成是这句没有写
        mWxApi.handleIntent(getIntent(), this);

    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {//用户同意
            final String code = ((SendAuth.Resp) resp).code;
            wxLoginPresenter.request(code);
        } else {
            Log.e("xzmWxLogin", "授权登录失败\n\n自动返回");
        }
    }

    class WxDataCall implements DataCall<Result<UserInfoBean>> {

        @Override
        public void success(Result<UserInfoBean> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getBaseContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(WXEntryActivity.this, ShowActivity.class));
                finish();
            } else {
                Toast.makeText(getBaseContext(), data.getMessage() + "", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {

        }
    }


}
