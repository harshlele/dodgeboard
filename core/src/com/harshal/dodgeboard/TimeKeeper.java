package com.harshal.dodgeboard;

/**
 * Created by harshal on 6/1/16.
 * Stopwatch class for counting seconds
 */
public class TimeKeeper {

    //initial time
    private long initTime;

    //var to store the time on the latest update
    private long latestTime;

    //timer value
    private long timerVal;


    //start storing time
    public long initTimer(){
        initTime=System.currentTimeMillis();
        return initTime;
    }

    //update time
    public long updateTime(){
        latestTime=System.currentTimeMillis();
        return latestTime;

    }

    //get timer value in milliseconds
    public long getTimerValMSec(){
        updateTime();
        timerVal=latestTime-initTime;
        return timerVal;
    }

    //Get timer value as a string in Min:Sec format
    public String getTimerValStr(){
        String val="";
        getTimerValMSec();
        if(timerVal/1000 >= 60){
            long timerValSec=timerVal/1000;
            int timeMin=(int)timerValSec/60;
            int timeSec=(int)timerValSec%60;
            val=String.valueOf(timeMin)+":"+String.valueOf(timeSec);
        }
        else if(timerVal/1000 < 60){
            int timeSec=(int)timerVal/1000;
            val="00:"+String.valueOf(timeSec);
        }
        return val;
    }



}
