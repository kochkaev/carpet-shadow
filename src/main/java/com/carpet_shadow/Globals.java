package com.carpet_shadow;

import com.carpet_shadow.interfaces.ShadowItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class Globals {

    public static final Set<Thread> mergingThreads = new HashSet<>();

    public static final Set<Inventory> toUpdate = new HashSet<>();

    public static ItemStack getByIdOrNull(String shadow_id) {
        if(shadow_id == null)
            return null;
        return CarpetShadow.shadowMap.getIfPresent(shadow_id);
    }

    public static ItemStack getByIdOrAdd(String shadow_id, ItemStack stack) {
        ItemStack reference = CarpetShadow.shadowMap.getIfPresent(shadow_id);
        if (reference != null)
            return reference;
        ((ShadowItem)(Object)stack).carpet_shadow$setShadowId(shadow_id);
        CarpetShadow.shadowMap.put(shadow_id, stack);
        return stack;
    }


    public static boolean shadow_merge_check(ItemStack stack1, ItemStack stack2, boolean ret) {
        var allowed = ret;
        if (CarpetShadowSettings.shadowItemInventoryFragilityFix && mergingThreads.contains(Thread.currentThread())) {
            var shadowStack1 = (ShadowItem) (Object) stack1;
            var shadowStack2 = (ShadowItem) (Object) stack2;
            var isStack1Shadow = shadowStack1.carpet_shadow$isItShadowItem();
            var isStack2Shadow = shadowStack2.carpet_shadow$isItShadowItem();
            String shadow1 = shadowStack1.carpet_shadow$getShadowId();
            String shadow2 = shadowStack2.carpet_shadow$getShadowId();
            if (stack1.isOf(stack2.getItem()) && ((isStack1Shadow && !isStack2Shadow) || (!isStack1Shadow && isStack2Shadow)))
                allowed = true;
            if (CarpetShadowSettings.shadowItemPreventCombine && allowed) {
                if (isStack1Shadow && isStack2Shadow)
                    allowed =  false;
            } else if (isStack1Shadow && shadow1.equals(shadow2) && allowed)
                    allowed =  false;
        }
        return allowed;
    }
    public static boolean isShadowIdExists(String id) {
        return CarpetShadow.shadowMap.asMap().containsKey(id);
    }
}
