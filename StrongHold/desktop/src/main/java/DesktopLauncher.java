import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import games.spooky.gdx.nativefilechooser.NativeFileChooserConfiguration;
import games.spooky.gdx.nativefilechooser.desktop.DesktopFileChooser;
public class DesktopLauncher {
    public static void main(String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.useVsync(true);
        config.setTitle("AP-AA");
        config.setWindowedMode(640, 800);
        StrongHoldGame.fileChooser = new DesktopFileChooser();
        StrongHoldGame.fileChooserConfiguration = new NativeFileChooserConfiguration();

        new Lwjgl3Application(new StrongHoldGame(), config);
    }
}