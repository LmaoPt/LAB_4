import java.io.Serializable;
import java.util.Arrays;
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
                    addModel((i + 1) * 100000, "Model " + (i + 1));
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

    public void addModel(double price, String name) throws DuplicateModelNameException {
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

        buffer.append("Марка машины: " + mark + " Количество: " + countModel + "\n");
        for(int i = 0; i < countModel; i++){
            buffer.append((i + 1) + ") " + "Название: " + models[i] + " Цена: " + prices[i] +"\n");
        }
        return buffer.toString();
    }
    @Override
    public boolean equals(Vehicle vehicle){
        /*
        1) ссылка на себя же - true.
        2) объект не является транспортным средством - false
        3) объект не имеет такую же марку - false
        4) если либо модели нет - пропуск, или одной модели нет - false.
        5) объекты не имееют точно такие же модели - false и список моделей - false, думаю что кол-во тоже сравнить - false
        6) цены не совпадают - false
        Иначе true

         */
    }

    private class Model implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private double price;

        public Model(String name_, double price_) {
            this.name = name_;
            this.price = price_;
        }
    }
}