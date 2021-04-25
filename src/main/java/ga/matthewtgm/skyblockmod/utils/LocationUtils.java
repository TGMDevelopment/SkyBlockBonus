package ga.matthewtgm.skyblockmod.utils;

import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class LocationUtils {

    public static double getDistanceSquared(Vec3 entity, Entity entity1) {
        double d0 = entity.xCoord - entity1.posX;
        double d1 = entity.yCoord - entity1.posY;
        double d2 = entity.zCoord - entity1.posZ;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

}