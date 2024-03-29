package com.minelittlepony.ponynpcs;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import noppes.npcs.client.model.ModelPlayerAlt;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.EntityNpcAlex;
import noppes.npcs.entity.EntityNpcClassicPlayer;

@Mod(modid = PonyNPCs.MODID, name = PonyNPCs.NAME, version = PonyNPCs.VERSION, clientSideOnly = true,
        dependencies = "required-client-after:customnpcs"  // I am a client-side dedicated CNPCs addon
)
@Mod.EventBusSubscriber
public class PonyNPCs {
    public static final String MODID = "ponynpcs";
    public static final String NAME = "PonyNPCs";
    public static final String VERSION = "1.0.0";

    @SuppressWarnings({"deprecation"})
    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {  // Noppes uses a deprecated method, so I have to
        RenderingRegistry.registerEntityRenderingHandler(EntityCustomNpc.class, new RenderCustomNpcPony<>(new ModelPlayerAlt(0, false)));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcClassicPlayer.class, new RenderPonyNpc<>(Minecraft.getMinecraft().getRenderManager(), false));
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcAlex.class, new RenderPonyNpc<>(Minecraft.getMinecraft().getRenderManager(), true));
    }
}
