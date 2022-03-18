/*
 *
 *   AstralFlow - The plugin who is turning bukkit into mod-pack
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

package io.ib67.astralflow.storage.impl.chunk;

import io.ib67.astralflow.machines.IMachine;
import org.bukkit.Location;
import org.jetbrains.annotations.ApiStatus;

import java.util.Map;

@ApiStatus.Internal
public final class ChunkMachineIndex {

    final Map<Location, String> machines;
    final int chunkX;
    final int chunkZ;
    boolean hasMachines = false;

    public ChunkMachineIndex(Map<Location, String> machines, int chunkX, int chunkZ) {
        this.machines = machines;
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        if (!machines.isEmpty()) {
            hasMachines = true;
        }
    }

    public void addMachine(IMachine machine) {
        hasMachines = true;
        machines.put(machine.getLocation(), machine.getType().getName());
    }

    public void removeMachine(Location loc) {
        machines.remove(loc);
        if (machines.isEmpty()) {
            hasMachines = false;
        }
    }

    public String getMachineType(Location location) {
        return machines.get(location);
    }
}