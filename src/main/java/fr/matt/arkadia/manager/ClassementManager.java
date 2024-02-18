package fr.matt.arkadia.manager;

import fr.matt.arkadia.model.*;
import fr.matt.arkadia.repository.ClassementRepository;
import fr.matt.arkadia.model.PlayerQuest;
import fr.matt.arkadia.repository.PlayerQuestRepository;
import fr.matt.arkadia.repository.ShopRepository;
import fr.matt.arkadia.repository.TeamRepository;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.bukkit.Bukkit.getServer;

public class ClassementManager {

    private ClassementRepository classementRepository;
    private ShopRepository shopRepository;

    private TeamRepository teamRepository;

    private PlayerQuestRepository playerQuestRepository;
    private Classement classement;

    private List<PlayerQuest> quests = new ArrayList<PlayerQuest>();

    public ClassementManager() {

        this.classementRepository = new ClassementRepository();
        this.shopRepository = new ShopRepository();
        this.teamRepository = new TeamRepository();

        this.loadClassement();
        this.playerQuestRepository = new PlayerQuestRepository();


    }

    public void loadClassement() {

        if(!this.classementRepository.isClassementRunning()) return;
        this.classement = this.classementRepository.getRunningClassement();
    }

    public String startClassement(String classementName) {

        if(this.classementRepository.isClassementRunning()) return ChatColor.RED + "Un classement est deja en cours";
        if(this.classementRepository.isClassementNameExist(classementName)) return ChatColor.RED + "Ce nom est deja pris.";

        Classement classement = this.buildClassement(classementName);
        String response = this.classementRepository.startClassement(classement);

        this.classement = classement;

        Bukkit.broadcastMessage(ChatColor.GREEN + "Mob Quest :" + classement.getMobQuest());
        Bukkit.broadcastMessage(ChatColor.GREEN + "Block Quest :" + classement.getBlockQuest());

        for (Player player : getServer().getOnlinePlayers()) {
            this.loadPlayerQuest(player.getName());
        }

        return response;
    }

    public String stopClassement() {

        if(!this.classementRepository.isClassementRunning()) return ChatColor.RED + "Aucun classement n'est en cours.";

        String classementName = this.classementRepository.stopClassement();
        if(classementName == null) return ChatColor.RED + "Impossible de trouver le classement en cours.";

        for (Player player : getServer().getOnlinePlayers()) {
            this.savePlayerQuest(player.getName());
        }

        String response = this.buildResultat(classementName); //faire le string de reponse du repository apres avoir dev les quetes

        this.classement = null;
        return response;
    }

    public Classement buildClassement(String classementName) {
        Classement classement = new Classement();
        classement.setName(classementName);
        classement.setRunning(true);

        classement.setEconomie(this.buildEconomie());
        classement.setBlockQuest(this.buildBlockQuest());
        classement.setMobQuest(this.buildMobQuest());

        return classement;
    }

    public String buildMobQuest() {
        List<String> mobs = List.of(
                "Axolotl",
                "Bat",
                "Bee",
                "Blaze",
                "Camel",
                "Cat",
                "CaveSpider",
                "Chicken",
                "Cod",
                "Cow",
                "Dolphin",
                "Drowned",
                "Donkey",
                "ElderGuardian",
                "Enderman",
                "Endermite",
                "Evoker",
                "Fox",
                "Frog",
                "Ghast",
                "GlowSquid",
                "Goat",
                "Guardian",
                "Hoglin",
                "Horse",
                "IronGolem",
                "Llama",
                "MagmaCube",
                "MushroomCow",
                "Ocelot",
                "Panda",
                "Parrot",
                "Phantom",
                "Pig",
                "Piglin",
                "Pillager",
                "Rabbit",
                "Sheep",
                "Shulker",
                "Silverfish",
                "Skeleton",
                "Slime",
                "Sniffer",
                "Snowman",
                "Spider",
                "Squid",
                "Strider",
                "Turtle",
                "Vex",
                "Villager",
                "Vindicator",
                "Warden",
                "Witch",
                "Wolf",
                "Zombie"
        );

        return mobs.get(new Random().nextInt(mobs.size()));
    }

    public String buildBlockQuest() {
        List<String> blocks = List.of(
                "SAND",
                "TERRACOTTA",
                "SANDSTONE",
                "STONE",
                "DEEPSLATE",
                "ICE",
                "PACKED_ICE",
                "GRAVEL",
                "DEAD_BUBBLE_CORAL",
                "SNOW_BLOCK",
                "RED_SAND",
                "DRIPSTONE_BLOCK",
                "SOUL_SAND",
                "MAGMA_BLOCK",
                "BASALT",
                "OBSIDIAN",
                "SOUL_SOIL",
                "PRISMARINE",
                "NETHER_BRICK",
                "END_STONE",
                "NETHERRACK",
                "PURPUR_BLOCK"
        );

        return blocks.get(new Random().nextInt(blocks.size()));
    }

    public Economie buildEconomie() {

        Economie economie = new Economie();

        economie.addItem(new EconomieItem(Material.WHEAT, 90000, getRandomValue(3.33), "agriculteur"));
        economie.addItem(new EconomieItem(Material.BEETROOT, 90000, getRandomValue(3.33), "agriculteur"));
        economie.addItem(new EconomieItem(Material.WHEAT_SEEDS, 420000, getRandomValue(0.71), "agriculteur"));
        economie.addItem(new EconomieItem(Material.BEETROOT_SEEDS, 420000, getRandomValue(0.71), "agriculteur"));
        economie.addItem(new EconomieItem(Material.HAY_BLOCK, 6660, getRandomValue(132.13), "agriculteur"));
        economie.addItem(new EconomieItem(Material.CARROT, 420000, getRandomValue(0.71), "agriculteur"));
        economie.addItem(new EconomieItem(Material.POTATO, 420000, getRandomValue(0.71), "agriculteur"));
        economie.addItem(new EconomieItem(Material.BAKED_POTATO, 420000, getRandomValue(2.10), "agriculteur"));
        economie.addItem(new EconomieItem(Material.SUGAR_CANE, 1800000, getRandomValue(2.17), "agriculteur"));
        economie.addItem(new EconomieItem(Material.COCOA_BEANS, 720000, getRandomValue(1.11), "agriculteur"));
        economie.addItem(new EconomieItem(Material.MELON_SLICE, 1500000, getRandomValue(0.20), "agriculteur"));
        economie.addItem(new EconomieItem(Material.PUMPKIN, 300000, getRandomValue(1), "agriculteur"));
        economie.addItem(new EconomieItem(Material.BROWN_MUSHROOM, 150000, getRandomValue(2), "agriculteur"));
        economie.addItem(new EconomieItem(Material.RED_MUSHROOM, 750000, getRandomValue(0.40), "agriculteur"));
        economie.addItem(new EconomieItem(Material.KELP, 900000, getRandomValue(0.33), "agriculteur"));
        economie.addItem(new EconomieItem(Material.NETHER_WART, 500000, getRandomValue(1.60), "agriculteur"));
        economie.addItem(new EconomieItem(Material.CHORUS_FRUIT, 700000, getRandomValue(1.14), "agriculteur"));
        economie.addItem(new EconomieItem(Material.CAKE, 3000, getRandomValue(1000), "agriculteur"));
        economie.addItem(new EconomieItem(Material.SUGAR, 1200000, getRandomValue(0.73), "agriculteur"));
        economie.addItem(new EconomieItem(Material.COOKIE, 240000, getRandomValue(3.67), "agriculteur"));
        economie.addItem(new EconomieItem(Material.CACTUS, 1500000, getRandomValue(0.20), "agriculteur"));
        economie.addItem(new EconomieItem(Material.VINE, 112000, getRandomValue(7.14), "agriculteur"));
        economie.addItem(new EconomieItem(Material.BAMBOO, 2100000, getRandomValue(0.14), "agriculteur"));
        economie.addItem(new EconomieItem(Material.DEAD_BUSH, 46200, getRandomValue(56.28), "agriculteur"));
        economie.addItem(new EconomieItem(Material.BEETROOT_SOUP, 10000, getRandomValue(88), "agriculteur"));
        economie.addItem(new EconomieItem(Material.MUSHROOM_STEW, 10000, getRandomValue(88), "agriculteur"));
        economie.addItem(new EconomieItem(Material.SWEET_BERRIES, 1500000, getRandomValue(0.20), "agriculteur"));
        economie.addItem(new EconomieItem(Material.TORCHFLOWER_SEEDS, 15000, getRandomValue(20), "agriculteur"));
        economie.addItem(new EconomieItem(Material.GLOW_BERRIES, 1500000, getRandomValue(0.20), "agriculteur"));

        economie.addItem(new EconomieItem(Material.COAL, 74000, getRandomValue(10.81), "mineur"));
        economie.addItem(new EconomieItem(Material.IRON_INGOT, 300000, getRandomValue(1.10), "mineur"));
        economie.addItem(new EconomieItem(Material.GOLD_INGOT, 432000, getRandomValue(0.76), "mineur"));
        economie.addItem(new EconomieItem(Material.COPPER_INGOT, 60000, getRandomValue(14.67), "mineur"));
        economie.addItem(new EconomieItem(Material.EMERALD, 1200000, getRandomValue(0.33), "mineur"));
        economie.addItem(new EconomieItem(Material.REDSTONE, 132000, getRandomValue(2.27), "mineur"));
        economie.addItem(new EconomieItem(Material.LAPIS_LAZULI, 60000, getRandomValue(13.33), "mineur"));
        economie.addItem(new EconomieItem(Material.DIAMOND, 5000, getRandomValue(400), "mineur"));
        economie.addItem(new EconomieItem(Material.NETHERITE_INGOT, 210, getRandomValue(10476.19), "mineur"));
        economie.addItem(new EconomieItem(Material.RAW_IRON, 34000, getRandomValue(64.71), "mineur"));
        economie.addItem(new EconomieItem(Material.RAW_GOLD, 20000, getRandomValue(110), "mineur"));
        economie.addItem(new EconomieItem(Material.RAW_COPPER, 50000, getRandomValue(44), "mineur"));
        economie.addItem(new EconomieItem(Material.GOLD_NUGGET, 2400000, getRandomValue(0.37), "mineur"));
        economie.addItem(new EconomieItem(Material.AMETHYST_SHARD, 48000, getRandomValue(9.38), "mineur"));
        economie.addItem(new EconomieItem(Material.AMETHYST_CLUSTER, 8000, getRandomValue(275), "mineur"));
        economie.addItem(new EconomieItem(Material.AMETHYST_BLOCK, 8000, getRandomValue(110), "mineur"));
        economie.addItem(new EconomieItem(Material.COAL_BLOCK, 8220, getRandomValue(107.06), "mineur"));
        economie.addItem(new EconomieItem(Material.COPPER_BLOCK, 6660, getRandomValue(132.13), "mineur"));
        economie.addItem(new EconomieItem(Material.DIAMOND_BLOCK, 550, getRandomValue(4000), "mineur"));
        economie.addItem(new EconomieItem(Material.EMERALD_BLOCK, 135540, getRandomValue(6.49), "mineur"));
        economie.addItem(new EconomieItem(Material.GOLD_BLOCK, 32000, getRandomValue(27.50), "mineur"));
        economie.addItem(new EconomieItem(Material.IRON_BLOCK, 10960, getRandomValue(80.29), "mineur"));
        economie.addItem(new EconomieItem(Material.DRIPSTONE_BLOCK, 210000, getRandomValue(2.07), "mineur"));
        economie.addItem(new EconomieItem(Material.LAPIS_BLOCK, 3000, getRandomValue(733.33), "mineur"));
        economie.addItem(new EconomieItem(Material.NETHERITE_BLOCK, 20, getRandomValue(110000), "mineur"));
        economie.addItem(new EconomieItem(Material.QUARTZ_BLOCK, 26000, getRandomValue(33.85), "mineur"));

        economie.addItem(new EconomieItem(Material.STONE, 1440000, getRandomValue(0.56), "architect"));
        economie.addItem(new EconomieItem(Material.COBBLESTONE, 2160000, getRandomValue(0.14), "architect"));
        economie.addItem(new EconomieItem(Material.GRASS_BLOCK, 1000000, getRandomValue(0.80), "architect"));
        economie.addItem(new EconomieItem(Material.DIRT, 1400000, getRandomValue(0.57), "architect"));
        economie.addItem(new EconomieItem(Material.ICE, 1440000, getRandomValue(0.72), "architect"));
        economie.addItem(new EconomieItem(Material.PACKED_ICE, 160000, getRandomValue(7), "architect"));
        economie.addItem(new EconomieItem(Material.BLUE_ICE, 17760, getRandomValue(67.57), "architect"));
        economie.addItem(new EconomieItem(Material.TUBE_CORAL_BLOCK, 80000, getRandomValue(36.25), "architect"));
        economie.addItem(new EconomieItem(Material.BRAIN_CORAL_BLOCK, 80000, getRandomValue(36.25), "architect"));
        economie.addItem(new EconomieItem(Material.BUBBLE_CORAL_BLOCK, 80000, getRandomValue(36.25), "architect"));
        economie.addItem(new EconomieItem(Material.FIRE_CORAL_BLOCK, 80000, getRandomValue(36.25), "architect"));
        economie.addItem(new EconomieItem(Material.HORN_CORAL_BLOCK, 80000, getRandomValue(36.25), "architect"));
        economie.addItem(new EconomieItem(Material.GRANITE, 34660, getRandomValue(25.39), "architect"));
        economie.addItem(new EconomieItem(Material.DIORITE, 104000, getRandomValue(8.46), "architect"));
        economie.addItem(new EconomieItem(Material.ANDESITE, 208000, getRandomValue(4.23), "architect"));
        economie.addItem(new EconomieItem(Material.DEEPSLATE, 440000, getRandomValue(2.09), "architect"));
        economie.addItem(new EconomieItem(Material.CALCITE, 20000, getRandomValue(180), "architect"));
        economie.addItem(new EconomieItem(Material.TUFF, 50000, getRandomValue(52), "architect"));
        economie.addItem(new EconomieItem(Material.PODZOL, 240000, getRandomValue(3.33), "architect"));
        economie.addItem(new EconomieItem(Material.MUD, 240000, getRandomValue(3.33), "architect"));
        economie.addItem(new EconomieItem(Material.OBSIDIAN, 960000, getRandomValue(0.31), "architect"));
        economie.addItem(new EconomieItem(Material.SNOW_BLOCK, 200000, getRandomValue(4.40), "architect"));
        economie.addItem(new EconomieItem(Material.CLAY, 65500, getRandomValue(13.54), "architect"));
        economie.addItem(new EconomieItem(Material.BEE_NEST, 500, getRandomValue(1760), "architect"));
        economie.addItem(new EconomieItem(Material.BROWN_TERRACOTTA, 100000, getRandomValue(29), "architect"));
        economie.addItem(new EconomieItem(Material.TERRACOTTA, 200000, getRandomValue(14.50), "architect"));
        economie.addItem(new EconomieItem(Material.YELLOW_TERRACOTTA, 100000, getRandomValue(29), "architect"));
        economie.addItem(new EconomieItem(Material.ORANGE_TERRACOTTA, 100000, getRandomValue(29), "architect"));
        economie.addItem(new EconomieItem(Material.RED_TERRACOTTA, 200000, getRandomValue(14.50), "architect"));
        economie.addItem(new EconomieItem(Material.WHITE_TERRACOTTA, 100000, getRandomValue(29), "architect"));
        economie.addItem(new EconomieItem(Material.LIGHT_GRAY_TERRACOTTA, 100000, getRandomValue(29), "architect"));
        economie.addItem(new EconomieItem(Material.GREEN_GLAZED_TERRACOTTA, 200000, getRandomValue(14.50), "architect"));
        economie.addItem(new EconomieItem(Material.PINK_PETALS, 290280, getRandomValue(6.89), "architect"));
        economie.addItem(new EconomieItem(Material.BLUE_CANDLE, 92000, getRandomValue(9.57), "architect"));
        economie.addItem(new EconomieItem(Material.YELLOW_STAINED_GLASS, 400000, getRandomValue(2.40), "architect"));
        economie.addItem(new EconomieItem(Material.GILDED_BLACKSTONE, 1400, getRandomValue(2285.71), "architect"));

        economie.addItem(new EconomieItem(Material.LIGHTNING_ROD, 20000, getRandomValue(44), "crafteur"));
        economie.addItem(new EconomieItem(Material.LODESTONE, 420, getRandomValue(2095.24), "crafteur"));
        economie.addItem(new EconomieItem(Material.OAK_FENCE, 190000, getRandomValue(4.63), "crafteur"));
        economie.addItem(new EconomieItem(Material.SPRUCE_FENCE_GATE, 225000, getRandomValue(3.91), "crafteur"));
        economie.addItem(new EconomieItem(Material.BIRCH_DOOR, 300000, getRandomValue(2.93), "crafteur"));
        economie.addItem(new EconomieItem(Material.JUNGLE_TRAPDOOR, 200000, getRandomValue(4.40), "crafteur"));
        economie.addItem(new EconomieItem(Material.ACACIA_BUTTON, 600000, getRandomValue(1.47), "crafteur"));
        economie.addItem(new EconomieItem(Material.DARK_OAK_PRESSURE_PLATE, 400000, getRandomValue(2.20), "crafteur"));
        economie.addItem(new EconomieItem(Material.MANGROVE_STAIRS, 400000, getRandomValue(2.20), "crafteur"));
        economie.addItem(new EconomieItem(Material.CRIMSON_SLAB, 800000, getRandomValue(1.10), "crafteur"));
        economie.addItem(new EconomieItem(Material.WARPED_STAIRS, 400000, getRandomValue(2.20), "crafteur"));
        economie.addItem(new EconomieItem(Material.STONE_STAIRS, 800000, getRandomValue(1.10), "crafteur"));
        economie.addItem(new EconomieItem(Material.BRICK, 260000, getRandomValue(3.38), "crafteur"));
        economie.addItem(new EconomieItem(Material.STONE_BRICKS, 1200000, getRandomValue(0.73), "crafteur"));
        economie.addItem(new EconomieItem(Material.GRANITE_WALL, 34660, getRandomValue(25.39), "crafteur"));
        economie.addItem(new EconomieItem(Material.POLISHED_DIORITE_STAIRS, 69120, getRandomValue(12.73), "crafteur"));
        economie.addItem(new EconomieItem(Material.COBBLED_DEEPSLATE_SLAB, 800000, getRandomValue(1.10), "crafteur"));
        economie.addItem(new EconomieItem(Material.COBBLESTONE_SLAB, 800000, getRandomValue(1.10), "crafteur"));
        economie.addItem(new EconomieItem(Material.SANDSTONE_SLAB, 800000, getRandomValue(1.10), "crafteur"));
        economie.addItem(new EconomieItem(Material.PRISMARINE_SLAB, 40000, getRandomValue(22), "crafteur"));
        economie.addItem(new EconomieItem(Material.OXIDIZED_CUT_COPPER_STAIRS, 69120, getRandomValue(12.73), "crafteur"));
        economie.addItem(new EconomieItem(Material.BAMBOO_RAFT, 52000, getRandomValue(21.54), "crafteur"));
        economie.addItem(new EconomieItem(Material.DAMAGED_ANVIL, 6000, getRandomValue(146.67), "crafteur"));
        economie.addItem(new EconomieItem(Material.CAULDRON, 28560, getRandomValue(30.81), "crafteur"));
        economie.addItem(new EconomieItem(Material.SMOKER, 100000, getRandomValue(8.80), "crafteur"));
        economie.addItem(new EconomieItem(Material.SOUL_CAMPFIRE, 60000, getRandomValue(14.67), "crafteur"));
        economie.addItem(new EconomieItem(Material.BARREL, 69120, getRandomValue(12.73), "crafteur"));
        economie.addItem(new EconomieItem(Material.PURPLE_SHULKER_BOX, 10000, getRandomValue(88), "crafteur"));
        economie.addItem(new EconomieItem(Material.PINK_BED, 66660, getRandomValue(13.20), "crafteur"));
        economie.addItem(new EconomieItem(Material.IRON_BARS, 533320, getRandomValue(1.65), "crafteur"));
        economie.addItem(new EconomieItem(Material.SPYGLASS, 30000, getRandomValue(29.33), "crafteur"));
        economie.addItem(new EconomieItem(Material.TARGET, 88000, getRandomValue(10), "crafteur"));
        economie.addItem(new EconomieItem(Material.COMPARATOR, 30000, getRandomValue(29.33), "crafteur"));
        economie.addItem(new EconomieItem(Material.REPEATER, 40000, getRandomValue(22), "crafteur"));
        economie.addItem(new EconomieItem(Material.REDSTONE_LAMP, 20000, getRandomValue(44), "crafteur"));
        economie.addItem(new EconomieItem(Material.ACTIVATOR_RAIL, 200000, getRandomValue(4.40), "crafteur"));
        economie.addItem(new EconomieItem(Material.CARROT_ON_A_STICK, 10000, getRandomValue(112), "crafteur"));
        economie.addItem(new EconomieItem(Material.SEA_LANTERN, 40000, getRandomValue(22), "crafteur"));

        economie.addItem(new EconomieItem(Material.ROTTEN_FLESH, 360000, getRandomValue(0.83), "chasseur"));
        economie.addItem(new EconomieItem(Material.ARROW, 360000, getRandomValue(0.83), "chasseur"));
        economie.addItem(new EconomieItem(Material.BONE, 360000, getRandomValue(0.83), "chasseur"));
        economie.addItem(new EconomieItem(Material.GUNPOWDER, 360000, getRandomValue(0.83), "chasseur"));
        economie.addItem(new EconomieItem(Material.STRING, 360000, getRandomValue(0.83), "chasseur"));
        economie.addItem(new EconomieItem(Material.SPIDER_EYE, 40000, getRandomValue(20), "chasseur"));
        economie.addItem(new EconomieItem(Material.SLIME_BALL, 135000, getRandomValue(2.22), "chasseur"));
        economie.addItem(new EconomieItem(Material.ENDER_PEARL, 1200000, getRandomValue(0.25), "chasseur"));
        economie.addItem(new EconomieItem(Material.BLAZE_ROD, 13500, getRandomValue(22.22), "chasseur"));
        economie.addItem(new EconomieItem(Material.GHAST_TEAR, 216000, getRandomValue(1.39), "chasseur"));
        economie.addItem(new EconomieItem(Material.MAGMA_CREAM, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.SCUTE, 20000, getRandomValue(40), "chasseur"));
        economie.addItem(new EconomieItem(Material.ENDER_EYE, 14000, getRandomValue(57.14), "chasseur"));
        economie.addItem(new EconomieItem(Material.PRISMARINE_SHARD, 420000, getRandomValue(0.71), "chasseur"));
        economie.addItem(new EconomieItem(Material.PRISMARINE_CRYSTALS, 186000, getRandomValue(1.61), "chasseur"));
        economie.addItem(new EconomieItem(Material.HONEYCOMB, 138000, getRandomValue(2.17), "chasseur"));
        economie.addItem(new EconomieItem(Material.INK_SAC, 210000, getRandomValue(1.43), "chasseur"));
        economie.addItem(new EconomieItem(Material.FEATHER, 20000, getRandomValue(40), "chasseur"));
        economie.addItem(new EconomieItem(Material.EGG, 30000, getRandomValue(10), "chasseur"));
        economie.addItem(new EconomieItem(Material.PHANTOM_MEMBRANE, 10000, getRandomValue(80), "chasseur"));
        economie.addItem(new EconomieItem(Material.RABBIT_HIDE, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.SNOWBALL, 75000, getRandomValue(4), "chasseur"));
        economie.addItem(new EconomieItem(Material.TURTLE_EGG, 8000, getRandomValue(100), "chasseur"));
        economie.addItem(new EconomieItem(Material.COOKED_CHICKEN, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.CHICKEN, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.COOKED_MUTTON, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.MUTTON, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.MAGENTA_WOOL, 200000, getRandomValue(4), "chasseur"));
        economie.addItem(new EconomieItem(Material.COOKED_RABBIT, 120000, getRandomValue(2.50), "chasseur"));
        economie.addItem(new EconomieItem(Material.RABBIT, 240000, getRandomValue(1.25), "chasseur"));
        economie.addItem(new EconomieItem(Material.RABBIT_FOOT, 20000, getRandomValue(40), "chasseur"));
        economie.addItem(new EconomieItem(Material.COOKED_BEEF, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.BEEF, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.COOKED_PORKCHOP, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.PORKCHOP, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.WITHER_SKELETON_SKULL, 4000, getRandomValue(200), "chasseur"));
        economie.addItem(new EconomieItem(Material.PEARLESCENT_FROGLIGHT, 150000, getRandomValue(2), "chasseur"));
        economie.addItem(new EconomieItem(Material.LEATHER, 300000, getRandomValue(1), "chasseur"));
        economie.addItem(new EconomieItem(Material.GLOW_INK_SAC, 60000, getRandomValue(5), "chasseur"));

        economie.addItem(new EconomieItem(Material.OAK_LOG, 190000, getRandomValue(4.21), "bucheron"));
        economie.addItem(new EconomieItem(Material.SPRUCE_LOG, 150000, getRandomValue(5.33), "bucheron"));
        economie.addItem(new EconomieItem(Material.BIRCH_LOG, 194000, getRandomValue(4.12), "bucheron"));
        economie.addItem(new EconomieItem(Material.JUNGLE_LOG, 140000, getRandomValue(5.71), "bucheron"));
        economie.addItem(new EconomieItem(Material.ACACIA_LOG, 170000, getRandomValue(4.71), "bucheron"));
        economie.addItem(new EconomieItem(Material.DARK_OAK_LOG, 200000, getRandomValue(4), "bucheron"));
        economie.addItem(new EconomieItem(Material.MANGROVE_LOG, 200000, getRandomValue(4), "bucheron"));
        economie.addItem(new EconomieItem(Material.CRIMSON_STEM, 140000, getRandomValue(5.71), "bucheron"));
        economie.addItem(new EconomieItem(Material.WARPED_STEM, 150000, getRandomValue(5.33), "bucheron"));
        economie.addItem(new EconomieItem(Material.OAK_LEAVES, 300000, getRandomValue(6.67), "bucheron"));
        economie.addItem(new EconomieItem(Material.SPRUCE_LEAVES, 100000, getRandomValue(20), "bucheron"));
        economie.addItem(new EconomieItem(Material.BIRCH_LEAVES, 300000, getRandomValue(6.67), "bucheron"));
        economie.addItem(new EconomieItem(Material.JUNGLE_LEAVES, 300000, getRandomValue(6.67), "bucheron"));
        economie.addItem(new EconomieItem(Material.ACACIA_LEAVES, 100000, getRandomValue(20), "bucheron"));
        economie.addItem(new EconomieItem(Material.DARK_OAK_LEAVES, 100000, getRandomValue(20), "bucheron"));
        economie.addItem(new EconomieItem(Material.MANGROVE_LEAVES, 100000, getRandomValue(20), "bucheron"));
        economie.addItem(new EconomieItem(Material.AZALEA_LEAVES, 40000, getRandomValue(50), "bucheron"));
        economie.addItem(new EconomieItem(Material.FLOWERING_AZALEA_LEAVES, 10000, getRandomValue(200), "bucheron"));
        economie.addItem(new EconomieItem(Material.OAK_SAPLING, 30000, getRandomValue(26.67), "bucheron"));
        economie.addItem(new EconomieItem(Material.SPRUCE_SAPLING, 10000, getRandomValue(80), "bucheron"));
        economie.addItem(new EconomieItem(Material.BIRCH_SAPLING, 30000, getRandomValue(26.67), "bucheron"));
        economie.addItem(new EconomieItem(Material.JUNGLE_SAPLING, 15000, getRandomValue(53.33), "bucheron"));
        economie.addItem(new EconomieItem(Material.ACACIA_SAPLING, 10000, getRandomValue(80), "bucheron"));
        economie.addItem(new EconomieItem(Material.DARK_OAK_SAPLING, 10000, getRandomValue(80), "bucheron"));
        economie.addItem(new EconomieItem(Material.MANGROVE_PROPAGULE, 400000, getRandomValue(2), "bucheron"));

        economie.addItem(new EconomieItem(Material.TROPICAL_FISH, 120, getRandomValue(6666.67), "pecheur"));
        economie.addItem(new EconomieItem(Material.PUFFERFISH, 520, getRandomValue(1538.46), "pecheur"));
        economie.addItem(new EconomieItem(Material.SALMON, 700, getRandomValue(1142.86), "pecheur"));
        economie.addItem(new EconomieItem(Material.COOKED_SALMON, 700, getRandomValue(1142.86), "pecheur"));
        economie.addItem(new EconomieItem(Material.COD, 60000, getRandomValue(13.33), "pecheur"));
        economie.addItem(new EconomieItem(Material.COOKED_COD, 60000, getRandomValue(13.33), "pecheur"));
        economie.addItem(new EconomieItem(Material.NAUTILUS_SHELL, 3000, getRandomValue(266.67), "pecheur"));
        economie.addItem(new EconomieItem(Material.LILY_PAD, 40000, getRandomValue(20), "pecheur"));

        economie.addItem(new EconomieItem(Material.ECHO_SHARD, 1, getRandomValue(11481.87), "explorateur"));
        economie.addItem(new EconomieItem(Material.IRON_HORSE_ARMOR, 1, getRandomValue(16021.21), "explorateur"));
        economie.addItem(new EconomieItem(Material.GOLDEN_HORSE_ARMOR, 1, getRandomValue(23432.38), "explorateur"));
        economie.addItem(new EconomieItem(Material.DIAMOND_HORSE_ARMOR, 1, getRandomValue(31314.18), "explorateur"));
        economie.addItem(new EconomieItem(Material.ENCHANTED_GOLDEN_APPLE, 1, getRandomValue(152993.23), "explorateur"));
        economie.addItem(new EconomieItem(Material.ELYTRA, 1, getRandomValue(34445.60), "explorateur"));
        economie.addItem(new EconomieItem(Material.DRAGON_HEAD, 1, getRandomValue(34445.60), "explorateur"));
        economie.addItem(new EconomieItem(Material.MUSIC_DISC_5, 1, getRandomValue(12303), "explorateur"));
        economie.addItem(new EconomieItem(Material.CONDUIT, 1, getRandomValue(12303), "explorateur"));

        return economie;
    }

    public double getRandomValue(double value) {
        DecimalFormat df=new DecimalFormat("#.##");

        double rdm = Math.random() + 0.5;
        int rdmValueInt = (int) (rdm * value * 100);

        return (double) rdmValueInt / 100;
    }

    public String buildResultat(String classementName) {

        List<VenteShop> ventesShop = this.shopRepository.getClassementVentesShop(classementName);
        List<PlayerQuest> playesQuests = this.playerQuestRepository.getClassementPlayersQuests(classementName);

        String result = ChatColor.BOLD + "" + ChatColor.LIGHT_PURPLE + "Résultat du classement : \n";

        Map<String, List<VenteShop>> venteGrouppedByTeam =
                ventesShop.stream().collect(Collectors.groupingBy(v -> v.getTeamName()));

        for(String teamName : venteGrouppedByTeam.keySet()) {
            result += teamName + ":" + venteGrouppedByTeam.get(teamName).stream().mapToDouble(v -> v.getValue()).sum() + "\n";
        }

        result += "Résultat des mobs : \n";
        for(PlayerQuest quest : playesQuests) {
            result+= quest.getPlayerName() + " mob " + quest.getCountMob() + " block " + quest.getCountBlock() + " \n";
        }

        return result;
    }

    public Economie getEconomie() {
        if(classement == null) return null;
        return this.classement.getEconomie();
    }

    public boolean addVenteShop(ItemStack itemstack, Player player) {

        EconomieItem ecoItem = this.classement.getEconomie().getItems().stream().filter(i ->
                i.getMaterial().equals(itemstack.getType())
                && i.getByteMaterial() == itemstack.getData().getData()
                ).findFirst().orElse(new EconomieItem());

        int limite = ecoItem.getMax();

        String itemName = itemstack.getItemMeta().getDisplayName();
        int quantity =  itemstack.getAmount();
        double value = ecoItem.getValue() * quantity;
        String classementName = this.classement.getName();


        VenteShop venteShop = new VenteShop();
        venteShop.setPlayerName(player.getName());
        venteShop.setQuantity(quantity);
        venteShop.setValue(value);
        venteShop.setClassementName(classementName);
        venteShop.setItemName(itemName);

        Team team = this.teamRepository.getTeamWithPlayerName(player.getName());
        if(team == null) {
            player.sendMessage(ChatColor.RED + "Vous n'etes pas dans une team.");
            return false;
        }
        venteShop.setTeamName(team.getName());

        return this.shopRepository.addShopVente(venteShop, limite);

    }

    public String getCurrentMobQuest() {
        if(this.classement == null) return null;

        return this.classement.getMobQuest();
    }

    public String getCurrentBlockQuest() {
        if(this.classement == null) return null;

        return this.classement.getBlockQuest();
    }

    public void loadPlayerQuest(String playerName) {
        if(classement == null) return;

        PlayerQuest playerQuest = this.playerQuestRepository.getPlayerQuest(playerName, this.classement.getName());

        if(playerQuest == null) {
            playerQuest = new PlayerQuest(playerName, this.classement.getName(), 0,0 );
            this.playerQuestRepository.createPlayerQuest(playerQuest);
        }

        this.quests.add(playerQuest);
    }

    public void savePlayerQuest(String playerName) {

        Optional<PlayerQuest> quest = this.quests.stream().filter(q -> q.getPlayerName().equals(playerName)).findFirst();
        if(!quest.isPresent()) {
            Bukkit.broadcastMessage("probleme avec les quetes");
            return;
        }

        this.playerQuestRepository.savePlayerQuest(quest.get());
        this.quests.remove(quest.get());
    }

    public void addBlockBreakCounter(String playerName) {
        Optional<PlayerQuest> quest = this.quests.stream().filter(q -> q.getPlayerName().equals(playerName)).findFirst();
        if(!quest.isPresent()) {
            Bukkit.broadcastMessage("probleme avec les quetes");
            return;
        }
        quest.get().setCountBlock(quest.get().getCountBlock()+1);
    }

    public void addMobKillCounter(String playerName) {
        Optional<PlayerQuest> quest = this.quests.stream().filter(q -> q.getPlayerName().equals(playerName)).findFirst();
        if(!quest.isPresent()) {
            Bukkit.broadcastMessage("probleme avec les quetes");
            return;
        }
        quest.get().setCountMob(quest.get().getCountMob()+1);
    }

    public String getPlayerQuestStat(String playerName) {
        if(this.classement == null) return ChatColor.RED + "Aucun classement en cours.";

        Optional<PlayerQuest> playerQuests = this.quests.stream().filter(q -> q.getPlayerName().equals(playerName)).findFirst();
        if(!playerQuests.isPresent()) {
            Bukkit.broadcastMessage("probleme avec les quetes");
            return "";
        }

        String result = ChatColor.AQUA + "Quetes de " + playerName + " : \n";
        result += "- Mob " + playerQuests.get().getCountMob() + " " + this.classement.getMobQuest() + " \n";
        result += "- Block " + playerQuests.get().getCountBlock() + " " + this.classement.getBlockQuest();

        return result;
    }

    public String getTeamQuestStat(String playerName) {
        if(this.classement == null) return ChatColor.RED + "Aucun classement en cours.";

        Team team = this.teamRepository.getTeamWithPlayerName(playerName);
        List<String> teamPlayersNames = team.getPlayers();

        List<String> onlinePlayersNames = Bukkit.getServer().getOnlinePlayers().stream().map(p -> p.getName()).filter(teamPlayersNames::contains).toList();

        List<PlayerQuest> teamQuests = new ArrayList<PlayerQuest>();

        for(String name : teamPlayersNames) { //pour les joueurs en ligne
            Optional<PlayerQuest> playerQuests = this.quests.stream().filter(q -> q.getPlayerName().equals(name)).findFirst();
            if(!playerQuests.isPresent()) {
                teamQuests.add(this.playerQuestRepository.getPlayerQuest(name, this.classement.getName()));
            }

            else teamQuests.add(playerQuests.get());
        }

        String result = ChatColor.AQUA + "Quetes de la team " + team.getName() + " : \n";
        for (PlayerQuest quest : teamQuests) {
            result += "- " + quest.getPlayerName() +" : \n";
            result += "  - Mob " + quest.getCountMob() + " " + this.classement.getMobQuest() + " \n";
            result += "  - Block " + quest.getCountBlock() + " " + this.classement.getBlockQuest() + " \n";
        }

        return result;
    }

    public String getShoptStat(String playerName) {
        if(this.classement == null) return ChatColor.RED + "Aucun classement en cours.";

        List<VenteShop> ventesShop = this.shopRepository.getPlayerVentesShop(playerName, this.classement.getName());

        return ChatColor.AQUA + "Money de " + playerName + " : " + ventesShop.stream().mapToDouble(v -> v.getValue()).sum();
    }

    public String getShopTeamStat(String playerName) {
        if(this.classement == null) return ChatColor.RED + "Aucun classement en cours.";

        Team team = this.teamRepository.getTeamWithPlayerName(playerName);
        List<String> teamPlayersNames = team.getPlayers();

        String result = ChatColor.AQUA + "Money de la team " + team.getName() + " : \n";

        for(String name : teamPlayersNames) {
            List<VenteShop> ventesShop = this.shopRepository.getPlayerVentesShop(name, this.classement.getName());
            result += "- " + name + " : " + ventesShop.stream().mapToDouble(v -> v.getValue()).sum() + "\n";
        }

        return result;
    }
}
