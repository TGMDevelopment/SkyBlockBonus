package ga.matthewtgm.skyblockmod.utils;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class NPCUtils {

    public static final Map<UUID, Vec3> npcLocations = new HashMap<>();

    public static boolean isNPC(Entity entity) {
        if (!(entity instanceof EntityOtherPlayerMP)) {
            return false;
        }
        EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
        return entity.getUniqueID().version() == 2 && entityLivingBase.getHealth() == 20.0F && !entityLivingBase.isPlayerSleeping();
    }

    public static boolean isNearNPC(Entity entity) {
        if (npcLocations != null) {
            for (Vec3 location : npcLocations.values()) {
                if (LocationUtils.getDistanceSquared(location, entity) <= 4) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

}