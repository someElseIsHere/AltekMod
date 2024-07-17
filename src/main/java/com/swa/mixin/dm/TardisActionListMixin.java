package com.swa.mixin.dm;

import com.swdteam.common.tardis.actions.TardisActionList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = TardisActionList.class, remap = false)
public class TardisActionListMixin {
    @Inject(method = "addForceField", at = @At("HEAD"), cancellable = true)
    private static void addForceField(ServerWorld serverWorld, BlockPos pos, CallbackInfo ci) {
        ci.cancel();
    }
}
