import java.io.Serializable;

public class Bike implements Vehicle{
    private static final long serialVersionUID = 1L;

    private String make;
    private int size = 0;
    private Model head;
    private transient long lastModified;
    {
        lastModified = System.currentTimeMillis();
    }

    public Bike(String make, int size) {
        this.head = new Model();
        this.head.next = head;
        this.head.prev = head;
        this.size = 0;
        this.make = make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getMake() {
        return this.make;
    }

    public String[] getNamesOfModels() {
        String[] names = new String[size];
        int i = 0;
        Model p = head.next;
        while (p != head) {
            names[i] = p.modelname;
            p = p.next;
            i++;
        }
        return names;
    }

    public void setModelName(String oldModelName, String newModelName) throws NoSuchModelNameException, DuplicateModelNameException {
        Model p = head.next;
        Model f = null;
        if (!oldModelName.equals(newModelName)) {
            while (p != head) {
                if (p.modelname.equals(oldModelName)) {
                    f = p;
                }
                if (p.modelname.equals(newModelName)) {
                    throw new DuplicateModelNameException(newModelName);
                }
                p = p.next;
            }
            if (f == null) {
                throw new NoSuchModelNameException(oldModelName);
            } else {
                f.modelname = newModelName;
            }
            lastModified = System.currentTimeMillis();
        }
    }

    public double getPriceOfModelName(String name) throws NoSuchModelNameException {
        Model p = head.next;
        while (p != head) {
            if (name.equals(p.modelname)) {
                return p.price;
            }
            p = p.next;
        }
        throw new NoSuchModelNameException(name);
    }

    public void setPriceOfModelName(String name, double price) throws NoSuchModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        Model p = head.next;
        while (p != head) {
            if (name.equals(p.modelname)) {
                p.price = price;
                lastModified = System.currentTimeMillis();
                return;
            }
            p = p.next;
        }
        throw new NoSuchModelNameException(name);
    }

    public double[] getPrices() {
        double[] prices = new double[size];
        int i = 0;
        Model p = head.next;
        while (p != head) {
            prices[i] = p.price;
            p = p.next;
            i++;
        }
        return prices;
    }

    public int getModelsLength() {
        return size;
    }

    public void addModel(double price, String name) throws DuplicateModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        Model p = head.next;
        Model w = new Model(name, price);
        while (p != head) {
            if (p.modelname.equals(name)) {
                throw new DuplicateModelNameException(name);
            }
            p = p.next;
        }
        w.next = head;
        w.prev = head.prev;
        head.prev.next = w;
        head.prev = w;
        size++;
        lastModified = System.currentTimeMillis();
    }

    public void deleteModel(String name) throws NoSuchModelNameException {
        Model p = head.next;
        while (p != head) {
            if (name.equals(p.modelname)) {
                p.prev.next = p.next;
                p.next.prev = p.prev;
                size--;
                lastModified = System.currentTimeMillis();
                return;
            }
            p = p.next;
        }
        throw new NoSuchModelNameException(name);
    }

    private class Model implements Serializable {
        private static final long serialVersionUID = 1L;
        String modelname = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;

        public Model() {
        }

        public Model(String modelname, double price) {
            this.modelname = modelname;
            this.price = price;
        }
    }
}