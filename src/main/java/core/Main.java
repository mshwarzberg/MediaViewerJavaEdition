package core;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tool.ExifToolScanner;
import tool.FileList;
import tool.FileMetadata;
import view.Viewer;
import window.MetadataWindow;

import java.io.File;
import java.net.URI;

public class Main extends Application {
    public static final int BAR_HEIGHT = 50;
    public static final BooleanProperty isFullscreen = new SimpleBooleanProperty(false);
    public static final Media EMPTY_VIDEO = new Media(new File("src/main/resources/empty.mp4").toURI().toString());
    private static File currentFile;
    private static Stage primaryStage;
    private static File mostRecentDirectory = null;
    private static FileList fileList;
    private static MetadataWindow childWindow;

    public static void main(String[] args) {
        launch(args);
    }

    public static void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp", "*.wbmp", "*.webp", "*.txt", "*.rtf", "*.mp4", "*.mkv", "*.avi"));
        fileChooser.setInitialDirectory(mostRecentDirectory);
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            currentFile = selectedFile;
            boolean isNewDirectory = mostRecentDirectory == null || !currentFile.getParentFile().getAbsolutePath().equals(mostRecentDirectory.getAbsolutePath());
            mostRecentDirectory = currentFile.getParentFile();
            if (isNewDirectory) {
                scanDirectoryForMedia();
            }
            displayFile(FileType.getByExtension(currentFile));
        }
    }

    private static void scanDirectoryForMedia() {
        Thread thread = new Thread(() -> {
            ExifToolScanner directoryScanner = new ExifToolScanner(mostRecentDirectory.getAbsolutePath());
            fileList = directoryScanner.scan();
            Navbar.INSTANCE.enableButtons();
        });
        thread.start();
    }

    private static void displayFile(FileType type) {
        primaryStage.setTitle(currentFile.getAbsolutePath());
        Viewer.INSTANCE.setSources(type, currentFile.toURI());
        URI fileURI = currentFile.toURI();
        Viewer.INSTANCE.requestFocus();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static File getCurrentFile() {
        return currentFile;
    }

    public static MetadataWindow getChildWindow() {
        return childWindow;
    }

    public static void setChildWindow(MetadataWindow childWindow) {
        Main.childWindow = childWindow;
    }

    public static void showMetadata() {
        FileMetadata metadata = fileList.getMetadata(currentFile);
        setChildWindow(new MetadataWindow(metadata));
    }

    public static void invertFullscreen() {
        isFullscreen.set(!isFullscreen.get());
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(new File("./src/main/resources/mediaicon.png").getAbsolutePath()));
        Main.primaryStage = primaryStage;
        Scene scene = new Scene(ContentViewPage.INSTANCE);
        scene.getStylesheets().add("style.css");
        primaryStage.setMaximized(true);
        primaryStage.setTitle("Media Viewer");
        primaryStage.setScene(scene);
        listeners();
        primaryStage.show();
    }

    private void listeners() {
        isFullscreen.addListener((observable, wasFullscreen, isValueFullscreen) -> {
            primaryStage.setFullScreen(isValueFullscreen);
            Navbar.INSTANCE.setVisible(!isValueFullscreen);
        });
    }

    public static class ContentViewPage extends BorderPane {
        public static ContentViewPage INSTANCE = new ContentViewPage();

        private ContentViewPage() {
            setBackground(Background.fill(Paint.valueOf("black")));
            setOnMouseClicked(event -> requestFocus());
            setOnKeyPressed(event -> {
                if (fileList != null) {
                    if (event.getCode() == KeyCode.LEFT) {
//                if (IMAGE.isType(getFileExtension(currentFile)) || event.isShiftDown()) {
                        File previousFile = fileList.getPrevious(currentFile);
                        if (previousFile != currentFile) {
                            currentFile = previousFile;
                            displayFile(FileType.getByExtension(currentFile));
                        }
//                }
                    } else if (event.getCode() == KeyCode.RIGHT) {
//                if (IMAGE.isType(getFileExtension(currentFile)) || event.isShiftDown()) {
                        File nextFile = fileList.getNext(currentFile);
                        if (nextFile != currentFile) {
                            currentFile = nextFile;
                            displayFile(FileType.getByExtension(currentFile));
                        }
//                }
                    }
                }
            });
            setCenter(Viewer.INSTANCE);
            setTop(Navbar.INSTANCE);
        }
    }
}