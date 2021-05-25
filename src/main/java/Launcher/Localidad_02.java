package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.Utils.Configuration;

public class Localidad_02 {
    public static void main(String[] args) {
        String scenarioFile = Configuration.get("EXPERIMENT_SCENARIO_FILE");
        Localities locality = Localities.SUBA;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_02.xml", locality, scenarioFile);
    }
}
