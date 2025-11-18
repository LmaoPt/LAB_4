import java.io.Closeable;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Auto implements Vehicle {
    private static final long serialVersionUID = 1L;

    private String mark;
    private Model[] models;

    public Auto(String mark_, int length) {
        this.mark = mark_;
        this.models = new Model[0];

        if (length > 0) {
            try {
                for (int i = 0; i < length; i++) {
                    addModel("Model " + (i + 1), (i + 1) * 100000);
                }
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
            }
        }
    }
    public String getMark() {
        return mark;
    }
    public void setMark(String carmake) {
        this.mark = carmake;
    }
    public int getModelsLength() {return models.length;}

    public String[] getNamesOfModels() {
        String[] modelnames = new String[models.length];
        for (int i = 0; i < models.length; i++) {
            modelnames[i] = models[i].name;
        }
        return modelnames;
    }

    public double getPriceOfModelName(String modelname) throws NoSuchModelNameException {
        for (int i = 0; i < models.length; i++) {
            if (modelname.equals(models[i].name)) {
                return models[i].price;
            }
        }
        throw new NoSuchModelNameException(modelname);
    }

    public void setPriceOfModelName(String modelname, double modelprice) throws NoSuchModelNameException {
        if (modelprice <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        for (int i = 0; i < models.length; i++) {
            if (modelname.equals(models[i].name)) {
                models[i].price = modelprice;
                return;
            }
        }
        throw new NoSuchModelNameException(modelname);
    }

    public double[] getPrices() {
        double[] modelprices = new double[models.length];
        for (int i = 0; i < models.length; i++) {
            modelprices[i] = models[i].price;
        }
        return modelprices;
    }

    public void addModel(String name, double price) throws DuplicateModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        for (int i = 0; i < this.models.length; i++) {
            if (name.equals(models[i].name)) {
                throw new DuplicateModelNameException(name);
            }
        }
        models = Arrays.copyOf(models, models.length + 1);
        models[models.length - 1] = new Model(name, price);
    }

    public void deleteModel(String name) throws NoSuchModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (name.equals(models[i].name)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            System.arraycopy(models, index + 1, models, index, models.length - index - 1);
            models = Arrays.copyOf(models, models.length - 1);
        } else {
            throw new NoSuchModelNameException(name);
        }
    }

    public void setModelName(String oldmodelname, String newmodelname) throws NoSuchModelNameException, DuplicateModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (oldmodelname.equals(models[i].name)) {
                index = i;
            }
            if (newmodelname.equals(models[i].name)) {
                throw new DuplicateModelNameException(newmodelname);
            }
        }
        if (index != -1) {
            models[index].name = newmodelname;
        } else {
            throw new NoSuchModelNameException(oldmodelname);
        }
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
        else if(!(obj instanceof Auto)){return false;}
        else{
            Auto auto = (Auto) obj;
            if(!(Objects.equals(this.getMark(), auto.getMark()))){return false;}
            if(!(Objects.equals(this.getModelsLength(), auto.getModelsLength()))){return false;}

            String[] name = auto.getNamesOfModels();
            double[] prices = auto.getPrices();
            for(int i = 0; i < name.length; i++) {
                if (!Objects.equals(this.models[i].getName(), name[i])) {return false;}
                if (!Objects.equals(this.models[i].getPrice(), prices[i])) {return false;}
            }
            return true;
        }
    }
    @Override
    public int hashCode(){
        int result = mark.hashCode();
        result = result * 31 + Arrays.hashCode(models);
        return result;
    }
    @Override
    public Object clone() throws CloneNotSupportedException{
        Auto autoClone = (Auto) super.clone();
        autoClone.models = new Model[this.models.length];
        for(int i = 0; i < this.models.length; i++){
            Model model = this.models[i];
            autoClone.models[i] = new Model(model.getName(), model.getPrice());
        }
        return autoClone;
    }

    private class Model implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private double price;

        public Model(String name_, double price_) {
            this.name = name_;
            this.price = price_;
        }
        public String getName(){return name;}
        public void setName(String nname){name = nname;}
        public double getPrice(){return price;}
        public void setPrice(double pprice){price = pprice;}
    }

}