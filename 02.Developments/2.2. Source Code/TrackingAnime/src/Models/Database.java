package Models;

import javafx.scene.image.Image;

import java.sql.Time;
import java.util.Date;

public class Database {
    private String title,introduction;
    private Image poster;
    private int status,episodes,new_episode,type,season,nation,animeid,studio_id;
    private Date aried;
    public Database(int animeid,Image poster, String title, int season, int type, int status, int nation, Date aried, int episodes, int new_episode,
                    int studio_id, String introduction){
        this.animeid = animeid;
        this.poster=poster;
        this.title=title;
        this.season=season;
        this.type=type;
        this.status=status;
        this.nation=nation;
        this.aried=aried;
        this.episodes=episodes;
        this.new_episode=new_episode;
        this.studio_id = studio_id;
        this.introduction=introduction;
    }

    public int getAnimeid() {
        return animeid;
    }

    public Image getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }


    public int getStudio_id() {
        return studio_id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public int getStatus() {
        return status;
    }

    public int getEpisodes() {
        return episodes;
    }

    public int getNew_episode() {
        return new_episode;
    }

    public int getType() {
        return type;
    }

    public int getSeason() {
        return season;
    }

    public int getNation() {
        return nation;
    }

    public Date getAried() {
        return aried;
    }
}
