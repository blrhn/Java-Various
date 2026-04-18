package ui.utils;

import ex.api.AnalysisService;

import java.util.Optional;
import java.util.ServiceLoader;

public final class AnalysisServiceLoader {
    private AnalysisServiceLoader() {}

    public static Optional<AnalysisService> loadAnalysisService(String algorithm) {
        ServiceLoader<AnalysisService> loader = ServiceLoader.load(AnalysisService.class);

        for (AnalysisService service : loader) {
            if (service.getName().equals(algorithm)) {
                return Optional.of(service);
            }
        }

        return Optional.empty();
    }
}
