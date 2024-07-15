package com.swa;

import com.google.gson.Gson;
import net.minecraft.util.Identifier;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AltekMod.MODID)
public class AltekMod {
    public static final Gson GSON = new Gson();
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "altekmod";

    public AltekMod() {}
}