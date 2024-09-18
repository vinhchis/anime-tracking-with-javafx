package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SharedData {
    private static SharedData instance;
    private int animeid;
    private ObservableList<Information> lastUpdatedInfo = FXCollections.observableArrayList();

    private SharedData() {}

    public static SharedData getInstance() {
        if (instance == null) {
            instance = new SharedData();
        }
        return instance;
    }

    public int getAnimeId() {
        return animeid;
    }

    public void setAnimeId(int animeid) {
        this.animeid = animeid;
    }

    public ObservableList<Information> getLastUpdatedInfo() {
        return lastUpdatedInfo;
    }

    public void addLastUpdatedInfo(Information info){
        lastUpdatedInfo.add(info);
    }
}
