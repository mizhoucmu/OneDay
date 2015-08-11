package edu.cmu.ebiz.oneday.bean;

/**
 * Created by julie on 8/9/15.
 */
public class TodoItemBean {
    private String title;
    private long startTimeStamp;
    private long endTimeStamp;
    private long expectedTime; // in seconds
    private int status;
    private int timeleft; // in seconds

    public static final int NOT_STARTED = 0;
    public static final int ING = 1;
    public static final int PAUSED = 2;
    public static final int FINISHED = 3;
    public static final int DELETED = 4;


    /**
     * @param title     : description of this todo item
     * @param expectedTime: expectedTime in minutes
     */
    public TodoItemBean(String title, int expectedTime) {
        this.title = title;
        this.expectedTime = (long) (expectedTime) * 60 * 1000;
        this.timeleft = expectedTime * 60; // in seconds
        this.status = NOT_STARTED;
    }

    public void addTime(int addTime) {
        this.timeleft += addTime;
        this.expectedTime += addTime * 60;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isFinished() {
        return this.status == FINISHED;
    }

    public void finish(boolean isFinished) {
        this.status = FINISHED;
    }

    public boolean isDeleted() {
        return this.status == DELETED;
    }

    public void delete() {
        this.status = DELETED;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }

    public void setStartTimeStamp(long startTimeStamp) {
        this.startTimeStamp = startTimeStamp;
    }

    public long getEndTimeStamp() {
        return endTimeStamp;
    }

    public void setEndTimeStamp(long endTimeStamp) {
        this.endTimeStamp = endTimeStamp;
    }

    public String getExpectedTime() {
        int minutes = (int) (expectedTime / 1000 / 60);
        int hour = minutes / 60;
        int min = minutes % 60;
        StringBuilder res = new StringBuilder();
        if (hour > 0) {
            res.append(hour);
        } else {
            res.append(0);
        }
        res.append(":");
        if (min > 10) {
            res.append(min);
        } else if (min > 0) {
            res.append("0");
            res.append(min);
        } else {
            res.append("00");
        }
        return res.toString();
    }

    public void setExpectedTime(int minutes) {
        this.expectedTime = (long) (minutes * 60 * 1000);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void start() {
        this.status = ING;
        this.startTimeStamp = System.nanoTime();
    }

    /**
     * get time left in seconds
     * @return time left in seconds
     */
    public int getTimeleft() {
        return this.timeleft;
    }

    public String getTimeleftString() {
        return secondsToString(this.getTimeleft());
    }

    public void setTimeLeft(int timeleft) {
        this.timeleft = timeleft;
    }

    public boolean countDown() {
        this.timeleft--;
        if (this.timeleft > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    private String secondsToString(int seconds) {
        int sign = 1;
        if (seconds < 0) {
            sign = -1;
            seconds = 0 - seconds;
        }
        int sec = seconds % 60;
        int min = seconds / 60;
        int hour = min / 60;
        min = min % 60;
        StringBuilder result = new StringBuilder();
        if (sign == -1) {
            result.append("-");
        }
        if (hour >= 10) {
            result.append(hour);
        }
        else {
            result.append("0");
            result.append(hour);
        }
        result.append(":");

        if (min >= 10) {
            result.append(min);
        }
        else {
            result.append("0");
            result.append(min);
        }
        result.append(":");

        if (sec >= 10) {
            result.append(sec);
        }
        else {
            result.append("0");
            result.append(sec);
        }
        return result.toString();
    }
}
