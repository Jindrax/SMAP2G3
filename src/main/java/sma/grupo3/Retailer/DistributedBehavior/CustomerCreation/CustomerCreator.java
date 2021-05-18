package sma.grupo3.Retailer.DistributedBehavior.CustomerCreation;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;

import java.util.concurrent.BlockingQueue;

public class CustomerCreator {

    private final BlockingQueue<CustomerAgent> queue;
    private final CommandProvider<CustomerAgent> producer;
    private Thread producerThread;

    public CustomerCreator() {
        this.producer = new CustomerProducer();
        this.queue = this.producer.getStream();
    }

    public void startProduction(AdmBESA admBESA) {
        this.producerThread = new Thread(this.producer);
        this.producerThread.start();
        while (true) {
            CustomerAgent agent = null;
            try {
                agent = this.queue.take();
                agent.start();
                admBESA.registerAgent(agent, agent.getAlias(), agent.getAlias());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
