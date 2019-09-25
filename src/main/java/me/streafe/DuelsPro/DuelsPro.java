package me.streafe.DuelsPro;

import me.streafe.DuelsPro.commands.PlayerList;
import me.streafe.DuelsPro.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class DuelsPro extends JavaPlugin implements Listener {

    private Map<UUID,Player> players;
    private static DuelsPro duelsPro;
    private static DuelsPro instance;
    public List<Player> inGame;
    public Utils utils;

    @Override
    public void onEnable(){
        instance = this;
        this.inGame = new ArrayList<Player>();
        this.players = new HashMap<>();
        this.utils = new Utils();

        getServer().getPluginManager().registerEvents(this,this);

        getConfig().options().copyDefaults(true);
        saveConfig();

        getCommand("PlayerList").setExecutor(new PlayerList());

        getServer().getConsoleSender().sendMessage(this.utils.translate(getConfig().get("duelspro.prefix").toString() + " &dHas been enabled"));
    }

    @Override
    public void onDisable(){

    }

    public static DuelsPro getInstance(){
        return instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        if(!this.players.containsKey(event.getPlayer().getUniqueId())){
            Player playerClass = new Player(event.getPlayer().getUniqueId());
            players.put(playerClass.getPlayerUUID(),playerClass);
            event.getPlayer().sendMessage(getPlayersClassList().toString());
            event.getPlayer().sendMessage(utils.translate(getConfig().get("duelspro.prefix").toString()) + utils.translate("&aSuccessfully added to the databases!"));
        }else{
            event.getPlayer().sendMessage(utils.translate(getConfig().get("duelspro.prefix").toString()) + utils.translate("&cError: &7Please rejoin or ask admin for help"));
            event.getPlayer().sendMessage(utils.translate(getConfig().get("duelspro.prefix").toString()) +utils.translate("&cError code &7-> 1"));
        }

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        if(players.containsKey(event.getPlayer().getUniqueId())){
            players.remove(event.getPlayer().getUniqueId());
        }

    }

    public Set<UUID> getPlayersClassList(){
        return players.keySet();
    }

}