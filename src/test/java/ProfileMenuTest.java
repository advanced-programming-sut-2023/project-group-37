import controller.viewControllers.ProfileMenuController;
import model.user.User;
import org.junit.Assert;
import org.junit.Test;

public class ProfileMenuTest {
    @Test
    public void changeUsernameTest1() { //valid username change expected
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");
        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.changeUsername("changedToMamad");

        Assert.assertEquals("changedToMamad", user.getUsername());
    }

    @Test
    public void changeUsernameTest2() { //invalid username
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");
        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.changeUsername("invalidUsername!!!!");

        Assert.assertNotEquals("invalidUsername!!!!", user.getUsername());
    }

    @Test
    public void changeNicknameTest() { //change expected
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");
        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.changeNickName("newNickname !!");

        Assert.assertEquals("newNickname !!", user.getNickName());
    }

    @Test
    public void changePasswordTest1() { // wrong old password
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");
        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.changePassword("wrongOldPassword", "newPass1234@");

        Assert.assertTrue(user.isWrongPassword("newPass1234@"));
    }

    @Test
    public void changePasswordTest2() { //invalid password format
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");
        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.changePassword("Mehr12345@", "newPass123");

        Assert.assertTrue(user.isWrongPassword("newPass1234@"));
    }

    @Test
    public void changeEmailTest1() { //invalid email format
        User user1 = new User("mehrshad", "Mehr12345@", "mehrshad@gmail.com", "winner",
                "mehri");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user1);
        profileMenuController.changeEmail("invaildEmailFormat");

        Assert.assertNotEquals("invaildEmailFormat", user1.getEmail());
    }

    @Test
    public void changeEmailTest2() { //email already exists
        User user1 = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");
        new User("erfan", "erfan1234@", "erfan@gmail.com", "yeeeees!",
                "payea");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user1);
        profileMenuController.changeEmail("erfan@gmail.com");

        Assert.assertNotEquals("erfan@gmail.com", user1.getEmail());
    }

    @Test
    public void changeEmailTest3() { //change expected
        User user = new User("mehrshad", "Mehr12345@", "randomEmail@gmail.com", "winner",
                "mehri");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.changeEmail("changedEmail@gmail.com");

        Assert.assertEquals("changedEmail@gmail.com", user.getEmail());
    }

    @Test
    public void changeSloganTest() { //change expected
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.changeSlogan("I need a new slogan!!");

        Assert.assertEquals("I need a new slogan!!", user.getSlogan());
    }

    @Test
    public void removeSloganTest() { //remove expected
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        profileMenuController.removeSlogan();

        Assert.assertNull(user.getSlogan());
    }

    @Test
    public void displayHighscoreTest() {
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);

        Assert.assertEquals(0, profileMenuController.showScore());

    }

    @Test
    public void displaySloganTest() {
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);

        Assert.assertEquals("winner", profileMenuController.showSlogan());

    }

    @Test
    public void displayProfileTest() {
        User user = new User("mehrshad", "Mehr12345@", "salam@gmail.com", "winner",
                "mehri");

        ProfileMenuController profileMenuController = new ProfileMenuController();
        ProfileMenuController.setCurrentUser(user);
        String expected = "Info:\nUsername: mehrshad\nNickname: mehri\nEmail: salam@gmail.com\nSlogan: winner\n" +
                "HighScore: 0\nRank: 6";
        Assert.assertEquals(expected, profileMenuController.showProfile());

    }
}
