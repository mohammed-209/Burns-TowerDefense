package edu.comp55.burnstd.screens;

import java.io.FileNotFoundException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

public class IntroCutscene implements Screen, InputProcessor {

	private SpriteBatch batch;
	private VideoPlayer video;
	private Music introMusic = Gdx.audio.newMusic(Gdx.files.internal("music/introcutscene.ogg"));
	private Viewport viewport;
	private Camera camera;

	Sprite skipButtonSprite;
	Texture skipButtonTexture;

	Sprite menuLogoSprite;
	Texture menuLogoTexture;

	@Override
	public void show() {

		camera = new OrthographicCamera();
		viewport = new FitViewport(800, 600, camera);

		batch = new SpriteBatch();

		// Tells the program to begin sending inputs to this screen
		Gdx.input.setInputProcessor(this);

		// Creates a new video player
		video = VideoPlayerCreator.createVideoPlayer();

		// Is called when the video completes playing
		video.setOnCompletionListener(new VideoPlayer.CompletionListener() {
			@Override
			public void onCompletionListener(FileHandle file) {
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());

			}
		});

		// Plays the video
		try {
			video.play(Gdx.files.internal("assets/video/intro.webm"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// HORRIBLE TERRIBLE NO GOOD WORKAROUND FOR AUDIO/VIDEO DESYNC
		Timer.schedule(new Task() {
			@Override
			public void run() {
				introMusic.play();
			}
		}, 1);

		skipButtonTexture = new Texture(Gdx.files.internal("textures/mainmenu/skipbutton.png"));
		skipButtonSprite = new Sprite(skipButtonTexture);
		skipButtonSprite.setAlpha(0.5f);

		menuLogoTexture = new Texture(Gdx.files.internal("textures/mainmenu/logo.png"));
		menuLogoSprite = new Sprite(menuLogoTexture);

	}

	@Override
	public void render(float delta) {
		// Clear the screen
		ScreenUtils.clear(0, 0, 0, 1);

		// Update the video
		video.update();

		batch.begin();

		// Draws the current frame of the video
		Texture frame = video.getTexture();
		if (frame != null) {
			batch.draw(frame, 0, 0, viewport.getScreenWidth(), viewport.getScreenHeight());
		}

		skipButtonSprite.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void dispose() {
		batch.dispose();
		//video.dispose();
		introMusic.dispose();
	}

	// Skips the intro when space is pressed
	@Override
	public boolean keyDown(int keycode) {
		System.out.println("button registered 0");
		if (keycode == Input.Keys.SPACE) {
			// System.out.println("button registered space");
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
