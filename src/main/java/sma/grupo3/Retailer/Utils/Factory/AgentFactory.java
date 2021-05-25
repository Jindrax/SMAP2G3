package sma.grupo3.Retailer.Utils.Factory;

import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

public class AgentFactory {

    private static final Reflections reflections = new Reflections("sma.grupo3.Retailer");

    public static <T> T agentInstance(Class<T> agentClass, Class<? extends Annotation> guardAnnotation, String alias, double passwd, StateBESA state) {
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(guardAnnotation);
        StructBESA structBESA = new StructBESA();
        for (Class<?> guard : annotated) {
            structBESA.bindGuard(guard);
        }
        try {
            Optional<Constructor<?>> constructor = Arrays.stream(agentClass.getConstructors()).findFirst();
            if (constructor.isPresent()) {
                return (T) constructor.get().newInstance(alias, state, structBESA, passwd);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
