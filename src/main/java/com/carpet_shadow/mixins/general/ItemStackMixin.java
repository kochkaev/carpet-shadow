package com.carpet_shadow.mixins.general;

import com.carpet_shadow.ShadowNBTData;
import com.carpet_shadow.interfaces.ShadowComponent;
import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.interfaces.TooltipStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;


@Mixin(ItemStack.class)
public class ItemStackMixin implements ShadowItem {
//    @Unique
//    private String shadow_id = null;
//    @Unique
//    private String client_shadow_id = null;

    @Override
    public String carpet_shadow$getShadowId() {
//        return shadow_id;
        var component = ((ItemStack)(Object)this).getComponents().get(ShadowNBTData.SHADOW);
        return component!=null ? component.shadowId() : null;
    }

    @Override
    public void carpet_shadow$setShadowId(String id) {
//        shadow_id = id;
//        carpet_shadow$setClientShadowId(id);
        var components = ((TooltipStack)(Object)this).carpet_shadow$getComponentMapImpl();
        components.set(ShadowNBTData.SHADOW, new ShadowComponent(id));
        ((ItemStack)(Object)this).applyComponentsFrom(components);
    }

//    @Override
//    public String carpet_shadow$getClientShadowId() {
//        return client_shadow_id;
//    }
//
//    @Override
//    public void carpet_shadow$setClientShadowId(String client_shadow_id) {
//        this.client_shadow_id = client_shadow_id;
//    }
}
