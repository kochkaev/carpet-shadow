package com.carpet_shadow.mixins.general;

import com.carpet_shadow.newAPI.ShadowNBTData;
import com.carpet_shadow.newAPI.ShadowComponent;
import com.carpet_shadow.interfaces.ShadowItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(ItemStack.class)
public class ItemStackMixin implements ShadowItem {

    @Override
    public boolean carpet_shadow$isItShadowItem() {
        var shadowId = this.carpet_shadow$getShadowId();
        return shadowId!=null && !shadowId.isEmpty() && shadowId.matches("\\S+?");
    }

    @Override
    public String carpet_shadow$getShadowId() {
        if (!carpet_shadow$containsShadowComponent()) return null;
        var component = ((ItemStack)(Object)this).getComponents().get(ShadowNBTData.SHADOW);
        return component!=null ? component.shadowId() : null;
    }

    @Override
    public boolean carpet_shadow$containsShadowComponent() {
        return ((ItemStack)(Object)this).getComponents().contains(ShadowNBTData.SHADOW);
    }

    @Override
    public void carpet_shadow$setShadowId(String id) {
//        var components = ((TooltipStack)(Object)this).carpet_shadow$getComponentMapImpl();
//        components.set(ShadowNBTData.SHADOW, new ShadowComponent(id));
//        ((ItemStack)(Object)this).applyComponentsFrom(components);
        ((ItemStack)(Object)this).set(ShadowNBTData.SHADOW, new ShadowComponent(id));
    }

    @Override
    public void carpet_shadow$removeShadow() {
        ((ItemStack)(Object)this).remove(ShadowNBTData.SHADOW);
//        ((TooltipStack)(Object)this).carpet_shadow$getComponentMapImpl().remove(ShadowNBTData.SHADOW);
    }
}
