import java.io.*;
import java.util.*;
import java.lang.reflect.*;


public class Test {
    public static void main(String[] args) throws NoSuchModelNameException, DuplicateModelNameException, IOException {
        HashMap<Integer, String> passwordAndNames = new HashMap<>();
        passwordAndNames.put(243431, "Дмитрий Семёнович Семга");
        passwordAndNames.put(897345, "Пётр Иванович Колбаскин");
        passwordAndNames.put(234329, "Игорь Антонович Голубцов");
        passwordAndNames.put(765324, "Анастасия Владимировна Хач");
        String a = passwordAndNames.get(897345);
        System.out.println(passwordAndNames);
        System.out.println(a);

        Vehicle vehicle = new Scooter("Audi", 10);
        vehicle.setPriceOfModelName("Model 1", 2343);
        vehicle.deleteModel("Model 2");

        Vehicle vehicle2 = new Moped("Audi", 10);
        vehicle2.setPriceOfModelName("Model 1", 32434);
        vehicle2.setModelName("Model 10", "Model 24334");
        vehicle2.deleteModel("Model 2");

        VehicleInfo.print(vehicle2);
        //System.out.println(vehicle.equals(vehicle2));

        System.out.println(VehicleInfo.getAveragePriceOfAllModels(vehicle,vehicle2));

        /*Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число!");
        int sss = scanner.nextInt();
        System.out.println("Мне кажется ты ввёл: " + sss);
        scanner.close();
        System.out.printf("Hello world!");
        System.out.printf("привет мир!");*/

        Vehicle quadbike = new Quadbike("Mercedes",10);
        Writer writer = new OutputStreamWriter(System.out);
        VehicleInfo.writeVehicle(quadbike, writer);

        Vehicle result = VehicleInfo.readVehicle(new InputStreamReader(System.in));
        VehicleInfo.print(result);

    }
}

