import java.io.Serializable;

public class Motorbike implements Vehicle{
    private static final long serialVersionUID = 1L;

    private String mark;
    private int size;
    private Model head;

    private transient long lastModified;
    {
        lastModified = System.currentTimeMillis();
    }

    public Motorbike(String mark, int size_)  {
        this.head = new Model();
        this.head.next = head;
        this.head.prev = head;
        this.mark = mark;
        this.size = 0;

        if(size_ > 0){
            try {
                for (int i = 0; i < size_; i++) {
                    addModel((i + 1) * 100000, "Model " + (i + 1));
                }
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
            }
        }
    }

    public void setMark(String make) {
        this.mark = make;
    }
    public String getMark() {
        return this.mark;
    }
    public int getModelsLength() {
        return size;
    }

    public String[] getNamesOfModels() {
        String[] names = new String[size];
        int i = 0;
        Model p = head.next;
        while (p != head) {
            names[i] = p.name;
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
                if (p.name.equals(oldModelName)) {
                    f = p;
                }
                if (p.name.equals(newModelName)) {
                    throw new DuplicateModelNameException(newModelName);
                }
                p = p.next;
            }
            if (f == null) {
                throw new NoSuchModelNameException(oldModelName);
            } else {
                f.name = newModelName;
            }
            lastModified = System.currentTimeMillis();
        }
    }

    public double getPriceOfModelName(String name) throws NoSuchModelNameException {
        Model p = head.next;
        while (p != head) {
            if (name.equals(p.name)) {
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
            if (name.equals(p.name)) {
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

    public void addModel(double price, String name) throws DuplicateModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        Model p = head.next;
        Model w = new Model(name, price);
        while (p != head) {
            if (p.name.equals(name)) {
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
            if (name.equals(p.name)) {
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
    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        String mark = getMark();
        int countModel = getModelsLength();
        String[] models = getNamesOfModels();
        double[] prices = getPrices();

        buffer.append("Марка машины: " + mark + " Количество: " + countModel + "\n");
        for(int i = 0; i < countModel; i++){
            buffer.append((i + 1) + ") " + "Название: " + models[i] + " Цена: " + prices[i] +"\n");
        }
        return buffer.toString();
    }

    private class Model implements Serializable {
        private static final long serialVersionUID = 1L;
        String name = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;

        public Model() {}
        public Model(String modelname, double price) {
            this.name = modelname;
            this.price = price;
        }
    }
}