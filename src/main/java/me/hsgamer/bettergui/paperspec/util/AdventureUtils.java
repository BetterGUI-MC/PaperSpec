package me.hsgamer.bettergui.paperspec.util;

import io.github.miniplaceholders.api.MiniPlaceholders;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecorationAndState;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class AdventureUtils {
    private static final TextDecorationAndState ITALIC_OFF = TextDecoration.ITALIC.withState(TextDecoration.State.FALSE);
    private static final boolean IS_MINI_PLACEHOLDERS_SUPPORTED;

    static {
        boolean isMiniPlaceholdersSupported = false;
        if (Bukkit.getPluginManager().getPlugin("MiniPlaceholders") != null) {
            try {
                Class<?> clazz = Class.forName("io.github.miniplaceholders.api.MiniPlaceholders");
                clazz.getDeclaredMethod("audienceGlobalPlaceholders");
                Class<?> pointerClass = Class.forName("net.kyori.adventure.pointer.Pointered");
                MiniMessage.class.getDeclaredMethod("deserialize", String.class, pointerClass, TagResolver.class);
                isMiniPlaceholdersSupported = true;
            } catch (Exception e) {
                // IGNORE
            }
        }
        IS_MINI_PLACEHOLDERS_SUPPORTED = isMiniPlaceholdersSupported;
    }

    private AdventureUtils() {
        // EMPTY
    }

    @Contract(pure = true)
    public static @NotNull Component disableItalic(@NotNull Component component) {
        return component.applyFallbackStyle(ITALIC_OFF);
    }

    @Contract(pure = true)
    public static @NotNull List<Component> disableItalic(@NotNull List<Component> componentList) {
        return componentList.stream().map(AdventureUtils::disableItalic).toList();
    }

    public static @NotNull Component toComponent(UUID uuid, @NotNull String mini) {
        if (IS_MINI_PLACEHOLDERS_SUPPORTED) {
            TagResolver tagResolver = MiniPlaceholders.audienceGlobalPlaceholders();
            Player player = Bukkit.getPlayer(uuid);
            if (player != null) {
                return MiniMessage.miniMessage().deserialize(mini, tagResolver);
            }
        }
        return MiniMessage.miniMessage().deserialize(mini);
    }

    public static @NotNull List<Component> toComponent(UUID uuid, @NotNull List<String> miniList) {
        return miniList.stream().map(s -> toComponent(uuid, s)).toList();
    }

    public static @NotNull String toMiniMessage(@NotNull Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    public static @NotNull List<String> toMiniMessage(@NotNull List<Component> componentList) {
        return componentList.stream().map(AdventureUtils::toMiniMessage).toList();
    }
}
