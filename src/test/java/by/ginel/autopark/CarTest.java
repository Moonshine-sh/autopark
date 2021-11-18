package by.ginel.autopark;

import by.ginel.autopark.model.Car;
import by.ginel.autopark.model.Parkinglot;
import by.ginel.autopark.model.Ticket;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.xml.crypto.Data;
import java.security.PublicKey;
import java.util.Date;
import java.util.concurrent.Exchanger;
import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

public class CarTest {

    @Test
    public void runTest() throws Exception{
        Parkinglot parkinglot = Mockito.mock(Parkinglot.class);
        Car car = new Car(1, parkinglot, new Exchanger<Ticket>());
        Ticket ticket = new Ticket();
        if(Math.random()*100 > 50){
            ticket.setSpotNum(1);
            ticket.setDateOfExp(new Date(new Date().getTime()));
        }
        else
            ticket = null;
        Mockito.when(parkinglot.checkIn()).thenReturn(ticket);
        car.start();
        sleep(500);

        Assert.assertFalse(car.isAlive());
    }
}
