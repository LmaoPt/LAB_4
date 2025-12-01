import java.io.*;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {

        try {
            //========================Тестирование рефлексии=============================
            System.out.println("======Тестирование рефлексии======");
            System.out.println("----до изменений");

            String className = args[0];
            String methodName = args[1];
            String name = args[2];
            double price = Double.parseDouble(args[3]);

            Class<?> clss = Class.forName(className);
            Vehicle reflection = (Vehicle) clss.getConstructor(String.class, int.class).newInstance("Audi", 3);
            Method method = clss.getMethod(methodName, String.class, double.class);


            reflection.addModel("F2",123);
            reflection.addModel("F3",12356);
            reflection.addModel("F234",12365);
            reflection.deleteModel("Model 2");
            reflection.setModelName("F3", "F666");
            reflection.setPriceOfModelName("F234", 13231);

            VehicleInfo.print(reflection);

            System.out.println("----после изменений");

            method.invoke(reflection, name, price);
            VehicleInfo.print(reflection);

            //========================Тестирование createVehicle=============================
            System.out.println("======Тестирование createVehicle======");

            Vehicle i = VehicleInfo.createVehicle(reflection, "MM", 12);
            VehicleInfo.print(i);

            //========================Тестирование с аргументом переменной длины=============================
            System.out.println("======Тест аргумента с переменной длины======");

            System.out.println("Средняя цена моделей транспортных средства: " + VehicleInfo.getAveragePriceOfAllModels(reflection,i));

            //========================Тестирование printf/Scanner=============================
            System.out.println("======Тестирование printf/Scanner======");

            Writer writer = new OutputStreamWriter(System.out);
            VehicleInfo.writeVehicle(i, writer);

            System.out.println("----Введите текст!");

            Vehicle result = VehicleInfo.readVehicle(new InputStreamReader(System.in));
            VehicleInfo.print(result);





        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }


    }
}