package com.halloween.trickortreat.items;

import com.halloween.trickortreat.TrickOrTreatPixelmonMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

public class CandyManager {
    
    private final TrickOrTreatPixelmonMod mod;
    private static final String CANDY_NBT_KEY = "HalloweenCandy";
    private static final String CANDY_TYPE_REGULAR = "regular";
    private static final String CANDY_TYPE_RARE = "rare";
    
    public CandyManager(TrickOrTreatPixelmonMod mod) {
        this.mod = mod;
    }
    
    /**
     * Creates a regular Halloween candy item
     */
    public ItemStack createRegularCandy() {
        ItemStack candy = new ItemStack(Item.SUGAR);
        
        CompoundNBT nbt = candy.getOrCreateTag();
        nbt.putString(CANDY_NBT_KEY, CANDY_TYPE_REGULAR);
        
        // Set display properties
        CompoundNBT display = nbt.getCompound("display");
        display.putString("Name", "{\"text\":\"🍬 Halloween Candy\",\"color\":\"gold\",\"bold\":true}");
        
        // Set lore
        ListNBT lore = new ListNBT();
        lore.add(StringNBT.valueOf("{\"text\":\"A spooky Halloween treat!\",\"color\":\"gray\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"Right-click to activate trick or treat!\",\"color\":\"yellow\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"\",\"color\":\"white\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"§7Drop chance: §e" + mod.getConfigManager().getCandyDropChance() + "%\",\"color\":\"gray\"}"));
        display.put("Lore", lore);
        
        nbt.put("display", display);
        
        System.out.println("🍬 Created regular Halloween candy");
        return candy;
    }
    
    /**
     * Creates a rare Halloween candy item
     */
    public ItemStack createRareCandy() {
        ItemStack rareCandy = new ItemStack(Item.GOLDEN_APPLE);
        
        CompoundNBT nbt = rareCandy.getOrCreateTag();
        nbt.putString(CANDY_NBT_KEY, CANDY_TYPE_RARE);
        
        // Set display properties
        CompoundNBT display = nbt.getCompound("display");
        display.putString("Name", "{\"text\":\"🌟 Rare Halloween Candy\",\"color\":\"light_purple\",\"bold\":true}");
        
        // Set lore
        ListNBT lore = new ListNBT();
        lore.add(StringNBT.valueOf("{\"text\":\"A mystical Halloween treat!\",\"color\":\"gray\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"Contains powerful skyblock magic...\",\"color\":\"dark_purple\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"Right-click to activate rare rewards!\",\"color\":\"yellow\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"\",\"color\":\"white\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"§7Rare chance: §d1%\",\"color\":\"gray\"}"));
        lore.add(StringNBT.valueOf("{\"text\":\"§7Cooldown: §c1 hour\",\"color\":\"gray\"}"));
        display.put("Lore", lore);
        
        nbt.put("display", display);
        
        System.out.println("🌟 Created rare Halloween candy");
        return rareCandy;
    }
    
    /**
     * Checks if an item is a regular Halloween candy
     */
    public boolean isRegularCandy(ItemStack item) {
        if (item == null || item.isEmpty()) {
            return false;
        }
        
        CompoundNBT nbt = item.getTag();
        if (nbt == null || !nbt.contains(CANDY_NBT_KEY)) {
            return false;
        }
        
        return CANDY_TYPE_REGULAR.equals(nbt.getString(CANDY_NBT_KEY));
    }
    
    /**
     * Checks if an item is a rare Halloween candy
     */
    public boolean isRareCandy(ItemStack item) {
        if (item == null || item.isEmpty()) {
            return false;
        }
        
        CompoundNBT nbt = item.getTag();
        if (nbt == null || !nbt.contains(CANDY_NBT_KEY)) {
            return false;
        }
        
        return CANDY_TYPE_RARE.equals(nbt.getString(CANDY_NBT_KEY));
    }
    
    /**
     * Checks if an item is any type of Halloween candy
     */
    public boolean isHalloweenCandy(ItemStack item) {
        return isRegularCandy(item) || isRareCandy(item);
    }
    
    /**
     * Gets the candy type from an item
     */
    public String getCandyType(ItemStack item) {
        if (item == null || item.isEmpty()) {
            return null;
        }
        
        CompoundNBT nbt = item.getTag();
        if (nbt == null || !nbt.contains(CANDY_NBT_KEY)) {
            return null;
        }
        
        return nbt.getString(CANDY_NBT_KEY);
    }
}
