package sma.grupo3.Retailer.DistributedBehavior;

import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.Agents.Controller.ControllerAgent;
import sma.grupo3.Retailer.Agents.Controller.ControllerState;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ContainerDeployer {
//    public static AdmBESA deploy(String containerConfig, Localities locality, Map<Localities, String> knownLocalities) {
//        AdmBESA admBESA = AdmBESA.getInstance(containerConfig);
//        ControllerAgent controllerAgent = AgentFactory.agentInstance(ControllerAgent.class, locality.value, 0.24,
//                new ControllerState(locality, knownLocalities));
//        if (controllerAgent != null) {
//            admBESA.registerAgent(controllerAgent, locality.value, locality.value);
//            controllerAgent.start();
//            controllerAgent.checkReady();
//            System.out.println("Comprobados containers");
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            controllerAgent.deployWarehouse();
//            controllerAgent.deployFleet();
//            for (String tId : Services.getGlobalServiceProviders(StandardServices.TRANSPORTER.value)) {
//                System.out.println(tId);
//            }
//        }
//        return admBESA;
//    }
}
