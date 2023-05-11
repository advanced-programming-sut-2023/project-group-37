package model.utils;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Captcha {

    private int width;
    private String captchaNumber;

    public void createCaptcha() {
        BufferedImage image = this.makeCaptcha();
        for (int y = 0; y < 15; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < this.getWidth(); x++) {

                sb.append(image.getRGB(x, y) == -16777216 ? "." : "@");

            }
            if (sb.toString().trim().isEmpty()) {
                continue;
            }
            System.out.println(sb);
        }
    }

    public String getCaptchaNumber() {
        return this.captchaNumber;
    }

    private int getWidth() {
        return width;
    }

    public BufferedImage makeCaptcha() {
        this.captchaNumber = randomNumber();
        this.width = captchaNumber.length() * 30;
        int height = 15;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("TimesRoman", Font.CENTER_BASELINE, 20));
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString(captchaNumber, 0, 15);
        return image;
    }

    private String randomNumber() {
        Random random = new Random();
        int low = 4;
        int high = 9;
        int size = random.nextInt(high - low) + low;
        String number = "";
        for (int i = 0; i < size; i++) {
            number += random.nextInt(10);
        }
        return number;
    }
}