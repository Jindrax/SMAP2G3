package Experiment;

import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Catalog;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;

public class ExperimentalScenario implements Serializable {
    private final int customersCount;
    private final Map<Localities, Map<Catalog, Integer>> stockForLocality;
    private final Queue<CustomerSkeleton> customerQueue;


    public ExperimentalScenario(int customersCount) {
        this.customersCount = customersCount;
        this.stockForLocality = new Hashtable<>();
        this.customerQueue = new ArrayDeque<>();
    }

    public void setStockForLocality(Localities locality, Map<Catalog, Integer> stock) {
        this.stockForLocality.put(locality, stock);
    }

    public void addCustomerToQueue(CustomerSkeleton customer) {
        this.customerQueue.add(customer);
    }

    public int getCustomersCount() {
        return customersCount;
    }

    public Map<Catalog, Integer> getStockForLocality(Localities locality) {
        return stockForLocality.get(locality);
    }

    public Queue<CustomerSkeleton> getCustomerQueue() {
        return customerQueue;
    }
}
