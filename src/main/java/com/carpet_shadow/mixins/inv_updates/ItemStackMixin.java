package com.carpet_shadow.mixins.inv_updates;


import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.Globals;
import com.carpet_shadow.interfaces.ShadowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

//    @Unique
//    Set<Pair<Inventory,Integer>> slots = new HashSet<>();

//    @Override
//    public Collection<Inventory> carpet_shadow$getInventories() {
//        return slots.stream().map(Pair::getLeft).toList();
//    }

//    @Override
//    public void carpet_shadow$addSlot(Inventory inventory, int slot) {
//        slots.add(new ImmutablePair<>(inventory,slot));
//    }

//    @Override
//    public void carpet_shadow$removeSlot(Inventory inventory, int slot) {
//        slots.remove(new ImmutablePair<>(inventory, slot));
//    }

    @Inject(method = "setCount", at=@At("RETURN"))
    public void propagate_update(int count, CallbackInfo ci){
        if (CarpetShadowSettings.shadowItemUpdateFix && ((ShadowItem)(Object)this).carpet_shadow$isItShadowItem()) {
            Globals.updateInventories(((ShadowItem)(Object)this).carpet_shadow$getShadowId());
        }
    }


}
