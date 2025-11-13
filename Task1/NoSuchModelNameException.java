public class NoSuchModelNameException extends Exception {
    public NoSuchModelNameException(String modelName) {
        super("Модель '" + modelName + "' не найдена");
    }
}