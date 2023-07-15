package view;

import core.FileType;
import core.Main;
import javafx.scene.layout.StackPane;

import java.net.URI;

public class Viewer extends StackPane {
    public static final Viewer INSTANCE = new Viewer();

    private Viewer() {
        getChildren().addAll(ImageViewer.INSTANCE, VideoViewer.INSTANCE, DocumentViewer.INSTANCE);
        setTranslateY(-Main.BAR_HEIGHT);
    }

    public void setSources(FileType fileType, URI uri) {
        ImageViewer.INSTANCE.clearSource();
        VideoViewer.INSTANCE.clearSource();
        DocumentViewer.INSTANCE.clearSource();
        switch (fileType) {
            case IMAGE -> ImageViewer.INSTANCE.setSource(uri);
            case VIDEO -> VideoViewer.INSTANCE.setSource(uri);
            case DOCUMENT -> DocumentViewer.INSTANCE.setSource(uri);
        }
    }
}
