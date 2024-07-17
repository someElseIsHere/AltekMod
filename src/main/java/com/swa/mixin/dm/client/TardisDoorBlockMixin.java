package com.swa.mixin.dm.client;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.block.tardis.TardisDoorBlock;
import com.swdteam.common.init.DMDimensions;
import com.swdteam.common.tardis.TardisData;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TardisDoorBlock.class)
public class TardisDoorBlockMixin {

    @Inject(method = "getRenderType", at = @At("HEAD"), cancellable = true)
    public void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> cir) {
        if (MinecraftClient.getInstance().player.world.getRegistryKey() == DMDimensions.TARDIS) {
            TardisData tardisCache = ClientTardisCache.getTardisData(MinecraftClient.getInstance().player.getBlockPos());
            if (!tardisCache.isInFlight()) cir.setReturnValue(BlockRenderType.INVISIBLE);
        }
    }
}
