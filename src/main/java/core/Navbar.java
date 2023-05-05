package core;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import tool.FileList;
import tool.FileMetadata;
import tool.MyButton;
import window.MetadataWindow;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Navbar extends HBox {
    private final MyButton openHere = new MyButton("Open File");
    private final MyButton openWithExplorer = new MyButton("Open File in Explorer");
    private final MyButton showMetadata = new MyButton("Show Metadata");

    public Navbar() {
        setSpacing(5);
        getChildren().addAll(openHere, openWithExplorer, showMetadata);
        setAlignment(Pos.CENTER);
        setFillHeight(true);
    }

    public void setup() {
        openHere.setOnAction(event -> Main.selectFile());
        openWithExplorer.setOnAction(event -> openWithExplorer(Main.getCurrentFile()));
        openWithExplorer.setDisable(true);
        showMetadata.setDisable(true);
        showMetadata.setOnAction(event -> showMetadata());
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

    private void showMetadata() {
        FileList fileList = Main.getFileList();
        FileMetadata metadata = fileList.getMetadata(Main.getCurrentFile());
        Main.setChildWindow(new MetadataWindow(metadata));
    }
}
