package edu.comp55.burnstd.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import edu.comp55.burnstd.BurnsTowerDefence;
import edu.comp55.burnstd.tooltips.TowerTextToolTip;
import edu.comp55.burnstd.towers.Tower;

public class UpgradeScreen implements Screen {
        private Stage stage;
        private Skin skin;
        private Sprite background;
        private SpriteBatch batch;

        public static int score = BurnsTowerDefence.prefs.getInteger("score", 0);
        public static float upgradeDamage = 1;
        public static float upgradeFireRate = 1;
        public static int upgradeMoneyGeneration = 0;
        public static float upgradeHealth = 1;
        public static int amount = 0;
        private static float damageCost = 10000;
        private static float fireRateCost = 10000;
        private static float moneyGenerationCost = 10000;
        private static float healthCost = 10000;
        private static float amountCost = 10000;

        private static int damageLevel = 1;
        private static int fireRateLevel = 1;
        private static int moneyGenerationLevel = 1;
        private static int healthLevel = 1;
        private static int amountLevel = 1;



        private Label scoreLabel;

        private TextButton backButton;
        private TextButton damageButton;
        private TextButton fireRateButton;
        private TextButton moneyGenerationButton;
        private TextButton healthButton;
        private TextButton amountButton;

        private Music music;
        private TextButton resetButton;
        private TextButton cheatButton;

        private Window resetWindow;

        Table resetWindowTable;



    @Override
        public void show() {
            // Create the stage and load the skin
            stage = new Stage(new ScreenViewport());
            skin = new Skin(Gdx.files.internal("skins/testskin/test.json"));
            this.batch = new SpriteBatch();

            // Setup reset window and buttons
            resetWindow = new Window("Reset", skin);
            resetWindowTable = new Table();
            resetWindow.setSize(500, 200);
            resetWindow.setPosition(Gdx.graphics.getWidth() / 2 - resetWindow.getWidth() / 2, Gdx.graphics.getHeight() / 2 - resetWindow.getHeight() / 2);
            resetWindow.setVisible(false);
            resetWindow.setMovable(false);
            resetWindow.setResizable(false);
            resetWindow.setModal(true);
            resetWindow.setKeepWithinStage(true);
            resetWindow.add(new Label("Are you sure you want to reset?", skin));
            resetWindow.row();
            TextButton yesButton = new TextButton("Yes", skin);
            TextButton noButton = new TextButton("No", skin);
            resetWindowTable.add(yesButton);
            resetWindowTable.row();
            resetWindowTable.add(noButton);
            resetWindow.add(resetWindowTable);


            yesButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    resetUpgrades();
                    resetWindow.setVisible(false);
                }
            });

            noButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    resetWindow.setVisible(false);
                }
            });



            // Set background image to png
            background = new Sprite(new Texture(Gdx.files.internal("textures/screencomponents/UI/grove.png")));

            background.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            music = Gdx.audio.newMusic(Gdx.files.internal("music/shop.ogg"));
            music.setLooping(true);
            music.setVolume(OptionsScreen.musicVolume);
            music.play();

            // Create small reset button in the top right corner
            resetButton = new TextButton("Reset", skin);
            resetButton.setPosition(Gdx.graphics.getWidth() - resetButton.getWidth(), Gdx.graphics.getHeight() - resetButton.getHeight());

            // Create cheat button in the top left corner
            cheatButton = new TextButton("Cheater D:<", skin);
            cheatButton.setPosition(0, Gdx.graphics.getHeight() - cheatButton.getHeight());

            backButton = new TextButton("Back", skin);
            damageButton = new TextButton("Damage: " + damageLevel + " Cost: " + damageCost, skin);
            fireRateButton = new TextButton("Fire Rate: " + fireRateLevel + " Cost: " + fireRateCost, skin);
            moneyGenerationButton = new TextButton("Money Generation: " + moneyGenerationLevel + " Cost: " + moneyGenerationCost, skin);
            healthButton = new TextButton("Health: " + healthLevel + " Cost: " + healthCost, skin);
            amountButton = new TextButton("Amount: " + amountLevel + " Cost: " + amountCost, skin);

            setDamageLevel(BurnsTowerDefence.prefs.getInteger("damageLevel"));
            setFireRateLevel(BurnsTowerDefence.prefs.getInteger("fireRateLevel"));
            setMoneyGenerationLevel(BurnsTowerDefence.prefs.getInteger("moneyGenerationLevel"));
            setHealthLevel(BurnsTowerDefence.prefs.getInteger("healthLevel"));
            setAmountLevel(BurnsTowerDefence.prefs.getInteger("amountLevel"));

            scoreLabel = new Label("CASHOLA: " + score, skin);

            Table table = new Table();
            table.setFillParent(true);
            table.defaults().pad(10f);

            table.add(damageButton);
            table.row();
            table.add(fireRateButton);
            table.row();
            table.add(moneyGenerationButton);
            table.row();
            table.add(healthButton);
            table.row();
            table.add(amountButton);
            table.row();
            table.add(backButton);
            table.row();

            stage.addActor(table);
            stage.addActor(scoreLabel);
            stage.addActor(resetButton);
            stage.addActor(cheatButton);
            stage.addActor(resetWindow);

            Gdx.input.setInputProcessor(stage);

            // Reset all upgrades and flush the preferences
            resetButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Create popup window to confirm reset
                    resetWindow.setVisible(true);
                }
            });

            cheatButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    score += 1000000;
                }
            });

            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                }
            });

            damageButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (score >= damageCost) {
                        score -= damageCost;
                        setDamageLevel(damageLevel + 1);
                    }
                }
            });
            TowerTextToolTip damageTooltip = new TowerTextToolTip("Increases the DPS of your towers by 20%", TooltipManager.getInstance(), skin);
            damageTooltip.setInstant(true);
            damageButton.addListener(damageTooltip);

            fireRateButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (score >= fireRateCost) {
                        score -= fireRateCost;
                        setFireRateLevel(fireRateLevel + 1);
                    }
                }
            });
            TowerTextToolTip fireRateTooltip = new TowerTextToolTip("Increases the fire rate of your towers by 5%", TooltipManager.getInstance(), skin);
            fireRateTooltip.setInstant(true);
            fireRateButton.addListener(fireRateTooltip);


            moneyGenerationButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (score >= moneyGenerationCost) {
                        score -= moneyGenerationCost;
                        setMoneyGenerationLevel(moneyGenerationLevel + 1);
                    }
                }
            });
            TowerTextToolTip moneyGenerationTooltip = new TowerTextToolTip("Increase your money generation per second\nwhile a wave is active by $1.", TooltipManager.getInstance(), skin);
            moneyGenerationTooltip.setInstant(true);
            moneyGenerationButton.addListener(moneyGenerationTooltip);


            healthButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (score >= healthCost) {
                        score -= healthCost;
                        setHealthLevel(healthLevel + 1);
                    }
                }
            });
            TowerTextToolTip healthTooltip = new TowerTextToolTip("Increases the health of Burns Tower by 50%", TooltipManager.getInstance(), skin);
            healthTooltip.setInstant(true);
            healthButton.addListener(healthTooltip);

            amountButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (score >= amountCost) {
                        score -= amountCost;
                        setAmountLevel(amountLevel + 1);
                    }
                }
            });

            TowerTextToolTip amountTooltip = new TowerTextToolTip("Increase the amount of projectiles that some towers fire by 1.", TooltipManager.getInstance(), skin);
            amountTooltip.setInstant(true);
            amountButton.addListener(amountTooltip);
        }

        public void setDamageLevel(int damageLevel) {
            int difference = damageLevel - this.damageLevel;
            for (int i = 0; i < difference; i++) {
                damageCost *= 1.5;
                this.damageLevel++;
                upgradeDamage *= 1.2;
                damageButton.setText("Damage: " + damageLevel + " Cost: " + damageCost);
            }
        }

        public void setFireRateLevel(int fireRateLevel) {
            int difference = fireRateLevel - this.fireRateLevel;
            for (int i = 0; i < difference; i++) {
                fireRateCost *= 1.5;
                this.fireRateLevel++;
                upgradeFireRate += 5;
                fireRateButton.setText("Fire Rate: " + fireRateLevel + " Cost: " + fireRateCost);
            }
        }

        public void setMoneyGenerationLevel(int moneyGenerationLevel) {
            int difference = moneyGenerationLevel - this.moneyGenerationLevel;
            for (int i = 0; i < difference; i++) {
                moneyGenerationCost *= 1.5;
                this.moneyGenerationLevel++;
                upgradeMoneyGeneration += 1;
                moneyGenerationButton.setText("Money Generation: " + moneyGenerationLevel + " Cost: " + moneyGenerationCost);
            }
        }

        public void setHealthLevel(int healthLevel) {
            int difference = healthLevel - this.healthLevel;
            for (int i = 0; i < difference; i++) {
                healthCost *= 1.5;
                this.healthLevel++;
                upgradeHealth *= 1.5;
                healthButton.setText("Health: " + healthLevel + " Cost: " + healthCost);
            }
        }

        public void setAmountLevel(int amountLevel) {
            int difference = amountLevel - this.amountLevel;
            for (int i = 0; i < difference; i++) {
                amountCost *= 1.5;
                this.amountLevel++;
                amount += 1;
                amountButton.setText("Amount: " + amountLevel + " Cost: " + amountCost);
            }
        }

        // Reset all upgrades and flush the preferences
        public void resetUpgrades() {
            score = 0;

            damageLevel = 1;
            damageCost = 10000;
            upgradeDamage = 1;
            damageButton.setText("Damage: " + damageLevel + " Cost: " + damageCost);

            fireRateLevel = 1;
            fireRateCost = 10000;
            upgradeFireRate = 1;
            fireRateButton.setText("Fire Rate: " + fireRateLevel + " Cost: " + fireRateCost);

            moneyGenerationLevel = 1;
            moneyGenerationCost = 10000;
            upgradeMoneyGeneration = 0;
            moneyGenerationButton.setText("Money Generation: " + moneyGenerationLevel + " Cost: " + moneyGenerationCost);

            healthLevel = 1;
            healthCost = 10000;
            upgradeHealth = 1;
            healthButton.setText("Health: " + healthLevel + " Cost: " + healthCost);

            amountLevel = 1;
            amountCost = 10000;
            amount = 0;
            amountButton.setText("Amount: " + amountLevel + " Cost: " + amountCost);

            BurnsTowerDefence.prefs.putInteger("damageLevel", damageLevel);
            BurnsTowerDefence.prefs.putInteger("fireRateLevel", fireRateLevel);
            BurnsTowerDefence.prefs.putInteger("moneyGenerationLevel", moneyGenerationLevel);
            BurnsTowerDefence.prefs.putInteger("healthLevel", healthLevel);
            BurnsTowerDefence.prefs.putInteger("amountLevel", amountLevel);
            BurnsTowerDefence.prefs.putInteger("score", score);
            BurnsTowerDefence.prefs.flush();
        }

        public void respec() {
            score += damageCost * damageLevel;
            score += fireRateCost * fireRateLevel;
            score += moneyGenerationCost * moneyGenerationLevel;
            score += healthCost * healthLevel;
            score += amountCost * amountLevel;
            resetUpgrades();
        }

        @Override
        public void render(float delta) {
            // Clear the screen
            ScreenUtils.clear(255, 255, 255, 1);
            scoreLabel.setText("CASHOLA: " + score);
            // Draw the background
            batch.begin();
            background.draw(batch);
            batch.end();
            // Update the stage
            stage.act(delta);
            stage.draw();
        }

        @Override
        public void resize(int width, int height) {
            // Update the stage viewport
            stage.getViewport().update(width, height, true);
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
            BurnsTowerDefence.prefs.putInteger("score", score);
            BurnsTowerDefence.prefs.putInteger("damageLevel", damageLevel);
            BurnsTowerDefence.prefs.putInteger("fireRateLevel", fireRateLevel);
            BurnsTowerDefence.prefs.putInteger("moneyGenerationLevel", moneyGenerationLevel);
            BurnsTowerDefence.prefs.putInteger("healthLevel", healthLevel);
            BurnsTowerDefence.prefs.putInteger("amountLevel", amountLevel);
            BurnsTowerDefence.prefs.flush();
        }

        @Override
        public void dispose() {
            // Dispose of the stage and skin
            stage.dispose();
            skin.dispose();
            music.dispose();
        }



        public static float getUpgradeDamage() {
            return upgradeDamage;
        }

        public static float getUpgradeFireRate() {
            return 1 - upgradeFireRate / 100;
        }

        public static int getUpgradeMoneyGeneration() {
            return upgradeMoneyGeneration;
        }

        public static float getUpgradeHealth() {
            return upgradeHealth;
        }

        public static int getUpgradeAmount() {
            return amount;
        }

        public static float getUpgradeRange() {
            return 1;
    }


    }