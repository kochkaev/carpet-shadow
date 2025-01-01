package com.carpet_shadow.mixins.tooltip;


import com.carpet_shadow.interfaces.ShadowItem;
import com.carpet_shadow.newAPI.ShadowNBTData;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.ComponentMapImpl;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.ArrayList;
import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

//    @Shadow @Final
//    ComponentMapImpl components;
//
//    @Override
//    public ComponentMapImpl carpet_shadow$getComponentMapImpl() {
//        return components;
//    }

    @Shadow public abstract ComponentMap getComponents();

    @ModifyReturnValue(method = "getTooltip", at = @At("RETURN"))
    private List<Text> postToolTip(List<Text> original) {
        var list = new ArrayList<>(original);
//        var components = ((ItemStack)(Object)this).getComponents();
//        var component = components.get(ShadowNBTData.SHADOW);
//        if (component != null && component.shouldShowTooltip())
//            list.add(component.getTooltip());
        if (((ShadowItem)(Object)this).carpet_shadow$isItShadowItem())
            list.add(this.getComponents().get(ShadowNBTData.SHADOW).getTooltip());
        return list;
    }

    @ModifyReturnValue(method = "copy", at =@At("RETURN"))
    private ItemStack removeTooltipInCopy(ItemStack original) {
//        var components = ((ItemStack)(Object)this).getComponents();
//        var component = components.get(ShadowNBTData.SHADOW);
//        if (component != null && component.shouldShowTooltip()) {
//            ((TooltipStack)(Object)original).carpet_shadow$getComponentMapImpl().set(ShadowNBTData.SHADOW, null);
//        }
        if (((ShadowItem)(Object)this).carpet_shadow$isItShadowItem())
            ((ShadowItem)(Object)original).carpet_shadow$removeShadow();
        return original;
    }
}
