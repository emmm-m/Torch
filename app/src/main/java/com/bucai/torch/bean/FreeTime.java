package com.bucai.torch.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zia on 2018/6/12.
 */
public class FreeTime implements Serializable {

    private List<Time> monday;
    private List<Time> tuesday;
    private List<Time> wednesday;
    private List<Time> thursday;
    private List<Time> friday;
    private List<Time> saturday;
    private List<Time> sunday;

    @Override
    public String toString() {
        return "FreeTime{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                '}';
    }

    public List<Time> getMonday() {
        return monday;
    }

    public void setMonday(List<Time> monday) {
        this.monday = monday;
    }

    public List<Time> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<Time> tuesday) {
        this.tuesday = tuesday;
    }

    public List<Time> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<Time> wednesday) {
        this.wednesday = wednesday;
    }

    public List<Time> getThursday() {
        return thursday;
    }

    public void setThursday(List<Time> thursday) {
        this.thursday = thursday;
    }

    public List<Time> getFriday() {
        return friday;
    }

    public void setFriday(List<Time> friday) {
        this.friday = friday;
    }

    public List<Time> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<Time> saturday) {
        this.saturday = saturday;
    }

    public List<Time> getSunday() {
        return sunday;
    }

    public void setSunday(List<Time> sunday) {
        this.sunday = sunday;
    }

    public static class Time implements Serializable {
        private String start;
        private String end;
        private boolean empty;

        public boolean isEmpty() {
            return empty;
        }

        public void setEmpty(boolean empty) {
            this.empty = empty;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        @Override
        public String toString() {
            return "Time{" +
                    "start='" + start + '\'' +
                    ", end='" + end + '\'' +
                    '}';
        }
    }
}
