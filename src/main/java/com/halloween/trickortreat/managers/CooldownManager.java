package com.halloween.trickortreat.managers;

import com.halloween.trickortreat.TrickOrTreatPixelmonMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    
    private final TrickOrTreatPixelmonMod mod;
    private final Map<UUID, Long> rareCandyCooldowns;
    private final Map<UUID, Long> candyUseCooldowns;
    
    public CooldownManager(TrickOrTreatPixelmonMod mod) {
        this.mod = mod;
        this.rareCandyCooldowns = new HashMap<>();
        this.candyUseCooldowns = new HashMap<>();
        System.out.println("🕒 Cooldown manager initialized");
    }
    
    // ===== RARE CANDY COOLDOWNS =====
    
    /**
     * Checks if a player is on rare candy cooldown
     */
    public boolean isOnRareCandyCooldown(UUID playerId) {
        Long cooldownEnd = rareCandyCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        if (currentTime >= cooldownEnd) {
            rareCandyCooldowns.remove(playerId);
            return false;
        }
        
        return true;
    }
    
    /**
     * Sets rare candy cooldown for a player
     */
    public void setRareCandyCooldown(UUID playerId) {
        long cooldownEnd = System.currentTimeMillis() + mod.getConfigManager().getRareCandyCooldownMillis();
        rareCandyCooldowns.put(playerId, cooldownEnd);
        System.out.println("🕒 Set rare candy cooldown for player: " + playerId);
    }
    
    /**
     * Gets remaining rare candy cooldown time in milliseconds
     */
    public long getRareCandyCooldownRemaining(UUID playerId) {
        Long cooldownEnd = rareCandyCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long remaining = cooldownEnd - currentTime;
        
        return Math.max(0, remaining);
    }
    
    /**
     * Clears rare candy cooldown for a player
     */
    public void clearRareCandyCooldown(UUID playerId) {
        rareCandyCooldowns.remove(playerId);
        System.out.println("🕒 Cleared rare candy cooldown for player: " + playerId);
    }
    
    // ===== CANDY USE COOLDOWNS =====
    
    /**
     * Checks if a player is on candy use cooldown
     */
    public boolean isOnCandyUseCooldown(UUID playerId) {
        Long cooldownEnd = candyUseCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return false;
        }
        
        long currentTime = System.currentTimeMillis();
        if (currentTime >= cooldownEnd) {
            candyUseCooldowns.remove(playerId);
            return false;
        }
        
        return true;
    }
    
    /**
     * Sets candy use cooldown for a player
     */
    public void setCandyUseCooldown(UUID playerId) {
        long cooldownEnd = System.currentTimeMillis() + mod.getConfigManager().getCandyUseCooldownMillis();
        candyUseCooldowns.put(playerId, cooldownEnd);
    }
    
    /**
     * Gets remaining candy use cooldown time in milliseconds
     */
    public long getCandyUseCooldownRemaining(UUID playerId) {
        Long cooldownEnd = candyUseCooldowns.get(playerId);
        
        if (cooldownEnd == null) {
            return 0;
        }
        
        long currentTime = System.currentTimeMillis();
        long remaining = cooldownEnd - currentTime;
        
        return Math.max(0, remaining);
    }
    
    // ===== UTILITY METHODS =====
    
    /**
     * Formats cooldown time into human-readable format
     */
    public String formatCooldownTime(long milliseconds) {
        if (milliseconds <= 0) {
            return "0s";
        }
        
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        
        if (hours > 0) {
            minutes = minutes % 60;
            if (minutes > 0) {
                return String.format("%dh %dm", hours, minutes);
            } else {
                return String.format("%dh", hours);
            }
        } else if (minutes > 0) {
            seconds = seconds % 60;
            if (seconds > 0) {
                return String.format("%dm %ds", minutes, seconds);
            } else {
                return String.format("%dm", minutes);
            }
        } else {
            return String.format("%.1fs", seconds);
        }
    }
    
    /**
     * Clears all cooldowns for a specific player
     */
    public void clearAllCooldowns(UUID playerId) {
        rareCandyCooldowns.remove(playerId);
        candyUseCooldowns.remove(playerId);
        System.out.println("🕒 Cleared all cooldowns for player: " + playerId);
    }
    
    /**
     * Clears all cooldowns for all players
     */
    public void clearAllCooldowns() {
        int rareCount = rareCandyCooldowns.size();
        int useCount = candyUseCooldowns.size();
        
        rareCandyCooldowns.clear();
        candyUseCooldowns.clear();
        
        System.out.println("🕒 Cleared all cooldowns (" + rareCount + " rare, " + useCount + " use)");
    }
    
    /**
     * Gets cooldown statistics
     */
    public String getCooldownStats() {
        return String.format("Active cooldowns: %d rare candy, %d candy use", 
            rareCandyCooldowns.size(), candyUseCooldowns.size());
    }
}
