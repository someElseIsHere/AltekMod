package com.swa.mixin.dm.client;

import com.swa.client.screen.AltMUScreen;
import com.swdteam.client.init.BusClientEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(BusClientEvents.class)
public class BusClientEventsMixin {

    @ModifyArg(
            method = "guiEvent(Lnet/minecraftforge/client/event/GuiScreenEvent$InitGuiEvent;)V", remap = false, index = 4,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V", remap = true)
    )
    private static Text modifyDMUButtonText(Text text) {
        return new TranslatableText("altekmod.altmu.name");
    }

    @ModifyArg(
            method = "guiEvent(Lnet/minecraftforge/client/event/GuiScreenEvent$InitGuiEvent;)V", remap = false, index = 5,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;<init>(IIIILnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)V", remap = true)
    )
    private static ButtonWidget.PressAction modifyDMUButtonAction(ButtonWidget.PressAction action) {
        Screen screen = MinecraftClient.getInstance().currentScreen;
        return (button) -> MinecraftClient.getInstance().openScreen(new AltMUScreen(screen));
    }
}