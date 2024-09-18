package Models;

import javafx.scene.image.Image;

public class Information {
    String title,date;
    int animeid,episodes,newepisode,status;
    float score;
    Image poster;

    public Information(int animeid, String title, Image poster, int episodes, int newepisode, int status, float score,String date) {
        this.animeid = animeid;
        this.title = title;
        this.poster = poster;
        this.episodes = episodes;
        this.newepisode = newepisode;
        this.status = status;
        this.score = score;
        this.date = date;

    }

    public int getAnimeid() {
        return animeid;
    }
    public String getTitle(){
        return title;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getNewepisode() {
        return newepisode;
    }

    public int getStatus() {
        return status;
    }
    public Image getPoster() {
        return poster;
    }

    public float getScore() {
        return score;
    }
    public String getDate() {
        return date;
    }
}
