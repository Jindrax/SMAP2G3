package sma.grupo3.Retailer.DistributedBehavior;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceFinder {
    private final Map<Localities, Map<String, Set<String>>> directory;

    public ServiceFinder(Map<Localities, Map<String, Set<String>>> directory) {
        this.directory = directory;
    }

    List<String> getNearbyServiceProviders(Localities locality, String service, int max) {
        List<String> nearbyProviders = new ArrayList<>();
        Set<Localities> visitedLocalities = new HashSet<>();
        Set<Localities> lookupLocalities = new HashSet<Localities>();
        lookupLocalities.add(locality);
        while (visitedLocalities.size() < directory.keySet().size()) {
            for (Localities visitedLocality : lookupLocalities) {
                for (String provider : directory.get(visitedLocality).get(service)) {
                    nearbyProviders.add(provider);
                    if (nearbyProviders.size() == max) {
                        return nearbyProviders;
                    }
                }
                visitedLocalities.add(visitedLocality);
                lookupLocalities.addAll(ConnectionMap.getNeighbours(visitedLocality).stream().filter(localities -> localities.enable).collect(Collectors.toSet()));
            }
            lookupLocalities.removeAll(visitedLocalities);
        }
        return nearbyProviders;
    }
}
