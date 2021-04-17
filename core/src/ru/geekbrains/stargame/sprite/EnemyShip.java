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
    private float defaultSpeed;
    private  EnemyEmitter enemyEmitter;


    public EnemyShip(BulletPool bulletPool, Rect worldBounds,
                     Sound sound, SpaceShip playerShip, EnemyEmitter enemyEmitter, ExplosionPool explosionPool) {
        super(bulletPool, sound);
        this.bulletPool=bulletPool;
        this.worldBounds=worldBounds;
        this.playerShip = playerShip;
        this.enemyEmitter = enemyEmitter;
        this.explosionPool = explosionPool;
        v = new Vector2();
        temp = new Vector2();
        startV = new Vector2(0, -0.007f);
    }

    @Override
    public void update(float worldSpeed, float delta) {
        bulletStartPosition.set(pos.x, getBottom()-bulletHeight);
        super.update(worldSpeed, delta);
        if (getTop()>worldBounds.getTop()){
            v.set(startV);
        } else {
            if (pos.x - playerShip.getPos().x > defaultSpeed) {
                v.set(-defaultSpeed, 0);
            } else if (pos.x - playerShip.getPos().x < -defaultSpeed) {
                v.set(defaultSpeed, 0);
            } else v.set(0, 0);
            v.add(0, -defaultSpeed * worldSpeed);
        }
        pos.add(v);
        if (getTop() < worldBounds.getBottom()) {
            destroy();
            enemyEmitter.shipOut();
        }
    }

    public void set (
            TextureRegion[] regions,
            float defaultSpeed,
            TextureRegion bulletRegion,
            float bulletHeight,
            Vector2 bulletSpeed,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.defaultSpeed = defaultSpeed;
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
