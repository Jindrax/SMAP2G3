package sma.grupo3.Retailer.Agents.Controller;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import sma.grupo3.Retailer.Agents.Controller.Behavior.OnEchoFromContainerControllerGuard;
import sma.grupo3.Retailer.Agents.Controller.Behavior.OnServiceUpdatedControllerGuard;
import sma.grupo3.Retailer.Agents.Controller.Data.EchoFromContainer;
import sma.grupo3.Retailer.Agents.Controller.Data.ServiceUpdateFromLocality;
import sma.grupo3.Retailer.Agents.Transporter.TransporterAgent;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.Utils.Factory.AgentFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerAgent extends AgentBESA {
    public ControllerAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {
        ControllerState state = (ControllerState) this.getState();
        Services.setThisLocality(state.getLocality());
        Services.setAgent(this);
    }

    @Override
    public void shutdownAgent() {

    }

    public void checkReady() {
        ControllerState state = (ControllerState) this.getState();
        Set<String> localitiesUnknownState = new HashSet<>(state.getKnownLocalities());
        boolean ready = false;
        AgHandlerBESA ah;
        while (!ready) {
            ReportBESA.info("Checking Containers");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ControllerAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                for (String locality : localitiesUnknownState) {
                    ah = this.getAdmLocal().getHandlerByAlias(locality);
                    EventBESA msj = new EventBESA(
                            OnEchoFromContainerControllerGuard.class.getName(),
                            new EchoFromContainer(Services.getThisLocality())
                    );
                    ah.sendEvent(msj);
                }
                ready = true;
            } catch (ExceptionBESA ex) {
                ReportBESA.info("Failed Check");
            }
        }
        ReportBESA.info("Successful Check");
    }

    public void deployWarehouse() {
        if (Services.getThisLocality().isThereWarehouse) {
            ControllerState state = (ControllerState) this.getState();
            WarehouseAgent warehouse = AgentFactory.agentInstance(WarehouseAgent.class,
                    Services.getThisLocality().value + "_" + StandardServices.WAREHOUSE,
                    0.5,
                    new WarehouseState());
            if (warehouse != null) {
                state.setWarehouse(warehouse);
                warehouse.start();
                Services.bindToService(warehouse.getAid(), StandardServices.WAREHOUSE.value);
            }
        }
    }

    public void deployFleet() {
        ControllerState state = (ControllerState) this.getState();
        Set<String> batchIds = new HashSet<>();
        for (int i = 0; i < Services.getThisLocality().fleetSize; i++) {
            TransporterAgent transporterAgent = AgentFactory.agentInstance(TransporterAgent.class,
                    Services.getThisLocality().value + "_" + StandardServices.TRANSPORTER + "_" + i,
                    0.6,
                    new TransporterState());
            if (transporterAgent != null) {
                state.addTransporterToFleet(transporterAgent);
                transporterAgent.start();
                batchIds.add(transporterAgent.getAid());
            }
        }
        Services.bindBatchToService(batchIds, StandardServices.TRANSPORTER.value);
        Services.bindBatchToService(batchIds, Services.getThisLocality().value + StandardServices.TRANSPORTER.value);
    }

    public void notifyServiceUpdate(String service, Set<String> update) {
        ControllerState state = (ControllerState) this.getState();
        EventBESA eventBESA = new EventBESA(OnServiceUpdatedControllerGuard.class.getName(), new ServiceUpdateFromLocality(Services.getThisLocality(), service, update));
        for (String aliasLocality : state.getKnownLocalities()) {
            try {
                this.getAdmLocal().getHandlerByAlias(aliasLocality).sendEvent(eventBESA);
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }
}
