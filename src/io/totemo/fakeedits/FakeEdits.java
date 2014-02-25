package io.totemo.fakeedits;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.diddiz.LogBlock.events.BlockChangePreLogEvent;

// ----------------------------------------------------------------------------
/**
 * Alter LogBlock edits as they are logged to ascribe them to other players.
 */
public class FakeEdits extends JavaPlugin implements Listener {
    // ------------------------------------------------------------------------
    /**
     * Set up player mapping and events.
     */
    @Override
    public void onEnable() {
        saveDefaultConfig();
        _playerMap = getConfig().getConfigurationSection("players");

        // Log the mapping in the server console.
        getLogger().info("Mapping edits from player to player:");
        for (String player : _playerMap.getKeys(false)) {
            getLogger().info(player + " --> " + _playerMap.getString(player));
        }

        getServer().getPluginManager().registerEvents(this, this);
    }

    // ------------------------------------------------------------------------
    /**
     * Ascribe the edit to another player prior to logging.
     */
    @EventHandler
    public void onLogBlockPreLogEvent(BlockChangePreLogEvent event) {
        if (_playerMap.contains(event.getOwner())) {
            event.setOwner(_playerMap.getString(event.getOwner()));
        }
    }

    // ------------------------------------------------------------------------
    /**
     * Map from the original editing player name to replacement player name.
     */
    protected ConfigurationSection _playerMap;
} // class FakeEdits