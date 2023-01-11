package me.hsgamer.bettergui.paperspec;

import me.hsgamer.bettergui.builder.InventoryBuilder;
import me.hsgamer.bettergui.builder.ItemModifierBuilder;
import me.hsgamer.bettergui.paperspec.modifier.AdventureLoreModifier;
import me.hsgamer.bettergui.paperspec.modifier.AdventureNameModifier;
import me.hsgamer.bettergui.paperspec.modifier.PaperSkullModifier;
import me.hsgamer.bettergui.paperspec.util.AdventureUtils;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.bukkit.gui.GUIHolder;
import me.hsgamer.hscore.bukkit.gui.GUIUtils;
import me.hsgamer.hscore.common.Validate;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

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
                Validate.isMethodLoaded("org.bukkit.inventory.meta.ItemMeta", "displayName") &&
                Validate.isMethodLoaded("org.bukkit.Bukkit", "createInventory", InventoryHolder.class, int.class, Component.class)
               );
    }

    @Override
    public void onEnable() {
        ItemModifierBuilder.INSTANCE.register(PaperSkullModifier::new, "paper-skull", "paper-head", "skull$", "head$");
        ItemModifierBuilder.INSTANCE.register(AdventureNameModifier::new, "mini-name", "name$");
        ItemModifierBuilder.INSTANCE.register(AdventureLoreModifier::new, "mini-lore", "lore$");

        InventoryBuilder.INSTANCE.register(pair -> (display, uuid) -> {
            GUIHolder holder = display.getHolder();
            InventoryType type = holder.getInventoryType();
            int size = holder.getSize(uuid);

            // MiniMessage deserializer will throw a runtime exception
            // if the string contains any legacy color codes (&1, &b).
            // So we strip it in case the menu fails to be loaded.
            String title = ChatColor.stripColor(holder.getTitle(uuid));

            Component adventure$title = AdventureUtils.toComponent(title);
            return type == InventoryType.CHEST && size > 0
                   ? Bukkit.createInventory(display, GUIUtils.normalizeToChestSize(size), adventure$title)
                   : Bukkit.createInventory(display, type, adventure$title);
        }, "mini-title");
    }
}
