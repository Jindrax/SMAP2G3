package sma.grupo3.Retailer.SharedDomain;

import sma.grupo3.Retailer.Utils.Configuration;
import sma.grupo3.Retailer.Utils.ConsoleRainbow;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistics {
    private static final List<CustomerRating> customerRatings = Collections.synchronizedList(new ArrayList<CustomerRating>());
    private static final List<CustomerOrder> finishedOrders = Collections.synchronizedList(new ArrayList<CustomerOrder>());
    private static int customersRegistered = 0;
    private static int maxCustomers = 0;

    public static void setMaxCustomers(int maxCustomers) {
        Statistics.maxCustomers = maxCustomers;
    }

    public static void registerFinalizedOrder(CustomerRating rating) {
        finishedOrders.add(rating.getCustomerOrder());
        customerRatings.add(rating);
        customersRegistered++;
        if (customersRegistered == maxCustomers) {
            getResults();
        }
    }


    private static void getResults() {
        double averageRating = 0;
        double averageTime = 0;
        if (!customerRatings.isEmpty()) {
            averageRating = customerRatings.stream().mapToDouble(CustomerRating::getCustomerSatisfaction).reduce(Double::sum).orElse(0.0) / customerRatings.size();
        }
        if (!finishedOrders.isEmpty()) {
            averageTime = finishedOrders.stream().mapToDouble(CustomerOrder::getTotalElapsedTime).reduce(Double::sum).orElse(0.0) / finishedOrders.size();
        }
        ConsoleRainbow.success(String.format("Average customer rating for this simulation with %d customers was: %f", customersRegistered, averageRating));
        ConsoleRainbow.success(String.format("Average time to finish an order for this simulation with %d customers was: %f", customersRegistered, averageTime));
        try {
            PrintWriter printer = new PrintWriter(new FileWriter("./ExperimentalResults.txt", true));
            boolean cooperativeBehavior = Configuration.getBoolean("COOPERATIVE_BEHAVIOR");
            if (cooperativeBehavior) {
                printer.write(String.format("Statistics for experiment with cooperative behavior in %d customers:\n", customersRegistered));
            } else {
                printer.write(String.format("Statistics for experiment with non cooperative behavior in %d customers:\n", customersRegistered));
            }
            printer.write(String.format("\tAverage customer rating: %f\n", averageRating));
            printer.write(String.format("\tAverage time to finish: %f\n", averageTime));
            printer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
