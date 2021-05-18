package Launcher;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.DistributedBehavior.ContainerDeployer;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Localidad_05 {
    public static void main(String[] args) {
        Localities locality = Localities.TEUSAQUILLO;
        AdmBESA admBESA = ContainerDeployer.deploy("config/Container_05.xml", locality,
                Arrays.stream(Localities.values()).filter(localities -> locality != localities).map(localities -> localities.value).collect(Collectors.toSet())
        );
    }
}
