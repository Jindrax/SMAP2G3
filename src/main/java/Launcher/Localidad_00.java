package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.CustomerCreation.CustomerCreator;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class Localidad_00 {
    public static void main(String[] args) {
        Localities locality = Localities.CHAPINERO;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_00.xml", locality);
        CustomerCreator customerCreator = new CustomerCreator();
        customerCreator.startProduction(admBESA);
    }
}