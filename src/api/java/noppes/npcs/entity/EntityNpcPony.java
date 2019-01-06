package noppes.npcs.entity;

import net.minecraft.world.World;
import noppes.npcs.entity.data.DataDisplay;

/**
 * Stub
 */
public abstract class EntityNpcPony extends EntityNPCInterface {
    public DataDisplay display;

    public EntityNpcPony(World worldIn) {
        super(worldIn);
    }
}