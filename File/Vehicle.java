import java.io.Serializable;

public interface Vehicle extends Serializable, Cloneable {
    void setMark(String make);
    String getMark();
    String[] getNamesOfModels();
    double getPriceOfModelName(String name) throws NoSuchModelNameException;
    void setPriceOfModelName(String name, double price) throws NoSuchModelNameException;
    double[] getPrices();
    void addModel(String name, double price) throws DuplicateModelNameException;
    void deleteModel(String name) throws NoSuchModelNameException;
    void setModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException;
    int getModelsLength();
    String toString();
    boolean equals(Object obj);
    public int hashCode();
    Object clone() throws CloneNotSupportedException;
}