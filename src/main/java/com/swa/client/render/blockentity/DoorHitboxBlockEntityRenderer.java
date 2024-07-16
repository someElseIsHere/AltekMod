package com.swa.client.render.blockentity;

import com.swdteam.client.tardis.data.ClientTardisCache;
import com.swdteam.common.tardis.TardisData;
import com.swdteam.common.tileentity.tardis.TardisDoorHitboxTileEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

public class DoorHitboxBlockEntityRenderer extends BlockEntityRenderer<TardisDoorHitboxTileEntity> {
    private final DummyEndGatewayBlockEntityRenderer renderer;

    public DoorHitboxBlockEntityRenderer(BlockEntityRenderDispatcher berd) {
        super(berd);
        renderer = new DummyEndGatewayBlockEntityRenderer(berd);
    }

    @Override
    public void render(TardisDoorHitboxTileEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        TardisData data = ClientTardisCache.getTardisData(entity.getPos());

        if (data != null && !data.isInFlight()) {
            this.renderer.render(entity, tickDelta, matrices, vertexConsumers, light, overlay);
        }
    }
}
