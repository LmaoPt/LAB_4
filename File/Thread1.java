public class Thread1 extends Thread{
    private Vehicle vehicle;
    public Thread1(Vehicle vehicle){
        this.vehicle = vehicle;
    }

    @Override
    public void run(){
        double[] prices = vehicle.getPrices();

        for(int i = 0; i < prices.length; i++){
            System.out.println(prices[i]);
        }

    }
}
