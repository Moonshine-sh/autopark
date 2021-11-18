package by.ginel.autopark;

import by.ginel.autopark.model.Car;
import by.ginel.autopark.model.Parkinglot;
import by.ginel.autopark.model.Ticket;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.concurrent.Exchanger;

import static java.lang.Thread.sleep;

public class CarTest {

    @Test
    public void runTest() throws Exception{
        Parkinglot parkinglot = Mockito.mock(Parkinglot.class);
        Car car = new Car(1, parkinglot, new Exchanger<Ticket>());
        Mockito.when(parkinglot.checkIn()).thenReturn(null);
        car.start();
        sleep(200);

        Assert.assertFalse(car.isAlive());
    }
}
