package alkalus.main.core.crafting;

import alkalus.main.core.util.AutoMap;
import alkalus.main.core.util.Pair;
import alkalus.main.core.util.Utils;

import com.emoniph.witchery.Witchery;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.ArrayList;

public class OvenRecipes {
	private static final OvenRecipes INSTANCE;
	public final ArrayList<OvenRecipe> recipes;

	public static OvenRecipes instance() {
		return OvenRecipes.INSTANCE;
	}
	
	public void generateDefaultOvenRecipes() {		
		//Vanilla Saplings
		addRecipe(Utils.simpleMetaStack(Blocks.sapling, 0, 1), 1, Witchery.Items.GENERIC.itemExhaleOfTheHornedOne.createStack(1)); //Oak - 0
		addRecipe(Utils.simpleMetaStack(Blocks.sapling, 1, 1), 1, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1)); //Spruce - 1
		addRecipe(Utils.simpleMetaStack(Blocks.sapling, 2, 1), 1, Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1)); //Birch - 2
		addRecipe(Utils.simpleMetaStack(Blocks.sapling, 3, 1), 1, Witchery.Items.GENERIC.itemFoulFume.createStack(1)); //Jungle - 3
		addRecipe(Utils.simpleMetaStack(Blocks.sapling, 4, 1), 1, Witchery.Items.GENERIC.itemHintOfRebirth.createStack(1)); //Acacia - 4
		addRecipe(Utils.simpleMetaStack(Blocks.sapling, 5, 1), 1, Witchery.Items.GENERIC.itemBreathOfTheGoddess.createStack(1)); //Dark Oak - 5
		
		//Witchery Saplings
		addRecipe(Utils.simpleMetaStack(Witchery.Blocks.SAPLING, 0, 1), 1, Witchery.Items.GENERIC.itemWhiffOfMagic.createStack(1));
		addRecipe(Utils.simpleMetaStack(Witchery.Blocks.SAPLING, 1, 1), 1, Witchery.Items.GENERIC.itemReekOfMisfortune.createStack(1));
		addRecipe(Utils.simpleMetaStack(Witchery.Blocks.SAPLING, 2, 1), 1, Witchery.Items.GENERIC.itemOdourOfPurity.createStack(1));
		
		//Raw Materials
		addRecipe("treewood", 1, Witchery.Items.GENERIC.itemFoulFume.createStack(1));
		addRecipe("meatRaw", 1, Witchery.Items.GENERIC.itemFoulFume.createStack(1));
		
	}	

	private final static ItemStack createStack(final int stackSize) {
		return new ItemStack((Item) Witchery.Items.GENERIC, stackSize, 27);
	}
	
	public static ItemStack getEmptyJar(int amount) {
		return createStack(amount);
	}
	
	public static ItemStack getWitcheryItemByID(int id, int stackSize) {
		return new ItemStack((Item) Witchery.Items.GENERIC, stackSize, id);
	}

	private OvenRecipes() {
		this.recipes = new ArrayList<OvenRecipe>();
	}

	/** @param input1 - The Input Item.
	 * @param jars - The required amount of Jars.
	 * @param jarOutput - The type of Jar output.
	 * @param outputAmount - Amount of outputs, output item is based on furnace recipe of input item.
	 * @return - A new {@link OverRecipe} object.
	 */
	public OvenRecipe addRecipe(final ItemStack input1, final int jars, final ItemStack jarOutput) {
		return addRecipe(input1, "", jars, null, 0, jarOutput, jars);
	}
	
	/** @param input1 - A Valid Oredict String.
	 * @param jars - The required amount of Jars.
	 * @param jarOutput - The type of Jar output.
	 * @param outputAmount - Amount of outputs, output item is based on furnace recipe of input item.
	 * @return - A new {@link OverRecipe} object.
	 */
	public OvenRecipe addRecipe(final String input1, final int jars, final ItemStack jarOutput) {
		return addRecipe(null, input1, jars, null, 0, jarOutput, jars);
	}
	
	/** @param input1 - The Input Item.
	 * @param jars - The required amount of Jars.
	 * @param amt1 - The amount of output1 you receive.
	 * @param output2 - The type of Jar output.
	 * @return - A new {@link OverRecipe} object.
	 */
	public OvenRecipe addRecipe(final ItemStack input1, final int jars, final ItemStack output0, int amt0, final ItemStack output1, final int amt1) {
		return addRecipe(input1, "", jars, output0, amt0, output1, amt1);
	}
	
	public OvenRecipe addRecipe(
			final ItemStack input1, final String inputString1, final int jars,
			final ItemStack output1, final int amt1, final ItemStack output2, final int amt2) {
		
		//Build Inputs
		ItemStack mInputStack = null;
		String mInputString = null;
		int mJars = 0;		
		if (input1 != null) {
			mInputStack = input1;
		}
		else if (inputString1 != null && !inputString1.equals("")) {
			mInputString = inputString1;
		}
		else {
			return null;
		}
		mJars = jars;
		//Build Outputs
		ItemStack mOutputStack = null;
		int mOutputAmount = 0;		
		ItemStack mJarOutputStack = null;
		int mJarOutputAmount = 0;
		boolean y[] = new boolean[2];
		
		//Furnace Output
		if (output1 != null && amt1 > 0) {
			mOutputStack = output1;
			mOutputAmount = amt1;
			y[0] = true;
		}
		else {
			ItemStack s = FurnaceRecipes.smelting().getSmeltingResult(Utils.getAllItemsFromOreDictEntry(inputString1)[0]);
			if (s != null) {
				mOutputStack = s;
				mOutputAmount = s.stackSize;
				y[0] = true;
			}
			else {
				y[0] = false;
			}			
		}
		//Jar Output
		if (output2 != null && (amt2 > 0 && amt2 == jars)) {
			mJarOutputStack = output2;
			mJarOutputAmount = amt2;
			y[1] = true;
		}
		else {
			y[1] = false;
		}
		if (y[0] && y[1]) {
			OvenRecipe recipe;
			if (mInputStack != null) {
				recipe = new OvenRecipe(mInputStack, mJars, mOutputStack, mOutputAmount, mJarOutputStack, mJarOutputAmount);
			}
			else {
				recipe = new OvenRecipe(mInputString, mJars, mOutputStack, mOutputAmount, mJarOutputStack, mJarOutputAmount);
			}			
			this.recipes.add(recipe);
			return recipe;
		}
		else {
			return null;
		}	
	}
	
	
	
	

	/**
	 * Returns an {@link OverRecipe} based on input item and an amount of jars.
	 * @param input1 - An ItemStack
	 * @param jars - Amount of Jars
	 * @return - The Closest Matching {@link OverRecipe}.
	 */
	public OvenRecipe getOvenResult(final ItemStack input1, final int jars) {
		for (final OvenRecipe recipe : this.recipes) {
			if (recipe.isInputMatch(input1, jars)) {
				return recipe;
			}
		}
		return null;
	}

	/**
	 * Returns a {@link OverRecipe} based on an output item.
	 * @param result - The output item.
	 * @return - The first Matching {@link OverRecipe}.
	 */
	public OvenRecipe findRecipeFor(final ItemStack result) {
		for (final OvenRecipe recipe : this.recipes) {
			if (recipe.resultsIn(result)) {
				return recipe;
			}
		}
		return null;
	}
	/**
	 * Returns a {@link OverRecipe} based on an input item. This may not always return the same recipe.
	 * @param input1 - An ItemStack
	 * @return - The first Matching {@link OverRecipe}.
	 */
	public OvenRecipe findRecipeUsing(final ItemStack ingredient) {
		for (final OvenRecipe recipe : this.recipes) {
			if (recipe.uses(ingredient)) {
				return recipe;
			}
		}
		return null;
	}

	static {
		INSTANCE = new OvenRecipes();
	}

	public static class OvenRecipe {
		public final String validOreDictInput;
		public final ItemStack inputs;
		public final int jars;
		public final ItemStack output;
		public final ItemStack outputJar;
		public final int outputAmount1;
		public final int outputAmountJar;
		public final AutoMap<Pair<ItemStack, Integer>> outputs = new AutoMap<Pair<ItemStack, Integer>>();

		
		/**
		 * 
		 * @param input1 - The {@link ItemStack} to be burned in the oven.
		 * @param jars - An {@link int} for the amount of Jars required.
		 * @param output1 - The {@link ItemStack} which is a result of using the Oven.
		 * @param amount1 - An {@link int} for the amount of {@link output1} given. 
		 * @param output2 - The {@link ItemStack} which is a filled Jar.
		 * @param amount2 - An {@link int} for the amount of {@link output2} given.
		 */
		private OvenRecipe(final String oreDictSub, final int jars, final ItemStack output1, final int amount1, final ItemStack output2, final int amount2) {
			this(null, oreDictSub, jars, output1, amount1, output2, amount2);
		}
		
		/**
		 * 
		 * @param input1 - The {@link ItemStack} to be burned in the oven.
		 * @param jars - An {@link int} for the amount of Jars required.
		 * @param output1 - The {@link ItemStack} which is a result of using the Oven.
		 * @param amount1 - An {@link int} for the amount of {@link output1} given. 
		 * @param output2 - The {@link ItemStack} which is a filled Jar.
		 * @param amount2 - An {@link int} for the amount of {@link output2} given.
		 */
		private OvenRecipe(final ItemStack input1, final int jars, final ItemStack output1, final int amount1, final ItemStack output2, final int amount2) {
			this(input1, "", jars, output1, amount1, output2, amount2);
		}
		
		/**
		 * 
		 * @param input1 - The {@link ItemStack} to be burned in the oven.
		 * @param jars - An {@link int} for the amount of Jars required.
		 * @param output1 - The {@link ItemStack} which is a result of using the Oven.
		 * @param amount1 - An {@link int} for the amount of {@link output1} given. 
		 * @param output2 - The {@link ItemStack} which is a filled Jar.
		 * @param amount2 - An {@link int} for the amount of {@link output2} given.
		 */
		private OvenRecipe(final ItemStack input1, String oreDict, final int jars, final ItemStack output1, final int amount1, final ItemStack output2, final int amount2) {
			this.inputs = input1;
			this.validOreDictInput = oreDict;
			this.jars = jars;
			this.output = output1;
			this.outputJar = output2;
			this.outputAmount1 = amount1;
			this.outputAmountJar = amount2;
			Pair<ItemStack, Integer> a1 = new Pair<ItemStack, Integer>(output1, amount1);
			Pair<ItemStack, Integer> a2 = new Pair<ItemStack, Integer>(output1, amount1);
			this.outputs.put(a1);
			this.outputs.put(a2);
		}

		private boolean isInputMatch(final ItemStack intput1, int amt1) {			
			return (this.jars == 0 || ((isMatch(intput1, this.inputs) && amt1 >= this.jars)) || (!this.validOreDictInput.equals("") && Utils.hasValidOreDictTag(this.validOreDictInput, intput1)));
		}
		
		private boolean isMatch(final ItemStack output1, int amt1, final ItemStack output2, int amt2) {			
			return (this.jars == 0 || ((amt1 == this.outputAmount1 && amt2 == this.outputAmountJar) && (this.isMatch(output1, this.output) && this.isMatch(output2, outputJar))));
		}

		public static boolean isMatch(final ItemStack a, final ItemStack b) {
			return (a == null && b == null) || (a != null && b != null && a.getItem() == b.getItem()
					&& (!a.getHasSubtypes() || a.getItemDamage() == b.getItemDamage()));
		}
		
		public static boolean isMatch(final ItemStack a, final ItemStack b, String oreName) {
			return ((Utils.hasValidOreDictTag(oreName, a) && Utils.hasValidOreDictTag(oreName, b)) || a == null && b == null) || (a != null && b != null && a.getItem() == b.getItem()
					&& (!a.getHasSubtypes() || a.getItemDamage() == b.getItemDamage()));
		}

		public int getJars() {
			return this.jars;
		}

		public ItemStack[] getOutputs() {
			ItemStack x[] = new ItemStack[2];
			Pair<ItemStack, Integer> a1 = outputs.get(0);
			Pair<ItemStack, Integer> a2 = outputs.get(1);
			x[0] = Utils.getSimpleStack(a1.getKey(), a1.getValue());
			x[1] = Utils.getSimpleStack(a2.getKey(), a2.getValue());
			return x;
		}

		public String getDescription() {
			return "";
		}

		public boolean resultsIn(final ItemStack result) {
			for (final ItemStack stack : getOutputs()) {
				if (stack != null && stack.isItemEqual(result)) {
					return true;
				}
			}
			return false;
		}

		public boolean uses(final ItemStack ingredient) {
			if (this.inputs != null && this.inputs.isItemEqual(ingredient)) {
				return true;
			}
			return Witchery.Items.GENERIC.itemEmptyClayJar.isMatch(ingredient) && this.jars > 0;
		}
	}
	
}