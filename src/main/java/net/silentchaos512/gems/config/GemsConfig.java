package net.silentchaos512.gems.config;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.chaos.ChaosEvents;
import net.silentchaos512.utils.config.*;

import java.util.function.Supplier;

public final class GemsConfig {
    private static final ConfigSpecWrapper WRAPPER = ConfigSpecWrapper.create(
            FMLPaths.CONFIGDIR.get().resolve("silentgems-common.toml"));

    public static final Common COMMON = new Common(WRAPPER);

    public static class Common {
        public final StringValue baseBiomeSeedOverride;
        public final IntValue chaosCoalBurnTime;
        public final IntValue chaosMaxValue;
        public final BooleanValue chaosNoEventsUntilHasBed;
        public final BooleanValue debugMasterSwitch;
        public final Supplier<Boolean> debugShowOverlay;
        public final Supplier<Boolean> debugExtraTooltipInfo;
        public final IntValue enderSlimeSpawnWeight;
        public final IntValue enderSlimeGroupSizeMin;
        public final IntValue enderSlimeGroupSizeMax;
        public final BooleanValue gearSoulsGetXpFromFakePlayers;
        public final IntValue glowroseMaxPlaceCount;
        public final IntValue glowroseNormalLight;
        public final IntValue glowrosePottedLight;
        public final IntValue glowroseSpawnTryCount;
        public final BooleanValue returnHomeAllowAnchors;
        public final BooleanValue returnHomeMatchGems;
        public final IntValue returnHomeMaxUses;
        public final IntValue returnHomeUseTime;
        public final DoubleValue soulGemDropRateAverage;
        public final DoubleValue soulGemDropRateBoss;
        public final DoubleValue soulGemDropRateDeviation;
        public final BooleanValue teleporterAllowAnchors;
        public final IntValue teleporterChaosCrossDimension;
        public final IntValue teleporterChaosPerBlock;
        public final IntValue teleporterFreeRange;
        public final BooleanValue teleporterMatchGems;
        public final IntValue teleporterSearchRadius;
        public final BooleanValue wispsCauseFire;
        public final BooleanValue worldGenWildFluffyPuffs;
        public final IntValue worldGenOverworldMinGemsPerBiome;
        public final IntValue worldGenOverworldMaxGemsPerBiome;
        public final IntValue worldGenNetherGemsRegionSize;
        public final IntValue worldGenEndGemsRegionSize;
        public final IntValue worldGenOtherDimensionGemsRegionSize;
        public final IntValue worldGenSilverVeinCount;
        public final DoubleValue worldGenGeodeBaseChance;
        public final DoubleValue worldGenGeodeChanceVariation;
        public final IntValue worldGenChaosOreMinCount;
        public final IntValue worldGenChaosOreMaxCount;
        public final IntValue worldGenEnderOreCount;

        Common(ConfigSpecWrapper wrapper) {
            baseBiomeSeedOverride = wrapper
                    .builder("world.generation.baseBiomeSeedOverride")
                    .comment("Seed used when setting up ore generation. This affects what biomes gems can spawn in.",
                            "If left empty, your PC username is hashed to create the seed.")
                    .defineString("");
            chaosCoalBurnTime = wrapper
                    .builder("general.chaosCoalBurnTime")
                    .comment("The burn time (in ticks) of chaos coal (normal coal is 1600)")
                    .defineInRange(6400, 0, Integer.MAX_VALUE);
            chaosMaxValue = wrapper
                    .builder("chaos.maxValue")
                    .comment("The most chaos the any source (player or world) can accumulate")
                    .defineInRange(10_000_000, 0, Integer.MAX_VALUE);
            chaosNoEventsUntilHasBed = wrapper
                    .builder("chaos.noEventsUntilPlayerHasBed")
                    .comment("If true, players will not experience chaos events until they have used a bed (set a respawn point)")
                    .define(true);
            debugMasterSwitch = wrapper
                    .builder("debug.masterSwitch")
                    .comment("Must be true for any other debug settings to take effect")
                    .define(SilentGems.isDevBuild());
            debugShowOverlay = debugConfig(wrapper
                    .builder("debug.showOverlay")
                    .comment("Display text on-screen with various information, such as player/world chaos")
                    .define(true));
            debugExtraTooltipInfo = debugConfig(wrapper
                    .builder("debug.extraTooltipInfo")
                    .comment("Add additional tooltip information to some items")
                    .define(true));
            wrapper.comment("entity.enderSlime.spawn", "Ender slime spawn properties (REQUIRES RESTART)");
            enderSlimeSpawnWeight = wrapper
                    .builder("entity.enderSlime.spawn.weight")
                    .comment("Spawn weight of ender slimes in The End. Set to zero to disable spawns.")
                    .defineInRange(3, 0, Integer.MAX_VALUE);
            enderSlimeGroupSizeMin = wrapper
                    .builder("entity.enderSlime.spawn.minGroupSize")
                    .comment("Smallest possible group size")
                    .defineInRange(1, 1, Integer.MAX_VALUE);
            enderSlimeGroupSizeMax = wrapper
                    .builder("entity.enderSlime.spawn.maxGroupSize")
                    .comment("Largest possible group size")
                    .defineInRange(2, 1, Integer.MAX_VALUE);
            gearSoulsGetXpFromFakePlayers = wrapper
                    .builder("gearSoul.fakePlayersGetXp")
                    .comment("If true, gear souls can gain XP when being used by fake players (certain machines)")
                    .define(false);
            glowroseMaxPlaceCount = wrapper
                    .builder("glowrose.world.maxPerPatch")
                    .comment("The most glowroses that can be in a single patch")
                    .defineInRange(16, 0, Integer.MAX_VALUE);
            glowroseNormalLight = wrapper
                    .builder("glowrose.normalLight")
                    .comment("The light level of free-standing glowroses [0 ~ 15]",
                            "Requires a Minecraft restart")
                    .defineInRange(10, 0, 15);
            glowrosePottedLight = wrapper
                    .builder("glowrose.pottedLight")
                    .comment("The light level of glowroses planted in vanilla flower pots [0 ~ 15]",
                            "Requires a Minecraft restart")
                    .defineInRange(15, 0, 15);
            glowroseSpawnTryCount = wrapper
                    .builder("glowrose.world.placeTryCount")
                    .comment("The number of placement attempts when generating new chunks (higher numbers = bigger patches)",
                            "Note this is the number of 'attempts', not the actual number you will likely see in any given patch")
                    .defineInRange(40, 0, Integer.MAX_VALUE);
            returnHomeAllowAnchors = wrapper
                    .builder("returnHomeCharm.allowAnchors")
                    .comment("Allow return home charms to be bound to teleporter anchors")
                    .define(true);
            returnHomeMatchGems = wrapper
                    .builder("returnHomeCharm.sameGemOnly")
                    .comment("Only allow return home charms to be bound to teleporters made with the same gem")
                    .define(false);
            returnHomeMaxUses = wrapper
                    .builder("returnHomeCharm.maxUses")
                    .comment("Durability of return home charms. 0 means unlimited. Charms with durability will still generate chaos when used.")
                    .defineInRange(0, 0, Integer.MAX_VALUE);
            returnHomeUseTime = wrapper
                    .builder("returnHomeCharm.useTime")
                    .comment("The time (in ticks) the player must use a return home charm to activate it")
                    .defineInRange(16, 0, Integer.MAX_VALUE);
            wrapper.comment("soulGem.dropRate",
                    "Drop rate of soul gems is randomly selected based on the world seed.",
                    "There is an average and a deviation, which makes a normal distribution.",
                    "The numbers will tend to be close to average, but could occasionally be plus/minus a couple deviations.");
            soulGemDropRateAverage = wrapper
                    .builder("soulGem.dropRate.average")
                    .comment("Average drop rate of soul gems (1 = 100%) [0 ~ 1]")
                    .defineInRange(0.025, 0, 1);
            soulGemDropRateBoss = wrapper
                    .builder("soulGem.dropRate.boss")
                    .comment("The drop rate for boss creatures (overrides normal calculation) [0 ~ 1]")
                    .defineInRange(1.0, 0, 1);
            soulGemDropRateDeviation = wrapper
                    .builder("soulGem.dropRate.deviation")
                    .comment("Standard deviation of drop rate (should be no more than a quarter of the average, preferably less) [0 ~ 1]")
                    .defineInRange(0.002, 0, 1);
            teleporterChaosCrossDimension = wrapper
                    .builder("teleporter.chaos.crossDimension")
                    .comment("The chaos produced when traveling between dimensions using a teleport")
                    .defineInRange(50_000, 0, Integer.MAX_VALUE);
            teleporterChaosPerBlock = wrapper
                    .builder("teleporter.chaos.perBlock")
                    .comment("The chaos produced per block traveled (ignores Y-axis)",
                            " Does not apply when teleporting to another dimension")
                    .defineInRange(50, 0, Integer.MAX_VALUE);
            teleporterFreeRange = wrapper
                    .builder("teleporter.chaos.freeRange")
                    .comment("When teleporting this distance or less, no chaos is produced (ignores Y-axis)")
                    .defineInRange(64, 0, Integer.MAX_VALUE);
            teleporterSearchRadius = wrapper
                    .builder("teleporter.redstone.searchRadius")
                    .comment("All entities within this distance of a redstone teleporter will teleport when activated with redstone.",
                            "Default is 2 blocks, restricted to [1,16]")
                    .defineInRange(2, 1, 16);
            teleporterAllowAnchors = wrapper
                    .builder("teleporter.allowAnchors")
                    .comment("Allow teleporters to link to teleporter anchors")
                    .define(true);
            teleporterMatchGems = wrapper
                    .builder("teleporter.sameGemOnly")
                    .comment("Only allow teleporters to be linked to teleporters made with the same gem")
                    .define(false);
            wispsCauseFire = wrapper
                    .builder("mob.wisp.canCauseFire")
                    .comment("Fire and lightning wisps can light blocks on fire")
                    .define(true);

            wrapper.comment("world.generation",
                    "World generation settings (ores, etc.) Most of these REQUIRE A RESTART!");
            worldGenWildFluffyPuffs = wrapper
                    .builder("world.generation.plants.wildFluffyPuffs")
                    .comment("Generate wild fluffy puff plants. If disabled, you will need to add some other way to obtain fluffy puff seeds.")
                    .define(true);
            worldGenOverworldMinGemsPerBiome = wrapper
                    .builder("world.generation.overworld.gems.minTypePerBiome")
                    .comment("The fewest number of gem types to select per biome. Should be no bigger than the max value.",
                            "Setting both min and max to zero will disable normal gem generation in the overworld, but not in other dimensions.")
                    .defineInRange(2, 0, 16);
            worldGenOverworldMaxGemsPerBiome = wrapper
                    .builder("world.generation.overworld.gems.maxTypePerBiome")
                    .comment("The most gem types to select per biome. Should be at least the same as the min value.",
                            "Setting both min and max to zero will disable normal gem generation in the overworld, but not in other dimensions.")
                    .defineInRange(5, 0, 16);
            worldGenNetherGemsRegionSize = wrapper
                    .builder("world.generation.nether.gems.regionSize")
                    .comment("The region size for gems in the Nether.",
                            "Each 'size x size' chunk area is a 'region', which contains a couple types of gems.",
                            "Larger regions will make finding many types of gems more difficult.",
                            "Setting to zero will disable gem generation in this dimension.")
                    .defineInRange(8, 0, Integer.MAX_VALUE);
            worldGenEndGemsRegionSize = wrapper
                    .builder("world.generation.end.gems.regionSize")
                    .comment("The region size for gems in The End.",
                            "Each 'size x size' chunk area is a 'region', which contains a couple types of gems.",
                            "Larger regions will make finding many types of gems more difficult.",
                            "Setting to zero will disable gem generation in this dimension.")
                    .defineInRange(6, 0, Integer.MAX_VALUE);
            worldGenOtherDimensionGemsRegionSize = wrapper
                    .builder("world.generation.other.gems.regionSize")
                    .comment("The region size for gems in non-vanilla dimensions.",
                            "Each 'size x size' chunk area is a 'region', which contains a couple types of gems.",
                            "Larger regions will make finding many types of gems more difficult.",
                            "Setting to zero will disable gem generation in these dimensions (does not include overworld).")
                    .defineInRange(4, 0, Integer.MAX_VALUE);
            worldGenSilverVeinCount = wrapper
                    .builder("world.generation.ores.silver.veinCount")
                    .comment("Number of veins of silver ore per chunk. Set 0 to disable.",
                            "Default: 0 if Silent's Mechanisms is installed when config is created, 2 otherwise")
                    .defineInRange(ModList.get().isLoaded("silents_mechanisms") ? 0 : 2, 0, Integer.MAX_VALUE);
            worldGenGeodeBaseChance = wrapper
                    .builder("world.generation.overworld.geode.baseChance")
                    .comment("The base chance of a chunk having a gem geode.",
                            " Setting to zero will disable geodes. A value of one would make every chunk have a geode.")
                    .defineInRange(0.05, 0.0, 1.0);
            worldGenGeodeChanceVariation = wrapper
                    .builder("world.generation.overworld.geode.chanceVariation")
                    .comment("Max variation in geode chance. The final chance is a normal distribution, with this being the standard deviation.",
                            "This will tend to be close to the base chance, but could be more/less by several times this value.",
                            "The chance is rolled separately for each biome.")
                    .defineInRange(0.0025, 0.0, 1.0);
            wrapper.comment("world.generation.ores.chaos.veinCount", "Number of chaos ore veins per chunk, selected randomly per biome. Set min and max to zero to disable.");
            worldGenChaosOreMinCount = wrapper
                    .builder("world.generation.ores.chaos.veinCount.min")
                    .defineInRange(1, 0, 1000);
            worldGenChaosOreMaxCount = wrapper
                    .builder("world.generation.ores.chaos.veinCount.max")
                    .defineInRange(2, 0, 1000);
            worldGenEnderOreCount = wrapper
                    .builder("world.generation.ores.ender.veinCount")
                    .comment("Number of ender ore veins per chunk in The End. Set zero to disable.")
                    .defineInRange(1, 0, 1000);

            ChaosEvents.loadConfigs(wrapper);
        }

        private Supplier<Boolean> debugConfig(BooleanValue config) {
            return () -> debugMasterSwitch != null && config != null && debugMasterSwitch.get() && config.get();
        }
    }

    private GemsConfig() { }

    public static void init() {
        WRAPPER.validate();
        WRAPPER.validate();
    }
}
