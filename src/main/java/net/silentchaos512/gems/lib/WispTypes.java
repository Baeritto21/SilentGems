package net.silentchaos512.gems.lib;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.silentchaos512.gems.SilentGems;
import net.silentchaos512.gems.entity.*;
import net.silentchaos512.gems.entity.projectile.*;
import net.silentchaos512.gems.init.GemsItemGroups;
import net.silentchaos512.gems.init.Registration;
import net.silentchaos512.lib.registry.ItemRegistryObject;
import net.silentchaos512.utils.Color;

import java.util.Locale;
import java.util.Random;

public enum WispTypes {
    CHAOS(
            EntityType.Builder.create(ChaosWispEntity::new, EntityClassification.MONSTER),
            EntityType.Builder.<AbstractWispShotEntity>create(ChaosWispShotEntity::new, EntityClassification.MISC)
                    .setCustomClientFactory((spawnEntity, world) -> new ChaosWispShotEntity(world)),
            Color.DARKVIOLET
    ),
    FIRE(
            EntityType.Builder.create(FireWispEntity::new, EntityClassification.MONSTER)
                    .immuneToFire(),
            EntityType.Builder.<AbstractWispShotEntity>create(FireWispShotEntity::new, EntityClassification.MISC)
                    .setCustomClientFactory((spawnEntity, world) -> new FireWispShotEntity(world)),
            Color.ORANGERED
    ),
    ICE(
            EntityType.Builder.create(IceWispEntity::new, EntityClassification.MONSTER),
            EntityType.Builder.<AbstractWispShotEntity>create(IceWispShotEntity::new, EntityClassification.MISC)
                    .setCustomClientFactory((spawnEntity, world) -> new IceWispShotEntity(world)),
            Color.CYAN
    ),
    LIGHTNING(
            EntityType.Builder.create(LightningWispEntity::new, EntityClassification.MONSTER),
            EntityType.Builder.<AbstractWispShotEntity>create(LightningWispShotEntity::new, EntityClassification.MISC)
                    .setCustomClientFactory((spawnEntity, world) -> new LightningWispShotEntity(world)),
            Color.GOLD
    ),
    WATER(
            EntityType.Builder.create(WaterWispEntity::new, EntityClassification.MONSTER),
            EntityType.Builder.<AbstractWispShotEntity>create(WaterWispShotEntity::new, EntityClassification.MISC)
                    .setCustomClientFactory((spawnEntity, world) -> new WaterWispShotEntity(world)),
            Color.DODGERBLUE
    );

    private final EntityType<? extends AbstractWispEntity> type;
    private final EntityType<? extends AbstractWispShotEntity> shotType;
    @SuppressWarnings("NonFinalFieldInEnum")
    private ItemRegistryObject<SpawnEggItem> spawnEgg;
    private final Color color;
    private final ResourceLocation entityTexture;
    private final ResourceLocation shotTexture;

    WispTypes(EntityType.Builder<? extends AbstractWispEntity> entityBuilder, EntityType.Builder<? extends AbstractWispShotEntity> shotBuilder, Color color) {
        String name = getName();
        this.type = entityBuilder.build(SilentGems.getId(name).toString());
        this.shotType = shotBuilder.build(SilentGems.getId(name + "_shot").toString());
        this.color = color;
        this.entityTexture = SilentGems.getId(String.format("textures/entity/%s.png", name));
        this.shotTexture = SilentGems.getId(String.format("textures/entity/%s_shot.png", name));
    }

    public static void registerItems() {
        for (WispTypes wispType : values()) {
            wispType.spawnEgg = new ItemRegistryObject<>(Registration.ITEMS.register(wispType.getName() + "_spawn_egg", () ->
                    new SpawnEggItem(wispType.type, wispType.color.getColor(), Color.DARKVIOLET.getColor(), new Item.Properties().group(GemsItemGroups.UTILITY))));
        }
    }

    public String getName() {
        return name().toLowerCase(Locale.ROOT) + "_wisp";
    }

    public EntityType<? extends AbstractWispEntity> getEntityType() {
        return this.type;
    }

    public EntityType<? extends AbstractWispShotEntity> getShotType() {
        return this.shotType;
    }

    public SpawnEggItem getSpawnEgg() {
        return this.spawnEgg.get();
    }

    public Color getColor() {
        return this.color;
    }

    public ResourceLocation getEntityTexture() {
        return entityTexture;
    }

    public ResourceLocation getShotTexture() {
        return shotTexture;
    }

    public static WispTypes selectRandom(Random random) {
        return values()[random.nextInt(values().length)];
    }
}
