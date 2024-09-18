package Models;

import javafx.scene.image.Image;

import java.util.Map;

public class Notification {

    private String title;
    private Image poster;
    private int status,tl_id;
//    private LocalDateTime time;
    private String time;
    private Boolean ischecked;
    public Notification(int tl_id, String title, Image poster, int status, String time) {
        this.tl_id = tl_id;
        this.title = title;
        this.poster = poster;
        this.status = status;
        this.time = time;
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getTime() {
        return time;
    }


    public int getTl_id() {
        return tl_id;
    }

    public void setTl_id(int tl_id) {
        this.tl_id = tl_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Image getPoster() {
        return poster;
    }

    public void setPoster(Image poster) {
        this.poster = poster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatusString(Map<Integer, String> statusMap){
        return statusMap.get(status);
    }

    public String getDate(){
        return time.split(" ")[0];
    }

    public String getTimes() {
        return time.split(" ")[1];
    }
}
