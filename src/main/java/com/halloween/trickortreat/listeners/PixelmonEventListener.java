package com.halloween.trickortreat.listeners;

import com.halloween.trickortreat.TrickOrTreatPixelmonMod;
import com.pixelmonmod.pixelmon.api.events.BeatWildPixelmonEvent;
import com.pixelmonmod.pixelmon.api.events.CaptureEvent;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.battles.controller.participants.WildPixelmonParticipant;
import com.pixelmonmod.pixelmon.entities.pixelmon.PixelmonEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;
import java.util.UUID;

public class PixelmonEventListener {
    
    private final TrickOrTreatPixelmonMod mod;
    private final Random random;
    
    public PixelmonEventListener(TrickOrTreatPixelmonMod mod) {
        this.mod = mod;
        this.random = new Random();
        System.out.println("🎮 Pixelmon event listener initialized");
    }
    
    @SubscribeEvent
    public void onPixelmonBeat(BeatWildPixelmonEvent event) {
        if (event.player == null) return;
        
        System.out.println("🥊 Pixelmon defeated by player: " + event.player.getName().getString());
        
        ServerPlayerEntity player = event.player;
        WildPixelmonParticipant wildParticipant = event.wpp;
        Pokemon pokemon = wildParticipant.pokemon;
        
        processCandyDrop(player, pokemon, "defeat");
    }
    
    @SubscribeEvent
    public void onPixelmonCapture(CaptureEvent.SuccessfulCapture event) {
        if (event.getPlayer() == null) return;
        
        System.out.println("🎯 Pixelmon captured by player: " + event.getPlayer().getName().getString());
        
        ServerPlayerEntity player = event.getPlayer();
        PixelmonEntity pixelmonEntity = event.getPokemon();
        Pokemon pokemon = pixelmonEntity.getPokemon();
        
        processCandyDrop(player, pokemon, "capture");
    }
    
    private void processCandyDrop(ServerPlayerEntity player, Pokemon pokemon, String method) {
        if (!mod.getConfigManager().isCandyDropEnabled()) {
            System.out.println("❌ Candy drops are disabled in config");
            return;
        }
        
        if (!shouldDropCandy(pokemon)) {
            System.out.println("❌ This Pixelmon type doesn't drop candy");
            return;
        }
        
        // Get player UUID from the actual player
        UUID playerId = player.getUUID();
        
        double dropChance = mod.getConfigManager().getCandyDropChance();
        double roll = random.nextDouble() * 100.0;
        
        System.out.println("🎲 Drop roll: " + String.format("%.2f", roll) + "% (need <= " + dropChance + "%)");
        
        if (roll <= dropChance) {
            // Check for rare candy (1% chance)
            double rareRoll = random.nextDouble() * 100.0;
            boolean shouldGiveRareCandy = rareRoll <= 1.0;
            
            System.out.println("🌟 Rare roll: " + String.format("%.2f", rareRoll) + "% (need <= 1.0%)");
            
            if (shouldGiveRareCandy && !mod.getCooldownManager().isOnRareCandyCooldown(playerId)) {
                // Give rare candy
                ItemStack rareCandy = mod.getCandyManager().createRareCandy();
                giveItemToPlayer(player, rareCandy);
                mod.getCooldownManager().setRareCandyCooldown(playerId);
                
                System.out.println("🌟 Gave rare candy to player! (via " + method + ")");
                
            } else if (shouldGiveRareCandy && mod.getCooldownManager().isOnRareCandyCooldown(playerId)) {
                // Rare candy was rolled but player is on cooldown - do nothing
                long remaining = mod.getCooldownManager().getRareCandyCooldownRemaining(playerId);
                System.out.println("⏰ Rare candy rolled but player on cooldown (" + 
                    mod.getCooldownManager().formatCooldownTime(remaining) + " remaining)");
                return;
                
            } else {
                // Give regular candy
                ItemStack candy = mod.getCandyManager().createRegularCandy();
                giveItemToPlayer(player, candy);
                
                System.out.println("🍬 Gave regular candy to player! (via " + method + ")");
            }
        } else {
            System.out.println("❌ No candy drop (failed roll)");
        }
    }
    
    private boolean shouldDropCandy(Pokemon pokemon) {
        // Don't drop from player-owned Pokemon
        if (pokemon.getOwnerPlayerUUID() != null) {
            return false;
        }
        
        // Check specific Pokemon types
        if (pokemon.isLegendary()) {
            return mod.getConfigManager().canDropFromLegendary();
        }
        
        if (pokemon.isMythical()) {
            return mod.getConfigManager().canDropFromMythical();
        }
        
        if (pokemon.isUltraBeast()) {
            return mod.getConfigManager().canDropFromUltraBeast();
        }
        
        // Regular wild Pokemon
        return mod.getConfigManager().canDropFromWild();
    }
    
    private void giveItemToPlayer(ServerPlayerEntity player, ItemStack item) {
        // Try to add to player inventory, if full drop at player location
        boolean added = player.inventory.add(item);
        if (!added) {
            // Drop the item at player's location if inventory is full
            player.drop(item, false);
            player.sendMessage(new StringTextComponent("§6Your inventory is full! Candy dropped at your feet."), Util.NIL_UUID);
        }
        System.out.println("📦 Gave item to player: " + player.getName().getString());
    }
    
//    /**
//     * Simulates a Pixelmon defeat for testing
//     */
//    public void simulatePixelmonDefeat(boolean isLegendary, boolean isMythical, boolean isUltraBeast) {
//        Pokemon pokemon = new BeatWildPixelmonEvent.Pokemon(isLegendary, isMythical, isUltraBeast);
//        Pokemon wildPixelmon = new BeatWildPixelmonEvent.WildPixelmon(pokemon);
//        BeatWildPixelmonEvent event = new BeatWildPixelmonEvent("TestPlayer", wildPixelmon);
//
//        onPixelmonBeat(event);
//    }
//
//    /**
//     * Simulates a Pixelmon capture for testing
//     */
//    public void simulatePixelmonCapture(boolean isLegendary, boolean isMythical, boolean isUltraBeast) {
//        BeatWildPixelmonEvent.Pokemon pokemon = new BeatWildPixelmonEvent.Pokemon(isLegendary, isMythical, isUltraBeast);
//        CaptureEvent.PokemonWrapper wrapper = new CaptureEvent.PokemonWrapper(pokemon);
//        CaptureEvent.SuccessfulCapture event = new CaptureEvent.SuccessfulCapture("TestPlayer", wrapper);
//
//        onPixelmonCapture(event);
//    }
}
