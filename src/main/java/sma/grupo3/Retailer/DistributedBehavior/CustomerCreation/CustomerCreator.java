package sma.grupo3.Retailer.DistributedBehavior.CustomerCreation;

import BESA.Kernel.System.AdmBESA;
import Experiment.CustomerSkeleton;
import Experiment.ExperimentalScenario;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;
import sma.grupo3.Retailer.Agents.Customer.CustomerState;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;


public class CustomerCreator {

    static class ConsumerThread implements Runnable {
        private int costumersConsumed;
        private final AdmBESA admBESA;
        private final ExperimentalScenario scenario;

        public ConsumerThread(AdmBESA admBESA, ExperimentalScenario scenario) {
            this.costumersConsumed = 0;
            this.admBESA = admBESA;
            this.scenario = scenario;
        }

        @Override
        public void run() {
            while (costumersConsumed < scenario.getCustomersCount()) {
                CustomerSkeleton skeleton = scenario.getCustomerQueue().poll();
                if (skeleton != null) {
                    CustomerState state = new CustomerState();
                    CustomerAgent agent = AgentFactory.agentInstance(CustomerAgent.class, skeleton.getAlias(), 0.8, state);
                    if (agent != null) {
                        CustomerOrder order = new CustomerOrder(skeleton.getLocality(), agent.getAlias(), skeleton.getRequiredProduct(), skeleton.getRequiredQuantity());
                        state.setActiveOrder(order);
                        agent.start();
                        admBESA.registerAgent(agent, agent.getAlias(), agent.getAlias());
                        costumersConsumed++;
                    }
                    sleep(skeleton.getDeltaFromPrevious());
                }
            }
        }

        public void sleep(long millis) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private Thread consumerThread;

    public CustomerCreator() {
    }

    public void startProduction(AdmBESA admBESA, ExperimentalScenario scenario) throws InterruptedException {
        consumerThread = new Thread(new ConsumerThread(admBESA, scenario));
        consumerThread.start();
    }

    public void stopProduction() {
        consumerThread.interrupt();
    }
}