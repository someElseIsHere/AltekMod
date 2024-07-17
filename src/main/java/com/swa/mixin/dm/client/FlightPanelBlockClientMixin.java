package com.swa.mixin.dm.client;

import com.swa.client.screen.VortexScreen;
import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.block.tardis.FlightPanelBlock;
import com.swdteam.common.init.DMDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FlightPanelBlock.class, remap = false)
public class FlightPanelBlockClientMixin {
    @Inject(method = "onUse", remap = true, at = @At("RETURN"))
    public void beforeOnUse(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockHitResult blockHitResult, CallbackInfoReturnable<ActionResult> cir) {
        if (worldIn.isClient && MinecraftClient.getInstance().player.world.getRegistryKey() == DMDimensions.TARDIS && ClientTardisCache.getTardisData(pos).isInFlight()){
            MinecraftClient.getInstance().openScreen(new VortexScreen());
        }
    }
}