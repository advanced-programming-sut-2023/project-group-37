package model.chat;

public enum MessageState {
    SENDING("*"),
    SENT("**"),
    SEEN("***");

    private final String symbol;

    MessageState(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
