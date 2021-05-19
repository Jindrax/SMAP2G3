package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class Localidad_05 {
    public static void main(String[] args) {
        Localities locality = Localities.TEUSAQUILLO;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_05.xml", locality);
    }
}
