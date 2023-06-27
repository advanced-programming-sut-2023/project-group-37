package controller;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;

public class StripPaneController {
    private final static int sizeOfImages;
    private final Pane stripPane;
    private ArrayList<File> buildingMenuImages;
    static {
        sizeOfImages = 40;
    }
    public StripPaneController(Pane stripPane) {
        this.stripPane = stripPane;
    }

    private void setBuildingMenuImages(String dir) {
        this.buildingMenuImages = new ArrayList<>();

        File file;
        try {
            file = new File(Objects.requireNonNull(CaptchaController.class.getResource("/Image/Buildings/" + dir)).toURI());
        } catch (Exception e) {
            return;
        }

        this.buildingMenuImages.addAll(MultiMenuFunctions.getAllImageFilesFromFolder(file));
    }

    public void insertImages(String dir) {
        this.stripPane.getChildren().removeAll();
        this.setBuildingMenuImages(dir);

        int i = 0;
        for (File imageFile : this.buildingMenuImages) {
            ImageView imageView = new ImageView(new Image(imageFile.getAbsolutePath(),
                    sizeOfImages, sizeOfImages, false, false));

            imageView.setLayoutX(100 + i * (sizeOfImages + 10));

            i++;
        }
    }
}