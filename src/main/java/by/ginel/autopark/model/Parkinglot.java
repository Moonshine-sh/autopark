package by.ginel.autopark.model;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Parkinglot {

    private List<Spot> spots;

    private Semaphore semaphore;
    private ReentrantLock lock = new ReentrantLock();

    Parkinglot(List<Spot> spots) {
        this.spots = spots;
        semaphore = new Semaphore(spots.size());
        spots.forEach(s -> s.setSemaphore(semaphore));
    }

    public Spot getSpot() {
        try {
            lock.lock();
            Spot spot = spots.stream().filter(s -> s.isFree()).findFirst().get();
            spot.taken();
            return spot;
        } finally {
            lock.unlock();
        }
    }

    public Ticket checkIn() throws InterruptedException {
        Ticket ticket = new Ticket();
        if (semaphore.tryAcquire((long) (100 + Math.random() * 300), TimeUnit.MILLISECONDS)) {
            ticket.setSpotNum(getSpot().getSpotNum());
            ticket.setDateOfExp(new Date(new Date().getTime() + 500l + (long) (Math.random() * 1200)));
            return ticket;
        }
        else
            return null;
    }

    public void checkOut(Ticket ticket) {
        spots.stream().filter(spot -> spot.getSpotNum()==ticket.getSpotNum()).findFirst().get().release();
    }

    public void extendTime(Ticket ticket) {
        ticket.setDateOfExp(new Date(new Date().getTime() + 500l + (long) (Math.random() * 1200)));
    }
}
