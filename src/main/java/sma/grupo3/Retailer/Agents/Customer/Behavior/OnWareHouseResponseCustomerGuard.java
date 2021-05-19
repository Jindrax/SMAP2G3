package sma.grupo3.Retailer.Agents.Customer.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderWarehouseResponse;
import sma.grupo3.Retailer.SharedDomain.CustomerRating;
import sma.grupo3.Retailer.SharedDomain.Statistics;

public class OnWareHouseResponseCustomerGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerAgent agent = (CustomerAgent) this.agent;
        CustomerOrderWarehouseResponse response = (CustomerOrderWarehouseResponse) eventBESA.getData();
        if (response.isCanceled()){
            Statistics.registerFinalizedOrder(new CustomerRating(agent.getAid(), response.getOrder(), agent.rateOrder(response)));
            try {
                this.getAgent().getAdmLocal().killAgent(this.agent.getAid(), this.agent.getPassword());
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }
}
