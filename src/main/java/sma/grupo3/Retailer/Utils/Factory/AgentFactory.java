package sma.grupo3.Retailer.Utils.Factory;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AgentFactory {

    public static <T> T agentInstance(Class<T> agentClass, String alias, double passwd, StateBESA state) {
        String location = agentClass.getProtectionDomain().getCodeSource().getLocation().getPath() + agentClass.getCanonicalName().replace(".", "/");
        String behaviorLocation = location.substring(0, location.lastIndexOf("/")) + "/Behavior";
        String classpath = agentClass.getCanonicalName().substring(0, agentClass.getCanonicalName().lastIndexOf("."));
        List<Class<?>> behaviors = getBehaviors(behaviorLocation, classpath);
        StructBESA structBESA = new StructBESA();
        for (Class<?> guard : behaviors) {
            structBESA.bindGuard(guard);
        }
        try {
            Optional<Constructor<?>> constructor = Arrays.stream(agentClass.getConstructors()).findFirst();
            if(constructor.isPresent()){
                return (T) constructor.get().newInstance(alias, state, structBESA, passwd);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Class<?>> getBehaviors(String location, String classpath) {
        File folder = new File(location);
        File[] listOfFiles = folder.listFiles();

        List<Class<?>> behaviors = new ArrayList<>();

        if (listOfFiles != null) {
            for (File listOfFile : listOfFiles) {
                if (listOfFile.isFile()) {
                    String fileName = listOfFile.getName();
                    int dot = fileName.indexOf(".");
                    try {
                        behaviors.add(ClassLoader.getSystemClassLoader().loadClass(classpath + ".Behavior." + fileName.substring(0, dot)));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return behaviors;
    }

    public static Class<?> getState(String location, String classpath) {
        File folder = new File(location);
        File[] listOfFiles = folder.listFiles();
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
                if (fileName.endsWith("State")) {
                    try {
                        return ClassLoader.getSystemClassLoader().loadClass(classpath + fileName);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
