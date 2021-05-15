package Launcher;

import BESA.ExceptionBESA;
import BESA.Kernel.System.AdmBESA;
import DistributedAgent.DistributedAgent;
import DistributedAgent.DistributedAgentState;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;

import java.util.ArrayList;
import java.util.Iterator;


public class Localidad_00 {
    static ArrayList<WarehouseAgent> warehouses = new ArrayList<>();

    public static void main(String[] args) {
        AdmBESA admBESA = AdmBESA.getInstance("config/Container_00.xml");
        String agentAliasLooked = "syncAgent_01";
        String agentAlias = "syncAgent_00";
        DistributedAgent syncAgent = AgentFactory.agentInstance(DistributedAgent.class, agentAlias, 0.93, new DistributedAgentState(agentAliasLooked));
        if (syncAgent != null) {
            syncAgent.start();
            admBESA.registerAgent(syncAgent, agentAlias, agentAlias);
            syncAgent.checkReady();
        }
        admBESA.addService("Pares");
        admBESA.addService("Impares");
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                String alias = "Warehouse_" + i;
                WarehouseAgent agent = AgentFactory.agentInstance(WarehouseAgent.class, alias, 0.27, new WarehouseState());
                if (agent != null) {
                    agent.start();
                    admBESA.bindService(agent.getAid(), "Pares");

                    admBESA.registerAgent(agent,
                            alias,
                            alias
                    );
                    warehouses.add(agent);
                }
            }
        }
        try {
            System.out.println(admBESA.getHandlerByAid(admBESA.lookupSPServiceInDirectory("Impares")).getAlias());
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
        try {
            Thread.sleep(3000);
            System.out.println("Imprimiendo impares");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterator serviceProviders = admBESA.searchAidByService("Impares");
        for (Iterator it = serviceProviders; it.hasNext(); ) {
            String serviceProviderId = (String) it.next();
            System.out.println(serviceProviderId);
        }
    }
}
