package com.bucai.torch.util.model;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.bucai.torch.bean.Teacher;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by zxzhu on 2017/8/20.
 */

public interface IGetDataModel {
    void getTeachersList(String content, final GetDataModel.GetDataListener<AVObject> listener);
    void gerUserData(String username, GetDataModel.GetDataListener<AVUser> listener);
    void getUserById(String id, final GetDataModel.GetObjectListener listener);
    void getObjectById(String id, final GetDataModel.GetObjectListener listener);
    void getComments(AVObject object, GetDataModel.GetCommentsListener listener);
    void getFollowers(String id, GetDataModel.GetDataListener<AVUser> listener);
    void getFollowees(String id, GetDataModel.GetDataListener<AVUser> listener);
    void getRecently(String username, GetDataModel.GetDataListener<AVObject> listener);
    void getRollPics(GetDataModel.GetDataListener<AVObject> listener);
    void getData(String dataType, final GetDataModel.GetDataListener<AVObject> listener);
    void getTeachersList(GetDataModel.GetDataListener<Teacher> getDataListener);
    Teacher getTeacher(String objectId) throws AVException, FileNotFoundException;
}

