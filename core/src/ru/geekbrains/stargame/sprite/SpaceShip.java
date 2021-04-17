package ru.geekbrains.stargame.sprite;


import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;

public class SpaceShip extends Sprite {

    protected final float DAMAGE_ANIMATE_INTERVAL = 0.1f;
    protected Rect worldBounds;
    protected  float reloadInterval;
    protected float reloadTimer;
    protected BulletPool bulletPool;
    protected Vector2 temp;
    protected Sound sound;
    protected TextureRegion bulletRegion;
    protected int damage;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected Vector2 v;
    protected Vector2 bulletStartPosition;
    protected int hp;
    protected float damageAnimateTimer;
    protected ExplosionPool explosionPool;



    public SpaceShip(BulletPool bulletPool, Sound sound){
        this.bulletPool = bulletPool;
        this.sound = sound;
        temp = new Vector2();
        v = new Vector2();
        bulletV = new Vector2();
        bulletStartPosition = new Vector2();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    @Override
    public void update(float speed, float delta) {
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }

    @Override
    public void draw(SpriteBatch batch){
        super.draw(batch);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    protected void shoot () {
        Bullet bullet = bulletPool.obtain();
        sound.play(0.1f);
        bullet.set(this, bulletRegion, this.bulletStartPosition, bulletV, worldBounds, damage, bulletHeight);
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public boolean isBulletCollision(Rect bullet){
        return false;
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(pos, getHeight());
    }
}
