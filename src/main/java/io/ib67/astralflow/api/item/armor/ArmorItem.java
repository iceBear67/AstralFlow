/*
 *
 *   AstralFlow - The plugin enriches bukkit servers
 *   Copyright (C) 2022 The Inlined Lambdas and Contributors
 *
 *   This library is free software; you can redistribute it and/or
 *   modify it under the terms of the GNU Lesser General Public
 *   License as published by the Free Software Foundation; either
 *   version 2.1 of the License, or (at your option) any later version.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *   Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the Free Software
 *   Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *   USA
 */

package io.ib67.astralflow.api.item.armor;

import io.ib67.astralflow.api.AstralHelper;
import io.ib67.astralflow.api.item.ItemBase;
import io.ib67.astralflow.hook.HookType;
import io.ib67.astralflow.item.ItemKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public final class ArmorItem extends ItemBase {
    private final ArmorProperty property;

    protected ArmorItem(ItemKey itemKey, ItemStack prototype, ArmorProperty property) {
        super(itemKey, prototype);
        this.property = property;
        HookType.ENTITY_DAMAGE.register(this::onEntityDamage);
    }

    private void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        var eq = entity.getEquipment();
        if (eq == null) {
            return;
        }
        var costedDamage = event.getDamage() - event.getDamage() * property.damageReduction().getOrDefault(event.getCause(), 0F);
        var finalDamage = event.getFinalDamage();
        for (ItemStack armorContent : eq.getArmorContents()) {
            if (armorContent != null && AstralHelper.isHolder(armorContent, this)) {
                finalDamage = finalDamage - costedDamage;
            }
        }
        event.setDamage(finalDamage);
    }

}
