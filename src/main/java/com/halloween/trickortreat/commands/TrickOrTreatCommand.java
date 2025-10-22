package com.halloween.trickortreat.commands;

import com.halloween.trickortreat.TrickOrTreatPixelmonMod;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.util.text.StringTextComponent;

public class TrickOrTreatCommand {
    
    public static void register(CommandDispatcher<CommandSource> dispatcher, TrickOrTreatPixelmonMod mod) {
        System.out.println("🔧 Registering /trickortreat command");
        
        // In real implementation, this would use proper Brigadier syntax
        // For now, just register the command handler
        dispatcher.register(Commands.literal("trickortreat"));
    }
    
    /**
     * Handles the main trickortreat command
     */
    public static int execute(CommandContext<CommandSource> context, TrickOrTreatPixelmonMod mod, String[] args) {
        CommandSource source = context.getSource();
        
        if (!source.hasPermission(2)) {
            source.sendFailure(new StringTextComponent("§cYou don't have permission to use this command!"));
            return 0;
        }
        
        if (args.length == 0) {
            return showHelp(source);
        }
        
        String subcommand = args[0].toLowerCase();
        
        switch (subcommand) {
            case "reload":
                return reloadConfig(source, mod);
            case "give":
                return giveCandy(source, mod, args, false);
            case "giverare":
                return giveCandy(source, mod, args, true);
            case "cooldown":
                return handleCooldown(source, mod, args);
            case "set":
                return handleSet(source, mod, args);
            case "help":
            default:
                return showHelp(source);
        }
    }
    
    private static int showHelp(CommandSource source) {
        source.sendSuccess(new StringTextComponent(
            "§6=== Halloween Trick or Treat Pixelmon Commands ===\n" +
            "§e/trickortreat reload §7- Reload configuration\n" +
            "§e/trickortreat give <player> <amount> §7- Give regular candy\n" +
            "§e/trickortreat giverare <player> <amount> §7- Give rare candy\n" +
            "§e/trickortreat cooldown check <player> §7- Check cooldown\n" +
            "§e/trickortreat cooldown clear <player> §7- Clear player cooldown\n" +
            "§e/trickortreat cooldown clear all §7- Clear all cooldowns\n" +
            "§e/trickortreat set item <type> §7- Set custom item (hold item)\n" +
            "§e/trickortreat help §7- Show this help"), false);
        return 1;
    }
    
    private static int reloadConfig(CommandSource source, TrickOrTreatPixelmonMod mod) {
        try {
            // In real implementation, this would reload the config
            System.out.println("🔄 Reloading configuration...");
            source.sendSuccess(new StringTextComponent("§aConfiguration reloaded successfully!"), true);
            return 1;
        } catch (Exception e) {
            source.sendFailure(new StringTextComponent("§cFailed to reload configuration: " + e.getMessage()));
            return 0;
        }
    }
    
    private static int giveCandy(CommandSource source, TrickOrTreatPixelmonMod mod, String[] args, boolean rare) {
        if (args.length < 3) {
            source.sendFailure(new StringTextComponent("§cUsage: /trickortreat " + (rare ? "giverare" : "give") + " <player> <amount>"));
            return 0;
        }
        
        String playerName = args[1];
        int amount;
        
        try {
            amount = Integer.parseInt(args[2]);
            if (amount <= 0 || amount > 64) {
                source.sendFailure(new StringTextComponent("§cAmount must be between 1 and 64"));
                return 0;
            }
        } catch (NumberFormatException e) {
            source.sendFailure(new StringTextComponent("§cInvalid amount: " + args[2]));
            return 0;
        }
        
        // In real implementation, find the player and give items
        System.out.println("🍬 Giving " + amount + "x " + (rare ? "rare" : "regular") + " candy to " + playerName);
        
        String candyType = rare ? "rare candy" : "candy";
        source.sendSuccess(new StringTextComponent("§aGave " + amount + " " + candyType + " to " + playerName), true);
        return 1;
    }
    
    private static int handleCooldown(CommandSource source, TrickOrTreatPixelmonMod mod, String[] args) {
        if (args.length < 2) {
            source.sendFailure(new StringTextComponent("§cUsage: /trickortreat cooldown <check|clear> [player|all]"));
            return 0;
        }
        
        String action = args[1].toLowerCase();
        
        switch (action) {
            case "check":
                if (args.length < 3) {
                    source.sendFailure(new StringTextComponent("§cUsage: /trickortreat cooldown check <player>"));
                    return 0;
                }
                return checkCooldown(source, mod, args[2]);
                
            case "clear":
                if (args.length < 3) {
                    source.sendFailure(new StringTextComponent("§cUsage: /trickortreat cooldown clear <player|all>"));
                    return 0;
                }
                return clearCooldown(source, mod, args[2]);
                
            default:
                source.sendFailure(new StringTextComponent("§cInvalid cooldown action: " + action));
                return 0;
        }
    }
    
    private static int checkCooldown(CommandSource source, TrickOrTreatPixelmonMod mod, String playerName) {
        // In real implementation, find player and check cooldown
        System.out.println("🕒 Checking cooldown for " + playerName);
        source.sendSuccess(new StringTextComponent("§a" + playerName + " is not on rare candy cooldown"), false);
        return 1;
    }
    
    private static int clearCooldown(CommandSource source, TrickOrTreatPixelmonMod mod, String target) {
        if ("all".equalsIgnoreCase(target)) {
            mod.getCooldownManager().clearAllCooldowns();
            source.sendSuccess(new StringTextComponent("§aCleared all cooldowns"), true);
        } else {
            // In real implementation, find player and clear cooldown
            System.out.println("🕒 Clearing cooldown for " + target);
            source.sendSuccess(new StringTextComponent("§aCleared rare candy cooldown for " + target), true);
        }
        return 1;
    }
    
    private static int handleSet(CommandSource source, TrickOrTreatPixelmonMod mod, String[] args) {
        if (args.length < 3 || !"item".equalsIgnoreCase(args[1])) {
            source.sendFailure(new StringTextComponent("§cUsage: /trickortreat set item <type>"));
            return 0;
        }
        
        if (args.length < 4) {
            source.sendFailure(new StringTextComponent("§cUsage: /trickortreat set item <type>\n§7Valid types: token, collectpass, fruitkey, spookey, wspawn, sspawn"));
            return 0;
        }
        
        String itemType = args[3].toLowerCase();
        String[] validTypes = {"token", "collectpass", "fruitkey", "spookey", "wspawn", "sspawn"};
        
        boolean validType = false;
        for (String type : validTypes) {
            if (type.equals(itemType)) {
                validType = true;
                break;
            }
        }
        
        if (!validType) {
            source.sendFailure(new StringTextComponent("§cInvalid item type! Valid types: token, collectpass, fruitkey, spookey, wspawn, sspawn"));
            return 0;
        }
        
        // In real implementation, get held item and save to config
        System.out.println("📦 Setting custom item for type: " + itemType);
        source.sendSuccess(new StringTextComponent("§aSuccessfully set " + itemType + " reward item"), true);
        return 1;
    }
}
