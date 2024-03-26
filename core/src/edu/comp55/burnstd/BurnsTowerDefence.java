package edu.comp55.burnstd;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import edu.comp55.burnstd.screens.OptionsScreen;
import edu.comp55.burnstd.screens.Splash;

// Extends game as this is essentially the main of the program
public class BurnsTowerDefence extends Game {
	SpriteBatch batch;
	public static Preferences prefs;
	Texture img;

	@Override
	public void create() {
		prefs = Gdx.app.getPreferences("BurnsTDPrefs");
		OptionsScreen.loadPrefs();

		setScreen(new Splash());
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
