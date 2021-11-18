package by.ginel.autopark.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

public class Starter {

    public static void main(String[] args) {
        Exchanger<Ticket> exchanger = new Exchanger<>();
        List<Spot> spots = getSpots();
        Parkinglot parkinglot = new Parkinglot(spots);
        List<Car> cars = getCars(parkinglot,exchanger);
        cars.forEach(Thread::start);
    }

    private static List<Car> getCars(Parkinglot parkinglot,Exchanger<Ticket> exchanger) {
        List<Car> cars = new ArrayList<>();
        for(int i=0;i<20;i++){
            Car car = new Car(i+1,parkinglot,exchanger);
            cars.add(car);
        }
        return cars;
    }

    private static List<Spot> getSpots() {
        List<Spot> spots = new ArrayList<>();
        for (int i =0;i<10;i++){
            Spot spot = new Spot((i+1));
            spots.add(spot);
        }
        return spots;
    }
}
