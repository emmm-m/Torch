package com.bucai.torch.util.leancloud;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FollowCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.bucai.torch.view.main.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by zxzhu on 2017/8/16.
 */

public class UserModel implements IUserModel {

    public interface UserListener {
        void onSuccess();

        void onError(AVException e);
    }

    @Override
    public void login(String username, String password, final UserListener listener) {
        AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if (e == null) {
                    listener.onSuccess();
                } else {
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void signUp(Context context, final String username, String password, String phoneNumber, Bitmap icon, final UserModel.UserListener listener) {
        AVUser user = new AVUser();
        user.setUsername(username);
        user.setPassword(password);
        user.setMobilePhoneNumber(phoneNumber);
        user.put("nickname", username);
        String iconPath = saveBitmap(icon, username);
        Log.d("zzzx", "signUp: " + iconPath);
        AVFile avFile = null;
        try {
            avFile = AVFile.withAbsoluteLocalPath(username, iconPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (avFile != null) user.put("head", avFile);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    listener.onSuccess();
                } else {
                    listener.onError(e);
                }
            }
        });
        user.saveInBackground();
    }

    @Override
    public void getCode(String phone, final UserListener listener) {
        AVOSCloud.requestSMSCodeInBackground(phone, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) listener.onSuccess();
                else listener.onError(e);
            }
        });
    }

    @Override
    public void signUpWithPhone(String phone, String code, final String password, final UserListener listener) {
        AVUser.signUpOrLoginByMobilePhoneInBackground(phone, code, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {

                if (e == null) {
                    listener.onSuccess();
                    avUser.setPassword(password);
                    avUser.saveInBackground();
                } else {
                    Log.d("zzxsign", "done: "+ e.toString());
                    listener.onError(e);
                }
            }
        });
    }


    @Override
    public void setIcon(String icon) {

    }

    @Override
    public void setInfo(String nickname, int age, Bitmap header, final UserListener listener) {
        AVUser user = AVUser.getCurrentUser();
        user.put("nickname", nickname);
        user.put("age", age);
        String iconPath = saveBitmap(header, MainActivity.Companion.getUSER());
        Log.d("zzzx", "signUp: " + iconPath);
        AVFile avFile = null;
        try {
            avFile = AVFile.withAbsoluteLocalPath(MainActivity.Companion.getUSER(), iconPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (avFile != null) user.put("head", avFile);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    listener.onSuccess();
                } else {
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void setReadme(String readme) {

    }

    @Override
    public Bitmap getIcon(String username) {
        return null;
    }

    @Override
    public String getPhoneNumber(String username) {
        return null;
    }

    @Override
    public void changePassword(String username, String password_pre, String password_new) {

    }

    @Override
    public String saveBitmap(Bitmap bitmap, String name) {
        FileOutputStream foutput = null;
        String imagePath = null;
        try {
            File appDir = new File(Environment.getExternalStorageDirectory(), "Torch");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            File headIcons = new File(appDir, "head");
            if (!headIcons.exists()) {
                headIcons.mkdir();
            }
//            if (file.exists()){
//                file.delete();
//            }
            File file = new File(headIcons, name + ".jpg");
            foutput = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, foutput);
            imagePath = file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return imagePath;
    }

    @Override
    public void followUser(String id, final UserListener listener) {
        AVUser.getCurrentUser().followInBackground(id, new FollowCallback() {
            @Override
            public void done(AVObject object, AVException e) {
                if (listener != null) {
                    if (e == null) {
                        listener.onSuccess();
                        Log.i("+++", "follow succeeded.");
                    } else {
                        listener.onError(e);
                    }
                }
            }
        });
    }

    @Override
    public void unFollowUser(String id, final UserListener listener) {
        AVUser.getCurrentUser().unfollowInBackground(id, new FollowCallback() {
            @Override
            public void done(AVObject object, AVException e) {
                if (listener != null) {
                    if (e == null) {
                        listener.onSuccess();
                        Log.i("+++", "unFollow succeeded.");
                    } else {
                        listener.onError(e);
                    }
                }
            }
        });

    }


}
