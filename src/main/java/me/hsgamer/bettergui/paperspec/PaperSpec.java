package me.hsgamer.bettergui.paperspec;

import me.hsgamer.bettergui.builder.InventoryBuilder;
import me.hsgamer.bettergui.builder.ItemModifierBuilder;
import me.hsgamer.bettergui.paperspec.modifier.AdventureLoreModifier;
import me.hsgamer.bettergui.paperspec.modifier.AdventureNameModifier;
import me.hsgamer.bettergui.paperspec.modifier.PaperSkullModifier;
import me.hsgamer.bettergui.paperspec.util.AdventureUtils;
import me.hsgamer.bettergui.util.MapUtil;
import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.bukkit.gui.GUIHolder;
import me.hsgamer.hscore.bukkit.gui.GUIUtils;
import me.hsgamer.hscore.common.Validate;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

        InventoryBuilder.INSTANCE.register(pair -> {
            Map<String, Object> map = pair.getValue();
            String title = Optional.ofNullable(MapUtil.getIfFound(map, "mini-title", "title$"))
                .map(String::valueOf)
                .orElse("");
            return (display, uuid) -> {
                GUIHolder holder = display.getHolder();
                InventoryType type = holder.getInventoryType();
                int size = holder.getSize(uuid);
                Component adventure$title = AdventureUtils.toComponent(title);
                return type == InventoryType.CHEST && size > 0
                       ? Bukkit.createInventory(display, GUIUtils.normalizeToChestSize(size), adventure$title)
                       : Bukkit.createInventory(display, type, adventure$title);
            };
        }, "mini-title");
    }
}
