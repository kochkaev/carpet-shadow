package com.carpet_shadow.mixins.fragility;

import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.Globals;
import com.carpet_shadow.interfaces.ShadowItem;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {
//    @WrapOperation(method = "extract(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/entity/ItemEntity;)Z",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"))
//    private static ItemStack extract_fix_entity(ItemStack instance, Operation<ItemStack> original) {
//        if (CarpetShadowSettings.shadowItemTransferFragilityFix && ((ShadowItem) (Object) instance).carpet_shadow$isItShadowItem()) {
//            return instance;
//        }
//        return original.call(instance);
//    }

//    @WrapOperation(method = "extract(Lnet/minecraft/block/entity/Hopper;Lnet/minecraft/inventory/Inventory;ILnet/minecraft/util/math/Direction;)Z",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/inventory/Inventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
//    private static void extract_fix_storage2(Inventory instance, int i, ItemStack itemStack, Operation<Void> original) {
//        if (CarpetShadowSettings.shadowItemTransferFragilityFix && ((ShadowItem) (Object) itemStack).carpet_shadow$isItShadowItem()) {
//            itemStack.setCount(itemStack.getCount() + 1);
//        }
//        original.call(instance, i, itemStack);
//    }
//
//    @WrapOperation(method = "insert",
//            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;setStack(ILnet/minecraft/item/ItemStack;)V"))
//    private static void insert_fix_storage2(HopperBlockEntity instance, int slot, ItemStack stack, Operation<Void> original) {
//        if (CarpetShadowSettings.shadowItemTransferFragilityFix && ((ShadowItem) (Object) stack).carpet_shadow$isItShadowItem()) {
//            stack.setCount(stack.getCount() + 1);
//        }
//        original.call(instance, slot, stack);
//    }

//    @Group(name = "fix_transfer")
    @Inject(method = "transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;", at = @At("HEAD"))
    private static void fix_transfer_start(Inventory from, Inventory to, ItemStack stack, int slot, Direction side, CallbackInfoReturnable<ItemStack> cir) {
        Globals.mergingThreads.add(Thread.currentThread());
    }
//    @Group(name = "fix_transfer")
    @Inject(method = "transfer(Lnet/minecraft/inventory/Inventory;Lnet/minecraft/inventory/Inventory;Lnet/minecraft/item/ItemStack;ILnet/minecraft/util/math/Direction;)Lnet/minecraft/item/ItemStack;", at = @At("RETURN"))
    private static void fix_transfer_end(Inventory from, Inventory to, ItemStack stack, int slot, Direction side, CallbackInfoReturnable<ItemStack> cir) {
        Globals.mergingThreads.remove(Thread.currentThread());
    }
}
