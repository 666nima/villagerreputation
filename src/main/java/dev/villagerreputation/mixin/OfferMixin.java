package dev.villagerreputation.mixin;
import net.minecraft.world.item.trading.MerchantOffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
@Mixin(MerchantOffer.class) public abstract class OfferMixin {
 @Redirect(method="getCostA",at=@At(value="INVOKE",target="Ljava/lang/Math;max(II)I"))
 private int vr$demand(int minimum,int demand){return demand;}
}
