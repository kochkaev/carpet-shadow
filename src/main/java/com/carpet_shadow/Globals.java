package com.carpet_shadow;

import com.carpet_shadow.interfaces.ShadowItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Globals {

    public static final Set<Thread> mergingThreads = new HashSet<>();

    public static final Set<Inventory> toUpdate = new HashSet<>();

    public static ItemStack getByIdOrNull(String shadow_id) {
        if(shadow_id == null)
            return null;
        var cache = CarpetShadow.shadowMap.get(shadow_id);
        return cache!=null ? cache.getLeft() : null;
    }

    public static ItemStack getByIdOrAdd(String shadow_id, ItemStack stack) {
        var cache = CarpetShadow.shadowMap.get(shadow_id);
        if (cache != null) {
            ItemStack reference = cache.getLeft();
            return reference;
        }
        CarpetShadow.shadowMap.put(shadow_id, new Pair<>(stack, new ArrayList<Pair<Inventory, Integer>>()));
        ((ShadowItem)(Object)stack).carpet_shadow$setShadowId(shadow_id);
//        if (!(((ShadowItem)(Object)stack).carpet_shadow$isItShadowItem()))
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
        return CarpetShadow.shadowMap.containsKey(id);
    }

    public static void updateInventory(Object object) {
        if (object instanceof Inventory inv) {
            try {
                for (int index = 0; index < inv.size(); index++) {
                    ItemStack stack = inv.getStack(index);
                    if (((ShadowItem) (Object) stack).carpet_shadow$isItShadowItem()) {
                        var shadowId = ((ShadowItem)(Object)stack).carpet_shadow$getShadowId();
                        var cache = CarpetShadow.shadowMap.get(shadowId);
                        var pair = new Pair<>(inv, index);
                        if (cache!=null) {
//                            stack.setCount(cache.getLeft().getCount());
                            inv.setStack(index, cache.getLeft());
                            if (!cache.getRight().contains(pair)) cache.getRight().add(pair);
                        } else {
                            var list = new ArrayList<Pair<Inventory, Integer>>();
                            list.add(pair);
                            CarpetShadow.shadowMap.put(shadowId, new Pair<>(stack, list));
                        }
                    }
                }
            } catch (Exception ignored){}
        }
    }
    public static void updateItemStack(ItemStack stack) {
        if (((ShadowItem) (Object) stack).carpet_shadow$isItShadowItem()) {
            var cache = CarpetShadow.shadowMap.get(((ShadowItem)(Object)stack).carpet_shadow$getShadowId());
            if (cache!=null) cache.getLeft().setCount(stack.getCount());
            else CarpetShadow.shadowMap.put(((ShadowItem) (Object) stack).carpet_shadow$getShadowId(), new Pair<>(stack, new ArrayList<>()));
        }
    }
    public static void addInventory(String shadowId, Object object, int slot) {
        if (object instanceof Inventory inv) {
            var cache = CarpetShadow.shadowMap.get(shadowId);
            if (cache != null) cache.getRight().add(new Pair<>(inv, slot));
        }
    }
    public static void removeInventory(String shadowId, Object object, int slot) {
        if (object instanceof Inventory inv) {
            var cache = CarpetShadow.shadowMap.get(shadowId);
//            if (cache != null) cache.setRight(cache.getRight().stream().filter(it -> !it.getLeft().equals(inv)).toList());
            if (cache != null) cache.getRight().remove(new Pair<>(inv, slot));
        }
    }
    public static void removeInventory(Object object) {
        if (object instanceof Inventory inv) {
            try {
                for (int index = 0; index < inv.size(); index++) {
                    ItemStack stack = inv.getStack(index);
                    if (((ShadowItem) (Object) stack).carpet_shadow$isItShadowItem()) {
                        var shadowId = ((ShadowItem)(Object)stack).carpet_shadow$getShadowId();
                        var cache = CarpetShadow.shadowMap.get(shadowId);
                        if (cache!=null) {
                            var pair = new Pair<>(inv, index);
                            cache.getRight().remove(pair);
                        }
                    }
                }
            } catch (Exception ignored){}
        }
    }
    public static void updateInventories(String shadowId) {
        var cache = CarpetShadow.shadowMap.get(shadowId);
//        CarpetShadow.LOGGER.info(cache);
        if (cache != null)
            toUpdate.addAll(cache.getRight().stream().map(it -> it.getLeft()).toList());
    }
}
