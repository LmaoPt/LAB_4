import java.io.*;
import java.nio.charset.StandardCharsets;

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

        String make = v.getMake();
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
                case "Car":
                    vehicle = new Car(make, 0);
                    break;
                case "Bike":
                    vehicle = new Bike(make, 0);
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

                vehicle.addModel(price, modelName);
            }

            return vehicle;

        } catch (DuplicateModelNameException e) {
            throw new IOException("Ошибка при чтении: дублирование имени модели", e);
        }
    }

    public static void writeVehicle(Vehicle vehicle, Writer out) {
        PrintWriter writer = new PrintWriter(out);
        writer.println(vehicle.getClass().getSimpleName());
        writer.println(vehicle.getMake());
        writer.println(vehicle.getModelsLength());

        String[] models = vehicle.getNamesOfModels();
        double[] prices = vehicle.getPrices();
        for (int i = 0; i < models.length; i++) {
            writer.println(models[i]);
            writer.println(prices[i]);
        }
        writer.flush();
    }


    public static Vehicle readVehicle(Reader in) throws IOException {
        BufferedReader reader = new BufferedReader(in);

        try {
            String className = reader.readLine();
            String make = reader.readLine();
            int modelCount = Integer.parseInt(reader.readLine());

            Vehicle vehicle;
            switch (className) {
                case "Car":
                    vehicle = new Car(make, 0);
                    break;
                case "Bike":
                    vehicle = new Bike(make, 0);
                    break;
                default:
                    throw new IOException("Неизвестный класс: " + className);
            }

            for (int i = 0; i < modelCount; i++) {
                String modelName = reader.readLine();
                String priceStr = reader.readLine();

                if (modelName == null || priceStr == null) {
                    throw new IOException("Неожиданный конец файла при чтении моделей");
                }

                double price = Double.parseDouble(priceStr);
                vehicle.addModel(price, modelName);
            }
            return vehicle;

        } catch (
                DuplicateModelNameException e) {
            throw new IOException("Ошибка при чтении: дублирование имени модели", e);
        } catch (
                NumberFormatException e) {
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
}