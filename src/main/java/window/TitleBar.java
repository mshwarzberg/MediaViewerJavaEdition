package window;

import core.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import tool.MyButton;

public class TitleBar extends HBox {
    private double xOffset = 0;
    private double yOffset = 0;

    public TitleBar(String title) {
        setWidth(MetadataWindow.WINDOW_WIDTH);
        setBackground(Background.fill(Paint.valueOf("grey")));
        getChildren().addAll(new Title(title), new Region(), new CloseButton());
        setHgrow(getChildren().get(1), Priority.ALWAYS);
        setAlignment(Pos.CENTER_LEFT);
        setFillHeight(true);
        setPadding(new Insets(0, 0, 0, 5));

        setOnMousePressed(this::handleMousePressed);
        setOnMouseDragged(this::handleMouseDragged);
        setOnMouseReleased(this::handleMouseReleased);
    }

    private void handleMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    private void handleMouseDragged(MouseEvent event) {
        setCursor(Cursor.CLOSED_HAND);
        double newX = event.getScreenX() - xOffset;
        double newY = event.getScreenY() - yOffset;
        Main.getChildWindow().setX(newX);
        Main.getChildWindow().setY(newY);
    }

    private void handleMouseReleased(MouseEvent event) {
        setCursor(Cursor.DEFAULT);
    }

    private static class Title extends Label {
        Title(String title) {
            setAlignment(Pos.TOP_LEFT);
            setText(title);
        }
    }

    private static class CloseButton extends MyButton {
        CloseButton() {
            super("X");
            setAlignment(Pos.TOP_RIGHT);
            setOnAction(event -> {
                Main.getChildWindow().close();
                Main.ContentViewPage.INSTANCE.requestFocus();
            });
        }
    }
}


