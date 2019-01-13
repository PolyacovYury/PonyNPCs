package com.minelittlepony.ponynpcs;

import com.minelittlepony.model.PMAPI;
import com.minelittlepony.pony.data.IPony;
import com.minelittlepony.pony.data.Pony;
import com.minelittlepony.render.RenderPonyMob;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.voxelmodpack.hdskins.HDSkinManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.renderer.RenderNPCInterface;
import noppes.npcs.entity.EntityNpcPony;

import javax.annotation.Nonnull;
import java.util.Map;

public class RenderPonyNpc<Npc extends EntityNpcPony> extends RenderNPCInterface<Npc> {

    private RenderPonyMob.Proxy<Npc> ponyRenderer;

    public RenderPonyNpc(RenderManager manager) {
        super(PMAPI.earthpony.getBody(), 0.5F);

        this.ponyRenderer = new RenderPonyMob.Proxy<Npc>(this.layerRenderers, manager, PMAPI.earthpony) {
            @Override
            public ResourceLocation getTexture(Npc entity) {
                if (entity.display.skinType == 1 && entity.display.playerProfile != null) {
                    entity.textureLocation = getProfileTexture(entity.display.playerProfile);
                } else {
                    entity.textureLocation = RenderPonyNpc.super.getEntityTexture(entity);
                }

                return entity.textureLocation;
            }
        };
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(Npc entity) {
        return ponyRenderer.getTextureFor(entity);
    }

    @Override
    protected void preRenderCallback(Npc entity, float ticks) {
        IPony pony = ponyRenderer.getEntityPony(entity);

        ponyRenderer.getInternalRenderer().setPonyModel(pony.getRace(false).getModel().getModel(false));
        ponyRenderer.preRenderCallback(entity, ticks);

        setMainModel(ponyRenderer.getModelWrapper().getBody());

        super.preRenderCallback(entity, ticks);
    }

    protected void setMainModel(ModelBase model) {
        this.mainModel = model;
    }

    //Copied from MineLittlePony's PlayerSkullRenderer
    private ResourceLocation getProfileTexture(GameProfile profile) {
        ResourceLocation skin = HDSkinManager.INSTANCE.getTextures(profile).get(MinecraftProfileTexture.Type.SKIN);

        if (skin != null && Pony.getBufferedImage(skin) != null) {
            return skin;
        }

        Minecraft minecraft = Minecraft.getMinecraft();
        Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(profile);

        if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
            ResourceLocation loc = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
            if (Pony.getBufferedImage(loc) != null) {
                return loc;
            }
        }

        return DefaultPlayerSkin.getDefaultSkin(EntityPlayer.getUUID(profile));
    }
}