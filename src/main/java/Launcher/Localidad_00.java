package Launcher;

import BESA.Kernel.System.AdmBESA;
import Experiment.ExperimentalScenario;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.CustomerCreation.CustomerCreator;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.Statistics;
import sma.grupo3.Retailer.Utils.Configuration;
import sma.grupo3.Retailer.Utils.ConsoleRainbow;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Localidad_00 {
    public static void main(String[] args) {
        String scenarioFile = Configuration.get("EXPERIMENT_SCENARIO_FILE");
        Localities locality = Localities.CHAPINERO;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_00.xml", locality, scenarioFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(scenarioFile);
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            ExperimentalScenario scenario = (ExperimentalScenario) inputStream.readObject();
            ConsoleRainbow.good("Iniciando simulacion con " + scenario.getCustomersCount() + " clientes");
            Statistics.setMaxCustomers(scenario.getCustomersCount());
            CustomerCreator customerCreator = new CustomerCreator();
            customerCreator.startProduction(admBESA, scenario);
        } catch (ClassNotFoundException | InterruptedException | IOException e) {
            e.printStackTrace();
        }

    }
}