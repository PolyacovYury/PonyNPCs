package com.minelittlepony.ponynpcs;

import com.google.common.collect.Lists;
import com.minelittlepony.model.PMAPI;
import com.minelittlepony.pony.data.IPony;
import com.minelittlepony.pony.data.Pony;
import com.minelittlepony.render.RenderPonyMob;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.voxelmodpack.hdskins.HDSkinManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import noppes.npcs.client.model.ModelClassicPlayer;
import noppes.npcs.client.model.ModelPlayerAlt;
import noppes.npcs.client.renderer.RenderCustomNpc;
import noppes.npcs.entity.EntityCustomNpc;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class RenderPonyNpc<Npc extends EntityCustomNpc> extends RenderCustomNpc<Npc> {

    private final RenderPonyMob.Proxy<Npc> ponyRenderer;
    private final boolean slimArms;
    private final ModelBiped humanModel;
    protected List<LayerRenderer<Npc>> ponyLayers = Lists.newArrayList();
    protected List<LayerRenderer<Npc>> humanLayers;

    public RenderPonyNpc(RenderManager manager, boolean slimArms) {
        super(PMAPI.earthpony.getBody());
        this.slimArms = slimArms;
        humanModel = slimArms ? new ModelPlayerAlt(0, true) : new ModelClassicPlayer(0);
        humanLayers = layerRenderers;
        this.ponyRenderer = new RenderPonyMob.Proxy<Npc>(this.ponyLayers, manager, PMAPI.earthpony) {
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
        if (pony.getRace(true).isHuman()) {
            this.layerRenderers = this.humanLayers;
            this.mainModel = this.humanModel;
        } else {
            this.layerRenderers = this.ponyLayers;
            ponyRenderer.getInternalRenderer().setPonyModel(pony.getRace(false).getModel().getModel(slimArms));
            ponyRenderer.preRenderCallback(entity, ticks);
            this.mainModel = ponyRenderer.getModelWrapper().getBody();
        }
        super.preRenderCallback(entity, ticks);
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