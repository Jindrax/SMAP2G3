package Launcher;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.System.AdmBESA;
import org.jgrapht.Graphs;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import sma.grupo3.Retailer.Agents.Distributed.Behavior.TestGuard;
import sma.grupo3.Retailer.Agents.Distributed.DistributedAgent;
import sma.grupo3.Retailer.Agents.Distributed.DistributedAgentState;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;

import java.util.ArrayList;
import java.util.List;


public class Localidad_Test {
    static ArrayList<WarehouseAgent> warehouses = new ArrayList<>();

    public static void main(String[] args) {
        AdmBESA admBESA = AdmBESA.getInstance("config/Container_00.xml");
        String testAgentAlias = "testAgent";
        DistributedAgent syncAgent = AgentFactory.agentInstance(DistributedAgent.class, testAgentAlias, 0.93, new DistributedAgentState("testProtocol"));
        if (syncAgent != null) {
            syncAgent.start();
            admBESA.registerAgent(syncAgent, testAgentAlias, testAgentAlias);
            EventBESA initialEvent = new EventBESA(TestGuard.class.getName(), null);
            try {
                admBESA.getHandlerByAlias(testAgentAlias).sendEvent(initialEvent);
                Services.setThisLocality(Localities.CHAPINERO);
                DijkstraShortestPath shortestPath = new DijkstraShortestPath(ConnectionMap.map);
                List<Localities> sp = shortestPath.getPath(Localities.ENGATIVA, Localities.CHAPINERO).getVertexList();
                for(Localities n: sp){
                    System.out.println(n);
                }
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }
}
