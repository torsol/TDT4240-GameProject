package com.rngg.utils;

import com.rngg.controllers.GameController;
import com.rngg.controllers.LobbyController;
import com.rngg.controllers.MenuController;
import com.rngg.controllers.SettingsController;
import com.rngg.controllers.WaitingRoomController;
import com.rngg.game.Rngg;
import com.rngg.models.GameModel;
import com.rngg.models.Player;
import com.rngg.models.WaitingRoomModel;
import com.rngg.views.GameView;
import com.rngg.views.LobbyView;
import com.rngg.views.MenuView;
import com.rngg.views.SettingsView;
import com.rngg.views.WaitingRoomView;

import java.util.List;

public class ScreenManager {

    private Rngg game;

    public ScreenManager(Rngg game) {
        this.game = game;
    }

    public void setMenuScreen() {
        game.setScreen(new MenuView(new MenuController(game)));
    }

    public void setGameScreen(GameModel model) {
        game.setScreen(new GameView(new GameController(game, model)));
    }

    public void setLobbyScreen() {
        game.setScreen(new LobbyView(new LobbyController(game)));
    }

    public void setSettingsScreen() {
        game.setScreen(new SettingsView(new SettingsController(game)));
    }


    public void setWaitingRoomScreen(WaitingRoomModel model, boolean isQuickGame, boolean isInviteRoom) {
        game.setScreen(new WaitingRoomView(new WaitingRoomController(game, model), isQuickGame, isInviteRoom, model));
    }

}
