package com.ru.tgra.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.ru.tgra.BBArcTanGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "BB Arc Tan";
		config.width = 465;
		config.height = 720;
		config.resizable = false;

		new LwjglApplication(new BBArcTanGame(), config);
	}
}
