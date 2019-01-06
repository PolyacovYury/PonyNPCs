package com.minelittlepony.cnpcs;

import java.io.File;

import com.mumfrey.liteloader.InitCompleteListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.util.ModUtilities;

import net.minecraft.client.Minecraft;
import noppes.npcs.entity.EntityNpcPony;

public class LiteModCNPCs implements LiteMod, InitCompleteListener {

	@Override
	public String getName() {
		return "@NAME@";
	}

	@Override
	public String getVersion() {
		return "@VERSION@";
	}

	@Override
	public void init(File configPath) {

	}

	@Override
	public void onInitCompleted(Minecraft minecraft, LiteLoader loader) {
	    ModUtilities.addRenderer(EntityNpcPony.class, new RenderPonyNpc<>(minecraft.getRenderManager()));
	}

	@Override
	public void upgradeSettings(String version, File configPath, File oldConfigPath) {

	}
}
