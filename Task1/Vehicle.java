import java.io.Serializable;

public interface Vehicle extends Serializable {
    void setMake(String make);

    String getMake();

    String[] getNamesOfModels();

    double getPriceOfModelName(String name) throws NoSuchModelNameException;

    void setPriceOfModelName(String name, double price) throws NoSuchModelNameException;

    double[] getPrices();

    void addModel(double price, String name) throws DuplicateModelNameException;

    void deleteModel(String name) throws NoSuchModelNameException;

    void setModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException;

    int getModelsLength();
}