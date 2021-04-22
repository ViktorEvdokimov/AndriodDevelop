package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;

public class Explosion extends Sprite {

    private static float ANIMATE_INTERVAL = 0.017f;

    private final Sound explosionSound;
    private float animateTimer;
    private float speedMul = -0.1f;

    public Explosion(TextureAtlas atlas, Sound explosionSound) {
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.explosionSound = explosionSound;
    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportional(height);
        explosionSound.play(0.1f);
    }

    @Override
    public void update(int level, float delta) {
        animateTimer += delta;
        if (animateTimer >= ANIMATE_INTERVAL) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
        pos.add(0, delta * speedMul * (1 + (float)level/10));
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}

