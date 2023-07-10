package view;

import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DocumentViewer extends StackPane implements ViewController {
    public static final DocumentViewer INSTANCE = new DocumentViewer();
    private static final int WIDTH = 500;
    private static final int HEIGHT = 900;
    private final TextArea textArea = new TextArea();

    private DocumentViewer() {
        getChildren().add(textArea);
        setVisible(false);
        textArea.setMaxHeight(HEIGHT);
        textArea.setMaxWidth(WIDTH);
        textArea.setWrapText(true);
    }

    @Override
    public void clearSource() {
        setVisible(false);
    }

    @Override
    public void setSource(URI source) {
        setVisible(true);
        textArea.setText(readFile(source));
    }

    private String readFile(URI fileUri) {
        try {
            Path path = Paths.get(fileUri);
            byte[] bytes = Files.readAllBytes(path);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException ignored) {
            return null;
        }
    }
}
