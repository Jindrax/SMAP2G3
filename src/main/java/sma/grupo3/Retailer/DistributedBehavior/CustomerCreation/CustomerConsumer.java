package sma.grupo3.Retailer.DistributedBehavior.CustomerCreation;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;

import java.util.concurrent.BlockingQueue;

public class CustomerConsumer extends CommandExecutor{

    private final BlockingQueue<CustomerAgent> queue;
    private AdmBESA admBESA;

    public CustomerConsumer(BlockingQueue<CustomerAgent> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {

    }

    @Override
    public void setAdm(AdmBESA adm) {
        this.admBESA = adm;
    }
}
