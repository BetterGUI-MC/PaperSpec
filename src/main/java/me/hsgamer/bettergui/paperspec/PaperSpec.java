package me.hsgamer.bettergui.paperspec;

import me.hsgamer.bettergui.builder.ItemModifierBuilder;
import me.hsgamer.bettergui.paperspec.modifier.AdventureLoreModifier;
import me.hsgamer.bettergui.paperspec.modifier.AdventureNameModifier;
import me.hsgamer.bettergui.paperspec.modifier.PaperSkullModifier;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.common.Validate;
import net.kyori.adventure.text.Component;

import java.util.List;

public final class PaperSpec extends PluginAddon {
    @Override
    public boolean onLoad() {
        return (Validate.isClassLoaded("com.destroystokyo.paper.PaperConfig") ||
                Validate.isClassLoaded("io.papermc.paper.configuration.Configuration")
               ) &&
               (Validate.isClassLoaded("net.kyori.adventure.text.Component") &&
                Validate.isClassLoaded("net.kyori.adventure.text.minimessage.MiniMessage") &&
                Validate.isClassLoaded("net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer") &&
                Validate.isMethodLoaded("org.bukkit.inventory.meta.ItemMeta", "lore", List.class) &&
                Validate.isMethodLoaded("org.bukkit.inventory.meta.ItemMeta", "lore") &&
                Validate.isMethodLoaded("org.bukkit.inventory.meta.ItemMeta", "displayName", Component.class) &&
                Validate.isMethodLoaded("org.bukkit.inventory.meta.ItemMeta", "displayName")
               );
    }

    @Override
    public void onEnable() {
        ItemModifierBuilder.INSTANCE.register(PaperSkullModifier::new, "paper-skull", "paper-head");
        ItemModifierBuilder.INSTANCE.register(AdventureNameModifier::new, "mini-name", "adventure-name", "name$");
        ItemModifierBuilder.INSTANCE.register(AdventureLoreModifier::new, "mini-lore", "adventure-lore", "lore$");
    }
}
