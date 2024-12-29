package com.carpet_shadow.mixins.tooltip;


import com.carpet_shadow.CarpetShadow;
import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.interfaces.ShadowItem;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

//    @Inject(method = "getTooltip", at = @At("RETURN"))
    @ModifyReturnValue(method = "getTooltip", at = @At("RETURN"))
    private List<Text> postToolTip(List<Text> original) {
        CarpetShadow.LOGGER.info("Getting tooltip of the item");
//         list = cir.getReturnValue();
        if (CarpetShadowSettings.shadowItemTooltip && ((ShadowItem) this).carpet_shadow$getClientShadowId() != null) {
            MutableText text = Text.literal("shadow_id: ");
            MutableText sub = Text.literal(((ShadowItem) this).carpet_shadow$getClientShadowId());
            sub.formatted(Formatting.GOLD, Formatting.BOLD);
            text.append(sub);
            text.formatted(Formatting.ITALIC);
            original.add(text);
            CarpetShadow.LOGGER.info("Tooltip was modified!");
        }
        return original;
    }

//    @ModifyReturnValue(at = @At("RETURN"), method = "Lnet/minecraft/item/ItemStack;encode(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;Lnet/minecraft/nbt/NbtElement;)Lnet/minecraft/nbt/NbtElement;")
//    private NbtElement postToolTip(NbtElement nbt, RegistryWrapper.WrapperLookup registries, NbtElement prefix) {
//        if (nbt instanceof NbtCompound ret) {
//            NbtCompound display = new NbtCompound();
//            if (CarpetShadowSettings.shadowItemTooltip && ((ShadowItem) this).carpet_shadow$getClientShadowId() != null) {
//                MutableText text = Text.literal("shadow_id: ");
//                MutableText sub = Text.literal(((ShadowItem) this).carpet_shadow$getClientShadowId());
//                sub.formatted(Formatting.GOLD, Formatting.BOLD);
//                text.append(sub);
//                text.formatted(Formatting.ITALIC);
//                NbtList list = new NbtList();
//                if (ret == null) {
//                    ret = new NbtCompound();
//                } else if (ret.contains(/*ItemStack.DISPLAY_KEY*/"components")) {
//                    display = ret.getCompound(/*ItemStack.DISPLAY_KEY*/"components");
//                    if (display.contains(/*ItemStack.LORE_KEY*/"minecraft:lore")) {
//                        list = ret.getList(/*ItemStack.LORE_KEY*/"minecraft:lore", 8);
//                    }
//                }
//                list.add(NbtString.of(Text.Serialization.toJsonString(text, registries)));
//                display.put(/*ItemStack.LORE_KEY*/"minecraft:lore", list);
//                ret.put(/*ItemStack.DISPLAY_KEY*/"components", display);
//                ret.putString(ShadowItem.SHADOW_KEY, ((ShadowItem) this).carpet_shadow$getClientShadowId());
//                nbt = ret;
//            }
//        }
//        return nbt;
//    }
}
