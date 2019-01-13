package com.minelittlepony.ponynpcs;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import noppes.npcs.entity.EntityNpcPony;

@Mod(modid = PonyNPCs.MODID, name = PonyNPCs.NAME, version = PonyNPCs.VERSION, clientSideOnly = true)
@Mod.EventBusSubscriber
public class PonyNPCs {
    public static final String MODID = "ponynpcs";
    public static final String NAME = "PonyNPCs";
    public static final String VERSION = "1.0.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {  // Noppes uses a deprecated method, so I have to
        //noinspection unchecked,deprecation
        RenderingRegistry.registerEntityRenderingHandler(EntityNpcPony.class, new RenderPonyNpc(Minecraft.getMinecraft().getRenderManager()));
    }
}
