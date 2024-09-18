package Models;

import javafx.scene.image.Image;

import java.sql.Time;

public class Schedule {
    private String title;
    private Image poster;
    private int status,day,schedule_id;
    private Time time;
    public Schedule(int schedule_id, String title, Image poster, int status, int day, Time time) {
        this.schedule_id = schedule_id;
        this.title = title;
        this.poster = poster;
        this.status = status;
        this.day = day;
        this.time = time;
    }

    public int getSchedule_id() {
        return schedule_id;
    }

    public String getTitle() {
        return title;
    }

    public Image getPoster() {
        return poster;
    }

    public int getStatus() {
        return status;
    }

    public int getDay() {
        return day;
    }

    public Time getTime() {
        return time;
    }
}
