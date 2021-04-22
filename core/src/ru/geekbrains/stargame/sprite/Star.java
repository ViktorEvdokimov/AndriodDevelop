package ru.geekbrains.stargame.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.stargame.base.Sprite;
import ru.geekbrains.stargame.math.Rect;
import ru.geekbrains.stargame.math.Rnd;

public class Star extends Sprite {

    private Rect worldBounds;
    private Vector2 v;
    private float height;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        v = new Vector2(Rnd.nextFloat(-0.0004F, 0.0004F), Rnd.nextFloat(-0.0014F, -0.004F));
        height = Rnd.nextFloat(0.01F, 0.001F);

    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
        pos.set(Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight()),
                Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop()));
    }

    @Override
    public void update(float woldSpeed, float delta) {
        pos.add(v.x, v.y*woldSpeed);
        height += Rnd.nextFloat(-0.0001f, 0.00015f);
        if (height<0.001f || height>0.01f) height=0.005f;
        setHeightProportional(height);
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }


}
