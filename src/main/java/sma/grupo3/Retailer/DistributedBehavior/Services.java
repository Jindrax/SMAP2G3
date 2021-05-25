package sma.grupo3.Retailer.DistributedBehavior;

import sma.grupo3.Retailer.Agents.Controller.ControllerAgent;

import java.util.*;

public class Services {
    private static ControllerAgent agent;
    private static final Map<String, Set<String>> globalDirectory = new Hashtable<>();
    private static final Map<Localities, Map<String, Set<String>>> directory = new Hashtable<Localities, Map<String, Set<String>>>();

    public static void bindToService(Localities locality, String id, String service) {
        synchronized (globalDirectory) {
            Set<String> gDirectory = globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
            gDirectory.add(id);
        }
        synchronized (directory) {
            Set<String> localityDirectory = directory.computeIfAbsent(locality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<String>()));
            localityDirectory.add(id);
            agent.notifyServiceUpdate(service, localityDirectory);
        }
    }

    public static void bindBatchToService(Localities locality, Set<String> ids, String service) {
        synchronized (globalDirectory) {
            Set<String> gDirectory = globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
            gDirectory.addAll(ids);
        }
        synchronized (directory) {
            Set<String> localityDirectory = directory.computeIfAbsent(locality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<String>()));
            localityDirectory.addAll(ids);
            agent.notifyServiceUpdate(service, localityDirectory);
        }
    }

    public static void unbindFromService(Localities locality, String id, String service) {
        synchronized (globalDirectory) {
            synchronized (directory) {
                if (globalDirectory.containsKey(service)) {
                    globalDirectory.get(service).remove(id);
                    if (directory.containsKey(locality)) {
                        if (directory.get(locality).containsKey(service)) {
                            directory.get(locality).get(service).remove(id);
                            agent.notifyServiceUpdate(service, directory.get(locality).get(service));
                        }
                    }
                }
            }
        }
    }

    public static List<String> getLocalityServiceProviders(Localities locality, String service) {
        List<String> localDirectory = new ArrayList<>(directory.computeIfAbsent(locality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> new HashSet<>()));
        Collections.shuffle(localDirectory);
        return localDirectory;
    }

    public static void updateServiceFromLocality(Localities locality, String service, Set<String> update) {
        synchronized (directory) {
            directory.computeIfAbsent(locality, k -> new Hashtable<>());
            directory.get(locality).put(service, update);
        }
        synchronized (globalDirectory) {
            globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
            Set<String> globalService = globalDirectory.get(service);
            globalService.addAll(update);
        }
    }

    public static List<String> getGlobalServiceProviders(String service) {
        List<String> localDirectory = new ArrayList<>(globalDirectory.get(service));
        Collections.shuffle(localDirectory);
        return localDirectory;
    }

    public static List<String> getNearbyServiceProviders(Localities locality, String service, int max) {
        List<String> nearbyProviders = Collections.synchronizedList(new ArrayList<>());
        synchronized (directory) {
            for (String provider : directory.get(locality).get(service)) {
                nearbyProviders.add(provider);
                if (nearbyProviders.size() == max) {
                    return nearbyProviders;
                }
            }
            for (Localities neighbor : ConnectionMap.getNeighbours(locality)) {
                for (String provider : directory.computeIfAbsent(neighbor, localities -> new Hashtable<>()).computeIfAbsent(service, s -> new HashSet<>())) {
                    nearbyProviders.add(provider);
                    if (nearbyProviders.size() == max) {
                        return nearbyProviders;
                    }
                }
            }
        }
        return nearbyProviders;
    }

    public static void setAgent(ControllerAgent _agent) {
        agent = _agent;
    }

}
