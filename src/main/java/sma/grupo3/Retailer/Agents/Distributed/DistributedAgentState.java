package sma.grupo3.Retailer.Agents.Distributed;

import BESA.Kernel.Agent.StateBESA;

public class DistributedAgentState extends StateBESA {

    private String lookupAgent;

    public DistributedAgentState(String lookupAgent) {
        this.lookupAgent = lookupAgent;
    }

    public String getLookupAgent() {
        return lookupAgent;
    }

    public void setLookupAgent(String lookupAgent) {
        this.lookupAgent = lookupAgent;
    }
}
