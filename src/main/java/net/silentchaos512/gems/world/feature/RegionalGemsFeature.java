package net.silentchaos512.gems.world.feature;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.BitSet;
import java.util.Random;

public class RegionalGemsFeature extends Feature<RegionalGemsFeatureConfig> {
    public static final RegionalGemsFeature INSTANCE = new RegionalGemsFeature(RegionalGemsFeatureConfig.CODEC);

    public RegionalGemsFeature(Codec<RegionalGemsFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean func_241855_a(ISeedReader worldIn, ChunkGenerator generator, Random rand, BlockPos pos, RegionalGemsFeatureConfig config) {
        if (config.regionSize < 1) return false;

        float f = rand.nextFloat() * (float) Math.PI;
        float f1 = (float) config.size / 8.0F;
        int i = MathHelper.ceil(((float) config.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d0 = (float) pos.getX() + MathHelper.sin(f) * f1;
        double d1 = (float) pos.getX() - MathHelper.sin(f) * f1;
        double d2 = (float) pos.getZ() + MathHelper.cos(f) * f1;
        double d3 = (float) pos.getZ() - MathHelper.cos(f) * f1;
        int j = 2;
        double d4 = pos.getY() + rand.nextInt(3) - 2;
        double d5 = pos.getY() + rand.nextInt(3) - 2;
        int k = pos.getX() - MathHelper.ceil(f1) - i;
        int l = pos.getY() - 2 - i;
        int i1 = pos.getZ() - MathHelper.ceil(f1) - i;
        int j1 = 2 * (MathHelper.ceil(f1) + i);
        int k1 = 2 * (2 + i);

        BlockState oreBlock = config.selectGem(worldIn, new BlockPos(k, l, i1), rand).getOre().getDefaultState();

        for (int l1 = k; l1 <= k + j1; ++l1) {
            for (int i2 = i1; i2 <= i1 + j1; ++i2) {
                if (l <= worldIn.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, l1, i2)) {
                    return this.func_207803_a(worldIn, rand, oreBlock, config, d0, d1, d2, d3, d4, d5, k, l, i1, j1, k1);
                }
            }
        }

        return false;
    }

    protected boolean func_207803_a(IWorld world, Random random, BlockState oreBlock, RegionalGemsFeatureConfig config, double p_207803_4_, double p_207803_6_, double p_207803_8_, double p_207803_10_, double p_207803_12_, double p_207803_14_, int p_207803_16_, int p_207803_17_, int p_207803_18_, int p_207803_19_, int p_207803_20_) {
        int i = 0;
        BitSet bitset = new BitSet(p_207803_19_ * p_207803_20_ * p_207803_19_);
        BlockPos.Mutable blockPos = new BlockPos.Mutable();
        double[] adouble = new double[config.size * 4];

        for (int j = 0; j < config.size; ++j) {
            float f = (float) j / (float) config.size;
            double d0 = MathHelper.lerp(f, p_207803_4_, p_207803_6_);
            double d2 = MathHelper.lerp(f, p_207803_12_, p_207803_14_);
            double d4 = MathHelper.lerp(f, p_207803_8_, p_207803_10_);
            double d6 = random.nextDouble() * (double) config.size / 16.0D;
            double d7 = ((double) (MathHelper.sin((float) Math.PI * f) + 1.0F) * d6 + 1.0D) / 2.0D;
            adouble[j * 4 + 0] = d0;
            adouble[j * 4 + 1] = d2;
            adouble[j * 4 + 2] = d4;
            adouble[j * 4 + 3] = d7;
        }

        for (int l2 = 0; l2 < config.size - 1; ++l2) {
            if (!(adouble[l2 * 4 + 3] <= 0.0D)) {
                for (int j3 = l2 + 1; j3 < config.size; ++j3) {
                    if (!(adouble[j3 * 4 + 3] <= 0.0D)) {
                        double d12 = adouble[l2 * 4 + 0] - adouble[j3 * 4 + 0];
                        double d13 = adouble[l2 * 4 + 1] - adouble[j3 * 4 + 1];
                        double d14 = adouble[l2 * 4 + 2] - adouble[j3 * 4 + 2];
                        double d15 = adouble[l2 * 4 + 3] - adouble[j3 * 4 + 3];
                        if (d15 * d15 > d12 * d12 + d13 * d13 + d14 * d14) {
                            if (d15 > 0.0D) {
                                adouble[j3 * 4 + 3] = -1.0D;
                            } else {
                                adouble[l2 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for (int i3 = 0; i3 < config.size; ++i3) {
            double d11 = adouble[i3 * 4 + 3];
            if (!(d11 < 0.0D)) {
                double d1 = adouble[i3 * 4 + 0];
                double d3 = adouble[i3 * 4 + 1];
                double d5 = adouble[i3 * 4 + 2];
                int startX = Math.max(MathHelper.floor(d1 - d11), p_207803_16_);
                int startY = Math.max(MathHelper.floor(d3 - d11), p_207803_17_);
                int startZ = Math.max(MathHelper.floor(d5 - d11), p_207803_18_);
                int endX = Math.max(MathHelper.floor(d1 + d11), startX);
                int endY = Math.max(MathHelper.floor(d3 + d11), startY);
                int endZ = Math.max(MathHelper.floor(d5 + d11), startZ);

                for (int x = startX; x <= endX; ++x) {
                    double d8 = ((double) x + 0.5D - d1) / d11;
                    if (d8 * d8 < 1.0D) {
                        for (int y = startY; y <= endY; ++y) {
                            double d9 = ((double) y + 0.5D - d3) / d11;
                            if (d8 * d8 + d9 * d9 < 1.0D) {
                                for (int z = startZ; z <= endZ; ++z) {
                                    double d10 = ((double) z + 0.5D - d5) / d11;
                                    if (d8 * d8 + d9 * d9 + d10 * d10 < 1.0D) {
                                        int k2 = x - p_207803_16_ + (y - p_207803_17_) * p_207803_19_ + (z - p_207803_18_) * p_207803_19_ * p_207803_20_;
                                        if (!bitset.get(k2)) {
                                            bitset.set(k2);
                                            blockPos.setPos(x, y, z);
                                            if (config.target.test(world.getBlockState(blockPos), random)) {
                                                world.setBlockState(blockPos, oreBlock, 2);
                                                ++i;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return i > 0;
    }
}
