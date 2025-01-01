package com.carpet_shadow.mixins.inv_updates.loaders;


import com.carpet_shadow.Globals;
import com.carpet_shadow.interfaces.InventoryItem;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.command.CloneCommand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CloneCommand.class)
public abstract class CloneCommandMixin {

    @Redirect(method = "execute",at=@At(value = "INVOKE",target = "Lnet/minecraft/block/entity/BlockEntity;readComponentlessNbt(Lnet/minecraft/nbt/NbtCompound;Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;)V"))
    private static void interceptBlockEntityLoad(BlockEntity instance, NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup){
        Globals.updateInventory(instance);
    }


}
