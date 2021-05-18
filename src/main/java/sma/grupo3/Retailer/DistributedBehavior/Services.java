package sma.grupo3.Retailer.DistributedBehavior;

import sma.grupo3.Retailer.Agents.Controller.ControllerAgent;

import java.util.*;

public class Services {
    private static ControllerAgent agent;
    private static Localities thisLocality;
    private static final Map<String, Set<String>> globalDirectory = new Hashtable<>();
    private static final Map<Localities, Map<String, Set<String>>> directory = new Hashtable<Localities, Map<String, Set<String>>>();

    public static Localities getThisLocality() {
        return thisLocality;
    }

    public static void setThisLocality(Localities thisLocality) {
        Services.thisLocality = thisLocality;
    }

    public static void bindToService(String id, String service) {
        Set<String> gDirectory = globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
        gDirectory.add(id);
        Set<String> localityDirectory = directory.computeIfAbsent(thisLocality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<String>()));
        localityDirectory.add(id);
        agent.notifyServiceUpdate(service, localityDirectory);
    }

    public static void bindBatchToService(Set<String> ids, String service) {
        Set<String> gDirectory = globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
        gDirectory.addAll(ids);
        Set<String> localityDirectory = directory.computeIfAbsent(thisLocality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<String>()));
        localityDirectory.addAll(ids);
        agent.notifyServiceUpdate(service, localityDirectory);
    }

    public static boolean unbindFromService(String id, String service) {
        if (globalDirectory.containsKey(service)) {
            globalDirectory.get(service).remove(id);
            if (directory.containsKey(thisLocality)) {
                if (directory.get(thisLocality).containsKey(service)) {
                    directory.get(thisLocality).get(service).remove(id);
                    agent.notifyServiceUpdate(service, directory.get(thisLocality).get(service));
                    return true;
                }
            }
        }
        return false;
    }

    public static Set<String> getLocalityServiceProviders(Localities locality, String service) {
        return directory.computeIfAbsent(locality, localities -> new Hashtable<>()).computeIfAbsent(service, s -> new HashSet<>());
    }

    public static void updateServiceFromLocality(Localities locality, String service, Set<String> update) {
        directory.computeIfAbsent(locality, k -> new Hashtable<>());
        directory.get(locality).put(service, update);
        globalDirectory.computeIfAbsent(service, s -> Collections.synchronizedSet(new HashSet<>()));
        Set<String> globalService = globalDirectory.get(service);
        globalService.addAll(update);
    }

    public static Set<String> getGlobalServiceProviders(String service) {
        return globalDirectory.get(service);
    }

    public static void setAgent(ControllerAgent _agent) {
        agent = _agent;
    }

}
