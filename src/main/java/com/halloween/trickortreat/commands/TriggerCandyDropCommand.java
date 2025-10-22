package com.halloween.trickortreat.commands;

import com.halloween.trickortreat.TrickOrTreatPixelmonMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

import java.util.Random;

public class TriggerCandyDropCommand {
    
    private final TrickOrTreatPixelmonMod mod;
    private final Random random;
    
    public TriggerCandyDropCommand(TrickOrTreatPixelmonMod mod) {
        this.mod = mod;
        this.random = new Random();
    }
    
    public static void register(CommandDispatcher<CommandSource> dispatcher, TrickOrTreatPixelmonMod mod) {
        System.out.println("🔧 Registering /triggercandydrop command");
        
        // In real implementation, this would use proper Brigadier syntax
        dispatcher.register(Commands.literal("triggercandydrop"));
    }
    
    /**
     * Handles the triggercandydrop command
     */
    public static int execute(CommandContext<CommandSource> context, TrickOrTreatPixelmonMod mod, String[] args) {
        CommandSource source = context.getSource();
        
        if (!source.hasPermission(2)) {
            source.sendFailure(new StringTextComponent("§cYou don't have permission to use this command!"));
            return 0;
        }
        
        if (args.length < 1) {
            source.sendFailure(new StringTextComponent("§cUsage: /triggercandydrop <player> [force-rare]"));
            return 0;
        }
        
        String playerName = args[0];
        boolean forceRare = args.length > 1 && "true".equalsIgnoreCase(args[1]);
        
        TriggerCandyDropCommand instance = new TriggerCandyDropCommand(mod);
        boolean dropped = instance.executeCandyDropLogic(playerName, forceRare);
        
        if (dropped) {
            source.sendSuccess(new StringTextComponent("§aCandy drop triggered for " + playerName), false);
        } else {
            source.sendSuccess(new StringTextComponent("§eNo candy dropped for " + playerName + " (cooldown or chance)"), false);
        }
        
        return 1;
    }
    
    /**
     * Executes candy drop logic for external integration
     */
    public boolean executeCandyDropLogic(String playerName, boolean forceRare) {
        System.out.println("🎯 Triggering candy drop for " + playerName + (forceRare ? " (force rare)" : ""));
        
        // Check if candy drops are enabled
        if (!mod.getConfigManager().isCandyDropEnabled()) {
            System.out.println("❌ Candy drops are disabled in config");
            return false;
        }
        
        // Check base drop chance (unless forcing rare)
        if (!forceRare) {
            double dropChance = mod.getConfigManager().getCandyDropChance();
            double roll = random.nextDouble() * 100.0;
            
            System.out.println("🎲 Drop roll: " + String.format("%.2f", roll) + "% (need <= " + dropChance + "%)");
            
            if (roll > dropChance) {
                return false; // No drop at all
            }
        }
        
        // In real implementation, get actual player object
        Object player = playerName; // Placeholder
        
        // Determine if should give rare candy
        boolean shouldGiveRareCandy;
        if (forceRare) {
            shouldGiveRareCandy = true;
        } else {
            double rareRoll = random.nextDouble() * 100.0;
            shouldGiveRareCandy = rareRoll <= 1.0;
            System.out.println("🌟 Rare roll: " + String.format("%.2f", rareRoll) + "% (need <= 1.0%)");
        }
        
        // Execute drop logic
        if (shouldGiveRareCandy) {
            // Give rare candy
            mod.getCandyManager().createRareCandy();
            System.out.println("🌟 Gave rare candy to " + playerName + "!");
            return true;
            
        } else {
            // Give regular candy
            mod.getCandyManager().createRegularCandy();
            System.out.println("🍬 Gave regular candy to " + playerName + "!");
            return true;
        }
    }
    
    /**
     * Checks if a player can receive rare candy (for external integration)
     */
    public boolean canPlayerReceiveRareCandy(String playerName) {
        // In real implementation, get player UUID and check cooldown
        System.out.println("🕒 Checking if " + playerName + " can receive rare candy");
        return true; // Simplified for now
    }
    
    /**
     * Forces a rare candy drop for a player
     */
    public boolean forceGiveRareCandy(String playerName) {
        return executeCandyDropLogic(playerName, true);
    }
    
    /**
     * Forces a regular candy drop for a player
     */
    public boolean forceGiveRegularCandy(String playerName) {
        if (!mod.getConfigManager().isCandyDropEnabled()) {
            return false;
        }
        
        mod.getCandyManager().createRegularCandy();
        System.out.println("🍬 Force gave regular candy to " + playerName + "!");
        return true;
    }
}
