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

            System.out.println(motorbike.toString());
            System.out.println(auto.toString());
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




            /*System.out.println("\n=== ИСХОДНЫЕ ДАННЫЕ ===");
            System.out.println("Автомобиль:");
            VehicleInfo.print(car);


            // =================== ТЕСТ БАЙТОВЫХ ПОТОКОВ ===================
            System.out.println("\n=== ТЕСТ БАЙТОВЫХ ПОТОКОВ ===");


            try (OutputStream output = new FileOutputStream("car_binary.bin")) {
                VehicleInfo.outputVehicle(car, output);
                System.out.println("записан в бинарный файл");
            }


            try (InputStream input = new FileInputStream("car_binary.bin")) {
                Vehicle carFromBinary = VehicleInfo.inputVehicle(input);
                System.out.println("прочитан из бинарного файла:");
                VehicleInfo.print(carFromBinary);
            }

            // =================== ТЕСТ СИМВОЛЬНЫХ ПОТОКОВ ===================
            System.out.println("\n=== ТЕСТ СИМВОЛЬНЫХ ПОТОКОВ ===");


            try (Writer writer = new FileWriter("bike_text.txt")) {
                VehicleInfo.writeVehicle(car, writer);
                System.out.println("записан в текстовый файл");
            }


            try (Reader reader = new FileReader("bike_text.txt")) {
                Vehicle bikeFromText = VehicleInfo.readVehicle(reader);
                System.out.println("прочитан из текстового файла:");
                VehicleInfo.print(bikeFromText);
            }

            // =================== ТЕСТ СЕРИАЛИЗАЦИИ ===================
            System.out.println("\n=== ТЕСТ СЕРИАЛИЗАЦИИ ===");


            VehicleInfo.serializeVehicle(car, "car_serialized.bin");
            System.out.println("✓ Автомобиль сериализован");


            Vehicle carDeserialized = VehicleInfo.deserializeVehicle("car_serialized.bin");
            System.out.println("✓ Автомобиль десериализован:");
            VehicleInfo.print(carDeserialized);
            /*System.out.println("Марка: " + carDeserialized.getMake());
            System.out.println("Количество моделей: " + carDeserialized.getModelsLength());*/

            // =================== ТЕСТ С System.in / System.out ===================
            /*System.out.println("\n=== ТЕСТ С СИСТЕМНЫМИ ПОТОКАМИ ===");// дописать тест системных потоков


            System.out.println("Тест System.out");
            VehicleInfo.writeVehicle(car, new PrintWriter(System.out));



            System.out.println("\nНапиши");
            Vehicle test = VehicleInfo.readVehicle(new InputStreamReader(System.in));
            VehicleInfo.writeVehicle(test, new OutputStreamWriter(System.out));

            */

            /*
            Car
            Audi
            2
            Mazda
            12400.0
            Camry
            31122.0
            */


        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }


    }
}