import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

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
                    addModel("Model " + (i + 1),(i + 1) * 100000);
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
        if(price <= 0.0f){
            throw new ModelPriceOutOfBoundsException("Отрицательная цена!");
        }
        Model current = head.next;
        boolean modelFound = false;
        while(current != head){
            if(current.getName().equals(name)){
                modelFound = true;
                break;
            }
            current = current.next;
        }
        if(modelFound){
            current.setPrice(price);
        }
        else{
            throw new NoSuchModelNameException("Такой модели нет!");
        }
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

    public void addModel(String name, double price) throws DuplicateModelNameException {
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

        buffer.append("Марка машин: " + mark + " Количество: " + countModel);
        for(int i = 0; i < countModel; i++){
            buffer.append("\n"+(i + 1) + ") " + "Название: " + models[i] + " Цена: " + prices[i]);
        }
        return buffer.toString();
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj){ return true;}
        else if(!(obj instanceof Vehicle)){return false;}
        else{
            Motorbike motorbike = (Motorbike) obj;
            if(!(Objects.equals(this.getMark(), motorbike.getMark()))){return false;}
            if(!(Objects.equals(this.getModelsLength(), motorbike.getModelsLength()))){return false;}

            String[] name = motorbike.getNamesOfModels();
            double[] prices = motorbike.getPrices();

            String[] thisName = this.getNamesOfModels();
            double[] thisPrice = this.getPrices();

            for(int i = 0; i < thisName.length; i++) {
                if (!Objects.equals(thisName[i], name[i])) {return false;}
                if (!Objects.equals(thisPrice[i], prices[i])) {return false;}
            }
            return true;
        }
    }
    @Override
    public int hashCode(){
        int result = mark.hashCode();
        Model current = head;
        while(current != head ){
            result = result * 31 + current.hashCode();
            current = current.next;
        }
        return result;
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
        Motorbike motorbikeClon = (Motorbike) super.clone();
        try {
            Model current = head.next;
            motorbikeClon.head = new Model();
            motorbikeClon.head.next = motorbikeClon.head;
            motorbikeClon.head.prev = motorbikeClon.head;
            motorbikeClon.size = 0;

            while (current != head) {
                motorbikeClon.addModel(current.getName(), current.getPrice());
                current = current.next;
            }
        }
        catch (DuplicateModelNameException e) {
            e.printStackTrace();
        }
        return motorbikeClon;
    }
    private class Model implements Serializable, Cloneable {
        private static final long serialVersionUID = 1L;
        String name = null;
        double price = Double.NaN;
        Model prev = null;
        Model next = null;

        public Model() {}
        public Model(String name, double price) {
            this.name = name;
            this.price = price;
        }
        public String getName(){return name;}
        public void setName(String name){this.name = name;}
        public Double getPrice(){return price;}
        public void setPrice(Double price){this.price = price;}
    }
}