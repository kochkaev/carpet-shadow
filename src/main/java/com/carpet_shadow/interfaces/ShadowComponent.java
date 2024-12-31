package com.carpet_shadow.interfaces;

import com.carpet_shadow.CarpetShadowSettings;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public record ShadowComponent(String shadowId) implements TooltipAppender {

//    private static final Style STYLE;
    public static final Identifier IDENTIFIER = Identifier.of("carpet-shadow", "shadow");
//    public static final Registry<ShadowComponent> REGISTRY = Registries.;
//    public static final RegistryKey<Registry<ShadowComponent>> REGISTRY_KEY_REGISTRY = RegistryKey.ofRegistry(IDENTIFIER);
//    public static final RegistryKey<ShadowComponent> REGISTRY_KEY = RegistryKey.of(REGISTRY_KEY_REGISTRY, IDENTIFIER);
    public static final Codec<ShadowComponent> CODEC;
    public static final PacketCodec<RegistryByteBuf, ShadowComponent> PACKET_CODEC;

    public ShadowComponent(String shadowId) {
        this.shadowId = shadowId;
    }
    public ShadowComponent(Text shadowIdText) {
        this(shadowIdText.getString());
    }

    public String getShadowId() {
        return shadowId;
    }
    public Text getTextShadowId() {
        return Text.of(shadowId);
    }
    public Text getTooltip() {
        MutableText text = Text.literal("shadow_id: ");
        MutableText sub = Text.literal(shadowId);
        sub.formatted(Formatting.GOLD, Formatting.BOLD);
        text.append(sub);
        text.formatted(Formatting.DARK_PURPLE, Formatting.ITALIC);
        return text;
    }

    public boolean shouldShowTooltip() {
        return shadowId!=null && !shadowId.isEmpty() && shadowId.matches("\\S+?");
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (shouldShowTooltip()) tooltip.accept(getTooltip());
    }

    static {
//        STYLE = Style.EMPTY.withColor(Formatting.DARK_PURPLE).withItalic(true);
        CODEC = TextCodecs.STRINGIFIED_CODEC.fieldOf("shadow_id").xmap(ShadowComponent::new, ShadowComponent::getTextShadowId).codec();
//        CODEC = Codec.lazyInitialized(() -> RecordCodecBuilder.create(instance -> instance.group(Codecs.)))
        PACKET_CODEC = TextCodecs.REGISTRY_PACKET_CODEC.xmap(ShadowComponent::new, ShadowComponent::getTextShadowId);
    }
}
