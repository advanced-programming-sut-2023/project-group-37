package model.user;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private String username;
    private String hashedPassword;
    private String nickname;
    private String slogan;
    private String email;
    private String securityQuestion;
    private String securityQuestionAnswer;
    private int highScore;
    private int rank;
    private static ArrayList<User> users = new ArrayList<>();

    public static User getCurrentUser() {
        return User.currentUser;
    }

    static final Gson gson = new Gson();

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }

    public User(String username, String password, String email, String slogan, String nickname) {
        this.username = username;
        this.hashedPassword = password;
        this.email = email;
        this.slogan = slogan;
        this.nickname = nickname;
        this.rank = users.size() + 1;
        users.add(this);
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

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username.toLowerCase()))
                return user;
        }

        return null;
    }

    public static User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email.toLowerCase()))
                return user;
        }

        return null;
    }

    public static Gson getGson() {
        return User.gson;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public User(String username, String password, String nickname, String slogan, String email, int questionNumber,
                String answer) {
        this.username = username;
        this.hashedPassword = password;
        this.nickname = nickname;
        this.slogan = slogan;
        this.email = email;
        this.securityQuestion = SecurityQuestion.getQuestion(questionNumber);
        this.securityQuestionAnswer = answer;
        this.highScore = 0;
        users.add(this);
    }

    public int getRank() {
        return rank;
    }

    public String getUsername() {
        return this.username;
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

    public String getSecurityQuestion() {
        return this.securityQuestion;
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

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
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

    public void setSecurityQuestion(int questionNumber) {
        this.securityQuestion = SecurityQuestion.getQuestion(questionNumber);
    }

    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
        setRanks();
    }


    public boolean isWrongPassword(String password) {
        return !PasswordHashing.checkPassword(password, this.hashedPassword);
    }

    public boolean isCorrectAnswer(String answer) {
        return this.securityQuestionAnswer.equals(answer);
    }

    public static void loadUsersFromFile() {
        String filePath = "./src/main/resources/userDatabase.json";
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
        String filePath = "./src/main/resources/stayLoggedIn.json";
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
        String filePath = "./src/main/resources/stayLoggedIn.json";
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
        String filePath = "./src/main/resources/userDatabase.json";
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(gson.toJson(users));
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

    public static void deleteUser(User user) {
        users.remove(user);
    }

    private static User getUserByRank(int rank) {
        for (User user : users) {
            if (user.rank == rank)
                return user;
        }
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
}
