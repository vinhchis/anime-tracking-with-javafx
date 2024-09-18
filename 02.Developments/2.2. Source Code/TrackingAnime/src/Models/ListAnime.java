package Models;

import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;

import java.util.Date;

public class ListAnime {
    private int lastwatched,status;
    private Date createdday;
    public Image poster;
    public Hyperlink title;
    public ListAnime(Image poster, Hyperlink title, int status, int lastwatched, Date createdday) {
        this.poster = poster;
        this.title = title;
        this.status = status;
        this.lastwatched = lastwatched;
        this.createdday = createdday;
    }

    public Image getPoster() {
        return poster;
    }
    public Hyperlink getTitle() {
        return title;
    }
    public int getStatus() {
        return status;
    }

    public int getLastwatched() {
        return lastwatched;
    }

    public Date getCreatedday() {
        return createdday;
    }
}

