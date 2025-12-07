import java.io.*;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {
        Vehicle vehicle = new Auto("Audi", 1000);
        Thread1 th = new Thread1(vehicle);
        Thread2 th1 = new Thread2(vehicle);

        th.setPriority(Thread.MAX_PRIORITY);
        th1.setPriority(Thread.MIN_PRIORITY);
        th.start();
        th1.start();

    }
}