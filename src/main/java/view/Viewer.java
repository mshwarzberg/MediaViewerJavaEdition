package view;

import core.FileType;
import core.Main;
import javafx.scene.layout.StackPane;
import video.VideoContainer;

import java.net.URI;

public class Viewer extends StackPane {
    public static final Viewer INSTANCE = new Viewer();

    private Viewer() {
        getChildren().addAll(ImageViewer.INSTANCE, VideoContainer.INSTANCE);
        setTranslateY(-Main.BAR_HEIGHT);
        addKeyListeners();
    }

    public void setSources(FileType fileType, URI uri) {
        ImageViewer.INSTANCE.clearSource();
        VideoContainer.INSTANCE.clearSource();
        switch (fileType) {
            case IMAGE -> ImageViewer.INSTANCE.setSource(uri);
            case VIDEO -> VideoContainer.INSTANCE.setSource(uri);
        }
    }

    private void addKeyListeners() {
//        setOnMouseClicked(event -> {
//            MediaPlayer mediaPlayer = Video.INSTANCE.getMediaPlayer();
//            if (event.getButton() == MouseButton.BACK) {
//                Main.showMetadata();
//            }
//            if (event.getButton() == MouseButton.PRIMARY && mediaPlayer != null) {
//                System.out.println(mediaPlayer.getMedia().getSource());
//            }
//        });
    }
}
