package instagram.service;

import java.util.Date;

public class CurrentTimeBeforeGetter {
    public static void main(String[] args) {

    }
    public static long getDate(){
        Date date = new Date();
        return date.getTime()- 20 * 24 * 60 * 60 * 1000;
    }
}
