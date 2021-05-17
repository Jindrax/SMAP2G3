package Launcher;

import BESA.ExceptionBESA;
import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.Agents.Controller.ControllerAgent;
import sma.grupo3.Retailer.Agents.Controller.ControllerState;
import sma.grupo3.Retailer.Agents.Distributed.DistributedAgent;
import sma.grupo3.Retailer.Agents.Distributed.DistributedAgentState;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Localidad_00 {
    static ArrayList<WarehouseAgent> warehouses = new ArrayList<>();

    public static void main(String[] args) {
        AdmBESA admBESA = AdmBESA.getInstance("config/Container_00.xml");
        String agentAliasLooked = "syncAgent_01";
        String agentAlias = "syncAgent_00";
        DistributedAgent syncAgent = AgentFactory.agentInstance(DistributedAgent.class, agentAlias, 0.93, new DistributedAgentState(agentAliasLooked));
        ControllerAgent controllerAgent = AgentFactory.agentInstance(ControllerAgent.class, "controller" + Localities.CHAPINERO.value, 0.24, new ControllerState(Localities.CHAPINERO,
                new HashMap<Localities, String>() {{
                    put(Localities.USAQUEN, "controller" + Localities.USAQUEN.value);
                }}));
        if (syncAgent != null) {
            syncAgent.start();
            admBESA.registerAgent(syncAgent, agentAlias, agentAlias);
            if (controllerAgent != null) {
                controllerAgent.start();
                admBESA.registerAgent(controllerAgent, "controller" + Localities.CHAPINERO.value, "controller" + Localities.CHAPINERO.value);
            }
            syncAgent.checkReady();
        }
        for (int i = 0; i < 10; i++) {
            String alias = "Warehouse_" + i;
            WarehouseAgent agent = AgentFactory.agentInstance(WarehouseAgent.class, alias, 0.27, new WarehouseState());
            if (agent != null) {
                agent.start();
                admBESA.registerAgent(agent,
                        alias,
                        alias
                );
                warehouses.add(agent);
                if (i % 2 == 0) {
                    Services.bindToService(agent.getAid(), "Pares");
                } else {
                    Services.bindToService(agent.getAid(), "Impares");
                }
            }
        }
        try {
            Thread.sleep(3000);
            System.out.println("Imprimiendo impares");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        List<String> serviceProviders = Services.getGlobalServiceProviders("Impares");
        for (String provider : serviceProviders) {
            try {
                System.out.println(admBESA.getHandlerByAid(provider).getAlias());
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
        try {
            Thread.sleep(1000);
            System.out.println("Imprimiendo solo mis impares");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        serviceProviders = Services.getLocalityServiceProviders(Services.getThisLocality(), "Impares");
        for (String provider : serviceProviders) {
            try {
                System.out.println(admBESA.getHandlerByAid(provider).getAlias());
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }
}
