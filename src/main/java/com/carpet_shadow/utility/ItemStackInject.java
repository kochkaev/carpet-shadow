package com.carpet_shadow.utility;

import com.carpet_shadow.CarpetShadow;
import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.Globals;
import com.carpet_shadow.ShadowNBTData;
import com.carpet_shadow.interfaces.ShadowComponent;
import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.interfaces.TooltipStack;
import com.google.gson.JsonParseException;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.MutableText;
import net.minecraft.text.PlainTextContent;
import net.minecraft.text.Text;
import net.minecraft.text.TextContent;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemStackInject {

    public static NbtElement encode(ItemStack instance, NbtElement nbt, RegistryWrapper.WrapperLookup registries) {
//        CarpetShadow.LOGGER.info("Encode: " + nbt.asString());
        if (nbt instanceof NbtCompound ret) {
            // post write nbt
            String shadow_id = ((ShadowItem) (Object) instance).carpet_shadow$getShadowId();
            if (shadow_id != null) {
                if (instance.isEmpty()) {
                    CarpetShadow.shadowMap.invalidate(shadow_id);
                    ((ShadowItem) (Object) instance).carpet_shadow$setShadowId(null);
                } else {
                    ret.putString(ShadowItem.SHADOW_KEY, shadow_id);
                    CarpetShadow.LOGGER.debug("Shadowed item saved in memory");
                    CarpetShadow.LOGGER.debug("id: " + shadow_id);
                }
            }
            // post tooltip
//            NbtCompound display = new NbtCompound();
//            if (CarpetShadowSettings.shadowItemTooltip && ((ShadowItem) (Object) instance).carpet_shadow$getClientShadowId() != null) {
//                MutableText text = Text.literal("shadow_id: ");
//                MutableText sub = Text.literal(((ShadowItem) (Object) instance).carpet_shadow$getClientShadowId());
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
//                ret.putString(ShadowItem.SHADOW_KEY, ((ShadowItem) (Object) instance).carpet_shadow$getClientShadowId());
//            }

            nbt = ret;
        }
//        CarpetShadow.LOGGER.info("Encode result: " + nbt.asString());
        return nbt;
    }

    public static ItemStack decode(ItemStack stack, NbtElement nbtElement, RegistryWrapper.WrapperLookup registries) {
//        CarpetShadow.LOGGER.info("Decode: " + nbtElement.asString());
        if (nbtElement instanceof NbtCompound nbt){
            // post from nbt
            if (nbt.contains(ShadowItem.SHADOW_KEY)) {
                if (CarpetShadowSettings.shadowItemMode.shouldResetCount()) {
                    stack.setCount(0);
                } else if (CarpetShadowSettings.shadowItemMode.shouldLoadItem()) {
                    String shadow_id = nbt.getString(ShadowItem.SHADOW_KEY);
                    stack = Globals.getByIdOrAdd(shadow_id, stack);
                    CarpetShadow.LOGGER.debug("Shadowed item loaded from memory");
                    CarpetShadow.LOGGER.debug("id: " + shadow_id);
                }
            }

            // post tooltip
            stack = postTooltip(stack);
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
        }
//        CarpetShadow.LOGGER.info("Decode result: " + nbtElement.asString());
        return stack;
    }

    public static ItemStack postTooltip(ItemStack stack) {
////        NbtCompound display = new NbtCompound();
        var shadowId = ((ShadowItem) (Object) stack).carpet_shadow$getShadowId();
//        var lore = stack.getComponents().get(DataComponentTypes.LORE);
//        List<Text> lines;
//        if (shadowId == null) {
//            if (lore == null || lore.lines().isEmpty()) return stack;
//            lines = lore.lines().stream().filter(it -> {
//                return !it.getString().matches("shadow_id: (\\S+?)");
//            }).toList();
////        ((TooltipStack)(Object)original).carpet_shadow$getComponentMapImpl().remove(DataComponentTypes.LORE);
//        }
//        else if (CarpetShadowSettings.shadowItemTooltip && !(lore!=null && lore.lines().stream().anyMatch(it->it.getString().matches("shadow_id: (\\S+?)")))) {
//            MutableText text = Text.literal("shadow_id: ");
//            MutableText sub = Text.literal(shadowId);
//            sub.formatted(Formatting.GOLD, Formatting.BOLD);
//            text.append(sub);
//            text.formatted(Formatting.ITALIC);
//            lines = lore!=null ? new ArrayList<>(lore.lines()) : new ArrayList<>();
//            lines.add(text);
//        }
//        else return stack;
//        lore = new LoreComponent(lines);
////        ((TooltipStack) (Object) stack).carpet_shadow$getComponentMapImpl().set(DataComponentTypes.LORE, lore);
//        stack.applyComponentsFrom(ComponentMap.builder().add(DataComponentTypes.LORE, lore).build());
//        CarpetShadow.LOGGER.info(lines.stream().map(it -> it.getString()).toList());
//        CarpetShadow.LOGGER.info(lore.lines().stream().map(it -> it.getString()).toList());
//        CarpetShadow.LOGGER.info(stack.getComponents().get(DataComponentTypes.LORE).lines().stream().map(it -> it.getString()).toList());
//        return stack;
        var component = stack.getComponents().get(ShadowNBTData.SHADOW);
        var components = ((TooltipStack)(Object)stack).carpet_shadow$getComponentMapImpl();
        if (shadowId == null) {
            components.remove(ShadowNBTData.SHADOW);
        } else if (component == null) {
            components.set(ShadowNBTData.SHADOW, new ShadowComponent(shadowId));
        } else return stack;
        stack.applyComponentsFrom(components);
        return stack;
    }
}
