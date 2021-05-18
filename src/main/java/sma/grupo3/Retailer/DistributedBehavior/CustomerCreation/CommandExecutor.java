package sma.grupo3.Retailer.DistributedBehavior.CustomerCreation;

import BESA.Kernel.System.AdmBESA;

public abstract class CommandExecutor implements Runnable{
    public abstract void setAdm(AdmBESA adm);
}
