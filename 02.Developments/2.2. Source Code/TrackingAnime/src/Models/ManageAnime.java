package Models;

import javafx.scene.image.Image;

import java.util.Date;

public class ManageAnime {
    private int episode;
    private String status,title,synopsis,type,season,nation;
    private Image poster;
    private Date aried;

    public ManageAnime(String status, int episode, Image poster, String title, String type, String season, Date aried,String synopsis
    , String nation) {
        this.status = status;
        this.episode = episode;
        this.poster = poster;
        this.title = title;
        this.type = type;
        this.season = season;
        this.aried = aried;
        this.synopsis = synopsis;
        this.nation = nation;
    }

    public String getStatus() {
        return status;
    }
    public int getEpisode() {
        return episode;
    }
    public Image getPoster() {
        return poster;
    }
    public String getTitle() {
        return title;
    }
    public String getType() {
        return type;
    }
    public String getSeason() {
        return season;
    }
    public Date getAried() {
        return aried;
    }
    public String getSynopsis() {
        return synopsis;
    }

    public String getNation() {
        return nation;
    }

}
