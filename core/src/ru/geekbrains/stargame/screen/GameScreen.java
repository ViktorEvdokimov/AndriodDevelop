package ru.geekbrains.stargame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.StarGame;
import ru.geekbrains.stargame.base.BaseScreen;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.EnemyPool;
import ru.geekbrains.stargame.sprite.Background;
import ru.geekbrains.stargame.sprite.PlayerShip;
import ru.geekbrains.stargame.sprite.SpaceShip;
import ru.geekbrains.stargame.sprite.Stars;
import ru.geekbrains.stargame.units.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private Texture bg;
    private Background background;
    private TextureAtlas atlas;
    private Stars stars;
    private float worldSpeed = 0.0001f;
    private SpaceShip spaceShip;
    private BulletPool bulletPool;
    private EnemyPool enemyPool;
    private Sound bulletSound;
    private EnemyEmitter enemyEmitter;





    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.png");
        background = new Background(bg);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Stars(atlas, 64);
        bulletPool = new BulletPool();
        spaceShip = new PlayerShip(atlas, bulletPool);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyPool = new EnemyPool(bulletPool, worldBounds, bulletSound, spaceShip);
        enemyEmitter = new EnemyEmitter(worldBounds, enemyPool, atlas);
        enemyPool.setEnemyEmitter(enemyEmitter);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        stars.resize(worldBounds);
        spaceShip.resize(worldBounds);

    }

    @Override
    public void render(float delta) {
        worldSpeed += 0.000001f;
        update(delta);
        freeAllDestroyed();
        draw();
    }

    private void update (float delta){
        stars.update(worldSpeed, delta);
        spaceShip.update(worldSpeed, delta);
        bulletPool.updateActiveSprites(worldSpeed, delta);
        enemyEmitter.generate(delta);
        enemyPool.updateActiveSprites(worldSpeed, delta);
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw (){
        batch.begin();
        background.draw(batch);
        stars.draw(batch);
        spaceShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
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

