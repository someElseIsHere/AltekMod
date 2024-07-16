package com.swa.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.swdteam.main.DalekMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class VortexScreen extends Screen {
    public static Identifier OVERLAY = new Identifier(DalekMod.MODID, "textures/gui/overlay/flight_overlay.png");
    private VortexCubeMapRenderer vortex = new VortexCubeMapRenderer();

    public VortexScreen() {
        super(new TranslatableText("altekmod.gui.vortex_screen"));
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float delta) {
        super.render(stack, mouseX, mouseY, delta);
        vortex.render(delta, 1.0f);

        RenderSystem.pushMatrix();
        RenderSystem.translatef(0.0F, 0.0F, 200.0F);
        RenderSystem.enableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 0.9F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(OVERLAY);
        float size = Math.max(this.width, this.height);

        ++size;
        RenderSystem.translatef((float)(this.width / 2) - size / 2.0F, (float)(this.height / 2) - size / 2.0F, 0.0F);
        RenderSystem.translatef(-0.5F, -0.5F, 0.0F);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, VertexFormats.POSITION_TEXTURE);
        bufferbuilder.vertex(0.0, size, 90.0).texture(0.0F, 1.0F).next();
        bufferbuilder.vertex(size, size, 90.0).texture(1.0F, 1.0F).next();
        bufferbuilder.vertex(size, 0.0, 90.0).texture(1.0F, 0.0F).next();
        bufferbuilder.vertex(0.0, 0.0, 90.0).texture(0.0F, 0.0F).next();
        tessellator.draw();
        RenderSystem.disableBlend();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.popMatrix();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
