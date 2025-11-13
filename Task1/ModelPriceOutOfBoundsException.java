class ModelPriceOutOfBoundsException extends RuntimeException {
    public ModelPriceOutOfBoundsException(String message) {
        super("Задана неверная цена: " + message);
    }
}