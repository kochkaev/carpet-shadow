package com.carpet_shadow.mixins.tooltip;

import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.interfaces.ShadowItem;
import com.google.gson.JsonParseException;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin {
//    @ModifyArg(method = "writeNbt(Lio/netty/buffer/ByteBuf;Lnet/minecraft/nbt/NbtElement;)V", at = @At(value = "HEAD"), index = 1)
//    private static @Nullable NbtElement add_shadow_lore(@Nullable NbtElement nbt) {
//        if (nbt instanceof NbtCompound ret) {
//            if (ret.contains("id", 8) && ret.contains("Count", 3)) {
//                NbtCompound display = new NbtCompound();
//                if (CarpetShadowSettings.shadowItemTooltip && ((ShadowItem) (Object) instance).carpet_shadow$getClientShadowId() != null) {
//                    MutableText text = MutableText.of(new LiteralTextContent("shadow_id: "));
//                    MutableText sub = MutableText.of(new LiteralTextContent(((ShadowItem) (Object) instance).carpet_shadow$getClientShadowId()));
//                    sub.formatted(Formatting.GOLD, Formatting.BOLD);
//                    text.append(sub);
//                    text.formatted(Formatting.ITALIC);
//                    NbtList list = new NbtList();
//                    if (ret == null) {
//                        ret = new NbtCompound();
//                    } else if (ret.contains(ItemStack.DISPLAY_KEY)) {
//                        display = ret.getCompound(ItemStack.DISPLAY_KEY);
//                        if (display.contains(ItemStack.LORE_KEY)) {
//                            list = ret.getList(ItemStack.LORE_KEY, 8);
//                        }
//                    }
//                    list.add(NbtString.of(Text.Serializer.toJson(text)));
//                    display.put(ItemStack.LORE_KEY, list);
//                    ret.put(ItemStack.DISPLAY_KEY, display);
//                    ret.putString(ShadowItem.SHADOW_KEY, ((ShadowItem) (Object) instance).carpet_shadow$getClientShadowId());
//                }
//            }
//        }
//        return nbt;
//    }

//    @Inject(method = "readItemStack", at = @At(value = "RETURN"))
//    public void filter_shadow_lore(CallbackInfoReturnable<ItemStack> cir) {
//        ItemStack stack = cir.getReturnValue();
//        String string;
//        MutableText mutableText2;
//        NbtCompound nbt = stack.getNbt();
//        if (nbt != null && (nbt.contains(ItemStack.DISPLAY_KEY) || nbt.contains(ShadowItem.SHADOW_KEY))) {
//            String shadow_id = nbt.getString(ShadowItem.SHADOW_KEY);
//            if (!shadow_id.isEmpty()){
//                ((ShadowItem) (Object) stack).carpet_shadow$setClientShadowId(shadow_id);
//                nbt.remove(ShadowItem.SHADOW_KEY);
//            }
//            NbtCompound display = nbt.getCompound(ItemStack.DISPLAY_KEY);
//            if (display.contains(ItemStack.LORE_KEY)) {
//                NbtList lore = display.getList(ItemStack.LORE_KEY, 8);
//                for (int i = 0; i < lore.size(); ++i) {
//                    string = lore.getString(i);
//                    try {
//                        mutableText2 = Text.Serializer.fromJson(string);
//                        if (mutableText2 != null && mutableText2.getContent() instanceof LiteralTextContent && ((LiteralTextContent)mutableText2.getContent()).string().equals("shadow_id: ")) {
//                            lore.remove(i);
//                            if(((ShadowItem) (Object) stack).carpet_shadow$getClientShadowId() == null)
//                                ((ShadowItem) (Object) stack).carpet_shadow$setClientShadowId(((LiteralTextContent)mutableText2.getSiblings().get(0).getContent()).string());
//                            break;
//                        }
//                    } catch (JsonParseException ignored) {
//                    }
//                }
//                if (lore.isEmpty())
//                    display.remove(ItemStack.LORE_KEY);
//                if (display.isEmpty())
//                    nbt.remove(ItemStack.DISPLAY_KEY);
//            }
//            if (nbt.isEmpty())
//                stack.setNbt(null);
//        }
//
//    }
}
