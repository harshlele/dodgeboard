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
    //updateTime sets whether time is to be updated or not
    //this is used while updating the time in every frame
    public long getTimerValMSec(boolean updateTime){
        if(updateTime) {
            updateTime();
        }
        timerVal=latestTime-initTime;
        return timerVal;
    }


    //get timer value formatted in min:sec type as a string
    public String getTimerValStr(){
        String val="";
        getTimerValMSec(true);
        if(timerVal/1000 >= 60){
            long timerValSec=timerVal/1000;
            int timeMin=(int)timerValSec/60;
            String minString=String.valueOf(timeMin);
            if(timeMin < 10){
                minString="0"+minString;
            }

            int timeSec=(int)timerValSec%60;
            String secString=String.valueOf(timeSec);
            if(timeSec < 10){
                secString="0"+secString;
            }
            val=minString+":"+secString;
        }
        else if(timerVal/1000 < 60){
            int timeSec=(int)timerVal/1000;
            String secString=String.valueOf(timeSec);
            if(timeSec < 10){
                secString="0"+secString;
            }
            val="00:"+secString;
        }
        return val;
    }



}

//class to store time in both milliseconds and as a string in min:sec format
class Time {
    protected long timeMilli;
    protected String timeStr;
    protected long timeSnap;




}

