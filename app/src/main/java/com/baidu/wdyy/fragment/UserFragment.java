package com.baidu.wdyy.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.wdyy.MyInfoActivity;
import com.baidu.wdyy.bean.UserInfoBean;
import com.baidu.wdyy.core.db.DBDao;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UserFragment extends Fragment {
    @BindView(R.id.simp_mine_head)
    SimpleDraweeView mSimpMineHead;
    @BindView(R.id.text_nick_name)
    TextView mTextNickName;
    @BindView(R.id.img_myinfo)
    ImageView mImgMyinfo;
    private View view;
    private Unbinder unbinder;
    private DBDao dbDao;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化dao层
        try {
            dbDao = new DBDao(getContext());
            //查询用户
            List<UserInfoBean> userInfoBeans = dbDao.getUser();
            UserInfoBean userInfoBean = userInfoBeans.get(0);
            mSimpMineHead.setImageURI(Uri.parse(userInfoBean.getHeadPic()));
            mTextNickName.setText(userInfoBean.getNickName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.simp_mine_head, R.id.img_myinfo})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.simp_mine_head:
                break;
            case R.id.img_myinfo:
                startActivity(new Intent(getContext(), MyInfoActivity.class));
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
