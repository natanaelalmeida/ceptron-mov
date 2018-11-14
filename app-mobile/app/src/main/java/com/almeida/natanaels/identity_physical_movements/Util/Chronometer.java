package com.almeida.natanaels.identity_physical_movements.Util;

import java.util.concurrent.TimeUnit;

public class Chronometer {

    private long start;
    private long end;

    public void start(){
        start = System.currentTimeMillis();
    }

    public void stop(){
        end = System.currentTimeMillis();
    }

    public long getSeconds(){
        return TimeUnit.MILLISECONDS.toSeconds(getTime());
    }

    public long getMilliseconds(){
        return TimeUnit.MILLISECONDS.toMillis(getTime());
    }

    public long getTime(){
        return start - end;
    }
}
