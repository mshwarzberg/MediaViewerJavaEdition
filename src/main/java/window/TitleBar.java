package window;

import core.Main;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Paint;
import tool.MyButton;

public class TitleBar extends HBox {
    public TitleBar(String title) {
        setWidth(MetadataWindow.WINDOW_WIDTH);
        setBackground(Background.fill(Paint.valueOf("grey")));
        getChildren().addAll(new Title(title), new Region(), new CloseButton()); // Add Region node
        setHgrow(getChildren().get(1), Priority.ALWAYS); // Expand and center the space between Title and CloseButton
        setAlignment(Pos.CENTER_LEFT); // Update alignment
        setFillHeight(true);
        setPadding(new Insets(0, 0, 0, 5));
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

