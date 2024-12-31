package com.carpet_shadow.mixins.persistence;

import com.carpet_shadow.CarpetShadow;
import com.carpet_shadow.CarpetShadowSettings;
import com.carpet_shadow.Globals;
import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.utility.ItemStackInject;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.RegistryWrapper;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.*;

import java.util.Optional;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

//    @Shadow public abstract boolean isEmpty();
//
//    @Shadow @Final
//    public static Codec<ItemStack> CODEC;
//
//    @Accessor("CODEC")
//    private static Codec<ItemStack> getCodec() {
//        return CODEC;
//    }
//    private static Codec<ItemStack> getShadowedCodec() {
//        Codec<ItemStack> shadowCodec = new Codec<ItemStack>() {
//            @Override
//            public <T> DataResult<T> encode(ItemStack input, DynamicOps<T> ops, T prefix) {
//                var originalResult = getCodec()
//                        .encode(input, ops, prefix);
//                var modified = originalResult.map(result -> {
//                    if (result instanceof NbtElement nbt) {
//                        nbt = ItemStackInject.encode(input, nbt, BuiltinRegistries.createWrapperLookup());
//                        result = (T) nbt;
//                    }
//                    return result;
//                });
//                return modified;
//            }
//
//            @Override
//            public <T> DataResult<Pair<ItemStack, T>> decode(DynamicOps<T> ops, T input) {
//                var originalResult = getCodec()
//                        .decode(ops, input);
//                var modified = originalResult.map(result -> {
//                    final var t = result.getSecond();
//                    final var stack = result.getFirst();
//                    if (t instanceof NbtElement nbt) {
//                        ItemStackInject.decode(stack, nbt, BuiltinRegistries.createWrapperLookup());
//                    }
//                    return result;
//                });
//                return modified;
//            }
//        };
//        return shadowCodec;
//    }
//    @Redirect(method = "*", at = @At(value  = "FIELD", target = "Lnet/minecraft/item/ItemStack;CODEC:Lcom/mojang/serialization/Codec;", opcode = Opcodes.GETSTATIC))
//    private static Codec<ItemStack> redirectCodec() {
//        return getShadowedCodec();
//    }

//    @Shadow
//    @Final
//    public static PacketCodec<RegistryByteBuf, ItemStack> OPTIONAL_PACKET_CODEC;
//    @Accessor("OPTIONAL_PACKET_CODEC")
//    private static PacketCodec<RegistryByteBuf, ItemStack> getOptionalPacketCodec() {
//        return OPTIONAL_PACKET_CODEC;
//    }
//    private static  PacketCodec<RegistryByteBuf, ItemStack> getShadowedOptionalPacketCodec() {
//        PacketCodec<RegistryByteBuf, ItemStack> shadowCodec = new PacketCodec<RegistryByteBuf, ItemStack>() {
//            @Override
//            public void encode(RegistryByteBuf buf, ItemStack value) {
//                try {
//                    getOptionalPacketCodec().encode(buf, value);
//                } catch (Exception e) {
//                    CarpetShadow.LOGGER.error("Error encoding ItemStack: ", e);
//                }
//                final var shadowId = ((ShadowItem)(Object)value).carpet_shadow$getClientShadowId();
//                if (shadowId!=null) {
//                    buf.writeString(shadowId);
//                }
//            }
//
//            @Override
//            public ItemStack decode(RegistryByteBuf buf) {
//                return getOptionalPacketCodec().decode(buf);
//            }
//        };
//        return shadowCodec;
//    }
//    @Redirect(method = "*", at = @At(value  = "FIELD", target = "Lnet/minecraft/item/ItemStack;OPTIONAL_PACKET_CODEC:Lnet/minecraft/network/codec/PacketCodec;", opcode = Opcodes.GETSTATIC))
//    private static  PacketCodec<RegistryByteBuf, ItemStack> redirectOptionalPacketCodec() {
//        CarpetShadow.LOGGER.info("CODEC redirecting!");
//        return getShadowedOptionalPacketCodec();
//    }

//    @ModifyReturnValue(at = @At("RETURN"), method = "fromNbt")
//    private static Optional<ItemStack> post_fromNbt(Optional<ItemStack> stackOptional, RegistryWrapper.WrapperLookup registries, NbtElement nbt) {
//        if (stackOptional.isEmpty()) return stackOptional;
//        return Optional.of(ItemStackInject.decode(stackOptional.orElse(ItemStack.EMPTY), nbt, registries));
//    }
//    @Inject(
//            method = "fromNbt",
//            at = @At("RETURN"),
//            cancellable = true
//    )
//    private static void post_fromNbt(RegistryWrapper.WrapperLookup registries, NbtElement nbt, CallbackInfoReturnable<Optional<ItemStack>> cir) {
//        final var stackOptional = cir.getReturnValue();
//        var stack = stackOptional.get();
//        if (nbt.contains("shadow")) {
//            if(CarpetShadowSettings.shadowItemMode.shouldResetCount()){
//                stack.setCount(0);
//            }else if(CarpetShadowSettings.shadowItemMode.shouldLoadItem()) {
//                String shadow_id = nbt.getString("shadow");
//                stack = Globals.getByIdOrAdd(shadow_id,stack);
//                CarpetShadow.LOGGER.debug("Shadowed item loaded from memory");
//                CarpetShadow.LOGGER.debug("id: " + shadow_id);
//            }
//        }
//        cir.setReturnValue(Optional.of(stack));
//    }

//    @ModifyReturnValue(at = @At("RETURN"), method = "Lnet/minecraft/item/ItemStack;encode(Lnet/minecraft/registry/RegistryWrapper$WrapperLookup;Lnet/minecraft/nbt/NbtElement;)Lnet/minecraft/nbt/NbtElement;")
//    private NbtElement post_writeNbt(NbtElement ret, RegistryWrapper.WrapperLookup registries, NbtElement prefix) {
//        if (ret instanceof NbtCompound nbtCompound) {
//            String shadow_id = ((ShadowItem) this).carpet_shadow$getShadowId();
//            if (shadow_id != null) {
//                if (this.isEmpty()) {
//                    CarpetShadow.shadowMap.invalidate(shadow_id);
//                    ((ShadowItem) this).carpet_shadow$setShadowId(null);
//                } else {
//                    nbtCompound.putString("shadow", shadow_id);
//                    CarpetShadow.LOGGER.debug("Shadowed item saved in memory");
//                    CarpetShadow.LOGGER.debug("id: " + shadow_id);
//                    ret = nbtCompound;
//                }
//            }
//        }
//        return ret;
//        return ItemStackInject.encode((ItemStack)(Object)this, ret, registries);
//    }
}
