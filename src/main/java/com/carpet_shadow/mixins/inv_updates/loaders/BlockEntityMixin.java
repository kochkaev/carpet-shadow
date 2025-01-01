package com.carpet_shadow.mixins.inv_updates.loaders;


import com.carpet_shadow.Globals;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntity.class)
public abstract class BlockEntityMixin {

    @Inject(method = "createFromNbt",at=@At("RETURN"))
    private static void interceptBlockEntityLoad(BlockPos pos, BlockState state, NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup, CallbackInfoReturnable<BlockEntity> cir){
//        if(cir.getReturnValue() instanceof Inventory inv){
//            try {
//                for (int index = 0; index < inv.size(); index++) {
//                    ItemStack stack = inv.getStack(index);
//                    if (((ShadowItem) (Object) stack).carpet_shadow$isItShadowItem()) {
////                        ((InventoryItem) (Object) stack).carpet_shadow$addSlot(inv, index);
//                        var shadowId = ((ShadowItem)(Object)stack).carpet_shadow$getShadowId();
//                        Globals.addInventory(shadowId, inv, index);
//                    }
//                }
//            }catch(Exception ignored){}
//        }
        Globals.updateInventory(cir.getReturnValue());
    }

}
