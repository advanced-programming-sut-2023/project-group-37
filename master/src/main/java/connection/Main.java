package connection;

import model.user.User;

public class Main {
    public static void main(String[] args) {
        User.loadUsersFromFile();
        new Master(8180);
    }
}
