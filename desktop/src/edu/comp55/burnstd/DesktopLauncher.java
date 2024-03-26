package edu.comp55.burnstd;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import edu.comp55.burnstd.BurnsTowerDefence;


// This class simply launches the game and sets important info like the frame rate and window title. 
// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		// those settings are used to resize the game window
		//config.setWindowSizeLimits(1000, 1000, 1000, 1000);
		config.setWindowedMode(800, 600);
		config.setTitle("Burns Tower Defence");
		new Lwjgl3Application(new BurnsTowerDefence(), config);
	}
}
