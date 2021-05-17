package sma.grupo3.Retailer.DistributedBehavior;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import sma.grupo3.Retailer.Utils.Configuration;

import java.util.*;

public class ConnectionMap {

    static class EdgeConnection {
        public Localities destination;
        public double weight;
        private static final double scaleFactor = Configuration.getDouble("TIME_SCALE_FACTOR");

        public EdgeConnection(Localities destination, double weight) {
            this.destination = destination;
            this.weight = weight * scaleFactor;
        }
    }

    static Map<Localities, List<EdgeConnection>> directory = new HashMap<Localities, List<EdgeConnection>>() {{
        put(Localities.CHAPINERO, new ArrayList<EdgeConnection>() {{
            add(new EdgeConnection(Localities.USAQUEN, 1735000));
            add(new EdgeConnection(Localities.BARRIOSUNIDOS, 686666));
            add(new EdgeConnection(Localities.TEUSAQUILLO, 776667));
        }});
        put(Localities.USAQUEN, new ArrayList<EdgeConnection>() {{
            add(new EdgeConnection(Localities.CHAPINERO, 1735000));
            add(new EdgeConnection(Localities.SUBA, 1070000));
        }});
        put(Localities.SUBA, new ArrayList<EdgeConnection>() {{
            add(new EdgeConnection(Localities.USAQUEN, 1070000));
            add(new EdgeConnection(Localities.BARRIOSUNIDOS, 1775000));
            add(new EdgeConnection(Localities.ENGATIVA, 958333));
        }});
        put(Localities.ENGATIVA, new ArrayList<EdgeConnection>() {{
            add(new EdgeConnection(Localities.SUBA, 958333));
            add(new EdgeConnection(Localities.BARRIOSUNIDOS, 965000));
            add(new EdgeConnection(Localities.TEUSAQUILLO, 1275000));
        }});
        put(Localities.BARRIOSUNIDOS, new ArrayList<EdgeConnection>() {{
            add(new EdgeConnection(Localities.CHAPINERO, 686666));
            add(new EdgeConnection(Localities.SUBA, 1775000));
            add(new EdgeConnection(Localities.ENGATIVA, 965000));
            add(new EdgeConnection(Localities.TEUSAQUILLO, 386667));
        }});
        put(Localities.TEUSAQUILLO, new ArrayList<EdgeConnection>() {{
            add(new EdgeConnection(Localities.CHAPINERO, 776667));
            add(new EdgeConnection(Localities.BARRIOSUNIDOS, 386667));
            add(new EdgeConnection(Localities.ENGATIVA, 965000));
        }});
    }};

    public static Graph<Localities, DefaultWeightedEdge> map = initialize();

    private static Graph<Localities, DefaultWeightedEdge> initialize() {
        Graph<Localities, DefaultWeightedEdge> returnMap = new DefaultDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        for (Localities locality : directory.keySet()) {
            returnMap.addVertex(locality);
        }
        for (Localities locality : directory.keySet()) {
            for (EdgeConnection connection : directory.get(locality)) {
                DefaultWeightedEdge edge = returnMap.addEdge(locality, connection.destination);
                returnMap.setEdgeWeight(edge, connection.weight);
            }
        }
        return returnMap;
    }

    public Set<Localities> getNeighbours(Localities local) {
        return Graphs.neighborSetOf(ConnectionMap.map, local);
    }

    public double getTimeFromTo(Localities from, Localities to) {
        return map.getEdgeWeight(map.getEdge(from, to));
    }

}
