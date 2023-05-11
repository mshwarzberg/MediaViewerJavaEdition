package core;

import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import tool.MyButton;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Navbar extends HBox {
    public static final Navbar INSTANCE = new Navbar();
    private final MyButton openHere = new MyButton("Open File");
    private final MyButton openWithExplorer = new MyButton("Open File in Explorer");
    private final MyButton showMetadata = new MyButton("Show Metadata");

    private Navbar() {
        setSpacing(5);
        getChildren().addAll(openHere, openWithExplorer, showMetadata);
        setPadding(new Insets(10));
        setHeight(Main.BAR_HEIGHT);
        openHere.setOnAction(event -> Main.selectFile());
        openWithExplorer.setOnAction(event -> openWithExplorer(Main.getCurrentFile()));
        openWithExplorer.setDisable(true);
        showMetadata.setDisable(true);
        showMetadata.setOnAction(event -> Main.showMetadata());
    }

    private void openWithExplorer(File currentFile) {
        if (currentFile != null) {
            try {
                File parentFile = currentFile.getParentFile();
                Desktop.getDesktop().open(parentFile);
                if (Desktop.isDesktopSupported() && parentFile.exists()) {
                    Desktop.getDesktop().open(parentFile);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void enableButtons() {
        openWithExplorer.setDisable(false);
        showMetadata.setDisable(false);
    }
}
