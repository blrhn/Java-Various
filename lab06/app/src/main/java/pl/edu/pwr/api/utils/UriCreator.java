package pl.edu.pwr.api.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public final class UriCreator {
    private UriCreator() {}

    public static URI createURIWithID(UUID id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }
}
