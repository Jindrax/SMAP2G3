package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.AdmBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterMovement;
import sma.grupo3.Retailer.Agents.Transporter.TransporterAgent;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.StandardMalfunction;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;
import sma.grupo3.Retailer.Utils.Configuration;
import sma.grupo3.Retailer.Utils.ConsoleRainbow;
import sma.grupo3.Retailer.Utils.ProbabilityModule;
import sma.grupo3.Retailer.Utils.Randomizer;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class OnMoveRequestTransporterGuard extends GuardBESA {

    private static final boolean cooperativeBehavior = Configuration.getBoolean("COOPERATIVE_BEHAVIOR");

    static class MovementStep extends TimerTask {

        TransporterMovement transporterMovement;
        AgHandlerBESA handlerBESA;

        public MovementStep(TransporterMovement transporterMovement, AgHandlerBESA handlerBESA) {
            this.transporterMovement = transporterMovement;
            this.handlerBESA = handlerBESA;
        }

        @Override
        public void run() {
            try {
                this.handlerBESA.sendEvent(
                        new EventBESA(OnMoveRequestTransporterGuard.class.getName(), transporterMovement)
                );
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }

    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransporterMovement movement = (TransporterMovement) eventBESA.getData();
        TransporterState state = (TransporterState) this.getAgent().getState();
        TransporterAgent agent = (TransporterAgent) this.getAgent();
        AdmBESA admBESA = agent.getAdmLocal();
        try {
            if (state.getCurrentLocality() != movement.getLocalityArrived()) {
                state.setCurrentLocality(movement.getLocalityArrived());
                Services.bindToService(state.getCurrentLocality(), agent.getAid(), StandardServices.TRANSPORTER.value);
            }
            List<TransportCommand> commandList = state.getCommandList();
            for (TransportCommand command : commandList) {
                if (command.isDelivery()) {
                    command.getOrder().addElapsedTime(movement.getMovementTimeCost(), true);
                } else {
                    command.getOrder().addElapsedTime(movement.getMovementTimeCost());
                }
            }
            for (TransportCommand command : commandList.stream()
                    .filter(command -> command.getDestination() == state.getCurrentLocality()).collect(Collectors.toList())) {
                EventBESA customerNotification = state.executeCommand(command);
                if (customerNotification != null) {
                    admBESA.getHandlerByAlias(command.getOrder().getCustomerAlias()).sendEvent(customerNotification);
                }
            }
            commandList.removeAll(
                    commandList.stream().filter(TransportCommand::isFulfilled).collect(Collectors.toList())
            );
            if (!commandList.isEmpty()) {
                //If a command is available the next destination is set
                TransportCommand nextCommand = commandList.get(0);
                List<Localities> nextDestination = ConnectionMap.shortestPath(state.getCurrentLocality(), nextCommand.getDestination());
                //If the next destination is the same, some command arrived while its was processing, so it stays until a next destination is commanded
                if (!nextDestination.isEmpty()) {
                    //Case: the transporter need to travel
                    resolveMovement(state, admBESA, nextDestination);
                } else {
                    //Case: the transporter stays in the same locality
                    awaitMovement(state, admBESA);
                }
            } else {
                //If there is not command available cycles in the same locality
                awaitMovement(state, admBESA);
            }
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }

    private void awaitMovement(TransporterState state, AdmBESA admBESA) throws ExceptionBESA {
        long timeCost = (long) ConnectionMap.getTimeFromTo(state.getCurrentLocality(), state.getCurrentLocality());
        state.scheduleMovement(
                new MovementStep(
                        new TransporterMovement(state.getCurrentLocality(), timeCost),
                        admBESA.getHandlerByAid(this.getAgent().getAid())),
                timeCost
        );
    }

    private void resolveMovement(TransporterState state, AdmBESA admBESA, List<Localities> nextDestination) throws ExceptionBESA {
        Services.unbindFromService(state.getCurrentLocality(), agent.getAid(), StandardServices.TRANSPORTER.value);
        if (ProbabilityModule.transporterMalfunction()) {
            malfunctionInMovement(state, admBESA);
        } else {
            scheduleMovement(state, admBESA, nextDestination);
        }
    }

    private void scheduleMovement(TransporterState state, AdmBESA admBESA, List<Localities> nextDestination) throws ExceptionBESA {
        long timeCost = (long) ConnectionMap.getTimeFromTo(state.getCurrentLocality(), nextDestination.get(0));
        state.scheduleMovement(
                new MovementStep(
                        new TransporterMovement(nextDestination.get(0), timeCost),
                        admBESA.getHandlerByAid(this.getAgent().getAid())),
                timeCost
        );
    }

    private void malfunctionInMovement(TransporterState state, AdmBESA admBESA) throws ExceptionBESA {
        StandardMalfunction malfunction = Randomizer.randomEnum(StandardMalfunction.class);
        if (cooperativeBehavior && false) {
            //TODO: Implementar comportamiento cooperativo en caso de que haya un malfuncionamiento en el camion
        } else {
            ConsoleRainbow.fail(String.format("[%s]: Entrando en reparacion por %d ms", this.getAgent().getAlias(), malfunction.time));
            state.scheduleMovement(
                    new MovementStep(
                            new TransporterMovement(state.getCurrentLocality(), malfunction.time),
                            admBESA.getHandlerByAid(this.getAgent().getAid())),
                    malfunction.time
            );
        }
    }
}
