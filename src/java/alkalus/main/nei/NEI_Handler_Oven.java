package alkalus.main.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.emoniph.witchery.Witchery;
import com.emoniph.witchery.blocks.BlockWitchesOvenGUI;

import codechicken.nei.ItemList;
import codechicken.nei.NEIServerUtils;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.block.Block;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.StatCollector;

public class NEI_Handler_Oven extends TemplateRecipeHandler
{
    public static ArrayList<FuelPair> afuels;
    public static TreeSet<Integer> efuels;
    
    public Class<? extends GuiContainer> getGuiClass() {
        return BlockWitchesOvenGUI.class;
    }
    
    public String getRecipeName() {
        return StatCollector.translateToLocal("tile.witchery:witchesovenidle.name");
    }
    
    public void loadCraftingRecipes(final String outputId, final Object... results) {
        if (outputId.equals("witchery_cooking") && this.getClass() == NEI_Handler_Oven.class) {
            final Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>)FurnaceRecipes.smelting().getSmeltingList();
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 0), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 1), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemHintOfRebirth.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 2), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 3), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemFoulFume.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 0), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemWhiffOfMagic.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 1), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemReekOfMisfortune.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 2), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemOdourOfPurity.createStack()));
            for (final Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
                final ItemStack result = recipe.getValue();
                final ItemStack ingred = new ItemStack(recipe.getKey().getItem(), 1, -1);
                ItemStack byproduct = Witchery.Items.GENERIC.itemFoulFume.createStack();
                if (ingred.getItem() == Item.getItemFromBlock(Blocks.sapling)) {
                    byproduct = ((ingred.getItemDamage() == 0) ? Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack() : ((ingred.getItemDamage() == 2) ? Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack() : ((ingred.getItemDamage() == 1) ? Witchery.Items.GENERIC.itemHintOfRebirth.createStack() : Witchery.Items.GENERIC.itemFoulFume.createStack())));
                    this.arecipes.add(new SmeltingPair(ingred, result, byproduct));
                }
                else if (ingred.getItem() == Item.getItemFromBlock(Witchery.Blocks.SAPLING)) {
                    byproduct = ((ingred.getItemDamage() == 0) ? Witchery.Items.GENERIC.itemWhiffOfMagic.createStack() : ((ingred.getItemDamage() == 2) ? Witchery.Items.GENERIC.itemOdourOfPurity.createStack() : ((ingred.getItemDamage() == 1) ? Witchery.Items.GENERIC.itemReekOfMisfortune.createStack() : Witchery.Items.GENERIC.itemFoulFume.createStack())));
                    this.arecipes.add(new SmeltingPair(ingred, result, byproduct));
                }
                else {
                    if (!Witchery.Items.GENERIC.itemAshWood.isMatch(result) && (result.getItem() != Items.coal || result.getItemDamage() != 1) && !(ingred.getItem() instanceof ItemFood)) {
                        continue;
                    }
                    this.arecipes.add(new SmeltingPair(ingred, result, byproduct));
                }
            }
        }
        else {
            super.loadCraftingRecipes(outputId, results);
        }
    }
    
    public void loadCraftingRecipes(final ItemStack result2) {
        final Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>)FurnaceRecipes.smelting().getSmeltingList();
        for (final Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            final ItemStack result3 = recipe.getKey();
            if (NEIServerUtils.areStacksSameType(result3, result2)) {
                final ItemStack ingred = new ItemStack(recipe.getValue().getItem(), 1, -1);
                ItemStack byproduct = Witchery.Items.GENERIC.itemFoulFume.createStack();
                if (ingred.getItem() == Item.getItemFromBlock(Blocks.sapling)) {
                    byproduct = ((ingred.getItemDamage() == 0) ? Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack() : ((ingred.getItemDamage() == 2) ? Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack() : ((ingred.getItemDamage() == 1) ? Witchery.Items.GENERIC.itemHintOfRebirth.createStack() : Witchery.Items.GENERIC.itemFoulFume.createStack())));
                    this.arecipes.add(new SmeltingPair(ingred, result3, byproduct));
                }
                else if (ingred.getItem() == Item.getItemFromBlock(Witchery.Blocks.SAPLING)) {
                    byproduct = ((ingred.getItemDamage() == 0) ? Witchery.Items.GENERIC.itemWhiffOfMagic.createStack() : ((ingred.getItemDamage() == 2) ? Witchery.Items.GENERIC.itemOdourOfPurity.createStack() : ((ingred.getItemDamage() == 1) ? Witchery.Items.GENERIC.itemReekOfMisfortune.createStack() : Witchery.Items.GENERIC.itemFoulFume.createStack())));
                    this.arecipes.add(new SmeltingPair(ingred, result3, byproduct));
                }
                else {
                    if (!Witchery.Items.GENERIC.itemAshWood.isMatch(result3) && ingred.getItem() != Items.coal && !(ingred.getItem() instanceof ItemFood)) {
                        continue;
                    }
                    this.arecipes.add(new SmeltingPair(ingred, result3, byproduct));
                }
            }
        }
        if (Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.isMatch(result2)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 0), Witchery.Items.GENERIC.itemAshWood.createStack(), result2));
        }
        else if (Witchery.Items.GENERIC.itemBreathOfTheGoddess.isMatch(result2)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 2), Witchery.Items.GENERIC.itemAshWood.createStack(), result2));
        }
        else if (Witchery.Items.GENERIC.itemHintOfRebirth.isMatch(result2)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 1), Witchery.Items.GENERIC.itemAshWood.createStack(), result2));
        }
        else if (Witchery.Items.GENERIC.itemWhiffOfMagic.isMatch(result2)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 0), Witchery.Items.GENERIC.itemAshWood.createStack(), result2));
        }
        else if (Witchery.Items.GENERIC.itemOdourOfPurity.isMatch(result2)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 2), Witchery.Items.GENERIC.itemAshWood.createStack(), result2));
        }
        else if (Witchery.Items.GENERIC.itemReekOfMisfortune.isMatch(result2)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 1), Witchery.Items.GENERIC.itemAshWood.createStack(), result2));
        }
        else if (Witchery.Items.GENERIC.itemFoulFume.isMatch(result2)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.log), new ItemStack(Items.coal, 1), result2));
        }
    }
    
    public void loadUsageRecipes(final String inputId, final Object... ingredients) {
        if (inputId.equals("fuel") && this.getClass() == NEI_Handler_Oven.class) {
            this.loadCraftingRecipes("witchery_cooking", new Object[0]);
        }
        else {
            super.loadUsageRecipes(inputId, ingredients);
        }
    }
    
    public void loadUsageRecipes(final ItemStack ingred) {
        final Map<ItemStack, ItemStack> recipes = (Map<ItemStack, ItemStack>)FurnaceRecipes.smelting().getSmeltingList();
        for (final Map.Entry<ItemStack, ItemStack> recipe : recipes.entrySet()) {
            final ItemStack result = recipe.getValue();
            if (ingred.getItem() == recipe.getKey().getItem()) {
                ItemStack byproduct = Witchery.Items.GENERIC.itemFoulFume.createStack();
                if (ingred.getItem() == Item.getItemFromBlock(Blocks.sapling)) {
                    byproduct = ((ingred.getItemDamage() == 0) ? Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack() : ((ingred.getItemDamage() == 1) ? Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack() : ((ingred.getItemDamage() == 2) ? Witchery.Items.GENERIC.itemHintOfRebirth.createStack() : Witchery.Items.GENERIC.itemFoulFume.createStack())));
                    this.arecipes.add(new SmeltingPair(ingred, result, byproduct));
                }
                else if (ingred.getItem() == Item.getItemFromBlock(Witchery.Blocks.SAPLING)) {
                    byproduct = ((ingred.getItemDamage() == 0) ? Witchery.Items.GENERIC.itemWhiffOfMagic.createStack() : ((ingred.getItemDamage() == 1) ? Witchery.Items.GENERIC.itemOdourOfPurity.createStack() : ((ingred.getItemDamage() == 2) ? Witchery.Items.GENERIC.itemReekOfMisfortune.createStack() : Witchery.Items.GENERIC.itemFoulFume.createStack())));
                    this.arecipes.add(new SmeltingPair(ingred, result, byproduct));
                }
                else {
                    if (!Witchery.Items.GENERIC.itemAshWood.isMatch(result) && ingred.getItem() != Items.coal && !(ingred.getItem() instanceof ItemFood)) {
                        continue;
                    }
                    this.arecipes.add(new SmeltingPair(ingred, result, byproduct));
                }
            }
        }
        if (Witchery.Items.GENERIC.itemEmptyClayJar.isMatch(ingred)) {
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 0), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 1), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemHintOfRebirth.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 2), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.sapling, 1, 3), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemFoulFume.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 0), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemWhiffOfMagic.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 1), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemReekOfMisfortune.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Witchery.Blocks.SAPLING, 1, 2), Witchery.Items.GENERIC.itemAshWood.createStack(), Witchery.Items.GENERIC.itemOdourOfPurity.createStack()));
            this.arecipes.add(new SmeltingPair(new ItemStack(Blocks.log), new ItemStack(Items.coal, 1, 1), Witchery.Items.GENERIC.itemFoulFume.createStack()));
        }
    }
    
    public String getGuiTexture() {
        return "witchery:textures/gui/witchesOven.png";
    }
    
    public void loadTransferRects() {
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(50, 23, 18, 18), "fuel", new Object[0]));
        this.transferRects.add(new TemplateRecipeHandler.RecipeTransferRect(new Rectangle(74, 9, 24, 18), "witchery_cooking", new Object[0]));
    }
    
    public void drawExtras(final int recipe) {
        this.drawProgressBar(51, 25, 176, 0, 14, 14, 48, 7);
        this.drawProgressBar(74, 9, 176, 14, 24, 16, 48, 0);
    }
    
    private static Set<Item> excludedFuels() {
        final Set<Item> efuels = new HashSet<Item>();
        efuels.add(Item.getItemFromBlock((Block)Blocks.brown_mushroom));
        efuels.add(Item.getItemFromBlock((Block)Blocks.red_mushroom));
        efuels.add(Item.getItemFromBlock(Blocks.standing_sign));
        efuels.add(Item.getItemFromBlock(Blocks.wall_sign));
        efuels.add(Item.getItemFromBlock(Blocks.wooden_door));
        efuels.add(Item.getItemFromBlock(Blocks.trapped_chest));
        return efuels;
    }
    
    private static void findFuels() {
        NEI_Handler_Oven.afuels = new ArrayList<FuelPair>();
        final Set<Item> efuels = excludedFuels();
        for (final ItemStack item : ItemList.items) {
            if (item != null && !efuels.contains(item.getItem())) {
                final int burnTime = TileEntityFurnace.getItemBurnTime(item);
                if (burnTime <= 0) {
                    continue;
                }
                NEI_Handler_Oven.afuels.add(new FuelPair(item.copy(), burnTime));
            }
        }
    }
    
    private static void findFuelsOnce() {
        if (NEI_Handler_Oven.afuels == null) {
            findFuels();
        }
    }
    
    public String getOverlayIdentifier() {
        return "witchery_cooking";
    }
    
    public TemplateRecipeHandler newInstance() {
        findFuelsOnce();
        return super.newInstance();
    }
    
    public class SmeltingPair extends TemplateRecipeHandler.CachedRecipe
    {
        PositionedStack ingred;
        PositionedStack result;
        PositionedStack byproduct;
        PositionedStack jar;
        
        public SmeltingPair(final ItemStack ingred, final ItemStack result, final ItemStack byproduct) {
            super();
            ingred.stackSize = 1;
            this.ingred = new PositionedStack((Object)ingred, 51, 6);
            this.result = new PositionedStack((Object)result, 113, 10);
            this.byproduct = new PositionedStack((Object)byproduct, 113, 42);
            this.jar = new PositionedStack((Object)Witchery.Items.GENERIC.itemEmptyClayJar.createStack(), 78, 42);
        }
        
        public List<PositionedStack> getIngredients() {
            return (List<PositionedStack>)this.getCycledIngredients(NEI_Handler_Oven.this.cycleticks / 48, (List)Arrays.asList(this.ingred));
        }
        
        public PositionedStack getResult() {
            return this.result;
        }
        
        public PositionedStack getOtherStack() {
            findFuelsOnce();
            if (NEI_Handler_Oven.afuels != null && NEI_Handler_Oven.afuels.size() > 0) {
                return NEI_Handler_Oven.afuels.get(NEI_Handler_Oven.this.cycleticks / 48 % NEI_Handler_Oven.afuels.size()).stack;
            }
            return null;
        }
        
        public List<PositionedStack> getOtherStacks() {
            final ArrayList<PositionedStack> stacks = new ArrayList<PositionedStack>();
            final PositionedStack stack = this.getOtherStack();
            if (stack != null) {
                stacks.add(stack);
            }
            stacks.add(this.byproduct);
            stacks.add(this.jar);
            return stacks;
        }
    }
    
    public static class FuelPair
    {
        public PositionedStack stack;
        public int burnTime;
        
        public FuelPair(final ItemStack ingred, final int burnTime) {
            this.stack = new PositionedStack((Object)ingred, 51, 42);
            this.burnTime = burnTime;
        }
    }
}
