package ru.geekbrains.stargame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class StarGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;
	Texture img;
	int coordinateX;
	int coordinateY;
	Vector2 direction = new Vector2(0, 0);
	Vector2 position = new Vector2(0, 0);


	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("backGround.jpg");
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		getDirection();
		if (Math.abs(position.x-coordinateX)<direction.x && Math.abs(position.y-coordinateY)<direction.y) {
			direction.set(0,0);
		}
		position.add(direction);
		batch.draw(img, position.x, position.y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		coordinateX=screenX;
		coordinateY=Gdx.graphics.getHeight()-screenY;
		getDirection();
		return false;
	}

	private void getDirection (){
		direction.set(coordinateX, coordinateY);
		direction.sub(position);
		direction.nor();
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}
}
