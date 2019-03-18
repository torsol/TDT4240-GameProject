package com.rngg.controllers;

import com.badlogic.gdx.utils.Sort;
import com.rngg.game.Rngg;
import com.rngg.models.GameModel;
import com.rngg.models.Player;
import com.rngg.models.WaitingRoomModel;
import com.rngg.services.IPlayServices;
import com.rngg.services.Message;
import com.rngg.services.NetworkManager;
import com.rngg.services.RealtimeListener;
import com.rngg.services.RoomListener;
import com.rngg.views.GameView;
import com.rngg.views.WaitingRoomView;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

public class WaitingRoomController extends Controller implements RoomListener, RealtimeListener {

    private IPlayServices sender;
    private WaitingRoomView waitingRoomView;
    private HashMap<String, Player> playerInfo = new HashMap<String, Player>(4);
    int leftToReady = 4;
    private Player localPlayer;
    private WaitingRoomModel model;

    public WaitingRoomController(Rngg game, WaitingRoomModel model) {

        super(game);
        this.model = model;
    }

    @Override
    public void update(float delta) {

    }

    public void enterGameScreen() {

        Player[] players = playerInfo.values().toArray(new Player[playerInfo.size()]);
        Sort.instance().sort(players, new Comparator<Player>() {
            @Override
            public int compare(Player p1, Player p2) {
                return p1.randomNumber - p2.randomNumber;
            }
        });

        for(int i = 0; i < players.length; i++){
            players[i].playerIndex = i;
        }
        NetworkManager netManager = NetworkManager.getInstance();
        netManager.setPlayers(Arrays.asList(players));
        game.screenManager.setGameScreen(new GameModel(4), Arrays.asList(players));;
    }

    @Override
    public void handleDataReceived(Message message) {
        String type = message.getString();
        System.out.println(type);
        if(type.equals("RAN_NUM")) {
            int num = message.getBuffer().getInt();
            System.out.println("Got number");
            System.out.println(num);
            System.out.println(message.getSender() + " " + game.getAPI().getLocalID());

            Player player = playerInfo.get(message.getSender());
            player.randomNumber = num;

            // Do a check to see if ready
            boolean gotall = true;
            for (Player p : playerInfo.values()) {
                if (p.randomNumber <= 0) {
                    gotall = false;
                }
            }
            if (gotall) {
                Message readyMessage = new Message(new byte[512], "", 0);
                readyMessage.putString("READY");
                sender.sendToAllReliably(readyMessage.getData());
            }
        } else if(type.equals("READY")){
            System.out.println("Player is ready " + message.getSender());
            leftToReady--;

            if(leftToReady <= 0){
                model.joinedRoom = true;
            }
        }

    }

    @Override
    public void setSender(IPlayServices playServices) {

    }

    @Override
    public void roomConnected() {

    }

    @Override
    public void leftRoom() {
        model.leftRoom = true;
    }
    public Rngg getGame(){
        return game;
    }
}
