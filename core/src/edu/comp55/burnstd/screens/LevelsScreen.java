package edu.comp55.burnstd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelsScreen {
	public LevelsScreen(Skin skin, MainMenu menu) {
		final Window levelsWindow = new Window("challenges", skin);
		TextButton level1, level2, level3, level4, level5, level6, level7, level8, level9, level10;

		TextButton backButton = new TextButton("X", skin);
		backButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				levelsWindow.remove();
			}
		});

		level1 = new TextButton("lvl 1", skin);
		level1.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				levelsWindow.setVisible(false);
				((Game) Gdx.app.getApplicationListener())
				.setScreen(new GameScreen(Gdx.files.internal("level/challenges/challenge1.json")));
			}
		});

		level2 = new TextButton("lvl 2", skin);
		level2.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				levelsWindow.setVisible(false);
				((Game) Gdx.app.getApplicationListener())
				.setScreen(new GameScreen(Gdx.files.internal("level/challenges/challenge2.json")));
			}
		});

		level3 = new TextButton("lvl 3", skin);
		level4 = new TextButton("lvl 4", skin);
		level5 = new TextButton("lvl 5", skin);
		level6 = new TextButton("lvl 6", skin);
		level7 = new TextButton("lvl 7", skin);
		level8 = new TextButton("lvl 8", skin);
		level9 = new TextButton("lvl 9", skin);
		level10 = new TextButton("lvl 10", skin);
		
		
		Table levels = new Table(skin);
		levels.add(level1).padRight(10);
		levels.add(level2).padRight(10);
		levels.add(level3).padRight(10);
		levels.add(level4).padRight(10);
		levels.add(level5).padRight(10).row();
		levels.add(level6).padRight(10).padTop(10);
		levels.add(level7).padRight(10).padTop(10);
		levels.add(level8).padRight(10).padTop(10);
		levels.add(level9).padRight(10).padTop(10);
		levels.add(level10).padRight(10).padTop(10);
		
		levelsWindow.add(levels);
		levelsWindow.getTitleTable().add(backButton).size(60, 35).padRight(-35).padTop(-25);
		
//		levelsWindow.setSize(level.getGameScreen().getStage().getWidth()/1.5f, level.getGameScreen().getStage().getHeight()/1.5f);
		levelsWindow.setSize(Gdx.graphics.getWidth()/1.5f, Gdx.graphics.getHeight()/1.5f);
		levelsWindow.setPosition(100,100);
		levelsWindow.setMovable(false);
//		levelsWindow.padBottom(39);
		
		menu.getStage().addActor(levelsWindow);
	}
}
