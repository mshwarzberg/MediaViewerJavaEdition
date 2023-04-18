package core;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import tool.ExifToolDirectoryScanner;
import tool.FileList;
import view.ImageViewer;
import view.VideoPlayer;
import window.MetadataWindow;

import java.io.File;

import static core.FileType.*;

public class Main extends Application {
    private static final StackPane stackPane = new StackPane();
    private static File currentFile;
    private static Stage primaryStage;
    private static final Navbar navbar = new Navbar();
    private static final VideoPlayer videoPlayer = new VideoPlayer();
    private static final ImageViewer imageView = new ImageViewer();
    private static File mostRecentDirectory = null;
    private static FileList fileList;
    private static MetadataWindow childWindow;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;
        setupStackPane();
        Scene scene = new Scene(stackPane);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Media Viewer");
        stackPane.setBackground(Background.fill(Paint.valueOf("black")));
        primaryStage.setScene(scene);
        primaryStage.show();
        stackPane.getChildren().add(navbar);
    }

    public static void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(mostRecentDirectory);
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            currentFile = selectedFile;
            boolean isNewDirectory = mostRecentDirectory == null || !currentFile.getParentFile().getAbsolutePath().equals(mostRecentDirectory.getAbsolutePath());
            mostRecentDirectory = currentFile.getParentFile();
            if (isNewDirectory) {
                scanDirectoryForMedia();
            }
            displayFile();
        }
    }

    private static void scanDirectoryForMedia() {
        Thread thread = new Thread(() -> {
            ExifToolDirectoryScanner directoryScanner = new ExifToolDirectoryScanner(mostRecentDirectory.getAbsolutePath());
            fileList = directoryScanner.scan();
            navbar.enableButtons();
        });
        thread.start();
    }

    private static void clearActiveMedia() {
        videoPlayer.clearSource();
        imageView.clearSource();
    }

    private static String getFileExtension(File selectedFile) {
        if (selectedFile == null) {
            return "";
        }
        return selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
    }

    private static void setupStackPane() {
        stackPane.setOnMouseClicked(event -> stackPane.requestFocus());
        stackPane.setOnKeyPressed(event -> {
            if (fileList != null) {
                if (event.getCode() == KeyCode.LEFT) {
//                if (IMAGE.isType(getFileExtension(currentFile)) || event.isShiftDown()) {
                    File previousFile = fileList.getPrevious(currentFile);
                    if (previousFile != currentFile) {
                        currentFile = previousFile;
                        displayFile();
                    }
//                }
                } else if (event.getCode() == KeyCode.RIGHT) {
//                if (IMAGE.isType(getFileExtension(currentFile)) || event.isShiftDown()) {
                    File nextFile = fileList.getNext(currentFile);
                    if (nextFile != currentFile) {
                        currentFile = nextFile;
                        displayFile();
                    }
//                }
                }
            }
        });
    }

    private static void displayFile() {
        primaryStage.setTitle(currentFile.getAbsolutePath());
        clearActiveMedia();
        if (IMAGE.isType(getFileExtension(currentFile))) {
            imageView.setImageSource(currentFile.toURI());
        }
        if (VIDEO.isType(getFileExtension(currentFile))) {
            videoPlayer.setVideoSource(currentFile.toURI());
        }
        if (DOCUMENT.isType(getFileExtension(currentFile))) {
            System.out.println();
        }
        stackPane.requestFocus();
    }

    public static StackPane getStackPane() {
        return stackPane;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static File getCurrentFile() {
        return currentFile;
    }

    @Nullable
    public static FileList getFileList() {
        return fileList;
    }

    public static HBox getNavbar() {
        return navbar;
    }

    public static MetadataWindow getChildWindow() {
        return childWindow;
    }

    public static void setChildWindow(MetadataWindow childWindow) {
        Main.childWindow = childWindow;
    }
}