package ru.geekbrains.stargame.pool;

import ru.geekbrains.stargame.base.BaseSpritesPool;
import ru.geekbrains.stargame.sprite.Bullet;

public class BulletPool  extends BaseSpritesPool<Bullet> {

    @Override
    protected Bullet newSprite() {
        return new Bullet();
    }
}
