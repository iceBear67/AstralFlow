/*
 *
 *   AstralFlow - Storage utilities for spigot servers.
 *   Copyright (C) 2022 iceBear67
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

package io.ib67.astralflow.item;

import org.bukkit.inventory.RecipeChoice;

public interface IOreDict {
    /**
     * 插件完成初始化后将会封锁所有的 registerItem 请求以减小维护成本
     *
     * @param prototype
     * @param dictKey
     * @return
     * @throws IllegalStateException if locked
     */
    IOreDict registerItem(Item prototype, String dictKey);

    RecipeChoice.ExactChoice getChoices(String dictKey);
}