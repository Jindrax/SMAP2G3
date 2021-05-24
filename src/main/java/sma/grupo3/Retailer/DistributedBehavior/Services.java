package sma.grupo3.Retailer.DistributedBehavior;

import sma.grupo3.Retailer.Agents.Controller.ControllerAgent;

import java.util.*;
import java.util.stream.Collectors;

public class Services {
    private static ControllerAgent agent;
    private static final Map<String, Set<String>> globalDirectory = new Hashtable<>();
    private static final Map<Localities, Map<String, Set<String>>> directory = new Hashtable<Localities, Map<String, Set<String>>>();

    public static void bindToService(Localities locality, String id, String service) {
        Set<String> gDirectory = globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
        gDirectory.add(id);
        Set<String> localityDirectory = directory.computeIfAbsent(locality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<String>()));
        localityDirectory.add(id);
        agent.notifyServiceUpdate(service, localityDirectory);
    }

    public static void bindBatchToService(Localities locality, Set<String> ids, String service) {
        Set<String> gDirectory = globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
        gDirectory.addAll(ids);
        Set<String> localityDirectory = directory.computeIfAbsent(locality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<String>()));
        localityDirectory.addAll(ids);
        agent.notifyServiceUpdate(service, localityDirectory);
    }

    public static boolean unbindFromService(Localities locality, String id, String service) {
        if (globalDirectory.containsKey(service)) {
            globalDirectory.get(service).remove(id);
            if (directory.containsKey(locality)) {
                if (directory.get(locality).containsKey(service)) {
                    directory.get(locality).get(service).remove(id);
                    agent.notifyServiceUpdate(service, directory.get(locality).get(service));
                    return true;
                }
            }
        }
        return false;
    }

    public static List<String> getLocalityServiceProviders(Localities locality, String service) {
        List<String> localDirectory = new ArrayList<>(directory.computeIfAbsent(locality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> new HashSet<>()));
        Collections.shuffle(localDirectory);
        return localDirectory;
    }

    public static void updateServiceFromLocality(Localities locality, String service, Set<String> update) {
        directory.computeIfAbsent(locality, k -> new Hashtable<>());
        directory.get(locality).put(service, update);
        globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
        Set<String> globalService = globalDirectory.get(service);
        globalService.addAll(update);
    }

    public static List<String> getGlobalServiceProviders(String service) {
        List<String> localDirectory = new ArrayList<>(globalDirectory.get(service));
        Collections.shuffle(localDirectory);
        return localDirectory;
    }

    public static List<String> getNearbyServiceProviders(Localities locality, String service, int max) {
        List<String> nearbyProviders = new ArrayList<>();
        Set<Localities> visitedLocalities = new HashSet<>();
        Set<Localities> lookupLocalities = new HashSet<Localities>() {{
            add(locality);
        }};
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

    public static void setAgent(ControllerAgent _agent) {
        agent = _agent;
    }

}
