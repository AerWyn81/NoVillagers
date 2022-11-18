package pro.aerwyn81.novillagers.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.EntityTransformEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import pro.aerwyn81.novillagers.NoVillagers;
import pro.aerwyn81.novillagers.utils.Utils;

import static org.bukkit.entity.EntityType.ZOMBIE_VILLAGER;

public class SpawnHandler implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void chunkLoad(ChunkLoadEvent chunkEvent) {
        World world = chunkEvent.getWorld();
        String worldName = world.getName();

        for (Entity entity: chunkEvent.getChunk().getEntities()) {
            if(entity.getType() != EntityType.VILLAGER){
                continue;
            }

            if (entity.hasMetadata("shopkeeper") || entity.hasMetadata("NPC")) {
                return;
            }

            if (ConfigService.getAllowedVillagerWorlds().contains(worldName)) {
               continue;
            }

            for (Player pl : Bukkit.getOnlinePlayers().stream().filter(p -> NoVillagers.playerDebugList.entrySet()
                    .stream().anyMatch(e -> p.getUniqueId() == e.getKey() && e.getValue())).toList())  {
                pl.sendMessage(LanguageService.getMessage("Debug.Message")
                        .replaceAll("%location%", Utils.parseLocation(entity.getLocation())));
            }

            entity.remove();
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entitySpawnEvent(EntitySpawnEvent entitySpawnEvent) {
        if (entitySpawnEvent.getEntityType() != EntityType.VILLAGER) {
            return;
        }

        Entity entity = entitySpawnEvent.getEntity();

        if (entity.hasMetadata("shopkeeper") || entity.hasMetadata("NPC")) {
            return;
        }

        Location location = entitySpawnEvent.getLocation();
        World world = location.getWorld();

        if (world == null) {
            return;
        }

        if (ConfigService.getAllowedVillagerWorlds().contains(world.getName())) {
            return;
        }

        for (Player pl : Bukkit.getOnlinePlayers().stream().filter(p -> NoVillagers.playerDebugList.entrySet()
                .stream().anyMatch(e -> p.getUniqueId() == e.getKey() && e.getValue())).toList())  {
            pl.sendMessage(LanguageService.getMessage("Debug.Message")
                    .replaceAll("%location%", Utils.parseLocation(entity.getLocation())));
        }

        entitySpawnEvent.setCancelled(true);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityBreed(EntityBreedEvent event) {
        if (event.getEntityType() == EntityType.VILLAGER) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void entityTransform(EntityTransformEvent event) {
        if (event.getTransformReason() == EntityTransformEvent.TransformReason.CURED && event.getEntityType() == ZOMBIE_VILLAGER) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void createSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CURED && event.getEntityType() == ZOMBIE_VILLAGER) {
            event.setCancelled(true);
        }
    }
}
