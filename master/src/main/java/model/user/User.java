package model.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import connection.packet.registration.RegisterPacket;
import controller.viewControllers.ProfileMenuController;
import javafx.scene.image.Image;
import model.chat.Chat;
import model.utils.PasswordHashing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private static User currentUser;
    private static ArrayList<User> users;
    private static final Gson gson;
    private ArrayList<Chat> chats;
    private ArrayList<User> friends;
    private String username;
    private String hashedPassword;
    private String nickname;
    private String slogan;
    private String email;
    private String recoveryQuestion;
    private String recoveryAnswer;
    private int highScore;
    private int rank;
    private int avatarNumber;

    static {
        users = new ArrayList<>();
        gson = new Gson();
        loadUsersFromFile();
    }
    public User(RegisterPacket registerPacket) {
        this.username = registerPacket.getUsername();
        this.hashedPassword = registerPacket.getPassword();
        this.nickname = registerPacket.getNickname();
        this.slogan = registerPacket.getSlogan();
        this.email = registerPacket.getEmail();
        this.recoveryQuestion = registerPacket.getRecoveryQuestion();
        this.recoveryAnswer = registerPacket.getRecoveryAnswer();
        this.highScore = 0;
        this.avatarNumber = ProfileMenuController.getRandomAvatarURL();
        this.friends = new ArrayList<>();
        this.chats = new ArrayList<>();
        users.add(this);
        User.updateDatabase();
    }

    public User(String username, String password, String nickname, String email, String recoveryQuestion,
                String recoveryAnswer, String slogan) {
        this.username = username;
        this.hashedPassword = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.recoveryQuestion = recoveryQuestion;
        this.recoveryAnswer = recoveryAnswer;
        this.highScore = 0;
        this.avatarNumber = ProfileMenuController.getRandomAvatarURL();
        this.friends = new ArrayList<>();
        this.chats = new ArrayList<>();
        this.avatarNumber = ProfileMenuController.getRandomAvatarURL();
        users.add(this);
        User.updateDatabase();
    }

    public static User getCurrentUser() {
        return User.currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public static void reset(ArrayList<User> users) {
        User.users = users;
        for (User user : users) {
            System.out.println(user.getUsername());
        }
        System.out.println("BAW");
        User.updateDatabase();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username.toLowerCase()))
                return user;
        }

        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users)
            if (user.getEmail().equals(email.toLowerCase()))
                return user;
        return null;
    }

    public int getRank() {
        return rank;
    }

    private void setRanks() {
        for (User user : users)
            user.rank = 0;

        int rank = 1, highScore;
        User rankedUser = users.get(0);

        while (rank <= users.size()) {
            highScore = 0;
            for (User user : users) {
                if (user.highScore > highScore && user.rank == 0) {
                    rankedUser = user;
                    highScore = user.highScore;
                }
            }
            rankedUser.rank = rank;
            rank++;
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public String getNickName() {
        return this.nickname;
    }

    public String getSlogan() {
        return this.slogan;
    }

    public String getEmail() {
        return this.email;
    }

    public String getRecoveryQuestion() {
        return this.recoveryQuestion;
    }

    public String getRecoveryAnswer() {
        return this.recoveryAnswer;
    }

    public int getHighScore() {
        return this.highScore;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void changePassword(String newPassword) {
        this.hashedPassword = PasswordHashing.encode(newPassword);
    }

    public void setPassword(String password) {
        this.hashedPassword = PasswordHashing.encode(password);
    }

    public void setNickName(String nickname) {
        this.nickname = nickname;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRecoveryQuestion(int questionNumber) {
        this.recoveryQuestion = RecoveryQuestion.getQuestion(questionNumber);
    }

    public void setAvatarNumber(int avatarNumber) {
        this.avatarNumber = avatarNumber;
    }

    public void setRecoveryAnswer(String recoveryAnswer) {
        this.recoveryAnswer = recoveryAnswer;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        setRanks();
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Image getAvatar() {
        return new Image(ProfileMenuController.getAllAvatarImages().get(this.avatarNumber).getAbsolutePath());
    }

    public boolean isWrongPassword(String password) {
        return !PasswordHashing.checkPassword(password, this.hashedPassword);
    }

    public boolean isWrongHashedPassword(String hashedPassword) {
        return !this.hashedPassword.equals(hashedPassword);
    }

    public boolean isWrongAnswer(String answer) {
        return !this.recoveryAnswer.equals(answer);
    }

    public static void deleteUser(User user) {
        users.remove(user);
    }

    private static User getUserByRank(int rank) {
        for (User user : users)
            if (user.rank == rank)
                return user;
        return null;
    }

    public static void setRankByHyScore(User user) {
        int rank = user.rank;
        User rankUser;
        int rankNumber = 1;
        while (rankNumber <= rank) {
            rankUser = getUserByRank(rankNumber);
            if (rankUser != null) {
                if (rankUser.highScore < user.highScore) {
                    user.rank = rankNumber;
                    rankUser.rank = -1;
                    break;
                }
            }
            rankNumber++;
        }
        for (int rankToChange = rankNumber + 1; rankToChange < rank; rankToChange++) {
            rankUser = getUserByRank(rankToChange);
            assert rankUser != null;
            rankUser.rank = rankToChange + 1;
        }

        rankUser = getUserByRank(-1);
        if (rankUser != null)
            rankUser.rank = rankNumber + 1;
    }

    // Database functions :

    public static void updateDatabase() {
        saveUsersToFile();

        if (loadStayLoggedIn() != null) {
            setStayLoggedIn(currentUser);
        }
    }

    public static void loadUsersFromFile() {
        String filePath = "./master/src/main/resources/Database/userDatabase.json";
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));
            ArrayList<User> createdUsers = gson.fromJson(json, new TypeToken<List<User>>() {
            }.getType());

            if (createdUsers != null) {
                users = createdUsers;
            }
        } catch (IOException ignored) {
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static User loadStayLoggedIn() {
        String filePath = "./master/src/main/resources/Database/stayLoggedIn.json";
        try {
            String json = new String(Files.readAllBytes(Paths.get(filePath)));

            return gson.fromJson(json, new TypeToken<User>() {
            }.getType());

        } catch (IOException ignored) {
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return null;
        }
    }

    public static void setStayLoggedIn(User loggedInUser) {
        String filePath = "./master/src/main/resources/Database/stayLoggedIn.json";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(gson.toJson(loggedInUser));
            fileWriter.close();
        } catch (IOException ignored) {
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void saveUsersToFile() {
        String filePath = "./master/src/main/resources/Database/userDatabase.json";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(gson.toJson(users));
            fileWriter.close();
        } catch (IOException ignored) {
            System.out.println("ERRORED");
            File file = new File(filePath);
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<User> getFriends() {
        return this.friends;
    }

    public void setFriends(ArrayList<User> friends) {
        this.friends = friends;
    }

    public ArrayList<Chat> getChats() {
        return this.chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public void addFriend(User friend){
        this.friends.add(friend);
    }

    public void joinChat(Chat chat) {
        this.chats.add(chat);
    }

}