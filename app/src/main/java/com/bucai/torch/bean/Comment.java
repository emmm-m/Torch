package com.bucai.torch.bean;

import java.io.Serializable;

/**
 * Created by zia on 2018/6/21.
 */
public class Comment implements Serializable{
    private int effectRatio;//效果满意度
    private int serveRatio;//服务满意度
    private String userObjectId;//评论人的id
    private String comment;//评价
    private int good;//评论点赞数，可能会用到吧
    private int totalComment;//评论总数
    private String time;//评论时间

    @Override
    public String toString() {
        return "Comment{" +
                "effectRatio=" + effectRatio +
                ", serveRatio=" + serveRatio +
                ", userObjectId='" + userObjectId + '\'' +
                ", comment='" + comment + '\'' +
                ", good=" + good +
                ", totalComment=" + totalComment +
                ", time='" + time + '\'' +
                '}';
    }

    public int getTotalComment() {
        return totalComment;
    }

    public void setTotalComment(int totalComment) {
        this.totalComment = totalComment;
    }

    public int getEffectRatio() {
        return effectRatio;
    }

    public void setEffectRatio(int effectRatio) {
        this.effectRatio = effectRatio;
    }

    public int getServeRatio() {
        return serveRatio;
    }

    public void setServeRatio(int serveRatio) {
        this.serveRatio = serveRatio;
    }

    public String getUserObjectId() {
        return userObjectId;
    }

    public void setUserObjectId(String userObjectId) {
        this.userObjectId = userObjectId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
