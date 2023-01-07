package me.hsgamer.bettergui.paperspec.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecorationAndState;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class AdventureUtils {

    private static final TextDecorationAndState ITALIC_OFF = TextDecoration.ITALIC.withState(TextDecoration.State.FALSE);

    @Contract(pure = true)
    public static @NotNull Component disableItalic(@NotNull Component component) {
        return component.applyFallbackStyle(ITALIC_OFF);
    }

    @Contract(pure = true)
    public static @NotNull List<Component> disableItalic(@NotNull List<Component> componentList) {
        return componentList.stream().map(AdventureUtils::disableItalic).collect(Collectors.toList());
    }

    public static @NotNull Component toComponent(@NotNull String mini) {
        return MiniMessage.miniMessage().deserialize(mini);
    }

    public static @NotNull List<Component> toComponent(@NotNull List<String> miniList) {
        return miniList.stream().map(AdventureUtils::toComponent).collect(Collectors.toList());
    }

    public static @NotNull String toMiniMessage(@NotNull Component component) {
        return MiniMessage.miniMessage().serialize(component);
    }

    public static @NotNull List<String> toMiniMessage(@NotNull List<Component> componentList) {
        return componentList.stream().map(AdventureUtils::toMiniMessage).collect(Collectors.toList());
    }

    public static @NotNull String toPlainText(@NotNull Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }

    public static @NotNull List<String> toPlainText(@NotNull List<Component> componentList) {
        return componentList.stream().map(AdventureUtils::toPlainText).collect(Collectors.toList());
    }

    public static @NotNull String stripTags(@NotNull String mini) {
        return MiniMessage.miniMessage().stripTags(mini);
    }

    public static @NotNull List<String> stripTags(@NotNull List<String> miniList) {
        return miniList.stream().map(AdventureUtils::stripTags).collect(Collectors.toList());
    }

}
