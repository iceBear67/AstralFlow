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

package io.ib67.astralflow.machines.scheduler;

import io.ib67.astralflow.Tickable;
import io.ib67.astralflow.scheduler.AwaitingTickable;
import io.ib67.astralflow.scheduler.Scheduler;
import io.ib67.astralflow.scheduler.TickReceipt;
import io.ib67.astralflow.scheduler.exception.TickTaskException;
import io.ib67.astralflow.util.LogCategory;
import io.ib67.astralflow.util.WeakHashSet;
import io.ib67.util.bukkit.Log;
import lombok.RequiredArgsConstructor;

import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

@RequiredArgsConstructor
public class SimpleCatchingScheduler implements Scheduler {
    private final Queue<AwaitingTickable<? extends Tickable<?>>> tickables = new LinkedBlockingQueue<>();
    private final Set<Tickable<?>> waitingForRemoval = new WeakHashSet<>();
    private final int exceptionLimiter;

    @Override
    public void tick() {
        var iter = tickables.iterator();
        while (iter.hasNext()) {
            var tickable = iter.next();
            if (tickable.receipt.isDropped() || waitingForRemoval.contains(tickable.tickable)) { // deactivated.
                iter.remove();
                continue;
            }
            try {
                tickable.tick();
            } catch (TickTaskException exception) {
                if (tickable.exceptionCounter.incrementAndGet() > exceptionLimiter) {
                    Log.warn(LogCategory.SCHEDULER, "Tickable " + tickable.getClass().getName() + "#" + System.identityHashCode(tickable) + " has thrown " + exceptionLimiter + " exceptions. It will be deactivated.");
                    tickable.receipt.drop();
                    iter.remove();
                }
            }
        }
    }

    @Override
    public <T extends Tickable<T>> TickReceipt<T> add(Tickable<T> tickable) {
        if (tickables.stream().map(e -> e.tickable).anyMatch(e -> e.equals(tickable))) {
            throw new IllegalStateException("Tickable " + tickable.getClass().getName() + "#" + System.identityHashCode(tickable) + " is already registered.");
        }
        var receipt = new TickReceipt<T>();
        tickables.add(new AwaitingTickable<>(tickable, receipt));
        return receipt;
    }

    @Override
    public void remove(Tickable<?> tickable) {
        waitingForRemoval.add(tickable);
    }
}
