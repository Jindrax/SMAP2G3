package Launcher;

import BESA.Kernel.System.AdmBESA;
import DistributedAgent.DistributedAgent;
import DistributedAgent.DistributedAgentState;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;

import java.util.ArrayList;
import java.util.Iterator;

public class Localidad_01 {
    static ArrayList<WarehouseAgent> warehouses = new ArrayList<>();

    public static void main(String[] args) {
        AdmBESA admBESA = AdmBESA.getInstance("config/Container_01.xml");
        String agentAliasLooked = "syncAgent_00";
        String agentAlias = "syncAgent_01";
        DistributedAgent syncAgent = AgentFactory.agentInstance(DistributedAgent.class, agentAlias, 0.93, new DistributedAgentState(agentAliasLooked));
        if (syncAgent != null) {
            syncAgent.start();
            admBESA.registerAgent(syncAgent, agentAlias, agentAlias);
            syncAgent.checkReady();
        }
        admBESA.addService("Pares");
        admBESA.addService("Impares");
        for (int i = 0; i < 10; i++) {
            if (i % 2 != 0) {
                String alias = "Warehouse_" + i;
                WarehouseAgent agent = AgentFactory.agentInstance(WarehouseAgent.class, alias, 0.27, new WarehouseState());
                if (agent != null) {
                    agent.start();
                    admBESA.bindService(agent.getAid(), "Impares");
                    admBESA.registerAgent(agent,
                            alias,
                            alias
                    );
                    warehouses.add(agent);
                }
            }
        }
        try {
            Thread.sleep(3000);
            System.out.println("Imprimiendo impares");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Iterator serviceProviders = admBESA.searchAidByService("Pares");
        for (Iterator it = serviceProviders; it.hasNext(); ) {
            String serviceProviderId = (String) it.next();
            System.out.println(serviceProviderId);
        }
    }
}
