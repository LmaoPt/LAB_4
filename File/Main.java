import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            Vehicle motorbike = new Motorbike("Audi", 0);
            motorbike.addModel( "123",1532300);
            motorbike.addModel("S12",2533000);

            Vehicle motorbike1 = new Motorbike("Audi", 2);
            motorbike1.addModel( "123",1532300);
            motorbike1.addModel("S12",2533000);

            Vehicle auto = new Auto("Mercedes", 2);
            auto.addModel("Turbo-S", 1532300);
            auto.addModel("B12", 2533000);

            System.out.println(motorbike);
            System.out.println(auto);
            System.out.println("Значения равны? " + motorbike.equals(motorbike1));
            System.out.println("Хеш-код: " + motorbike.hashCode());
            System.out.println("Хеш-код: " + motorbike1.hashCode());


            Vehicle motorbike3 = new Motorbike("Audi", 0);
            motorbike3.addModel( "12",1532300);
            motorbike3.addModel( "S12",2533000);

            Vehicle motorbikeClone = (Vehicle) motorbike3.clone();

            motorbikeClone.setModelName("12", "ИЗМЕНЕННО");
            motorbikeClone.setModelName("S12", "ИЗМЕНЕННО2");
            motorbikeClone.setPriceOfModelName("ИЗМЕНЕННО",666);
            motorbikeClone.setPriceOfModelName("ИЗМЕНЕННО2",999);

            VehicleInfo.print(motorbike3);
            VehicleInfo.print(motorbikeClone);







        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }


    }
}