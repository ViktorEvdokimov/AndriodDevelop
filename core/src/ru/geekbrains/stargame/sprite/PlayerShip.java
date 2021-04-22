package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Split;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class PlayerShip extends SpaceShip {

    protected final int DEFAULT_HP = 10;
    private Vector2 purpose = new Vector2();
    private Vector2 direction = new Vector2();
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private int previousLevel = 1;


    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool,
                      ExplosionPool explosionPool, Sound sound) {
        super(bulletPool, sound);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.explosionPool = explosionPool;
        regions = Split.split(atlas.findRegion("main_ship"), 1, 2, 2);
        bulletV = new Vector2(0, 0.5f);
        reloadInterval = 0.25f;
        setHeightProportional(0.15f);
        this.damage =1;
        this.bulletHeight = 0.01f;
        hp = DEFAULT_HP;
        this.speedMul = 1.5f * speedMul;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        pos.set(0f, worldBounds.getBottom());
    }

    public void dispose (){
        sound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        purpose.set(touch);
        return false;
    }

    @Override
    public void update(int level, float delta) {
        bulletStartPosition.set(pos.x, getTop()+bulletHeight);
        super.update(level, delta);
        if (previousLevel < level) hp +=4 + level;
        previousLevel = level;
        temp.set(purpose);
        direction.set(purpose);
        direction.sub(pos);
        direction.setLength(speedMul * delta * (1 + (float)level/10));
        if(temp.sub(pos).len()<=direction.len()) {
            pos.set(purpose);
        }
        else pos.add(direction);
        if (getTop() > worldBounds.getTop()) {
            purpose.set(purpose.x, worldBounds.getTop()-getHalfHeight());
        }
        if (getBottom() < worldBounds.getBottom()) {
            purpose.set(purpose.x, worldBounds.getBottom()+getHalfHeight());
        }
        if (getLeft() < worldBounds.getLeft()) {
            purpose.set(worldBounds.getLeft()+getHalfWidth(), purpose.y);
        }
        if (getRight() > worldBounds.getRight()) {
            purpose.set(worldBounds.getRight()-getHalfWidth(), purpose.y);
        }
        if (upPressed) purpose.add(0, speedMul * delta *(1 + (float)level/10) );
        if (downPressed) purpose.add(0, -(speedMul* delta *(1 + (float)level/10)));
        if (rightPressed) purpose.add(speedMul* delta *(1 + (float)level/10), 0);
        if (leftPressed) purpose.add(-(speedMul* delta *(1 + (float)level/10)), 0);

    }
    @Override
    public boolean keyDown(int keycode) {
        System.out.println(keycode);
        if (keycode==19 || keycode==51) upPressed=true;
        if (keycode==20 || keycode==47) downPressed=true;
        if (keycode==21 || keycode==29) leftPressed=true;
        if (keycode==22 || keycode==32) rightPressed=true;
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode==19 || keycode==51) upPressed=false;
        if (keycode==20 || keycode==47) downPressed=false;
        if (keycode==21 || keycode==29) leftPressed=false;
        if (keycode==22 || keycode==32) rightPressed=false;
        return false;
    }

    @Override
    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }

    @Override
    public void newGame() {
        hp = DEFAULT_HP;
        upPressed=false;
        downPressed=false;
        leftPressed=false;
        rightPressed=false;
        pos.set(0f, worldBounds.getBottom());
        purpose.set(0f, worldBounds.getBottom());
    }
}