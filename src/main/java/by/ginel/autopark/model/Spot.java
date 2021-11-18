package by.ginel.autopark.model;

import java.util.concurrent.Semaphore;

public class Spot {

    private Integer spotNum;
    private boolean free = true;
    private Semaphore semaphore;

    public Spot(Integer spotNum) { this.spotNum = spotNum; }

    public void setSemaphore(Semaphore semaphore) { this.semaphore = semaphore; }

    public boolean isFree() { return free; }

    public void taken() { this.free = false; }

    public Integer getSpotNum() { return spotNum; }

    public void release() {
        this.free = true;
        semaphore.release();
    }
}
