package com.swa.mixin.dm.client;

import com.swa.client.screen.AltMUScreen;
import com.swa.client.screen.VortexCubeMapRenderer;
import com.swdteam.client.init.BusClientEvents;
import com.swdteam.main.DMConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(
            method = "guiEvent(Lnet/minecraftforge/client/event/GuiScreenEvent$InitGuiEvent;)V", remap = false,
            at = @At(value = "RETURN")
    )
    private static void postGuiEvent(GuiScreenEvent.InitGuiEvent event, CallbackInfo ci) {
        if (event.getGui() instanceof TitleScreen && DMConfig.CLIENT.customTitleScreen.get()) {
            TitleScreen screen = (TitleScreen) event.getGui();

            for(int i = 0; i < screen.buttons.size(); ++i) {
                ClickableWidget w = screen.buttons.get(i);
                if (w.getMessage() instanceof TranslatableText && ((TranslatableText) w.getMessage()).getKey().equalsIgnoreCase("menu.online")) {
                    ButtonWidget b = new ButtonWidget(w.x, w.y, w.getWidth(), w.getHeight(), new TranslatableText("altekmod.altmu.gui.button.discord"),
                            (button) -> Util.getOperatingSystem().open("https://discord.gg/7XwAg3hBN5")
                    );
                    screen.children.add(screen.children.indexOf(w), b);
                    screen.buttons.set(i, b);
                }
            }
        }
    }

    @Inject(
            method = "guiEvent(Lnet/minecraftforge/client/event/GuiOpenEvent;)V", remap = false,
            at = @At(value = "TAIL")
    )
    private static void postGuiEvent(GuiOpenEvent event, CallbackInfo ci) {
        if (event.getGui() instanceof TitleScreen && DMConfig.CLIENT.customTitleScreen.get()) {
            if (!(((TitleScreen)event.getGui()).backgroundRenderer instanceof VortexCubeMapRenderer)){
                ((TitleScreen)event.getGui()).backgroundRenderer = new VortexCubeMapRenderer();
            }
        }
    }
}