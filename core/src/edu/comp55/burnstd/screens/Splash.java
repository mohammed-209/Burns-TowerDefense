package edu.comp55.burnstd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import edu.comp55.burnstd.tween.SpriteAccessor;

public class Splash implements Screen, InputProcessor {

	private SpriteBatch batch;
	private Sprite splash;
	private Texture splashTexture1;
	private Texture splashTexture2;
	private Sound splashSFX;
	private TweenManager tweenManager;
	private Viewport viewport;
	private Camera camera;

	@Override
	public void show() {

		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 600, camera);

		// Initializes the spritebatch
		batch = new SpriteBatch();

		// Initializes the manager for the Tween Managers
		tweenManager = new TweenManager();

		// Registers the tween accessor for sprites. Other types of objects will need
		// different accessors
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());

		// Creates a texture from the game files, then assigns the texture to the splash
		// sprite
		splashTexture1 = new Texture(Gdx.files.internal("textures/splashscreen/splashscreen1.png"));
		splashTexture2 = new Texture(Gdx.files.internal("textures/splashscreen/splashscreen2.png"));
		splash = new Sprite(splashTexture1);

		// Sets the size of the splash screen to match the window size, stretching it
		// across the entire window

		splash.setCenter(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);

		// Plays the splash screen sound effect
		splashSFX = Gdx.audio.newSound(Gdx.files.internal("sounds/splash.ogg"));
		splashSFX.play(OptionsScreen.soundVolume);

		Gdx.input.setInputProcessor(this);

		Timer.schedule(new Task() {
			@Override
			public void run() {
				splash.setTexture(splashTexture2);
			}
		}, 1.20f);

		// Sets a fade animation for the splash screen sprite using the tween manager
		// and sprite accessor
		Tween.set(splash, SpriteAccessor.ALPHA).target(1).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(0).delay(2).setCallback(new TweenCallback() {
			// Is called when the tween animation completes
			@Override
			public void onEvent(int type, BaseTween<?> source) {
				// Starts a timer to delay the screen transition by 2 seconds
				Timer.schedule(new Task() {
					@Override
					public void run() {
						((Game) Gdx.app.getApplicationListener()).setScreen(new IntroCutscene());
					}
				}, 2);

			}
		}).start(tweenManager);

	}

	@Override
	public void render(float delta) { // delta is time elapsed since last render
		// Clears the previous frame
		ScreenUtils.clear(0, 0, 0, 1);
		// Updates the tween animation
		tweenManager.update(delta);

		// All screen rendering happens inside this batch
		batch.begin();
		splash.draw(batch);
		batch.end();

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {
		// Disposes the batch on close to free memory
		batch.dispose();
	}

	// Skips the intro when space is pressed
	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Input.Keys.SPACE) {
			((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchCancelled(int a, int b, int c, int d){
		return false;
	}

}
