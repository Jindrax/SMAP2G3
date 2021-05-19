package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class Localidad_03 {
    public static void main(String[] args) {
        Localities locality = Localities.BARRIOSUNIDOS;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_03.xml", locality);
    }
}
