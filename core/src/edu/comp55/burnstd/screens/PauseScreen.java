package edu.comp55.burnstd.screens;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;


import java.security.PublicKey;
import javax.management.loading.PrivateClassLoader;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import edu.comp55.burnstd.map.Level;

public class PauseScreen {

	public PauseScreen(Skin skin, final Level level) {
		final Window pauseWindow = new Window("paused", skin);
		
		TextButton continueButton = new TextButton("continue", skin);
		continueButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("continue pressed");
				pauseWindow.remove();
				level.setIsPaused(false);
				
			}
		});
		
		TextButton exitButton = new TextButton("exit", skin);
		exitButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				System.out.println("exit pressed");
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		});
		
		//adding an options button
		TextButton optionButton = new TextButton("Settings", skin);
		optionButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				OptionsScreen options = new OptionsScreen(level.getGameScreen().getStage());
				level.getGameScreen().getStage().addActor(options);
			}
		});
		
		
		pauseWindow.add(continueButton).row();;


		pauseWindow.add(exitButton).row();
		pauseWindow.add(optionButton).row();
		
		pauseWindow.setSize(level.getGameScreen().getStage().getWidth()/1.5f, level.getGameScreen().getStage().getHeight()/1.5f);
		pauseWindow.setPosition(100,100);
		pauseWindow.setMovable(false);
		//pauseWindow.pack();
		
		level.getGameScreen().getStage().addActor(pauseWindow);
	}

}
