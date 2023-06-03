package view.enums;

public enum Captcha {
    ;
    private final String number;

    Captcha(String number) {
        this.number = number;
    }

    public String getNumber() {
        return this.number;
    }
}
