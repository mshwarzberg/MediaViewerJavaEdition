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

public class TitleBar extends HBox {
    public TitleBar(String title) {
        this.setWidth(MetadataWindow.WINDOW_WIDTH);
        this.setBackground(Background.fill(Paint.valueOf("grey")));
        this.getChildren().addAll(new Title(title), new Region(), new CloseButton()); // Add Region node
        setHgrow(getChildren().get(1), Priority.ALWAYS); // Expand and center the space between Title and CloseButton
        this.setAlignment(Pos.CENTER_LEFT); // Update alignment
        this.setFillHeight(true);
        this.setPadding(new Insets(0, 0, 0, 5));
    }

    private static class Title extends Label {
        Title(String title) {
            this.setAlignment(Pos.TOP_LEFT);
            this.setText(title);
        }
    }

    private static class CloseButton extends Button {
        CloseButton() {
            this.setAlignment(Pos.TOP_RIGHT);
            this.setText("X");
            this.setOnAction(event -> {
                Main.getChildWindow().close();
                Main.ContentViewPage.INSTANCE.requestFocus();
            });
        }
    }
}

