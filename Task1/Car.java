import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class Car implements Vehicle {
    private static final long serialVersionUID = 1L;

    private String make;
    private Model[] models;

    public Car(String make, int length) {
        this.make = make;
        this.models = new Model[0];

        if (length > 0) {
            try {
                Random r = new Random();
                for (int i = 0; i < length; i++) {
                    this.addModel(r.nextDouble(1000000), "Model " + (i + 1));
                }
            } catch (DuplicateModelNameException e) {
                e.printStackTrace();
            }
        }
    }


    public String getMake() {
        return make;
    }

    public void setMake(String carmake) {
        this.make = carmake;
    }

    public String[] getNamesOfModels() {
        String[] modelnames = new String[models.length];
        for (int i = 0; i < models.length; i++) {
            modelnames[i] = models[i].modelname;
        }
        return modelnames;
    }

    public double getPriceOfModelName(String modelname) throws NoSuchModelNameException {
        for (int i = 0; i < models.length; i++) {
            if (modelname.equals(models[i].modelname)) {
                return models[i].modelprice;
            }
        }
        throw new NoSuchModelNameException(modelname);
    }

    public void setPriceOfModelName(String modelname, double modelprice) throws NoSuchModelNameException {
        if (modelprice <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        for (int i = 0; i < models.length; i++) {
            if (modelname.equals(models[i].modelname)) {
                models[i].modelprice = modelprice;
                return;
            }
        }
        throw new NoSuchModelNameException(modelname);
    }

    public double[] getPrices() {
        double[] modelprices = new double[models.length];
        for (int i = 0; i < models.length; i++) {
            modelprices[i] = models[i].modelprice;
        }
        return modelprices;
    }

    public void addModel(double price, String name) throws DuplicateModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        for (int i = 0; i < this.models.length; i++) {
            if (name.equals(models[i].modelname)) {
                throw new DuplicateModelNameException(name);
            }
        }
        models = Arrays.copyOf(models, models.length + 1);
        models[models.length - 1] = new Model(name, price);
    }

    public void deleteModel(String name) throws NoSuchModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (name.equals(models[i].modelname)) {
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

    public int getModelsLength() {
        return models.length;
    }

    public void setModelName(String oldmodelname, String newmodelname) throws NoSuchModelNameException, DuplicateModelNameException {
        int index = -1;
        for (int i = 0; i < models.length; i++) {
            if (oldmodelname.equals(models[i].modelname)) {
                index = i;
            }
            if (newmodelname.equals(models[i].modelname)) {
                throw new DuplicateModelNameException(newmodelname);
            }
        }
        if (index != -1) {
            models[index].modelname = newmodelname;
        } else {
            throw new NoSuchModelNameException(oldmodelname);
        }
    }

    private class Model implements Serializable {
        private static final long serialVersionUID = 1L;
        private String modelname;
        private double modelprice;

        public Model(String modelname, double modelprice) {
            this.modelname = modelname;
            this.modelprice = modelprice;
        }
    }
}