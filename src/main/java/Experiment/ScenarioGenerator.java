package Experiment;

import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.Utils.Randomizer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;
import java.util.Map;

public class ScenarioGenerator {
    public static void main(String[] args) {
        int[] customersSet = new int[]{40, 60, 80, 100};
        int catalogLowerLimit = 20;
        int catalogUpperLimit = 50;
        int maxProductPerOrder = 10;
        long maxWaitForCostumer = 2000;
        try {
            for (int i : customersSet) {
                FileOutputStream fileOutputStream = new FileOutputStream(
                        String.format("./ExperimentalScenarios/ExperimentalScenario[%d]customers[%d]LCat[%d]UCat[%d]MaxCatPerOrder[%d]sleep.dat",
                                i,
                                catalogLowerLimit,
                                catalogUpperLimit,
                                maxProductPerOrder,
                                maxWaitForCostumer)
                );
                ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
                ExperimentalScenario experimentalScenario = new ExperimentalScenario(i);
                for (Localities locality : Localities.values()) {
                    Map<Catalog, Integer> stock = new Hashtable<>();
                    for (Catalog catalog : Catalog.values()) {
                        stock.put(catalog, Randomizer.randomInt(catalogLowerLimit, catalogUpperLimit));
                    }
                    experimentalScenario.setStockForLocality(locality, stock);
                }
                for (int j = 0; j < i; j++) {
                    long deltaFromPrevious = Randomizer.randomLong(maxWaitForCostumer);
                    Localities locality = Randomizer.randomLocality();
                    String alias = "Customer" + locality.value + j;
                    Catalog product = Randomizer.randomEnum(Catalog.class);
                    int quantity = Randomizer.randomInt(1, maxProductPerOrder);
                    CustomerSkeleton customerSkeleton = new CustomerSkeleton(deltaFromPrevious, locality, alias, product, quantity);
                    experimentalScenario.addCustomerToQueue(customerSkeleton);
                }
                outputStream.writeObject(experimentalScenario);
                outputStream.close();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}