package ui.utils;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.function.Predicate;

// T - interfejsy z spi

public class ServiceLoaderHelper {
    private ServiceLoaderHelper() {}

    public static <T> Optional<T> getService(Class<T> serviceClass, Predicate<T> condition) {
        ServiceLoader<T> loader = ServiceLoader.load(serviceClass);

        for  (T service : loader) {
            if (condition.test(service)) {
                return Optional.of(service);
            }
        }

        return Optional.empty();
    }
}
