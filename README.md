# 🎃 Halloween Trick or Treat - Pixelmon Mod

**Rebuilt for Pixelmon as a mod**

A Halloween-themed Forge mod for Pixelmon servers that adds candy drops when defeating or capturing Pixelmon, complete with rare treats, cooldowns, and admin commands.

## 🎮 Features

- **Pixelmon Integration**: Candy drops from defeating/capturing Pixelmon
- **Rare Treats System**: 7 different rare rewards with configurable chances
- **Cooldown Management**: Prevents spam with 1-hour rare candy cooldowns
- **Admin Commands**: Complete management suite for server admins
- **JSON Configuration**: Fully customizable settings
- **External Integration**: API for other mods/plugins

## 📋 Requirements

- **Minecraft**: 1.16.5
- **Forge**: 1.16.5-36.2.0+
- **Pixelmon Reforged**: 9.1.11+
- **Server Type**: Works on Forge or Arclight (Forge+Bukkit hybrid)


## ⚙️ Configuration

The mod creates a `trickortreat-pixelmon-config.json` file with the following settings:

```json
{
  "enabled": true,
  "candy-drop": {
    "enabled": true,
    "chance": 15.0,
    "drop-from-legendary": true,
    "drop-from-mythical": true,
    "drop-from-ultra-beast": true
  },
  "rare-treats": {
    "treat-chance": 50.0,
    "individual-chances": {
      "token": 10,
      "collectpass": 10,
      "fruitkey": 20,
      "spookey": 20,
      "netherite": 10,
      "wspawn": 5,
      "sspawn": 15
    }
  },
  "cooldowns": {
    "rare-candy-hours": 1,
    "candy-use-seconds": 3
  }
}
```

## 🎯 Rare Treats

The mod includes 7 different rare treats:

- **Token** - Server currency token
- **Collectable Pass** - Special access pass
- **Fruitsters Key** - Custom key item
- **SpooKey** - Halloween-themed key
- **Block of Netherite** - Valuable building block
- **Witch Spawner** - Mob spawner
- **Spider Spawner** - Mob spawner

## 🔧 Admin Commands

### `/trickortreat`
Main admin command (requires OP level 2):

- `/trickortreat help` - Show command help
- `/trickortreat reload` - Reload configuration
- `/trickortreat give <player> <amount>` - Give regular candy
- `/trickortreat giverare <player> <amount>` - Give rare candy
- `/trickortreat cooldown check <player>` - Check player cooldown
- `/trickortreat cooldown clear <player>` - Clear player cooldown
- `/trickortreat cooldown clear all` - Clear all cooldowns
- `/trickortreat set item <type>` - Set custom item (hold item in hand)

### `/triggercandydrop`
External integration command:

- `/triggercandydrop <player>` - Trigger candy drop for player
- `/triggercandydrop <player> true` - Force rare candy drop

## 🎪 How It Works

1. **Pixelmon Events**: When a player defeats or captures a Pixelmon, there's a 15% chance of candy drop
2. **Candy Types**: 99% regular candy, 1% rare candy
3. **Using Candy**: Right-click to consume and activate trick/treat system
4. **Rare Treats**: 50% chance of getting a rare treat, 50% chance of rare trick
5. **Cooldowns**: Rare candy has 1-hour cooldown, regular candy use has 3-second cooldown

## 🔌 External Integration

Other mods can integrate with this mod using the `TriggerCandyDropCommand` class:

```java
TrickOrTreatPixelmonMod mod = TrickOrTreatPixelmonMod.getInstance();
TriggerCandyDropCommand cmd = mod.getTriggerCandyDropCommand();

// Trigger candy drop
cmd.executeCandyDropLogic("PlayerName", false);

// Force rare candy
cmd.forceGiveRareCandy("PlayerName");
```

## 🏗️ Building from Source

1. Clone the repository
2. Run `mvn clean package`
3. Find the JAR in `target/TrickOrTreatPixelmon-X.X.X-pixelmon.jar`

## 📝 Version History

- **v1.2.0-pixelmon** - Initial Pixelmon mod release
  - Rebuilt from Bukkit plugin to Forge mod
  - Added Pixelmon event integration
  - Implemented JSON configuration system
  - Added admin command suite

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🎃 Happy Halloween!

Enjoy the spooky candy drops and rare treats on your Pixelmon server!
