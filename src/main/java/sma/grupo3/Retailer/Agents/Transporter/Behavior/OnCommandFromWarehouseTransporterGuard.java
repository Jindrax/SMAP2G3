package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterCommandFromWarehouse;

public class OnCommandFromWarehouseTransporterGuard extends GuardBESA {

    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransporterCommandFromWarehouse command = (TransporterCommandFromWarehouse) eventBESA.getData();
        TransporterState state = (TransporterState) this.getAgent().getState();
        state.addCommand(command.getCommand());
    }
}
