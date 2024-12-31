package com.carpet_shadow.mixins.tooltip;


import com.carpet_shadow.CarpetShadow;
import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.ShadowNBTData;
import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.interfaces.TooltipStack;
import com.carpet_shadow.utility.ItemStackInject;
import com.google.gson.JsonParseException;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
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
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements TooltipStack {

    @Shadow @Final
    ComponentMapImpl components;

    @Override
    public ComponentMapImpl carpet_shadow$getComponentMapImpl() {
        return components;
    }


//    @Inject(method = "getTooltip", at = @At("RETURN"))
    @ModifyReturnValue(method = "getTooltip", at = @At("RETURN"))
    private List<Text> postToolTip(List<Text> original) {
//        CarpetShadow.LOGGER.info("Getting tooltip of the item");
////         list = cir.getReturnValue();
//        if (CarpetShadowSettings.shadowItemTooltip && ((ShadowItem) this).carpet_shadow$getClientShadowId() != null) {
//            MutableText text = Text.literal("shadow_id: ");
//            MutableText sub = Text.literal(((ShadowItem) this).carpet_shadow$getClientShadowId());
//            sub.formatted(Formatting.GOLD, Formatting.BOLD);
//            text.append(sub);
//            text.formatted(Formatting.ITALIC);
//            original.add(text);
//            CarpetShadow.LOGGER.info("Tooltip was modified!");
//        }
        var list = new ArrayList<>(original);
        var components = ((ItemStack)(Object)this).getComponents();
        var component = components.get(ShadowNBTData.SHADOW);
        if (component != null && component.shouldShowTooltip())
            list.add(component.getTooltip());
        return list;
    }

    @ModifyReturnValue(method = "copy", at =@At("RETURN"))
    private ItemStack removeTooltipInCopy(ItemStack original) {
////        if (!CarpetShadowSettings.shadowItemTooltip) return original;
//        var lore = original.getComponents().get(DataComponentTypes.LORE);
//        if (lore == null || lore.lines().isEmpty()) return original;
//        var newList = lore.lines().stream().filter( it -> {
//            return !it.getString().matches("shadow_id: (\\S+?)");
//        }).toList();
//        lore = new LoreComponent(newList);
////        ((TooltipStack)(Object)original).carpet_shadow$getComponentMapImpl().remove(DataComponentTypes.LORE);
//        ((TooltipStack)(Object)original).carpet_shadow$getComponentMapImpl().set(DataComponentTypes.LORE, lore);
        var components = ((ItemStack)(Object)this).getComponents();
        var component = components.get(ShadowNBTData.SHADOW);
        if (component != null && component.shouldShowTooltip()) {
            ((TooltipStack)(Object)original).carpet_shadow$getComponentMapImpl().set(ShadowNBTData.SHADOW, null);
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
//    @Inject(
//            method = "fromNbt",
//            at = @At("RETURN"),
//            cancellable = true
//    )
//    private static void post_fromNbt(RegistryWrapper.WrapperLookup registries, NbtElement nbtElement, CallbackInfoReturnable<Optional<ItemStack>> cir) {
//        var optionalStack = cir.getReturnValue();
//        if (nbtElement instanceof NbtCompound nbt && optionalStack.isPresent()){
//            var stack = optionalStack.get();
//            String string;
//            MutableText mutableText2;
//            if (nbt != null && (nbt.contains(/*ItemStack.DISPLAY_KEY*/"components") || nbt.contains(ShadowItem.SHADOW_KEY))) {
//                String shadow_id = nbt.getString(ShadowItem.SHADOW_KEY);
//                if (!shadow_id.isEmpty()) {
////                    ((ShadowItem) (Object) stack).carpet_shadow$setShadowId(shadow_id);
//                    ((ShadowItem) (Object) stack).carpet_shadow$setClientShadowId(shadow_id);
////                    nbt.remove(ShadowItem.SHADOW_KEY);
//                }
//                NbtCompound display = nbt.getCompound(/*ItemStack.DISPLAY_KEY*/"components");
//                if (display.contains(/*ItemStack.LORE_KEY*/"minecraft:lore")) {
//                    NbtList lore = display.getList(/*ItemStack.LORE_KEY*/"minecraft:lore", 8);
//                    for (int i = 0; i < lore.size(); ++i) {
//                        string = lore.getString(i);
//                        try {
//                            mutableText2 = Text.Serialization.fromJson(string, registries);
//                            if (mutableText2 != null && mutableText2.getContent() instanceof PlainTextContent.Literal && ((PlainTextContent.Literal) mutableText2.getContent()).string().equals("shadow_id: ")) {
//                                lore.remove(i);
//                                if (((ShadowItem) (Object) stack).carpet_shadow$getClientShadowId() == null)
//                                    ((ShadowItem) (Object) stack).carpet_shadow$setClientShadowId(((PlainTextContent.Literal) mutableText2.getSiblings().get(0).getContent()).string());
//                                break;
//                            }
//                        } catch (JsonParseException ignored) {
//                        }
//                    }
//                    if (lore.isEmpty())
//                        display.remove(/*ItemStack.LORE_KEY*/"minecraft:lore");
//                    if (display.isEmpty())
//                        nbt.remove(/*ItemStack.DISPLAY_KEY*/"components");
//                }
////                if (nbt.isEmpty())
////                    stack.senNbt(null);
//            }
//            cir.setReturnValue(Optional.of(stack));
//        }
//    }
}
