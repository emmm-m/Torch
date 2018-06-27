package com.bucai.torch.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zia on 2018/6/21.
 */
public class Lecturer implements Serializable{
    private String objectId;
    private List<String> description;//教师的教学特点标签
    private List<String> commentGroup;//学生评价的标签
    private List<String> grade;//教学年级
    private int studentCount;//交过的学生数量，单独分一栏来写死真的难看=_=
    private String simpleIntroduce;//1教师简单介绍 example: 教龄8年 | 已授536课
    private List<String> successCase;//成功案例
    private String experience;//教学经历
    private int star;//星级，最高|5星1
    private String teaName;//教师姓名1
    private String completeIntroduce;//完整介绍
    private String simpleComment;//简单评论
    private int goodCommentCount;//好评数
    private double longitude;
    private double latitude;
    private int price;//价格1
    private String head;//头像1
    private String address;//位置
    private List<Comment> completeComment;//完整的评论，序列化字符串

    @Override
    public String toString() {
        return "Lecturer{" +
                "objectId='" + objectId + '\'' +
                ", description=" + description +
                ", commentGroup=" + commentGroup +
                ", grade=" + grade +
                ", studentCount=" + studentCount +
                ", simpleIntroduce='" + simpleIntroduce + '\'' +
                ", successCase=" + successCase +
                ", experience='" + experience + '\'' +
                ", star=" + star +
                ", teaName='" + teaName + '\'' +
                ", completeIntroduce='" + completeIntroduce + '\'' +
                ", simpleComment='" + simpleComment + '\'' +
                ", goodCommentCount=" + goodCommentCount +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", price=" + price +
                ", head='" + head + '\'' +
                ", completeComment=" + completeComment +
                '}';
    }

    public List<String> getGrade() {
        return grade;
    }

    public void setGrade(List<String> grade) {
        this.grade = grade;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getSimpleComment() {
        return simpleComment;
    }

    public void setSimpleComment(String simpleComment) {
        this.simpleComment = simpleComment;
    }

    public List<Comment> getCompleteComment() {
        return completeComment;
    }

    public void setCompleteComment(List<Comment> completeComment) {
        this.completeComment = completeComment;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public String getSimpleIntroduce() {
        return simpleIntroduce;
    }

    public void setSimpleIntroduce(String simpleIntroduce) {
        this.simpleIntroduce = simpleIntroduce;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getCommentGroup() {
        return commentGroup;
    }

    public void setCommentGroup(List<String> commentGroup) {
        this.commentGroup = commentGroup;
    }

    public List<String> getSuccessCase() {
        return successCase;
    }

    public void setSuccessCase(List<String> successCase) {
        this.successCase = successCase;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getCompleteIntroduce() {
        return completeIntroduce;
    }

    public void setCompleteIntroduce(String completeIntroduce) {
        this.completeIntroduce = completeIntroduce;
    }

    public int getGoodCommentCount() {
        return goodCommentCount;
    }

    public void setGoodCommentCount(int goodCommentCount) {
        this.goodCommentCount = goodCommentCount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
