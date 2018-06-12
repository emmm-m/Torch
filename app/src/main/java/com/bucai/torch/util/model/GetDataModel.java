package com.bucai.torch.util.model;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetCallback;
import com.bucai.torch.bean.FreeTime;
import com.bucai.torch.bean.Teacher;
import com.bucai.torch.util.ThreadPool;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zxzhu on 2017/8/20.
 */

public class GetDataModel implements IGetDataModel {
    public interface GetDataListener<T> {
        void onStart();

        void onError(AVException e);

        void onFinish(List<T> list);
    }

//    @Override
//    public void getTeachersList(final GetDataListener<AVObject> listener) {
//        listener.onStart();
//        AVQuery<AVObject> query = new AVQuery<>("Teachers");
//        query.whereContains("name", "");
//        query.findInBackground(new FindCallback<AVObject>() {
//            @Override
//            public void done(List<AVObject> list, AVException e) {
//                if (e == null) {
//                    listener.onFinish(list);
//                } else {
//                    Log.d("]]]", "done: " + e);
//                    listener.onError(e);
//                }
//            }
//        });
//    }

    @Override
    public void getTeachersList(String content, final GetDataListener<AVObject> listener) {
        listener.onStart();
        AVQuery<AVObject> query1 = new AVQuery<>("Teachers");
        query1.whereContains("name", content);
        AVQuery<AVObject> query2 = new AVQuery<>("Teachers");
        query2.whereContains("subject", content);
        AVQuery<AVObject> query3 = new AVQuery<>("Teachers");
        query3.whereContains("grade", content);
        AVQuery<AVObject> query = AVQuery.or(Arrays.asList(query1, query2, query3));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    listener.onFinish(list);
                } else {
                    Log.d("]]]", "done: " + e);
                    listener.onError(e);
                }
            }
        });
    }


    @Override
    public void gerUserData(String username, final GetDataListener<AVUser> listener) {
        Log.d("----", "error！！！！: ");
        listener.onStart();
        AVQuery<AVUser> query = new AVQuery<>("_User");
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (e == null) {
                    listener.onFinish(list);
                } else {
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void getUserById(String id, final GetObjectListener listener) {
        AVQuery<AVObject> query = new AVQuery<>("_User");
        query.getInBackground(id, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null) {
                    listener.done(object);
                } else {
                    Log.d("'''", "done: " + e.getMessage());
                }
            }
        });
    }

    public interface GetObjectListener {
        void done(AVObject obj);
    }

    @Override
    public void getObjectById(String id, final GetObjectListener listener) {
        AVQuery<AVObject> query = new AVQuery<>("Square");
        query.getInBackground(id, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject object, AVException e) {
                if (e == null) {
                    listener.done(object);
                } else {
                    Log.d("'''", "done: " + e.getMessage());
                }
            }
        });
    }

    public interface GetCommentsListener {
        void finish(List<String> list, HashMap<String, Object> map);
    }

    @Override
    public void getComments(AVObject object, GetCommentsListener listener) {
        List<String> list = (List<String>) object.get("comment_tx");
        HashMap<String, Object> map = (HashMap<String, Object>) object.get("comment_audio");
        listener.finish(list, map);
    }

    @Override
    public void getFollowers(String id, final GetDataListener<AVUser> listener) {
        listener.onStart();
        AVQuery<AVUser> followerQuery = AVUser.followerQuery(id, AVUser.class);
        followerQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> avObjects, AVException e) {
                if (e == null) {
                    listener.onFinish(avObjects);
                } else {
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void getFollowees(String id, final GetDataListener<AVUser> listener) {
        listener.onStart();
        AVQuery<AVUser> followerQuery = AVUser.followeeQuery(id, AVUser.class);
        followerQuery.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> avObjects, AVException e) {
                if (e == null) {
                    listener.onFinish(avObjects);
                } else {
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void getRecently(String username, final GetDataListener<AVObject> listener) {
        listener.onStart();
        AVQuery<AVObject> query = new AVQuery<>("Square");
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    Log.d("]]]", "done: " + list.size());
                    listener.onFinish(list);
                } else {
                    Log.d("]]]", "done: " + e);
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void getRollPics(final GetDataListener<AVObject> listener) {
        listener.onStart();
        AVQuery<AVObject> query = new AVQuery<>("InfoCzjy");
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    listener.onFinish(list);
                } else {
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void getData(String dataType, final GetDataListener<AVObject> listener) {
        listener.onStart();
        AVQuery<AVObject> query = new AVQuery<>(dataType);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    listener.onFinish(list);
                } else {
                    listener.onError(e);
                }
            }
        });
    }

    @Override
    public void getTeachersList(final GetDataListener<Teacher> getDataListener) {
        getDataListener.onStart();
        AVQuery<AVObject> query = new AVQuery<>("Teacher");
        query.setOrder("star");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> list, AVException e) {
                if (e != null) {
                    getDataListener.onError(e);
                } else {
                    ThreadPool.instance.getSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            List<Teacher> teachers = new ArrayList<>();
                            for (AVObject avObject : list) {
                                try {
                                    teachers.add(convert(avObject));
                                } catch (AVException e1) {
                                    e1.printStackTrace();
                                    getDataListener.onError(e1);
                                    return;
                                } catch (FileNotFoundException e2) {
                                    e2.printStackTrace();
                                }
                            }
                            Log.d("GetDataModel", teachers.toString());
                            getDataListener.onFinish(teachers);
                        }
                    });

                }
            }
        });
    }

    @Override
    public Teacher getTeacher(String objectId) throws AVException, FileNotFoundException {
        AVQuery avQuery = new AVQuery("Teacher");
        return convert(avQuery.get(objectId));
    }

    private Teacher convert(AVObject avObject) throws AVException, FileNotFoundException {
        Teacher teacher = new Teacher();
        teacher.setAge((int) avObject.get("age"));
        teacher.setCertification((int) avObject.get("certification"));
        teacher.setDescription((ArrayList<String>) avObject.get("description"));
        teacher.setFreeTime((ArrayList<FreeTime>) avObject.get("freeTime"));
        teacher.setName((String) avObject.get("name"));
        teacher.setPhone((String) avObject.get("phone"));
        teacher.setPrice((String) avObject.get("price"));
        teacher.setSex((String) avObject.get("sex"));
        teacher.setStar((int) avObject.get("star"));
        teacher.setYear((int) avObject.get("year"));
        teacher.setObjectId(avObject.getObjectId());
        AVFile head = avObject.getAVFile("header");
        if (head != null) {
            teacher.setHead(AVFile.withObjectId(head.getObjectId()).getUrl());
        }
        return teacher;
    }

}
