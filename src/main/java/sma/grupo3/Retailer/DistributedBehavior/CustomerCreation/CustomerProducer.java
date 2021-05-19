package sma.grupo3.Retailer.DistributedBehavior.CustomerCreation;

import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;
import sma.grupo3.Retailer.Agents.Customer.CustomerState;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.Utils.Configuration;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;
import sma.grupo3.Retailer.Utils.Randomizer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomerProducer extends CommandProvider<CustomerAgent> {

    private BlockingQueue<CustomerAgent> queue;
    private int customerCount;
    private final int maxCustomers;

    public CustomerProducer() {
        this.customerCount = 0;
        this.maxCustomers = Configuration.getInt("MAX_CUSTOMERS");
    }

    @Override
    public void run() {
        while (this.customerCount < this.maxCustomers) {
            Localities locality = Randomizer.randomLocality();
            CustomerState state = new CustomerState();
            CustomerAgent agent = AgentFactory.agentInstance(CustomerAgent.class, "Customer" + locality.value + customerCount, 0.8, state);
            if (agent != null) {
                CustomerOrder order = new CustomerOrder(locality, agent.getAlias(), Randomizer.randomEnum(Catalog.class), Randomizer.randomInt(1, Configuration.getInt("MAX_QUANTITY_ORDER")));
                state.setActiveOrder(order);
                try {
                    queue.put(agent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                customerCount++;
            }
            sleep(Randomizer.randomLong(Configuration.getLong("MAX_WAIT_UNTIL_NEXT_CUSTOMER")));
        }
    }

    public void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public BlockingQueue<CustomerAgent> getStream() {
        this.queue = new LinkedBlockingQueue<CustomerAgent>();
        return this.queue;
    }
}
