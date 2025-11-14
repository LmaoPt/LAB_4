public class DuplicateModelNameException extends Exception {
    public DuplicateModelNameException(String modelName) {
        super("Модель '" + modelName + "' уже существует");
    }
}