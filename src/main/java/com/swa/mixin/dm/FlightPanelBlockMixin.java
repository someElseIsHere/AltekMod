package com.swa.mixin.dm;

import com.swdteam.common.block.tardis.FlightPanelBlock;
import com.swdteam.util.ChatUtil;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FlightPanelBlock.class, remap = false)
public class FlightPanelBlockMixin {
    @Redirect(
            method = "onUse", remap = true,
            at = @At(value = "INVOKE", remap = false, target = "Lcom/swdteam/util/ChatUtil;sendError(Lnet/minecraft/entity/player/PlayerEntity;Ljava/lang/String;Lcom/swdteam/util/ChatUtil$MessageType;)V")
    )
    public void cancelSendError(PlayerEntity player, String s, ChatUtil.MessageType type) {}
}
