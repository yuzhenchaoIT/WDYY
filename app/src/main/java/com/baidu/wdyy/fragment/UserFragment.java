package com.baidu.wdyy.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.wdyy.HomeActivity;
import com.baidu.wdyy.MyAttentionActivity;
import com.baidu.wdyy.MyFeedBackActivity;
import com.baidu.wdyy.MyInfoActivity;
import com.baidu.wdyy.bean.Result;
import com.baidu.wdyy.bean.UserInfo;
import com.baidu.wdyy.bean.UserInfoBean;
import com.baidu.wdyy.core.ApiException;
import com.baidu.wdyy.core.app.WDYYApp;
import com.baidu.wdyy.core.db.DBDao;
import com.baidu.wdyy.http.DataCall;
import com.baidu.wdyy.presenter.UploadPicPresenter;
import com.baidu.wdyy.presenter.my.SignInPresenter;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;

public class UserFragment extends Fragment {
    @BindView(R.id.simp_mine_head)
    SimpleDraweeView mSimpMineHead;
    @BindView(R.id.text_nick_name)
    TextView mTextNickName;
    @BindView(R.id.img_myinfo)
    ImageView mImgMyinfo;
    @BindView(R.id.my_attention)
    ImageView mMyAttention;
    @BindView(R.id.my_buyrecord)
    ImageView mMyBuyrecord;
    @BindView(R.id.my_feed_back)
    ImageView mMyFeedBack;
    @BindView(R.id.my_log_out)
    ImageView mMyLogOut;
    @BindView(R.id.my_sign)
    Button mMySign;
    private View view;
    private Unbinder unbinder;
    private Bitmap head;// 头像Bitmap
    private static String path = "/sdcard/myHead/";// sd路径
    private UploadPicPresenter uploadPicPresenter = new UploadPicPresenter(new UploadDataCall());
    private SignInPresenter signInPresenter = new SignInPresenter(new SignDataCall());
    private Dao<UserInfo, String> userDao;
    private List<UserInfo> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);
        // 从SD卡中找头像，转换成Bitmap
        Bitmap bt = BitmapFactory.decodeFile(path + "head.jpg");
        if (bt != null) {
            @SuppressWarnings("deprecation")
            Drawable drawable = new BitmapDrawable(bt);// 转换成drawable
            mSimpMineHead.setImageDrawable(drawable);
        } else {
            /**
             * 如果SD里面没有则需要从服务器取头像，取回来的头像再保存在SD中
             *
             */
        }


        //动态权限申请
        String[] p = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(getActivity(), p, 1);
        return view;
    }


    /**
     * 动态权限请求码
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0) {
            int result = grantResults[0];
            if (result == PackageManager.PERMISSION_GRANTED) {
                //权限同意
                showTypeDialog();
            } else {
                //权限拒绝
                Toast.makeText(getActivity(), "请同意权限", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            userDao = DBDao.getInstance(getActivity()).getUserDao();
            list = userDao.queryForAll();
            userDao.notifyChanges();
            if (list.size() == 0) {
                //无操作
                mSimpMineHead.setImageResource(R.drawable.head);
                mTextNickName.setText("请登录");
            } else {
                UserInfo userInfo = list.get(0);
                UserInfoBean userInfo1 = userInfo.getUserInfo();
                String pic = userInfo1.getHeadPic();
                mSimpMineHead.setImageURI(Uri.parse(pic));
                String nickName = userInfo1.getNickName();
                mTextNickName.setText(nickName);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        }


    }

    @OnClick({R.id.simp_mine_head, R.id.img_myinfo, R.id.my_attention, R.id.my_buyrecord, R.id.my_feed_back, R.id.my_log_out, R.id.my_sign})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_sign:
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), HomeActivity.class));
                } else {
                    UserInfo userInfo = list.get(0);
                    signInPresenter.request(userInfo.getUserId(), userInfo.getSessionId());
                }

                break;
            case R.id.simp_mine_head:
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), HomeActivity.class));
                } else {
                    showTypeDialog();
                }
                break;
            case R.id.img_myinfo:
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), HomeActivity.class));
                } else {
                    startActivity(new Intent(getContext(), MyInfoActivity.class));
                }
                break;
            case R.id.my_attention:
                if (list.size() == 0) {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), HomeActivity.class));
                } else {
                    startActivity(new Intent(getContext(), MyAttentionActivity.class));
                }
                break;
            case R.id.my_buyrecord:
                break;
            case R.id.my_feed_back:
                startActivity(new Intent(getContext(), MyFeedBackActivity.class));
                break;
            case R.id.my_log_out:
                try {
                    //删除用户
                    userDao.delete(list);
                    WDYYApp.getShare().edit().remove("userId").remove("sessionId").commit();
                    list.clear();
                    mSimpMineHead.setImageResource(R.drawable.head);
                    mTextNickName.setText("请登录");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;

        }
    }


    /**
     * 头像更换
     */
    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getActivity(), R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = view.findViewById(R.id.tv_select_camera);
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {// 在相册中选取
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent2.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent2, 2);
                dialog.dismiss();
            }
        });
        tv_select_camera.setOnClickListener(new View.OnClickListener() {// 调用照相机
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent3.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "head.jpg")));
                startActivityForResult(intent3, 3);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 2:
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case 3:
                if (resultCode == RESULT_OK) {
                    File temp = new File(Environment.getExternalStorageDirectory() + "/head.jpg");
                    cropPhoto(Uri.fromFile(temp));// 裁剪图片
                }

                break;
            case 4:
                if (data != null) {
                    Bundle extras = data.getExtras();
                    head = extras.getParcelable("data");
                    if (head != null) {
                        /**
                         * 上传服务器代码
                         */
                        setPicToView(head);// 保存在SD卡中
                        mSimpMineHead.setImageBitmap(head);// 用ImageButton显示出来
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setPicToView(Bitmap mBitmap) {
        String sdStatus = Environment.getExternalStorageState();
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
            return;
        }
        FileOutputStream b = null;
        File file = new File(path);
        file.mkdirs();// 创建文件夹
        String fileName = path + "head.jpg";// 图片名字
        try {
            b = new FileOutputStream(fileName);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // 关闭流
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 调用系统的裁剪功能
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 4);
    }


    class UploadDataCall implements DataCall<Result<UserInfo>> {

        @Override
        public void success(Result<UserInfo> data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getStatus() + "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                UserInfo userInfo = data.getResult();
                int userId = userInfo.getUserId();
                String sessionId = userInfo.getSessionId();
                uploadPicPresenter.request(userId, sessionId, head);
            } else {
                Toast.makeText(getContext(), data.getStatus() + "" + data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getContext(), "异常" + e, Toast.LENGTH_SHORT).show();
        }
    }


    class SignDataCall implements DataCall<Result> {

        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void fail(ApiException e) {
            Toast.makeText(getContext(), "签到请求有误" + e, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        uploadPicPresenter.unBind();
    }
}
