package com.carpet_shadow.newAPI;

import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.interfaces.TooltipStack;
import net.minecraft.item.ItemStack;

public class ItemStackInject {

    public static ItemStack postTooltip(ItemStack stack) {
        var shadowId = ((ShadowItem) (Object) stack).carpet_shadow$getShadowId();
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
