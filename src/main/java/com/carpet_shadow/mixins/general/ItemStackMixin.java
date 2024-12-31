package com.carpet_shadow.mixins.general;

import com.carpet_shadow.newAPI.ShadowNBTData;
import com.carpet_shadow.newAPI.ShadowComponent;
import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.interfaces.TooltipStack;
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
        var component = ((ItemStack)(Object)this).getComponents().get(ShadowNBTData.SHADOW);
        return component!=null ? component.shadowId() : null;
    }

    @Override
    public void carpet_shadow$setShadowId(String id) {
        var components = ((TooltipStack)(Object)this).carpet_shadow$getComponentMapImpl();
        components.set(ShadowNBTData.SHADOW, new ShadowComponent(id));
        ((ItemStack)(Object)this).applyComponentsFrom(components);
    }
}
