package CLI;

import java.time.LocalDateTime;

public class Customer implements Runnable {

    private int customerRetrievalRate;
    private int customerTicketQuantity;
    private TicketPool ticketPool;
    private volatile boolean running = true;


    public Customer(int customerTicketQuantity, Configuration customerRetrievalRate, TicketPool ticketPool) {
        this.customerTicketQuantity = customerTicketQuantity;
        this.customerRetrievalRate = customerRetrievalRate.getCustomerRetrievalRate();
        this.ticketPool = ticketPool;
    }




    @Override
    public void run(){
        for(int i = 0; i < customerTicketQuantity && running ; i++){
            Ticket ticket = ticketPool.buyTicket();
            if(ticket != null){
                ticket.setPurchasedDateTime(LocalDateTime.now());
            }
            try{
                Thread.sleep(customerRetrievalRate * 1000);
            } catch (InterruptedException e) {
                if (!running)
                    break;
            }
        }
    }

    public void stopThread() {
        this.running = false; // Set the flag to false to stop the thread execution
    }
}
