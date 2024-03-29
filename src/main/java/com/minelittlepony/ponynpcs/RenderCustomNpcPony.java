package com.minelittlepony.ponynpcs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.entity.EntityCustomNpc;

public class RenderCustomNpcPony<T extends EntityCustomNpc> extends RenderCustomNpc<T> {
    public RenderCustomNpcPony(ModelBiped model) {
        super(model);
    }

    public void doRender(T npc, double d, double d1, double d2, float f, float partialTicks) {
        EntityLivingBase entity = npc.modelData.getEntity(npc);
        if (entity != null) {
            Render<Entity> render = renderManager.getEntityRenderObject(entity);
            if (render instanceof RenderPonyNpc) {
                npc.textureLocation = ((EntityCustomNpc) entity).textureLocation;
            }
        }
        super.doRender(npc, d, d1, d2, f, partialTicks);

    }
}