package lib.person.parser;

import javafx.scene.Node;
import javafx.scene.control.TextArea;
import spi.FileRenderer;

import java.util.List;

public class PersonFileTextRenderer implements FileRenderer {
    @Override
    public boolean supports(String renderType) {
        return renderType.equals("text");
    }

    @Override
    public Node render(List<String> lines, int maxLines) {
        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        lines.stream().limit(maxLines).forEach(line -> textArea.appendText(line + "\n"));

        return textArea;
    }
}
