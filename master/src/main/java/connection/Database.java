package connection;

import model.user.User;

import java.util.ArrayList;

public class Database {
    private static final Database DATABASE;
    private final ArrayList<User> connectedUsers;
    static {
        DATABASE = new Database();
    }

    private Database() {
        this.connectedUsers = new ArrayList<>();
    }

    public static Database getInstance() {
        return Database.DATABASE;
    }

}
