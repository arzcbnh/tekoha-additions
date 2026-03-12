package dev.arzcbnh.tekoha;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.arzcbnh.tekoha.auth.PasswordEntry;
import dev.arzcbnh.tekoha.data.PlayerData;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedDataType;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModData extends SavedData {
//    private final Map<UUID, Integer> usernameColors;
//    private final Map<UUID, PasswordEntry> passwords;
//    private final Map<UUID, Set<EquipmentSlot>> hiddenEquipment;
//    private final GameType defaultGameType;
//    private final Set<EquipmentSlot> hiddenEquipment;

//    public final Codec<Set<EquipmentSlot>> SET_EQUIPMENT_SLOT_CODEC = EquipmentSlot.CODEC.listOf().xmap(list -> EnumSet., set -> set.stream().toList())



//    final Codec<ModData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
//            Codec.unboundedMap(UUIDUtil.CODEC, Codec.INT).optionalFieldOf("UsernameColors").forGetter(o -> Optional.of(getUsernameColors())))
//                .apply(instance, ModData::new));
//
//    private ModData(Optional<Map<UUID, Integer>> usernameColors) {
//        this.usernameColors = usernameColors.orElse(new HashMap<>());
//    }
//
//    private Map<UUID, Integer> getUsernameColors() {
//        return this.usernameColors;
//    }



//                    Codec.unboundedMap(UUIDUtil.CODEC, PasswordEntry.CODEC).optionalFieldOf("passwords").forGetter(ModData::getPasswords),
//                    Codec.unboundedMap(UUIDUtil.CODEC, ).optionalFieldOf("passwords").forGetter(ModData::getPasswords),
//                    PasswordEntry.CODEC.optionalFieldOf("password").forGetter(PlayerData::getPassword),
//                    GameType.CODEC.optionalFieldOf("gametype").forGetter(PlayerData::getDefaultGameType),
//                    EquipmentSlot.CODEC
//                            .listOf()
//                            .fieldOf("hiddenEquipment")
//                            .forGetter(obj -> obj.hiddenEquipment.stream().toList()))
//            .apply(
//                    instance,
//                    (password, gametype, equipment) -> new PlayerData(player, password, gametype, equipment)));
//
//    final SavedDataType<PlayerData> TYPE = new SavedDataType<>(
//            "%s-player-%s".formatted(TekohaAdditions.MOD_ID, player.getUUID()),
//            () -> new PlayerData(player),
//            codec,
//            null);
//
//    private ModData(
//            ServerPlayer player,
//            @Nullable PasswordEntry password,
//            @Nullable GameType gametype,
//            Set<EquipmentSlot> hiddenEquipment) {
//        this.player = player;
//        this.password = password;
//        this.defaultGameType = gametype;
//        this.hiddenEquipment = hiddenEquipment;
//    }
//
//    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
//    private PlayerData(
//            ServerPlayer player,
//            Optional<PasswordEntry> password,
//            Optional<GameType> gametype,
//            List<EquipmentSlot> hiddenEquipment) {
//        this(player, password.orElse(null), gametype.orElse(null), new HashSet<>(hiddenEquipment));
//    }
//
//    private PlayerData(ServerPlayer player) {
//        this.player = player;
//        this.hiddenEquipment = new HashSet<>();
//    }
//
//    public static PlayerData of(ServerPlayer player) {
//        final Codec<PlayerData> codec = RecordCodecBuilder.create(instance -> instance.group(
//                        PasswordEntry.CODEC.optionalFieldOf("password").forGetter(PlayerData::getPassword),
//                        GameType.CODEC.optionalFieldOf("gametype").forGetter(PlayerData::getDefaultGameType),
//                        EquipmentSlot.CODEC
//                                .listOf()
//                                .fieldOf("hiddenEquipment")
//                                .forGetter(obj -> obj.hiddenEquipment.stream().toList()))
//                .apply(
//                        instance,
//                        (password, gametype, equipment) -> new PlayerData(player, password, gametype, equipment)));
//
//        final SavedDataType<PlayerData> type = new SavedDataType<>(
//                "%s-player-%s".formatted(TekohaAdditions.MOD_ID, player.getUUID()),
//                () -> new PlayerData(player),
//                codec,
//                null);
//
//        return Objects.requireNonNull(player.level().getServer().getLevel(ServerLevel.OVERWORLD))
//                .getDataStorage()
//                .computeIfAbsent(type);
//    }
//
//    public Optional<PasswordEntry> getPassword() {
//        return Optional.ofNullable(password);
//    }
//
//    public void setPassword(@Nullable String password) {
//        this.password = password == null ? null : PasswordEntry.of(password);
//        this.setDirty();
//    }
//
//    public Optional<GameType> getDefaultGameType() {
//        return Optional.ofNullable(defaultGameType);
//    }
//
//    public void setDefaultGameType(@Nullable GameType defaultGameType) {
//        this.defaultGameType = defaultGameType;
//        this.setDirty();
//    }
//
//    public boolean isEquipmentHidden(EquipmentSlot slot) {
//        return hiddenEquipment.contains(slot);
//    }
//
//    public void setEquipmentHidden(EquipmentSlot slot, boolean bl) {
//        if (bl) {
//            hiddenEquipment.add(slot);
//        } else {
//            hiddenEquipment.remove(slot);
//        }
//    }
//
//    public boolean isAuthenticated() {
//        return this.defaultGameType == null;
//    }
}
