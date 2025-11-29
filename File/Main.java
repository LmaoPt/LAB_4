import java.io.*;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) {

        try {
            String className = args[0];
            String methodName = args[1];
            String name = args[2];
            double price = Double.parseDouble(args[3]);

            Class<?> clss = Class.forName(className);
            Vehicle reflection = (Vehicle) clss.getDeclaredConstructor(String.class, int.class).newInstance("Audi", 3);
            Method method = clss.getMethod(methodName, String.class, double.class);


            reflection.addModel("F2",123);
            reflection.addModel("F3",12356);
            reflection.addModel("F234",12365);
            reflection.deleteModel("Model 2");
            reflection.setModelName("F3", "F666");
            reflection.setPriceOfModelName("F234", 13231);
            VehicleInfo.print(reflection);
            System.out.println("------------");

            method.invoke(reflection, name, price);
            VehicleInfo.print(reflection);

            System.out.println("------------");

            Vehicle i = VehicleInfo.createVehicle(reflection, "MM", 12);
            VehicleInfo.print(i);

            System.out.println("------------");


            Writer writer = new OutputStreamWriter(System.out);
            VehicleInfo.writeVehicle(i, writer);

            Vehicle result = VehicleInfo.readVehicle(new InputStreamReader(System.in));
            VehicleInfo.print(result);








        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }


    }
}