package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.pool.BulletPool;
import ru.geekbrains.stargame.pool.ExplosionPool;
import ru.geekbrains.stargame.units.EnemyEmitter;

public class EnemyShip extends SpaceShip{

    private Vector2 startV;
    private  SpaceShip playerShip;
    private Vector2 defaultSpeed;
    private  EnemyEmitter enemyEmitter;
    private Vector2 speed;
    private int mulVX = 1;



    public EnemyShip(BulletPool bulletPool, Rect worldBounds,
                     Sound sound, SpaceShip playerShip, EnemyEmitter enemyEmitter, ExplosionPool explosionPool) {
        super(bulletPool, sound);
        this.bulletPool=bulletPool;
        this.worldBounds=worldBounds;
        this.playerShip = playerShip;
        this.enemyEmitter = enemyEmitter;
        this.explosionPool = explosionPool;
        defaultSpeed = new Vector2();
        v = new Vector2();
        temp = new Vector2();
        speed = new Vector2();
        startV = new Vector2(0, -0.007f);
        this.speedMul = speedMul * 5f;
    }

    @Override
    public void update(int level, float delta) {
        bulletStartPosition.set(pos.x, getBottom()-bulletHeight);
        super.update(level, delta);
        v.set(mulVX * defaultSpeed.x * speedMul * (1 + (float)level/10), -defaultSpeed.y * speedMul * (1 + (float)level/10));
        if (getTop()>worldBounds.getTop()){
            v.set(startV);
        }
        pos.add(v);
        if (getTop() < worldBounds.getBottom() ) {
            destroy();
            enemyEmitter.shipOut();
        } else if (getRight() > worldBounds.getRight() || getLeft() < worldBounds.getLeft()){
            mulVX = -1 * mulVX;
        }
    }

    public void set (
            TextureRegion[] regions,
            Vector2 defaultSpeed,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletSpeed,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.defaultSpeed.set(defaultSpeed);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV = bulletSpeed;
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        setHeightProportional(height);
        this.hp = hp;
        reloadTimer = reloadInterval * 0.8f;
    }

    @Override
    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }

}
