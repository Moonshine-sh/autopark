package by.ginel.autopark.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Car extends Thread {

    private static final Logger LOG = LoggerFactory.getLogger(Car.class);
    private Exchanger<Ticket> exchanger;
    private Ticket ticket;
    private Parkinglot parkinglot;
    private Integer carNum;

    public Car(Integer carNum, Parkinglot parkinglot, Exchanger<Ticket> exchanger) {
        this.carNum = carNum;
        this.parkinglot = parkinglot;
        this.exchanger = exchanger;
        setName("CAR-" + carNum);
    }

    @Override
    public void run() {
        try {
            if(ticket==null) {
                LOG.info("Car {} tries to get parking spot", carNum);
                ticket = parkinglot.checkIn();
            }

            if(ticket!=null){
                LOG.info("Car {} parked at spot {}", carNum, ticket.getSpotNum());

                sleep(100);

                if(Math.random()*100 > 40)
                    tryExchange();

                long toWait = ticket.getDateOfExp().getTime() - new Date().getTime();
                sleep(toWait < 0 ? 0 : toWait);

                if(Math.random()*100 < 40) {
                    LOG.info("Car {} extend its parking time at spot {}", carNum, ticket.getSpotNum());
                    parkinglot.extendTime(ticket);
                    run();
                }
                else {
                    LOG.info("Car {} checked out of spot {}",carNum, ticket.getSpotNum());
                    parkinglot.checkOut(ticket);
                }
            }else {
                LOG.info("Car {} waited for too long and drove off", carNum);
            }
        } catch (InterruptedException e) {
            interrupt();
            LOG.error("Car crashed", e);
            throw new RuntimeException();
        }

    }

    private void tryExchange() throws InterruptedException {
        try {
            LOG.info("Car {} tries to exchange ticket", carNum);
            ticket = exchanger.exchange(ticket, 100, TimeUnit.MILLISECONDS);
            LOG.info("Car {} exchanged ticket successfully", carNum);
        }
         catch (TimeoutException e) {
            LOG.info("Car {} tried to exchange ticket but in vain",carNum);
        }
    }
}
