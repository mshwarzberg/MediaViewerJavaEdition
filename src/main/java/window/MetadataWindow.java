package window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
import tool.FileMetadata;

import java.util.ArrayList;
import java.util.List;

public class MetadataWindow extends Stage {
    public static final int WINDOW_WIDTH = 400;
    private final TitleBar titleBar;
    private final TabPane tabPane = new TabPane();
    private final Tab generalTab = new Tab("General");
    private final Tab descriptionTab = new Tab("Description");
    private final Tab tagsTab = new Tab("Tags");
    private final FileMetadata metadata;

    public MetadataWindow(FileMetadata metadata) {
        this.metadata = metadata;
        setup();
        tabPane.getTabs().addAll(generalTab, descriptionTab, tagsTab);
        titleBar = new TitleBar(metadata.getSourceFile());
        this.initStyle(StageStyle.UNDECORATED);
        this.initModality(Modality.APPLICATION_MODAL);
        setWindowContent();
    }

    public void setup() {
        generalTab.setContent(new GeneralTab(metadata));
        descriptionTab.setContent(new DescriptionTab(metadata));
        setTagsTab();
        tagsTab.setClosable(false);
        generalTab.setClosable(false);
        descriptionTab.setClosable(false);
    }

    private void setWindowContent() {
        this.setScene(new Scene(new VBox(titleBar, tabPane)));
        this.setTitle(metadata.getSourceFile());
        this.setWidth(WINDOW_WIDTH);
        this.setHeight(300);
        this.show();
    }


    private void setTagsTab() {
        List<HBox> tagBoxes = new ArrayList<>();
        if (metadata.getTagsArray() != null) {
            for (String tag : metadata.getTagsArray()) {
                tagBoxes.add(new HBox(new Button("-"), new Button(tag)));
            }
        }
        if (metadata.getTagsString() != null) {
            tagBoxes.add(new HBox(new Button("-"), new Button(metadata.getTagsString())));
        }
        tagsTab.setContent(new TagPane(tagBoxes));
    }

    private static class TagPane extends FlowPane {
        TagPane(List<HBox> tagBoxes) {
            this.setHgap(5);
            this.setVgap(10);
            this.getChildren().addAll(tagBoxes);
            this.getChildren().add(new Button("+"));

            VBox container = new VBox();
            container.setPadding(new Insets(10, 10, 10, 10));
            container.getChildren().addAll(this);
        }
    }

    private static class GeneralTab extends ScrollPane {
        GeneralTab(FileMetadata metadata) {
            this.setContent(new Label(metadata.getGeneral()));
        }
    }

    private static class DescriptionTab extends VBox {
        DescriptionTab(FileMetadata metadata) {
            this.getChildren().addAll(
                    new Headline(metadata.getHeadline()),
                    new Description(metadata.getDescription()),
                    new SaveButton()
            );
            this.setPadding(new Insets(5, 5, 5, 5));
            setMargin(this.getChildren().get(2), new Insets(5, 0, 0, 0));
        }

        private static class Headline extends TextField {
            Headline(String text) {
                this.setText(text);
            }
        }

        private static class Description extends TextArea {
            Description(String text) {
                this.setText(text);
                this.setWrapText(true);
            }
        }

        private static class SaveButton extends Button {
            SaveButton() {
                this.setText("Save");
                this.setAlignment(Pos.BASELINE_RIGHT);
                this.setDisable(true);
            }
        }
    }
}

