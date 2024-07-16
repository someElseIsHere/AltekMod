package com.swa.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.swa.client.render.VortexRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class VortexCubeMapRenderer extends RotatingCubeMapRenderer {
    public float time;
    public VortexCubeMapRenderer() {
        super(null);
    }

    @Override
    public void render(float delta, float alpha) {
        int width = MinecraftClient.getInstance().currentScreen.width;
        int height = MinecraftClient.getInstance().currentScreen.height;
        float scale = Math.max(width, height) / 1.25f;

        GlStateManager.pushMatrix();
        GlStateManager.translatef(width / 2f, height / 2f, -100);
        GlStateManager.scalef(scale, scale, 0);

        VortexRenderer.render(new MatrixStack(), time);

        GlStateManager.popMatrix();
        time += MinecraftClient.getInstance().getLastFrameDuration() / 50f;
    }
}