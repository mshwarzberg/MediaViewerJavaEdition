package core;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import view.ImageViewer;

public class ResizeToScreen {
    private static final Stage primaryStage = Main.getPrimaryStage();

    public static void setDimensions(BorderPane pane) {
        pane.setPrefWidth(primaryStage.getWidth());
        pane.setPrefHeight(primaryStage.getHeight());
    }

    public static void setDimensions(VBox videoContainer) {
        videoContainer.setPrefWidth(primaryStage.getWidth());
        videoContainer.setPrefHeight(primaryStage.getHeight());
    }

    public static void setDimensions(ImageViewer imageViewer) {
        imageViewer.setPreserveRatio(true);
        imageViewer.fitWidthProperty().bind(primaryStage.widthProperty());
        imageViewer.fitHeightProperty().bind(primaryStage.heightProperty());
    }

    public static void setDimensions(MediaView mediaView, MediaPlayer mediaPlayer) {
        Rectangle2D r = new Rectangle2D(
                primaryStage.getWidth(),
                primaryStage.getHeight(),
                Math.min(primaryStage.getWidth(), mediaPlayer.getMedia().getWidth()),
                primaryStage.getHeight()
        );
        mediaView.setViewport(r);
    }
}
