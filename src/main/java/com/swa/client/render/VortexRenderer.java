package com.swa.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.swa.AltekMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public class VortexRenderer {
    public static final Identifier TEXTURE = new Identifier(AltekMod.MODID, "textures/vortex.png");

    public static void render(MatrixStack matrixStack, VertexConsumer builder, float delta) {
        matrixStack.push();
        RenderSystem.enableCull();

        RenderSystem.enableTexture();

        matrixStack.scale(10f, 10f, 1);

        float f0 = (float) Math.toDegrees(2f * Math.sin(delta * 0.5f));
        float f2 = f0 / 360.0f - (int) (f0 / 360.0);
        float f3 = 0f * f2 - (int) (0f * f2);
        GL11.glRotated(f2 * 360.0, 0.0, 0.0, 1.0);

        MinecraftClient.getInstance().textureManager.bindTexture(TEXTURE);

        for (int i = 0 ; i < 24; ++i) {
            renderSection(delta, builder, i, delta * -4f, f3, (float) Math.sin(i * Math.PI / 36), (float) Math.sin((i + 1) * Math.PI / 36));
        }

        RenderSystem.disableCull();
        matrixStack.pop();
    }

    public static void render(MatrixStack matrixStack, float delta) {
        matrixStack.push();
        RenderSystem.enableCull();

        RenderSystem.enableTexture();

        matrixStack.scale(10f, 10f, 1);

        float f0 = (float) Math.toDegrees(2f * Math.sin(delta * 0.5f));
        float f2 = f0 / 360.0f - (int) (f0 / 360.0);
        float f3 = 0f * f2 - (int) (0f * f2);
        GL11.glRotated(f2 * 360.0, 0.0, 0.0, 1.0);

        MinecraftClient.getInstance().textureManager.bindTexture(TEXTURE);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        buffer.begin(GL11.GL_QUADS, VertexFormats.POSITION_TEXTURE);

        for (int i = 0 ; i < 24; ++i) {
            renderSection(delta, buffer, i, delta * -4.0f, f3, (float) Math.sin(i * Math.PI / 36), (float) Math.sin((i + 1) * Math.PI / 36));
        }

        tessellator.draw();
        RenderSystem.disableCull();
        matrixStack.pop();
    }

    private static void renderSection(float delta, VertexConsumer builder, int locationOffset, float textureDistanceOffset, float textureRotationOffset, float startScale, float endScale) {
        float oneSixth = 1 / 6f;
        float sqrt3Over2 = (float) Math.sqrt(3) / 2.0f;
        int verticalOffset = (locationOffset * oneSixth + textureDistanceOffset > 1.0) ? locationOffset - 6 : locationOffset;
        int horizontalOffset = (textureRotationOffset > 1.0) ? -6 : 0;
        float computedDistortionFactor = computeDistortionFactor(delta, locationOffset);
        float computedDistortionFactorPlusOne = computeDistortionFactor(delta, locationOffset + 1);

        builder.vertex(0, -startScale + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        builder.vertex(0, -endScale + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * -sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(startScale * -sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        horizontalOffset = ((oneSixth + textureRotationOffset > 1.0) ? -5 : 1);
        builder.vertex(startScale * -sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * -sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * -sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(startScale * -sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        horizontalOffset = ((1.0f / 3.0f + textureRotationOffset > 1.0) ? -4 : 2);
        builder.vertex(startScale * -sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * -sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * -0.0f, endScale + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(startScale * -0.0f, startScale + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        horizontalOffset = ((0.5f + textureRotationOffset > 1.0) ? -3 : 3);
        builder.vertex(startScale * -0.0f, startScale + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * -0.0f, endScale + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(startScale * sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        horizontalOffset = ((2.0f / 3.0f + textureRotationOffset > 1.0) ? -2 : 4);
        builder.vertex(startScale * sqrt3Over2, startScale * 0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * sqrt3Over2, endScale * 0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(startScale * sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        horizontalOffset = ((5.0f / 6.0f + textureRotationOffset > 1.0) ? -1 : 5);
        builder.vertex(startScale * sqrt3Over2, startScale * -0.5 + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * sqrt3Over2, endScale * -0.5 + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(endScale * -0.0f, endScale * -1.0f + computedDistortionFactorPlusOne, -1 - locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + oneSixth + textureDistanceOffset).next();
        builder.vertex(startScale * -0.0f, startScale * -1.0f + computedDistortionFactor, -locationOffset)
                .texture(horizontalOffset * oneSixth + oneSixth + textureRotationOffset, verticalOffset * oneSixth + textureDistanceOffset).next();
    }

    private static float computeDistortionFactor(float delta, int t) {
        return (float) (Math.sin(delta * 2.0f * 2.0 * Math.PI + (13 - t) * 32f) * 2f) / 8;
    }
}
