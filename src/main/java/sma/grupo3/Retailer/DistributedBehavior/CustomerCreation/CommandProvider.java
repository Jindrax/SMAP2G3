package sma.grupo3.Retailer.DistributedBehavior.CustomerCreation;

import java.util.concurrent.BlockingQueue;

public abstract class CommandProvider<T> implements Runnable {
    public abstract BlockingQueue<T> getStream();
}
