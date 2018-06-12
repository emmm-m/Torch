package com.bucai.torch.util.model;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by zxzhu on 2017/8/16.
 */

public interface IUserModel {
    void login(String username, String password, UserModel.UserListener listener);
    void signUp(Context context, String username, String password, String phoneNumber, Bitmap icon, UserModel.UserListener listener);
    void getCode(String phone, UserModel.UserListener listener);
    void signUpWithPhone(String phone, String code, String password, UserModel.UserListener listener);
    void setIcon(String icon);
    void setReadme(String readme);
    Bitmap getIcon(String username);
    String getPhoneNumber(String username);
    void changePassword(String username, String password_pre, String password_new);
    String saveBitmap(Bitmap bitmap, String name);
    void followUser(String id, UserModel.UserListener listener);
    void unFollowUser(String id, final UserModel.UserListener listener);
}
