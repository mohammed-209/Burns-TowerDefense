package edu.comp55.burnstd.map;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import edu.comp55.burnstd.enemy.Enemy;
import edu.comp55.burnstd.towers.EnemyBase;
import edu.comp55.burnstd.towers.ExplosionHandler;
import edu.comp55.burnstd.towers.PlayerBase;
import edu.comp55.burnstd.towers.Tower;

public class Map {

	private ArrayList<Tile> tiles;
	private LinkedList<Path> pathList;

	Path path;

	boolean pathConnects = false;

	private Level level;
	private ShapeRenderer lines = new ShapeRenderer();
	private ArrayList<Tower> towers = new ArrayList<Tower>();
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

	private LinkedList<Enemy> enemyQueue = new LinkedList<Enemy>();

	private float enemySpawnX = 25f;
	private float enemySpawnY = 25f;

	private EnemyBase enemyBase;
	private PlayerBase playerBase;




	float numRows;

	public float getEnemySpawnX() {
		return enemySpawnX;
	}

	public void setEnemySpawnX(Float enemySpawnX) {
		this.enemySpawnX = enemySpawnX;
	}

	public float getEnemySpawnY() {
		return enemySpawnY;
	}

	public void setEnemySpawnY(Float enemySpawnY) {
		this.enemySpawnY = enemySpawnY;
	}

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public Tower explosionHandler;

	float numCols;


	final int MAP_HEIGHT = Gdx.graphics.getHeight();
	final int MAP_WIDTH = Gdx.graphics.getHeight();

	public Map(int rows, int cols, Level level) {

		this.numRows = rows;
		this.numCols = cols;
		this.level = level;
		System.out.println("new level");

	}

	public Level getLevel() {
		return level;
	}

	public void update(Float delta) {
		clearDeadEnemies();
		if (!getTowers().contains(playerBase)){
			level.getGameScreen().setLost(true);
		}
	}

	// Clears dead enemies from the enemy list
	public void clearDeadEnemies() {
		Iterator<Enemy> itr = enemyList.iterator();
		while (itr.hasNext()) {
			Enemy e = itr.next();
			if (e.isDead()) {
				itr.remove();
			}
		}
	}

	public void queueEnemy(Enemy e) {
		enemyQueue.add(e);
	}

	public void placeTower(Tower t) {
		towers.add(t);
	}

	public void setEnemyBase(EnemyBase e) {
		this.enemyBase = e;
		placeTower(enemyBase);
	}

	public void setPlayerBase(PlayerBase p) {
		this.playerBase = p;
		placeTower(playerBase);
	}

	public EnemyBase getEnemyBase() {
		return enemyBase;
	}

	public PlayerBase getPlayerBase() {
		return playerBase;
	}

	public float spawnEnemy() {
		if (!enemyQueue.isEmpty()) {
			Enemy e = enemyQueue.pop();
			enemyList.add(e);
			return e.getSpawnCooldown();
		}
		if (enemyList.isEmpty()){
			level.setWaveActive(false);
		}
		return -1f;
	}


	public ExplosionHandler getExplosionHandler(){
		return (ExplosionHandler) explosionHandler;
	}

	public void setExplosionHandler(Tower t){
		explosionHandler = t;
	}

	public void createTiles() {
		tiles = new ArrayList<Tile>();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				Tile tile = new Tile(j * cellWidth(), i * cellHeight(), (int) cellWidth(), (int) cellHeight(), this);
				tiles.add(tile);
			}
		}
	}

	public void buildPath() {
		pathList = new LinkedList<Path>();
		path = new Path(1, (numCols - 1) / 2 * cellHeight(), cellWidth(), cellHeight(), this);
		pathList.add(path);

	}

	public void backPath() {
		if (pathList.size() > 1) {
			pathList.remove(pathList.getLast());
			level.setMoney(level.getMoney() + 50);
			checkPathConnects();
		}
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public LinkedList<Path> getPath() {
		return pathList;
	}

	public void buildNewPathTile(String direction) {

		if (pathConnects == false && level.getMoney() >= 50) {
			// moves left
			if (direction.equals("left")) {
				path = new Path(pathList.getLast().getX() - cellWidth(), pathList.getLast().getY(), cellWidth(),
						cellHeight(), this);
				path.rotate(180);
				pathList.add(path);
				// checkValidPosition(path);
				path.checkValidPosition();
			} // adds a path to the right
			if (direction.equals("right")) {
				path = new Path(pathList.getLast().getX() + cellWidth(), pathList.getLast().getY(), cellWidth(),
						cellHeight(), this);
				pathList.add(path);
				// checkValidPosition(path);
				path.checkValidPosition();
				System.out.println("test");
			}

			if (direction.equals("up")) { // adds a path up
				path = new Path(pathList.getLast().getX(), pathList.getLast().getY() + cellHeight(), cellWidth(),
						cellHeight(), this);
				pathList.add(path);
				path.rotate(90);
				// checkValidPosition(path);
				path.checkValidPosition();
			}

			if (direction.equals("down")) { // adds a path down
				path = new Path(pathList.getLast().getX(), pathList.getLast().getY() - cellHeight(), cellWidth(),
						cellHeight(), this);
				pathList.add(path);
				path.rotate(270);
				// checkValidPosition(path);
				path.checkValidPosition();
			}
		}
		checkPathConnects();

	}

	public void drawMap() {

		lines.setProjectionMatrix(level.getGameScreen().getBatch().getProjectionMatrix());
		lines.begin(ShapeType.Line);
		lines.setColor(Color.BLACK);
		// Draws Vertical lines
		for (int i = 0; i < numRows; i++) {
			lines.line(0, i * cellHeight(), MAP_WIDTH, i * cellHeight());
		}
		// Draws Horizontal lines
		for (int i = 0; i < numCols; i++) {
			lines.line(i * cellWidth(), 0, i * cellWidth(), MAP_HEIGHT);
		}

		lines.end();
	}

	public void checkPathConnects() {

		if (pathList.getLast().getBoundingRectangle().contains(playerBase.getCenterX(), playerBase.getCenterY())) {
			pathConnects = true;
			System.out.println("path connects");
		} else {
			pathConnects = false;
		}

	}

	public LinkedList<Enemy> getEnemyQueue() {
		return enemyQueue;
	}

	public void setEnemyQueue(LinkedList<Enemy> enemyQueue) {
		this.enemyQueue = enemyQueue;
	}

	public ArrayList<Enemy> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(ArrayList<Enemy> enemyList) {
		this.enemyList = enemyList;
	}

	public float cellWidth() {
		return MAP_WIDTH / numCols;
	}

	public float cellHeight() {
		return MAP_HEIGHT / numRows;
	}


}