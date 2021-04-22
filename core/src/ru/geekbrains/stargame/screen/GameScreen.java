package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.base.Font;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.EnemyShip;
import ru.geekbrains.stargame.sprite.GameOver;
import ru.geekbrains.stargame.sprite.NewGameButton;
import ru.geekbrains.stargame.sprite.PlayerShip;
import ru.geekbrains.stargame.sprite.SpaceShip;
import ru.geekbrains.stargame.sprite.Stars;
import ru.geekbrains.stargame.units.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Stars stars;
    private float worldSpeed = 1f;
    private PlayerShip spaceShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Sound bulletSound;
    private EnemyEmitter enemyEmitter;
    private Sound laserSound;
    private Sound explosionSound;
    private ExplosionPool explosionPool;
    private GameOver gameOver;
    private NewGameButton newGameButton;

    private Font font;
    private static final float FONT_SIZE = 0.02f;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;
    private static final float PADDING = 0.01f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private int frags;
    private int level;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Stars(atlas, 64);
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        bulletPool = new BulletPool();
        laserSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        spaceShip = new PlayerShip(atlas, bulletPool, explosionPool, laserSound);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound, spaceShip, explosionPool);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyPool, atlas);
        enemyPool.setEnemyEmitter(enemyEmitter);
        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas, this);

        font = new Font("font/font.fnt", "font/font.png");
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        frags = 0;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        stars.resize(worldBounds);
        spaceShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
        font.setSize(FONT_SIZE);
    }

    @Override
    public void render(float delta) {
        worldSpeed += 0.0001f;
        update(delta);
        checkCollision();
        freeAllDestroyed();
        draw();
    }

    private void update (float delta){
        level = 1 + frags / 10;
        stars.update(level, delta);
        if (!spaceShip.isDestroyed()) {
            spaceShip.update(level, delta);
            bulletPool.updateActiveSprites(level, delta);
            enemyEmitter.generate(level, delta);
            enemyPool.updateActiveSprites(level, delta);
        }
        explosionPool.updateActiveSprites(level, delta);
    }

    private void checkCollision() {
        if (spaceShip.isDestroyed()) {
            return;
        }
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + spaceShip.getHalfWidth();
            if (enemyShip.getPos().dst(spaceShip.getPos()) < minDist) {
                enemyShip.destroy();
                spaceShip.damage(enemyShip.getDamage() * 2);
            }
        }
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed() || bullet.getOwner() != spaceShip) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {
                        frags++;
                    }
                }
            }
            if (bullet.getOwner() != spaceShip) {
                if (spaceShip.isBulletCollision(bullet)) {
                    spaceShip.damage(bullet.getDamage());
                    bullet.destroy();
                }
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
    }

    private void draw (){
        batch.begin();
        background.draw(batch);
        stars.draw(batch);
        if (!spaceShip.isDestroyed()) {
            spaceShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();
        bulletSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (spaceShip.isDestroyed()) {
            newGameButton.touchDown(touch, pointer, button);
        } else spaceShip.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if  (spaceShip.isDestroyed()) {
            newGameButton.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        spaceShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        spaceShip.keyUp(keycode);
        return false;
    }

    public void newGame (){
        enemyPool.cleanAllActiveElements();
        bulletPool.cleanAllActiveElements();
        worldSpeed = 1f;
        spaceShip.newGame();
        spaceShip.flushDestroy();
        frags = 0;
    }

    private void printInfo() {
        sbFrags.setLength(0);
        sbHp.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING);
        font.draw(batch, sbHp.append(HP).append(spaceShip.getHp()), worldBounds.getPos().x, worldBounds.getTop() - PADDING, Align.center);
        font.draw(batch, sbLevel.append(LEVEL).append(level), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);
    }
}

