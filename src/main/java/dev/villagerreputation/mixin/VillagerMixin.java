package dev.villagerreputation.mixin;
import net.minecraft.world.entity.npc.Villager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Villager.class) public abstract class VillagerMixin {
 @Redirect(method="onReputationEventFrom",at=@At(value="INVOKE",target="Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V",ordinal=3))
 private void vr$hurt(net.minecraft.world.entity.ai.gossip.GossipContainer g,java.util.UUID id,net.minecraft.world.entity.ai.gossip.GossipType type,int amount){Villager v=(Villager)(Object)this;float ratio=v.getHealth()/v.getMaxHealth();g.add(id,ratio<0.5f?net.minecraft.world.entity.ai.gossip.GossipType.MAJOR_POSITIVE:type,ratio<0.5f?50-net.minecraft.util.Mth.floor(ratio*100):amount);}
 @ModifyArg(method="onReputationEventFrom",at=@At(value="INVOKE",target="Lnet/minecraft/world/entity/ai/gossip/GossipContainer;add(Ljava/util/UUID;Lnet/minecraft/world/entity/ai/gossip/GossipType;I)V",ordinal=4),index=1)
 private net.minecraft.world.entity.ai.gossip.GossipType vr$death(net.minecraft.world.entity.ai.gossip.GossipType old){return net.minecraft.world.entity.ai.gossip.GossipType.MAJOR_POSITIVE;}
 @Inject(method="handleEntityEvent",at=@At("HEAD"),cancellable=true)
 private void vr$particles(byte event,CallbackInfo ci){Villager v=(Villager)(Object)this;if(event==13 && v.getHealth()/v.getMaxHealth()<0.5f)ci.cancel();}
 @ModifyArg(method="updateSpecialPrices",at=@At(value="INVOKE",target="Lnet/minecraft/world/item/trading/MerchantOffer;addToSpecialPriceDiff(I)V"),index=0)
 private int vr$discount(int value){return value==Integer.MIN_VALUE?Integer.MIN_VALUE:-Math.abs(value);}
}
