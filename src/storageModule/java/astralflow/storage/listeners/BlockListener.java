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

package astralflow.storage.listeners;

import astralflow.storage.machines.HelloMachine;
import io.ib67.astralflow.AstralFlow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getItemInHand().hasItemMeta() && event.getItemInHand().getItemMeta().getDisplayName().equals("jeb_")) {
            var mech = AstralFlow.getInstance().getFactories().getMachineFactory(HelloMachine.class).createMachine(event.getBlock().getLocation());
            var mm = AstralFlow.getInstance().getMachineManager();
            mm.setupMachine(mech, true);
            event.getPlayer().sendMessage("Magics will come...");
        }
    }
}
