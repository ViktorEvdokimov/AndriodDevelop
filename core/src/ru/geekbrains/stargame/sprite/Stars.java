package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class Stars {

    private Star[] stars;
    private Rect worldBounds;
    private int level;

    public Stars(TextureAtlas atlas, int starsCount) {
        stars = new Star[starsCount];
        for (int i=0; i<starsCount; i++) {
            stars[i] = new Star(atlas);
        }

    }


    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        for (Star star: stars) {
            star.resize(worldBounds);
        }
    }

    public void update(int level, float delta) {
        this.level=level;
        for (Star star: stars) {
            star.update(level, delta);
        }
    }

    public void draw(SpriteBatch batch) {
        for (Star star: stars) {
            star.draw(batch);
        }
    }


}
