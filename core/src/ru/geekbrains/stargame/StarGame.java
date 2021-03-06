package ru.geekbrains.stargame;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import ru.geekbrains.stargame.screen.MenuScreen;

public class StarGame extends Game {

	private Music music;

	@Override
	public void create() {
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
		music.setVolume(0.5f);
		music.play();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		music.dispose();
	}
}
