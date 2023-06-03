package controller;

import javafx.scene.image.Image;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class CaptchaController {
    private final static ArrayList<File> allCaptchaImageFiles;
    private String captchaCode;
    private Image captchaImage;

    static {
        allCaptchaImageFiles = new ArrayList<>();

        File file;
        try {
            file = new File(Objects.requireNonNull(CaptchaController.class.getResource("/Images/Captcha")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        allCaptchaImageFiles.addAll(MultiMenuFunctions.getAllImageFilesFromFolder(file));
    }
    public void generateCaptcha() {
        Random random = new Random();
        int randomNumber = random.nextInt(0, 50);

        this.captchaImage = new Image(allCaptchaImageFiles.get(randomNumber).getAbsolutePath(),
                145, 25, false, false);

        this.captchaCode = allCaptchaImageFiles.get(randomNumber).getName().substring(0, 4);
    }

    public boolean isCaptchaInCorrect(String captchaInput) {
        return !this.captchaCode.equals(captchaInput);
    }

    public Image getCaptchaImage() {
        return this.captchaImage;
    }
}
