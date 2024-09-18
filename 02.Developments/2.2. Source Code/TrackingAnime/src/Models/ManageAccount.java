package Models;

import java.util.Date;

public class ManageAccount {
    private int accountid,animeid,status,lastwatched;
    private Date createdday;
    private String lastupdated;

    public ManageAccount(int accountid, int animeid, int status, int lastwatched, Date createdday, String lastupdated) {
        this.accountid = accountid;
        this.animeid = animeid;
        this.status = status;
        this.lastwatched = lastwatched;
        this.createdday = createdday;
        this.lastupdated = lastupdated;
    }

    public int getAccountid() {
        return accountid;
    }

    public int getAnimeid() {
        return animeid;
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

    public String getLastupdated() {
        return lastupdated;
    }
}
