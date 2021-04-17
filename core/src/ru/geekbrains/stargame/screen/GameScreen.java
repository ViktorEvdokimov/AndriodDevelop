package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.Bullet;
import ru.geekbrains.stargame.sprite.EnemyShip;
import ru.geekbrains.stargame.sprite.GameOver;
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
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        stars.resize(worldBounds);
        spaceShip.resize(worldBounds);
        gameOver.resize(worldBounds);
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
        stars.update(worldSpeed, delta);
        if (!spaceShip.isDestroyed()) {
            spaceShip.update(worldSpeed, delta);
            bulletPool.updateActiveSprites(worldSpeed, delta);
            enemyEmitter.generate(delta);
            enemyPool.updateActiveSprites(worldSpeed, delta);
        }
        explosionPool.updateActiveSprites(worldSpeed, delta);
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
        } else gameOver.draw(batch);
        explosionPool.drawActiveSprites(batch);
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
        spaceShip.touchDown(touch,pointer,button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {

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
}

