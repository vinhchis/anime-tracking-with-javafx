package Util;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Airing {
    public LocalTime time;
    public DayOfWeek dayOfWeek;

    public Airing(LocalTime of, DayOfWeek dayOfWeek) {
        this.time = of;
        this.dayOfWeek = dayOfWeek;
    }
}
