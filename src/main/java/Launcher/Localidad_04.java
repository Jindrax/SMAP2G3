package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class Localidad_04 {
    public static void main(String[] args) {
        Localities locality = Localities.ENGATIVA;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_04.xml", locality);
    }
}
