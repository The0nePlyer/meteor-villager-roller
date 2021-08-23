package meteordevelopment.addons.roller.mixins;

import meteordevelopment.addons.roller.VillagerRollerAddon;
import meteordevelopment.addons.roller.modules.VillagerRoller;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
class VillagerInteractMixin {
    @Inject(at = @At("HEAD"), method = "interactMob(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/ActionResult;", cancellable = true)
    public void interactMob(CallbackInfoReturnable<ActionResult> cir) {
        if (VillagerRollerAddon.Roller.CurrentState == VillagerRoller.State.WaitingForTargetVillager) {
            VillagerRollerAddon.Roller.RollingVillager = (VillagerEntity) (Object) this;
            VillagerRollerAddon.Roller.info("We got your villager");
            VillagerRollerAddon.Roller.CurrentState = VillagerRoller.State.RollingBreakingBlock;
            cir.setReturnValue(ActionResult.SUCCESS);
            cir.cancel();
        }
    }
}
