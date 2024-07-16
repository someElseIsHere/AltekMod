package com.swa.client.render.blockentity;

import com.swdteam.common.tileentity.tardis.TardisDoorHitboxTileEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.EndGatewayBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class DummyEndGatewayBlockEntityRenderer extends EndGatewayBlockEntityRenderer {
    private DummyEndGatewayBlockEntity blockEntity = new DummyEndGatewayBlockEntity();

    public DummyEndGatewayBlockEntityRenderer(BlockEntityRenderDispatcher berd) {
        super(berd);
    }

    public void render(TardisDoorHitboxTileEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        blockEntity.setLocation(entity.getWorld(), entity.getPos());
        super.render(blockEntity, tickDelta, matrices, vertexConsumers, light, overlay);
    }

    public static class DummyEndGatewayBlockEntity extends EndGatewayBlockEntity {
        public DummyEndGatewayBlockEntity() {
            super();
            this.age = Integer.MIN_VALUE;
        }
    }
}
