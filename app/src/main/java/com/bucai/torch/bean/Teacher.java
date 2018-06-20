package com.bucai.torch.bean;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zia on 2018/6/12.
 */
public class Teacher implements Serializable {
    private ArrayList<String> description;
    private String sex = "";
    private int age;
    private String phone = "";
    private String name = "";
    private int star;
    private int year;
    private int certification;
    private String price = "";
    private String simpleIntroduce;
    private String completeIntroduce;
    private FreeTime freeTime;
    private String head = "";
    private String objectId = "";

    @Override
    public String toString() {
        return "Teacher{" +
                "description=" + description +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", name='" + name + '\'' +
                ", star=" + star +
                ", year=" + year +
                ", certification=" + certification +
                ", price='" + price + '\'' +
                ", simpleIntroduce='" + simpleIntroduce + '\'' +
                ", completeIntroduce='" + completeIntroduce + '\'' +
                ", freeTime=" + freeTime +
                ", head='" + head + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }

    public String getSimpleIntroduce() {
        return simpleIntroduce;
    }

    public void setSimpleIntroduce(String simpleIntroduce) {
        this.simpleIntroduce = simpleIntroduce;
    }

    public String getCompleteIntroduce() {
        return completeIntroduce;
    }

    public void setCompleteIntroduce(String completeIntroduce) {
        this.completeIntroduce = completeIntroduce;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public ArrayList<String> getDescription() {
        return description;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setDescription(ArrayList<String> description) {
        this.description = description;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCertification() {
        return certification;
    }

    public void setCertification(int certification) {
        this.certification = certification;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public FreeTime getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(FreeTime freeTime) {
        this.freeTime = freeTime;
    }

    public interface OnTeacherGet {
        void done(@Nullable Teacher teacher, @Nullable AVException e);
    }
}
