package sma.grupo3.Retailer.DistributedBehavior.CustomerCreation;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;
import sma.grupo3.Retailer.Utils.Configuration;

import java.util.concurrent.BlockingQueue;

public class CustomerCreator {

    private final BlockingQueue<CustomerAgent> queue;
    private final CommandProvider<CustomerAgent> producer;
    private int costumersConsumed;
    private final int maxCostumers;
    private Thread producerThread;

    public CustomerCreator() {
        this.producer = new CustomerProducer();
        this.queue = this.producer.getStream();
        this.maxCostumers = Configuration.getInt("MAX_CUSTOMERS");
        this.costumersConsumed = 0;
    }

    public void startProduction(AdmBESA admBESA) {
        this.producerThread = new Thread(this.producer);
        this.producerThread.start();
        while (costumersConsumed < maxCostumers) {
            CustomerAgent agent = null;
            try {
                agent = this.queue.take();
                agent.start();
                admBESA.registerAgent(agent, agent.getAlias(), agent.getAlias());
                costumersConsumed++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
