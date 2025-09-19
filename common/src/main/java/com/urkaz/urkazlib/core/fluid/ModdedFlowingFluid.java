/*
 * This file is part of "UrkazLib".
 * Copyright (C) 2025 Urkaz - Fran Sánchez
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package com.urkaz.urkazlib.core.fluid;

import com.urkaz.urkazlib.platform.core.fluid.IFluidRegisterHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public abstract class ModdedFlowingFluid extends FlowingFluid {

    private final IFluidProperties properties;

    ModdedFlowingFluid(IFluidProperties properties) {
        this.properties = properties;
        IFluidRegisterHelper.INSTANCE.register(this, properties);
    }

    public IFluidProperties getProperties() {
        return properties;
    }

    @Override
    public @NotNull Fluid getFlowing() {
        return properties.getFlowingFluid();
    }

    @Override
    public @NotNull Fluid getSource() {
        return properties.getSourceFluid();
    }

    @Override
    protected boolean canConvertToSource(@NotNull ServerLevel level) {
        return properties.canConvertToSource();
    }

    @Override
    protected void beforeDestroyingBlock(@NotNull LevelAccessor level, @NotNull BlockPos pos, BlockState state) {
        // Copy from WaterFluid.
        BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockEntity);
    }

    @Override
    protected int getSlopeFindDistance(@NotNull LevelReader level) {
        return properties.getSlopeFindDistance(level);
    }

    @Override
    protected int getDropOff(@NotNull LevelReader level) {
        return properties.getDropOff(level);
    }

    @Override
    public @NotNull Item getBucket() {
        Item bucket = properties.getBucket();
        return bucket == null ? Items.AIR : bucket;
    }

    @Override
    protected boolean canBeReplacedWith(@NotNull FluidState fluidState, @NotNull BlockGetter blockGetter, @NotNull BlockPos blockPos, @NotNull Fluid fluid, @NotNull Direction direction) {
        // Copy from WaterFluid.
        return direction == Direction.DOWN && !this.isSame(fluid);
    }

    @Override
    public int getTickDelay(@NotNull LevelReader level) {
        return properties.getTickDelay(level);
    }

    @Override
    protected float getExplosionResistance() {
        return properties.getExplosionResistance();
    }

    @Override
    protected @NotNull BlockState createLegacyBlock(@NotNull FluidState fluidState) {
        LiquidBlock block = properties.getBlock();
        if (block == null) return Blocks.AIR.defaultBlockState();
        return block.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(fluidState));
    }

    public static class Source extends ModdedFlowingFluid {
        public Source(IFluidProperties properties) {
            super(properties);
        }

        public int getAmount(@NotNull FluidState state) {
            return 8;
        }

        public boolean isSource(@NotNull FluidState state) {
            return true;
        }
    }

    @Override
    public boolean isSame(@NotNull Fluid fluid) {
        return fluid == getSource() || fluid == getFlowing();
    }

    public static class Flowing extends ModdedFlowingFluid {
        public Flowing(IFluidProperties properties) {
            super(properties);
            this.registerDefaultState(this.getStateDefinition().any().setValue(LEVEL, 7));
        }

        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        public boolean isSource(@NotNull FluidState state) {
            return false;
        }
    }
}
