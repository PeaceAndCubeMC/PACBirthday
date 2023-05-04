package fr.peaceandcube.pacbirthday.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SuggestionProviders {

    public static List<String> getOnlinePlayers(String prefix) {
        List<String> players = new ArrayList<>();

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().toLowerCase().startsWith(prefix.toLowerCase())) {
                players.add(player.getName());
            }
        }

        return players;
    }
}
