package com.swa.mixin.dm;

import com.swdteam.common.init.DMItems;
import com.swdteam.common.tileentity.ArtronFuelTankTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Tickable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ArtronFuelTankTileEntity.class, remap = false)
public abstract class ArtronFuelTankTileEntityMixin implements Tickable {
    @Shadow
    public ItemStack fuelSlot;
    @Shadow
    public double charge;
    @Shadow
    public abstract void sendUpdates();
    @Shadow
    public abstract ItemStack fillGlass(ItemStack stack);

    @Override
    public void tick() {
        if (!this.fuelSlot.isEmpty()) {
            this.fuelSlot = this.fillGlass(this.fuelSlot);
            this.sendUpdates();
        }
    }

    @Inject(method = "fillGlass", at = @At("HEAD"), cancellable = true)
    public void beforeFillGlass(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (this.charge >= 100.0){
            this.charge = 0.0;
            cir.setReturnValue(new ItemStack(DMItems.FULL_ARTRON.get()));
        }
    }
}
