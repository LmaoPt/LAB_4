import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class Scooter implements Vehicle{
    private String mark;
    private HashMap<String, Double> models = new HashMap<>();

    public Scooter(String mark, int count)
    {
        this.mark = mark;
        if(count > 0) {
            try {
            for (int i = 0; i < count; i++) {
                    addModel("Model " + (i + 1), (i + 1) * 100000);
                }
            }
            catch(DuplicateModelNameException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String getMark() {
        return mark;
    }

    @Override
    public String[] getNamesOfModels() {
        String[] names = new String[models.size()];
        int i = 0;
        for(String model : models.keySet()){
            names[i] = model;
            i++;
        }
        return names;
    }

    @Override
    public double getPriceOfModelName(String name) throws NoSuchModelNameException {
        Double price = models.get(name);
        if(price == null){
            throw new NoSuchModelNameException("Модель не найдена!");
        }
        return price;
    }

    @Override
    public void setPriceOfModelName(String name, double price) throws NoSuchModelNameException {
        if (price <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        if (!models.containsKey(name)) {
            throw new NoSuchModelNameException("Такого названия нет: " + name);
        }
        models.put(name, price);
    }
    @Override
    public double[] getPrices() {
        double[] prices = new double[models.size()];
        int i = 0;
        for(Double model : models.values()){
            prices[i] = model;
            i++;
        }
        return prices;
    }

    @Override
    public void addModel(String name, double price) throws DuplicateModelNameException {
        if(price <= 0){
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        if(models.containsKey(name)){
            throw new DuplicateModelNameException("Такое название уже есть!");
        }
        models.put(name, price);
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        if(!models.containsKey(name)){
            throw new NoSuchModelNameException("Такого названия нет!");
        }
        else{
            models.remove(name);
        }
    }

    @Override
    public void setModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        if(models.containsKey(newName)){
            throw new DuplicateModelNameException("Такое название уже есть");
        }
        else if(!models.containsKey(oldName)){
            throw new NoSuchModelNameException("Старого название нет!");
        }
        else{
            double price = models.remove(oldName);
            models.put(newName, price);
        }
    }

    @Override
    public int getModelsLength() {
        return models.size();
    }
    @Override
    public String toString(){
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
        else if(!(obj instanceof Scooter)){return false;}
        else{
            Scooter shooter = (Scooter) obj;
            if(!(Objects.equals(this.getMark(), shooter.getMark()))){return false;}
            if(!(Objects.equals(this.getModelsLength(), shooter.getModelsLength()))){return false;}

            for(String model : this.models.keySet()){
                if(!shooter.models.containsKey(model)){ return false;}
                else if(!shooter.models.get(model).equals(this.models.get(model))){return false;}
            }
            return true;
        }
    }
    @Override
    public int hashCode(){
        int result = Objects.hashCode(mark);
        result = result * 31 + models.hashCode();
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Scooter scooter = (Scooter)super.clone();
        scooter.models = new HashMap<>(this.models.size());
        for(String name_model : this.models.keySet()){
            double price = this.models.get(name_model);
            scooter.models.put(name_model, price);
        }
        return scooter;
    }
}
