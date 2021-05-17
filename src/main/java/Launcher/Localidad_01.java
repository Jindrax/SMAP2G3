package Launcher;

import BESA.ExceptionBESA;
import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.Agents.Controller.ControllerAgent;
import sma.grupo3.Retailer.Agents.Controller.ControllerState;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Localidad_01 {
    public static void main(String[] args) {
        AdmBESA admBESA = AdmBESA.getInstance("config/Container_01.xml");
        Localities locality = Localities.USAQUEN;
        ControllerAgent controllerAgent = AgentFactory.agentInstance(ControllerAgent.class,
                locality.value,
                0.24,
                new ControllerState(locality,
                        Collections.synchronizedList(new ArrayList<String>() {{
                            add(Localities.CHAPINERO.value);
                        }})));
        if (controllerAgent != null) {
            admBESA.registerAgent(controllerAgent, locality.value, locality.value);
            controllerAgent.start();
            ControllerState state = (ControllerState) controllerAgent.getState();
            System.out.println(state.getKnownLocalities());
            controllerAgent.checkReady();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            controllerAgent.deployWarehouse();
            controllerAgent.deployFleet();
            for (String tId : Services.getGlobalServiceProviders(StandardServices.TRANSPORTER.value)) {
                try {
                    System.out.println(admBESA.getHandlerByAid(tId).getAlias());
                } catch (ExceptionBESA exceptionBESA) {
                    exceptionBESA.printStackTrace();
                }
            }
        }
    }
}
