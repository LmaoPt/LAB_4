public class Thread2 extends Thread{
    private Vehicle vehicle;
    public Thread2(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    @Override
    public void run(){
        String[] prices = vehicle.getNamesOfModels();

        for(int i = 0; i < prices.length; i++){
            System.out.println(prices[i]);
        }

    }
}
