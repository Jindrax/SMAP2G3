package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.Utils.Configuration;

public class Localidad_01 {
    public static void main(String[] args) {
        String scenarioFile = Configuration.get("EXPERIMENT_SCENARIO_FILE");
        Localities locality = Localities.USAQUEN;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_01.xml", locality, scenarioFile);
    }
}
