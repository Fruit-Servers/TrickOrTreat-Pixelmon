package com.halloween.trickortreat.rewards;

import com.halloween.trickortreat.TrickOrTreatPixelmonMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Random;

public class RareTreatManager {
    
    private final TrickOrTreatPixelmonMod mod;
    private final Random random;
    
    public RareTreatManager(TrickOrTreatPixelmonMod mod) {
        this.mod = mod;
        this.random = new Random();
        System.out.println("🎁 Rare treat manager initialized");
    }
    
    /**
     * Activates rare treat system for a player
     */
    public void activateRareTreat(Object player) {
        double treatChance = mod.getConfigManager().getRareTreatChance();
        double roll = random.nextDouble() * 100.0;
        
        System.out.println("🎲 Rare treat roll: " + String.format("%.2f", roll) + "% (need <= " + treatChance + "%)");
        
        if (roll <= treatChance) {
            // Select and give rare treat
            RareTreat treat = selectRandomTreat();
            giveTreat(player, treat);
        } else {
            // Give rare trick (simplified for now)
            System.out.println("🎪 Rare trick activated! Something spooky happened!");
            sendMessageToPlayer(player, "§5§lRARE TRICK! §r§5Something very spooky happened!");
        }
    }
    
    /**
     * Selects a random rare treat based on configured weights
     */
    private RareTreat selectRandomTreat() {
        Map<String, Integer> chances = mod.getConfigManager().getRareTreatChances();
        int totalWeight = chances.values().stream().mapToInt(Integer::intValue).sum();
        int randomValue = random.nextInt(totalWeight);
        
        int currentWeight = 0;
        for (Map.Entry<String, Integer> entry : chances.entrySet()) {
            currentWeight += entry.getValue();
            if (randomValue < currentWeight) {
                return RareTreat.fromConfigKey(entry.getKey());
            }
        }
        
        return RareTreat.TOKEN; // Fallback
    }
    
    /**
     * Gives a specific treat to a player
     */
    public void giveTreat(Object player, RareTreat treat) {
        System.out.println("🎁 Giving treat: " + treat.getDisplayName());
        
        switch (treat) {
            case BLOCK_OF_NETHERITE:
                // Hardcoded netherite block
                ItemStack netherite = new ItemStack(Item.ANCIENT_DEBRIS); // Using ancient debris as substitute
                giveItemToPlayer(player, netherite);
                sendMessageToPlayer(player, "§8💎 " + treat.getDescription());
                break;
                
            default:
                // Custom item treats (need to be configured by admin)
                giveCustomItemTreat(player, treat);
                break;
        }
    }
    
    /**
     * Gives a custom item treat (needs to be configured by admin)
     */
    private void giveCustomItemTreat(Object player, RareTreat treat) {
        // In real implementation, this would get the custom item from config
        // For now, just simulate giving the item
        System.out.println("📦 Custom item treat: " + treat.getDisplayName());
        sendMessageToPlayer(player, "§d§lRARE TREAT! §r§d" + treat.getDescription());
        
        // Note: Admin needs to configure custom items using /trickortreat set item <treat-key>
        System.out.println("⚠️  Note: Custom item '" + treat.getConfigKey() + "' needs to be configured by admin");
    }
    
    /**
     * Gives an item to a player
     */
    private void giveItemToPlayer(Object player, ItemStack item) {
        System.out.println("📦 Giving " + item.getCount() + "x " + item.getItem().getClass().getSimpleName() + " to player");
        // In real implementation: player.inventory.add(item) or drop at location
    }
    
    /**
     * Sends a message to a player
     */
    private void sendMessageToPlayer(Object player, String message) {
        System.out.println("💬 Message to " + getPlayerName(player) + ": " + message);
        // In real implementation: player.sendMessage(new StringTextComponent(message))
    }
    
    /**
     * Gets player name for commands
     */
    private String getPlayerName(Object player) {
        // In real implementation: ((ServerPlayerEntity) player).getName().getString()
        return "TestPlayer";
    }
    
    /**
     * Forces a specific treat for testing/admin commands
     */
    public void forceGiveTreat(Object player, String treatKey) {
        RareTreat treat = RareTreat.fromConfigKey(treatKey);
        System.out.println("🔧 Force giving treat: " + treat.getDisplayName());
        giveTreat(player, treat);
    }
    
    /**
     * Gets all available treats for tab completion
     */
    public String[] getAllTreatKeys() {
        RareTreat[] treats = RareTreat.values();
        String[] keys = new String[treats.length];
        for (int i = 0; i < treats.length; i++) {
            keys[i] = treats[i].getConfigKey();
        }
        return keys;
    }
    
    /**
     * Gets treat statistics
     */
    public String getTreatStats() {
        Map<String, Integer> chances = mod.getConfigManager().getRareTreatChances();
        int totalWeight = chances.values().stream().mapToInt(Integer::intValue).sum();
        
        StringBuilder stats = new StringBuilder("🎁 Rare Treat Chances:\n");
        for (RareTreat treat : RareTreat.values()) {
            int weight = chances.getOrDefault(treat.getConfigKey(), 0);
            double percentage = (weight * 100.0) / totalWeight;
            stats.append(String.format("  %s: %.1f%%\n", treat.getDisplayName(), percentage));
        }
        
        return stats.toString();
    }
}
