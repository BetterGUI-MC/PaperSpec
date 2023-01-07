package me.hsgamer.bettergui.paperspec.modifier;

import me.hsgamer.bettergui.paperspec.util.AdventureUtils;
import me.hsgamer.hscore.bukkit.item.ItemMetaModifier;
import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.common.interfaces.StringReplacer;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;

import java.util.*;

public class AdventureLoreModifier extends ItemMetaModifier {
    private final List<String> lore = new ArrayList<>(); // Stored as MiniMessage representation

    @Override
    public String getName() {
        return "adventure-lore";
    }

    private List<String> getReplacedLore(UUID uuid, Map<String, StringReplacer> stringReplacerMap) {
        List<String> replacedLore = new ArrayList<>(lore);
        replacedLore.replaceAll(s -> StringReplacer.replace(s, uuid, stringReplacerMap.values()));
        return replacedLore;
    }

    @Override
    public ItemMeta modifyMeta(ItemMeta meta, UUID uuid, Map<String, StringReplacer> stringReplacerMap) {
        List<Component> lore = AdventureUtils.toComponent(getReplacedLore(uuid, stringReplacerMap));
        List<Component> noItalic = AdventureUtils.disableItalic(lore);
        meta.lore(noItalic);
        return meta;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void loadFromItemMeta(ItemMeta meta) {
        setLore(AdventureUtils.toMiniMessage(meta.lore()));
    }

    @Override
    public boolean canLoadFromItemMeta(ItemMeta meta) {
        return meta.hasLore();
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public boolean compareWithItemMeta(ItemMeta meta, UUID uuid, Map<String, StringReplacer> stringReplacerMap) {
        if (!meta.hasLore() && this.lore.isEmpty()) {
            return true;
        }

        // Since text components are complex, we compare the plain text representation for equality
        List<String> plainText1 = AdventureUtils.stripTags(getReplacedLore(uuid, stringReplacerMap));
        List<String> plainText2 = AdventureUtils.toPlainText(meta.lore());
        return plainText1.equals(plainText2);
    }

    @Override
    public Object toObject() {
        return lore;
    }

    @Override
    public void loadFromObject(Object object) {
        setLore(CollectionUtils.createStringListFromObject(object, false));
    }

    /**
     * Set the lore
     *
     * @param lore the lore
     *
     * @return {@code this} for builder chain
     */
    @Contract("_ -> this")
    public AdventureLoreModifier setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    /**
     * Add a lore
     *
     * @param lore the lore
     *
     * @return {@code this} for builder chain
     */
    @Contract("_ -> this")
    public AdventureLoreModifier addLore(String lore) {
        this.lore.addAll(Arrays.asList(lore.split("\\n")));
        return this;
    }

    /**
     * Set the lore
     *
     * @param lore the lore
     *
     * @return {@code this} for builder chain
     */
    @Contract("_ -> this")
    public AdventureLoreModifier setLore(Collection<String> lore) {
        clearLore();
        this.lore.addAll(CollectionUtils.splitAll("\\n", lore));
        return this;
    }

    /**
     * Clear the lore
     *
     * @return {@code this} for builder chain
     */
    @Contract(" -> this")
    public AdventureLoreModifier clearLore() {
        this.lore.clear();
        return this;
    }
}
