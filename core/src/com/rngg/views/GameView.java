package com.rngg.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rngg.controllers.GameController;
import com.rngg.game.Rngg;
import com.rngg.models.GameMap;
import com.rngg.models.HexMap;
import com.rngg.models.Player;
import com.rngg.models.SquareMap;
import com.rngg.utils.Assets;
import com.rngg.utils.GameAssetManager;

import java.util.List;

public class GameView extends View {

    private GameController controller;

    private SpriteBatch batch;
    private BitmapFont font;
    private MapRenderer mapRenderer;
    private ShapeRenderer sr;
    private List<Player> players;
    private HUDRenderer hudRenderer;


    public GameView(GameAssetManager assetManager, GameController controller, List<Player> players) {
        super(assetManager);
        camera.viewportHeight = (float) (Rngg.HEIGHT * 10 / 8);

        this.controller = controller;
        this.players = players;

        batch = new SpriteBatch();
        font = assetManager.manager.get(Assets.MINECRAFTIA);
        font.setColor(Color.WHITE);
        this.sr = new ShapeRenderer();
        mapRenderer = getMapRenderer();
        hudRenderer = new HUDRenderer(controller.gameModel, font, assetManager, controller);
    }

    @Override
    public void render(float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        sr.setProjectionMatrix(camera.combined);

        controller.update(delta);
        controller.setCamera(camera);

        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapRenderer.draw();
        hudRenderer.draw();
    }

    private MapRenderer getMapRenderer() {
        GameMap map = controller.gameModel.getMap();

        if (map instanceof SquareMap) {
            return new SquareMapRenderer((SquareMap) map, sr, batch, font);
        } else if (map instanceof HexMap) {
            return new HexMapRenderer((HexMap) map, sr, batch, font);
        }

        return null;
    }

    public ShapeRenderer getSR() {
        return this.sr;
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return this.batch;
    }
}
