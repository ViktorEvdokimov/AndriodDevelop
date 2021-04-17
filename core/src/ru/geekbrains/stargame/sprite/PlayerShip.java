package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;

public class PlayerShip extends SpaceShip {


    private Vector2 purpose = new Vector2();
    private Vector2 direction = new Vector2();
    private Vector2 temp = new Vector2();
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private BulletPool bulletPool;
    private Vector2 bulletV;
    protected TextureRegion bulletRegion;
    private Sound sound;


    public PlayerShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletRegion = atlas.findRegion("bulletMainShip");
        bulletV = new Vector2(0, 0.5f);
        sound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        reloadInterval = 0.25f;
        setHeightProportional(0.15f);
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
    public void update(float speed, float delta) {
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        super.update(speed, delta);
        temp.set(purpose);
        direction.set(purpose);
        direction.sub(pos);
        direction.setLength(0.003f+speed);
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
        if (upPressed) purpose.add(0, 0.003f+speed);
        if (downPressed) purpose.add(0, -(0.003f+speed));
        if (rightPressed) purpose.add(0.003f+speed, 0);
        if (leftPressed) purpose.add(-(0.003f+speed), 0);

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


    protected void shoot (){
        Bullet bullet = bulletPool.obtain();
        temp.set(pos.x, getTop());
        sound.play(0.1f);
        bullet.set(this, bulletRegion, this.temp, bulletV, worldBounds, 1, 0.01f);
    }
}