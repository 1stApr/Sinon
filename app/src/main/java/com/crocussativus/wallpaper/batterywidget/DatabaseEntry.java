package com.crocussativus.wallpaper.batterywidget;

import java.util.Date;

public class DatabaseEntry {

    private long time;
    private int level;

    public DatabaseEntry(int level) {
        this.time = new Date().getTime();
        this.level = level;
    }

    public DatabaseEntry(long time, int level) {
        this.time = time;
        this.level = level;
    }

    public long getTime() {
        return time;
    }

    public int getLevel() {
        return level;
    }
}
