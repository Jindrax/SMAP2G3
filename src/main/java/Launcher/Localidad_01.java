package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class Localidad_01 {
    public static void main(String[] args) {
        Localities locality = Localities.USAQUEN;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_01.xml", locality);
    }
}
