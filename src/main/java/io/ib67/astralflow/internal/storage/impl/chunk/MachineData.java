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

package io.ib67.astralflow.internal.storage.impl.chunk;

import io.ib67.astralflow.internal.storage.impl.MachineStorageType;
import io.ib67.kiwi.tuple.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.jetbrains.annotations.ApiStatus;

import java.util.HashMap;
import java.util.Map;

@ApiStatus.Internal
@RequiredArgsConstructor
@Getter
public final class MachineData {
    private final Map<Location, Pair<MachineStorageType, byte[]>> machineData = new HashMap<>();
    private final int chunkX;
    private final int chunkZ;

    public void save(Location loc, MachineStorageType type, byte[] data) {
        machineData.put(loc, new Pair<>(type, data));
    }

    public Pair<MachineStorageType, byte[]> getData(Location loc) {
        return machineData.get(loc);
    }

    public void remove(Location loc) {
        machineData.remove(loc);
    }
}
