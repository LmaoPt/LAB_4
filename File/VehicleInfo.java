import java.io.*;
import java.nio.charset.StandardCharsets;
import java.lang.reflect.*;
import java.util.Scanner;

public class VehicleInfo {

    public static double getAveragePrice(Vehicle vehicle) {
        double sum = 0;
        double[] prices = vehicle.getPrices();
        for (double price : prices) {
            sum += price;
        }
        return sum / prices.length;
    }

    public static void print(Vehicle vehicle) {
        String clssName = vehicle.getClass().getSimpleName();
        String mark = vehicle.getMark();
        System.out.println("Класс: " + clssName + " Марка: " + mark);
        String[] models = vehicle.getNamesOfModels();
        double[] prices = vehicle.getPrices();
        for (int i = 0; i < models.length; i++) {
            System.out.println("Модель: " + models[i] + " Цена: " + prices[i]);
        }
    }

    public static void outputVehicle(Vehicle v, OutputStream out) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);

        String className = v.getClass().getSimpleName();
        byte[] classBytes = className.getBytes(StandardCharsets.UTF_8);
        dos.writeInt(classBytes.length);
        dos.write(classBytes);

        String make = v.getMark();
        byte[] makeBytes = make.getBytes(StandardCharsets.UTF_8);
        dos.writeInt(makeBytes.length);
        dos.write(makeBytes);

        dos.writeInt(v.getModelsLength());

        String[] models = v.getNamesOfModels();
        double[] prices = v.getPrices();
        for (int i = 0; i < models.length; i++) {
            byte[] modelBytes = models[i].getBytes(StandardCharsets.UTF_8);
            dos.writeInt(modelBytes.length);
            dos.write(modelBytes);
            dos.writeDouble(prices[i]);
        }
        dos.flush();
    }
    public static Vehicle inputVehicle(InputStream in) throws IOException {
        DataInputStream dis = new DataInputStream(in);
        try {
            int classNameLength = dis.readInt();
            byte[] classNameBytes = new byte[classNameLength];
            dis.readFully(classNameBytes);
            String className = new String(classNameBytes, StandardCharsets.UTF_8);

            int makeLength = dis.readInt();
            byte[] makeBytes = new byte[makeLength];
            dis.readFully(makeBytes);
            String make = new String(makeBytes, StandardCharsets.UTF_8);

            int modelCount = dis.readInt();

            Vehicle vehicle;
            switch (className) {
                case "Auto":
                    vehicle = new Auto(make, 0);
                    break;
                case "Motorbike":
                    vehicle = new Motorbike(make, 0);
                    break;
                default:
                    throw new IOException("Неизвестный класс: " + className);
            }

            for (int i = 0; i < modelCount; i++) {
                int modelNameLength = dis.readInt();
                byte[] modelNameBytes = new byte[modelNameLength];
                dis.readFully(modelNameBytes);
                String modelName = new String(modelNameBytes, StandardCharsets.UTF_8);
                double price = dis.readDouble();

                vehicle.addModel(modelName, price);
            }
            return vehicle;

        } catch (DuplicateModelNameException e) {
            throw new IOException("Ошибка при чтении: дублирование имени модели", e);
        }
    }

    public static void writeVehicle(Vehicle vehicle, Writer out) {
        PrintWriter writer = new PrintWriter(out);

        writer.printf("Класс: %s%n", vehicle.getClass().getSimpleName());
        writer.printf("Марка: %s%n", vehicle.getMark());
        writer.printf("Количество моделей: %d%n", vehicle.getModelsLength());

        String[] models = vehicle.getNamesOfModels();
        double[] prices = vehicle.getPrices();
        for (int i = 0; i < models.length; i++) {
            writer.printf("Модель: %s%n",models[i]);
            writer.printf("Цена: %.2f%n", prices[i]);
        }
        writer.flush();
    }


    public static Vehicle readVehicle(Reader in) throws IOException {
        Scanner scanner = new Scanner(in);
        try {
            String className = scanner.nextLine().replace("Класс: ","");
            String make = scanner.nextLine().replace("Марка: ", "");
            int modelCount = Integer.parseInt(scanner.nextLine().replace("Количество моделей: ", ""));

            Vehicle vehicle;
            switch (className) {
                case "Auto":
                    vehicle = new Auto(make, 0);
                    break;
                case "Motorbike":
                    vehicle = new Motorbike(make, 0);
                    break;
                case "Scooter":
                    vehicle = new Scooter(make, 0);
                    break;
                case "Moped":
                    vehicle = new Moped(make, 0);
                    break;
                case "Quadbike":
                    vehicle = new Quadbike(make, 0);
                    break;
                default:
                    throw new IOException("Неизвестный класс: " + className);
            }
            for (int i = 0; i < modelCount; i++) {
                String name = scanner.nextLine().replace("Модель: ", "");
                double price = Double.parseDouble((scanner.nextLine().replace("Цена: ", "")).replace(",","."));
                vehicle.addModel(name, price);
            }
            return vehicle;
        }
        catch (DuplicateModelNameException e) {
            throw new IOException("Ошибка при чтении: дублирование имени модели", e);
        }
        catch (NumberFormatException e) {
            throw new IOException("Ошибка формата числа", e);
        }
    }

    public static void serializeVehicle(Vehicle vehicle, String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(vehicle);
        }
    }

    public static Vehicle deserializeVehicle(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (Vehicle) ois.readObject();
        }
    }
    public static Vehicle createVehicle(Vehicle vehicle, String mark, int length){
        Vehicle instance = null;
        try {
            Class<?> clss = vehicle.getClass();
            Constructor constructor = clss.getDeclaredConstructor(String.class, int.class);
            instance = (Vehicle) constructor.newInstance(mark,length);

        }
        catch(NoSuchMethodException e) {
            return null;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return instance;
    }
    public static double getAveragePriceOfAllModels(Vehicle... vehicles){
        double result = 0;
        double count = 0;
        for(int i = 0; i < vehicles.length; i++){
            for(int j = 0; j < vehicles[i].getModelsLength(); j++){
                result += vehicles[i].getPrices()[j];
                count++;
            }
        }
        return result / count;
    }
}