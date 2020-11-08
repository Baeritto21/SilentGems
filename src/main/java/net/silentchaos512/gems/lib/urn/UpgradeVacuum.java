/*
 * Silent's Gems -- UpgradeVacuum
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.gems.lib.urn;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.silentchaos512.gems.block.urn.SoulUrnTileEntity;

public class UpgradeVacuum extends UrnUpgrade {
    private static final int RANGE = 4;

    @Override
    public void tickTile(SoulUrnTileEntity.SoulUrnState state, World world, BlockPos pos) {
        if (!state.getLidState().isOpen()) return;

        Vector3d target = new Vector3d(pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5);
        AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE,
                pos.getX() + RANGE + 1, pos.getY() + RANGE, pos.getZ() + RANGE + 1
        );
        boolean itemsAbsorbed = false;

        for (ItemEntity entity : world.getEntitiesWithinAABB(ItemEntity.class, axisAlignedBB)) {
            double distanceSq = entity.getDistanceSq(target.x, target.y, target.z);
            if (distanceSq < 0.5) {
                // Try to add item to urn's inventory
                if (state.getTileEntity().tryAddItemToInventory(entity.getItem())) {
                    itemsAbsorbed = true;
                    if (entity.getItem().isEmpty()) {
                        entity.remove();
                    }
                }
            } else {
                // Accelerate to target point
                Vector3d vec = entity.getEyePosition(0f).subtractReverse(target);
                vec = vec.normalize().scale(0.05);
                if (entity.getPosY() < target.y) {
                    double xzDistanceSq = ((entity.getPosX() - target.x) * (entity.getPosX() - target.x))
                            + ((entity.getPosZ() - target.z) * (entity.getPosZ() - target.z));
                    vec = vec.add(0, 0.01 + xzDistanceSq / 250, 0);
                }
                // Slow down a bit to prevent orbiting, then add new velocity
                final Vector3d slowdown = entity.getMotion().scale(-0.05);
                entity.addVelocity(slowdown.x, slowdown.y, slowdown.z);
                entity.addVelocity(vec.x, vec.y, vec.z);
            }
        }

        if (itemsAbsorbed) {
            state.setItemsAbsorbed(true);
        }
    }
}
