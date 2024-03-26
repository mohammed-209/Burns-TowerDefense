package edu.comp55.burnstd.map;

import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.JsonValue;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.enemy.EnemyFactory;
import edu.comp55.burnstd.screens.GameScreen;
import edu.comp55.burnstd.screens.OptionsScreen;
import edu.comp55.burnstd.screens.UpgradeScreen;
import edu.comp55.burnstd.towers.EnemyBase;
import edu.comp55.burnstd.towers.ExplosionHandler;
import edu.comp55.burnstd.towers.PlayerBase;

public class Level implements InputProcessor {

	private Map map;
	private JsonValue levelData = null;
	private float deltaTimer = 0f;
	private float spawnCooldownTimer = 0f;
	GameScreen gameScreen;
	private Sprite levelBackground;
	public SetupUI setupUI;
	private Music music;
	private int money;
	private int infiniteModeLevel = 1;

	private boolean infiniteMode = false;
	Stage currMode;

	boolean isPaused;
	boolean challengeMode;
	float score;

	private int currentWave = 0;

	private int enemiesInWave = 0;
	private int maxEnemiesInWave = 0;

	private float moneyGenerationTimer = 0f;

	private boolean allowOverlappingPaths = false;

	private boolean isWaveActive = false;

	private LinkedList<LinkedList<Enemy>> enemyWaves;

	public Level(JsonValue levelData, GameScreen gameScreen) {

		map = new Map(levelData.getInt("rows"), levelData.getInt("cols"), this);
		this.infiniteMode = levelData.getBoolean("infiniteMode");
		System.out.println("Infinite mode: " + infiniteMode);
		this.levelData = levelData;
		this.gameScreen = gameScreen;
		this.music = Gdx.audio.newMusic(Gdx.files.internal("music/" + levelData.getString("music")));
		this.money = levelData.getInt("money");
		this.score = 0;
		this.challengeMode = levelData.getBoolean("challenge");
		this.setAllowOverlappingPaths(levelData.getBoolean("allowOverlappingPaths"));
		levelBackground = new Sprite(new Texture(Gdx.files.internal(levelData.getString("background"))));
		levelBackground.setSize(map.cellWidth() * map.numCols, map.cellHeight() * map.numRows);
		setupWaves();
		map.createTiles();

		map.setEnemyBase(new EnemyBase(1, (map.numRows - 1) / 2 * map.cellHeight(), map));
		map.setPlayerBase(new PlayerBase((map.numCols - 1) * map.cellWidth(), (map.numRows - 1) / 2 * map.cellHeight(), map));
		map.buildPath();

		music.setVolume(OptionsScreen.musicVolume);
		music.setLooping(true);
		music.play();

		map.setExplosionHandler(new ExplosionHandler(25,25, map));
		map.placeTower(map.getExplosionHandler());

		setGameMode(0);

		if (challengeMode) {
			challengeSetup();
			setGameMode(1);
		}
	}

	public void setIsPaused(boolean p) {
		isPaused = p;
	}

	public boolean getIsPaused() {
		return isPaused;
	}

	public SetupUI getSetupUI() {
		return setupUI;
	}

	public void draw(Batch batch) {
		levelBackground.draw(batch);
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void queueWave() {
		System.out.println("Current level: " + infiniteModeLevel);
		enemiesInWave = 0;
		maxEnemiesInWave = 0;
		if (!enemyWaves.isEmpty()) {
			LinkedList<Enemy> firstWave = enemyWaves.getFirst();
			while (!firstWave.isEmpty()) {
				map.queueEnemy(firstWave.removeFirst());
				enemiesInWave++;
				maxEnemiesInWave++;
			}
			enemyWaves.removeFirst();
			currentWave++;
			isWaveActive = true;
		}
		else {
			if (infiniteMode) {
				infiniteModeLevel++;
				setupWaves();
				queueWave();
			}
			else {
				gameScreen.setWon(true);
			}
		}
		System.out.println("Wave " + currentWave + " queued");
	}

	public Music getMusic() {
		return music;
	}

	public void setMusic(Music music) {
		this.music = music;
	}

	public void update(float delta) {
		deltaTimer += delta;
		music.setVolume(OptionsScreen.musicVolume);
		moneyGenerationTimer += delta;
		if (deltaTimer > spawnCooldownTimer) {
			spawnCooldownTimer = map.spawnEnemy();
			deltaTimer = 0f;
		}
		map.update(delta);

		if (isWaveActive && moneyGenerationTimer > 1f) {
			setMoney(getMoney() + UpgradeScreen.getUpgradeMoneyGeneration());
			moneyGenerationTimer = 0f;
		}

	}

	public void setupWaves() {
		JsonValue waves = levelData.get("waves");
		if (!infiniteMode){
			infiniteModeLevel = 1;
		}

		enemyWaves = new LinkedList<LinkedList<Enemy>>();

		// Loop through the waves and create a linked list of enemies for each wave
		for (int i = 0; i < waves.size; i++) {
			JsonValue wave = waves.get(i);
			JsonValue enemies = wave.get("enemies");

			LinkedList<Enemy> waveEnemies = new LinkedList<Enemy>();

			for (int j = 0; j < enemies.size; j++) {
				JsonValue enemy = enemies.get(j);
				int numEnemies = enemy.getInt("count") * infiniteModeLevel;
				String enemyType = enemy.getString("type");
				float spawnCooldown = MathUtils.clamp(enemy.getFloat("spawnCooldown") - infiniteModeLevel, 0.01f, 100f);
				int level = MathUtils.clamp((enemy.getInt("level") * infiniteModeLevel), 0, 10);

				for (int k = 0; k < numEnemies; k++) {
					waveEnemies.add(EnemyFactory.createEnemy(enemyType, spawnCooldown, map.getEnemySpawnX(),
							map.getEnemySpawnY(), getMap(), level));
				}
			}

			enemyWaves.add(waveEnemies);
		}
	}

	public void challengeSetup(){
		// create tiles based on json tiles field
		JsonValue tiles = levelData.get("tiles");
		for (int i = 0; i < tiles.size; i++){
			JsonValue tile = tiles.get(i);
			map.buildNewPathTile(tile.getString("direction"));
		}
	}


	public void reset() {
		getMap().getPath().clear();
		getMap().buildPath();
		setMoney(levelData.getInt("money"));
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}


	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public boolean pathConnects() {
		return map.pathConnects;
	}

	public void setGameMode(int i) {

		if (currMode == null) {
			currMode = new SetupUI(this);
		}

		switch (i) {
			case 0:
				this.gameScreen.getInputMultiplexer().removeProcessor(currMode); // Take away input from previous UI
				currMode.clear(); // Clear all components of previous UI
				currMode = new SetupUI(this); // Change the value of currMode to the new UI class
				currMode.setViewport(getGameScreen().getViewport()); // Set the viewport that the UI is being draw *MAY NOT BE NEEDED*
				this.getGameScreen().getInputMultiplexer().addProcessor(currMode); // Pass input to the new UI
				break;
			case 1:
				this.gameScreen.getInputMultiplexer().removeProcessor(currMode);
				currMode.clear();
				currMode = new GameUI(this);
				currMode.setViewport(getGameScreen().getViewport());
				this.getGameScreen().getInputMultiplexer().addProcessor(currMode);
		}

	}

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	public Stage getCurrMode() {
		return currMode;
	}

	public boolean isWaveActive() {
		return isWaveActive;
	}


	public void setWaveActive(boolean b) {
		isWaveActive = b;
	}


	public int getCurrentWave() {
		return currentWave;
	}

	public void setCurrentWave(int currentWave) {
		this.currentWave = currentWave;
	}


	public int getEnemiesInWave() {
		return enemiesInWave;
	}

	public void setEnemiesInWave(int enemiesInWave) {
		this.enemiesInWave = enemiesInWave;
	}

	public int getMaxEnemiesInWave() {
		return maxEnemiesInWave;
	}

	public void setMaxEnemiesInWave(int maxEnemiesInWave) {
		this.maxEnemiesInWave = maxEnemiesInWave;
	}


	public boolean getAllowOverlappingPaths() {
		return allowOverlappingPaths;
	}

	public void setAllowOverlappingPaths(boolean allowOverlappingPaths) {
		this.allowOverlappingPaths = allowOverlappingPaths;
	}

	@Override
	public boolean keyDown(int keycode) {
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

	// Get percentage of remaining enemies in wave
	public float getEnemyQueueProgress() {
		if (maxEnemiesInWave != 0) {
			return (float) enemiesInWave / (float) maxEnemiesInWave;
		}
		return 0;
	}
}