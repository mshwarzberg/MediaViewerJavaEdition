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
        setGeneralTab();
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
        setupTagPane(tagBoxes);
    }

    private void setGeneralTab() {
        Label generalLabel = new Label("metadata.getGeneral()");
        generalTab.setContent(generalLabel);
    }

    private void setupTagPane(List<HBox> tagBoxes) {
        FlowPane tagPane = new FlowPane();
        tagPane.setHgap(5);
        tagPane.setVgap(10);
        tagPane.getChildren().addAll(tagBoxes);
        tagPane.getChildren().add(new Button("+"));

        VBox container = new VBox();
        container.setPadding(new Insets(10, 10, 10, 10)); // add a 20-pixel gap to the top
        container.getChildren().addAll(tagPane);

        tagsTab.setContent(container);
    }

    private static class DescriptionTab extends VBox {
        DescriptionTab(FileMetadata metadata) {
            this.getChildren().addAll(new Headline(metadata.getHeadline()), new Description(metadata.getDescription()), new SaveButton());
            this.setPadding(new Insets(5, 5, 5, 5));
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
            }
        }
    }
}

