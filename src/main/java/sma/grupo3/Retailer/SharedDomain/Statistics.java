package sma.grupo3.Retailer.SharedDomain;

import sma.grupo3.Retailer.Utils.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Statistics {
    private static final List<CustomerRating> customerRatings = Collections.synchronizedList(new ArrayList<CustomerRating>());
    private static int customersRegistered = 0;
    private static final int maxCustomers = Configuration.getInt("MAX_CUSTOMERS");

    public static void registerFinalizedOrder(CustomerRating rating) {
        customerRatings.add(rating);
        customersRegistered++;
        if (customersRegistered == maxCustomers) {
            getResults();
        }
    }

    private static void getResults() {
        double averageRating = 0;
        if (!customerRatings.isEmpty()) {
            averageRating = customerRatings.stream().mapToDouble(CustomerRating::getCustomerSatisfaction).reduce(Double::sum).orElse(0.0) / customerRatings.size();
        }
        System.out.println("El promedio de satisfaccion del cliente para esta simulacion fue de: " + averageRating);
    }
}
