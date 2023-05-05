package core;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;
import tool.ExifToolDirectoryScanner;
import tool.FileList;
import view.Viewer;
import window.MetadataWindow;

import java.io.File;

public class Main extends Application {
    private static File currentFile;
    private static Stage primaryStage;
    private static final Navbar navbar = new Navbar();
    private static File mostRecentDirectory = null;
    private static FileList fileList;
    private static MetadataWindow childWindow;
    public static final int BAR_HEIGHT = 30;

    public static void main(String[] args) {
        launch(args);
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
        primaryStage.show();
        navbar.setup();
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
            displayFile(FileType.getByExtension(getFileExtension(currentFile)));
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

    private static String getFileExtension(File selectedFile) {
        if (selectedFile == null) {
            return "";
        }
        return selectedFile.getName().substring(selectedFile.getName().lastIndexOf(".") + 1);
    }

    public static class ContentViewPage extends BorderPane {
        public static ContentViewPage INSTANCE = new ContentViewPage();
        private ContentViewPage() {
            setBackground(Background.fill(Paint.valueOf("black")));
            setOnMouseClicked(event -> requestFocus());
            setOnKeyPressed(event -> {
                FileType type = FileType.getByExtension(getFileExtension(currentFile));
                if (fileList != null) {
                    if (event.getCode() == KeyCode.LEFT) {
//                if (IMAGE.isType(getFileExtension(currentFile)) || event.isShiftDown()) {
                        File previousFile = fileList.getPrevious(currentFile);
                        if (previousFile != currentFile) {
                            currentFile = previousFile;
                            displayFile(type);
                        }
//                }
                    } else if (event.getCode() == KeyCode.RIGHT) {
//                if (IMAGE.isType(getFileExtension(currentFile)) || event.isShiftDown()) {
                        File nextFile = fileList.getNext(currentFile);
                        if (nextFile != currentFile) {
                            currentFile = nextFile;
                            displayFile(type);
                        }
//                }
                    }
                }
            });
            setTop(navbar);
            setCenter(Viewer.INSTANCE);
        }
    }

    private static void displayFile(FileType type) {
        primaryStage.setTitle(currentFile.getAbsolutePath());
        Viewer.INSTANCE.setSources(type, currentFile.toURI());
        Viewer.INSTANCE.requestFocus();
        navbar.toFront();
        navbar.setAlignment(Pos.BOTTOM_CENTER);
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