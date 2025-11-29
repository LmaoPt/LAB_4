import java.util.*;

public class Quadbike implements Vehicle{
    private String mark;
    private ArrayList<Model> models = new ArrayList<>();

    public Quadbike(String mark, int size)  {
        this.mark = mark;
        if(size > 0){
            try {
                for (int i = 0; i < size; i++) {
                    addModel("Model " + (i + 1), 100000 * (i + 1));
                }
            }
            catch (DuplicateModelNameException e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void setMark(String mark){this.mark = mark;}

    @Override
    public String getMark(){return mark;}

    @Override
    public String[] getNamesOfModels() {
        String[] names = new String[getModelsLength()];
        for(int i = 0; i < models.size(); i++){
            names[i] = models.get(i).name;
        }
        return names;
    }

    @Override
    public double getPriceOfModelName(String name) throws NoSuchModelNameException {
        for (int i = 0; i < models.size(); i++) {
            if (name.equals(models.get(i).name)) {
                return models.get(i).price;
            }
        }
        throw new NoSuchModelNameException(name);
    }

    @Override
    public void setPriceOfModelName(String name, double new_price) throws NoSuchModelNameException {
        if (new_price <= 0) {
            throw new ModelPriceOutOfBoundsException("Цена должна быть больше 0!");
        }
        for (int i = 0; i < models.size(); i++) {
            if (name.equals(models.get(i).name)) {
                models.get(i).price = new_price;
                return;
            }
        }
        throw new NoSuchModelNameException(name);
    }

    @Override
    public double[] getPrices() {
        double[] prices = new double[getModelsLength()];
        for(int i = 0; i < prices.length; i++){
            prices[i] = models.get(i).price;
        }
        return prices;
    }

    @Override
    public void addModel(String name, double price) throws DuplicateModelNameException {
        if(price <= 0){
            throw new ModelPriceOutOfBoundsException("Цена модели должна быть больше 0!");
        }
        for (int i = 0; i < models.size(); i++) {
            if (name.equals(models.get(i).name)) {
                throw new DuplicateModelNameException(name);
            }
        }
        models.add(new Model(name, price));
    }

    @Override
    public void deleteModel(String name) throws NoSuchModelNameException {
        int index = -1;
        for (int i = 0; i < models.size(); i++) {
            if (name.equals(models.get(i).name)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            models.remove(index);
        } else {
            throw new NoSuchModelNameException(name);
        }
    }

    @Override
    public void setModelName(String oldName, String newName) throws NoSuchModelNameException, DuplicateModelNameException {
        int index = -1;
        for (int i = 0; i < models.size(); i++) {
            if (oldName.equals(models.get(i).name)) {
                index = i;
            }
            if (newName.equals(models.get(i).name)) {
                throw new DuplicateModelNameException("Дублирование: " + newName);
            }
        }
        if (index != -1) {
            models.get(index).name = newName;
        } else {
            throw new NoSuchModelNameException(oldName);
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
        else if(!(obj instanceof Quadbike)){return false;}
        else{
            Quadbike quadbike = (Quadbike) obj;
            if(!(Objects.equals(this.getMark(), quadbike.getMark()))){return false;}
            if(!(Objects.equals(this.getModelsLength(), quadbike.getModelsLength()))){return false;}

            String[] name = quadbike.getNamesOfModels();
            double[] prices = quadbike.getPrices();
            for(int i = 0; i < name.length; i++) {
                if (!Objects.equals(this.models.get(i).getName(), name[i])) {return false;}
                if (!Objects.equals(this.models.get(i).getPrice(), prices[i])) {return false;}
            }
            return true;
        }
    }
    @Override
    public int hashCode(){
        int result = mark.hashCode();
        result = result * 31 + Arrays.hashCode(getPrices());
        result = result * 31 + Arrays.hashCode(getNamesOfModels());
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Quadbike quadbike = (Quadbike) super.clone();
        quadbike.models = new ArrayList<>();
        for(int i = 0; i < this.models.size(); i++){
            Model model = this.models.get(i);
            quadbike.models.add(new Model(model.getName(), model.getPrice()));
        }
        return quadbike;
    }

    private class Model{
        private String name;
        private double price;

        public Model(String name, double price){
            this.name = name;
            this.price = price;
        }
        public void setName(String name){this.name = name;}
        public String getName(){return name;}
        public void setPrice(double price){this.price = price;}
        public double getPrice(){return price;}
    }
}
