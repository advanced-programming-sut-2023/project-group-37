package controller;

import javafx.scene.image.Image;
import view.Main;
import view.menus.RegisterMenu;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class CaptchaController {
    private final static ArrayList<File> allCaptchaImageFiles;
    private static final Random random;
    private String captchaCode;
    private Image captchaImage;

    static {
        allCaptchaImageFiles = new ArrayList<>();

        File file;
        try {
            file = new File(Objects.requireNonNull(CaptchaController.class.getResource("/Image/Captcha")).toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        allCaptchaImageFiles.addAll(MultiMenuFunctions.getAllImageFilesFromFolder(file));

        random = new Random();
    }

    public void generateCaptcha() {
        int randomNumber = random.nextInt(0, allCaptchaImageFiles.size());

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
