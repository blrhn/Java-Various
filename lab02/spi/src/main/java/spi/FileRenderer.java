package spi;

import javafx.scene.Node;

import java.util.List;

public interface FileRenderer {
    boolean supports(String renderType);
    Node render(List<String> lines, int maxLines);
}
