package alkalus.main.api;

import java.util.EnumSet;
import java.util.Hashtable;

import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;
import com.emoniph.witchery.crafting.KettleRecipes.KettleRecipe;
import com.emoniph.witchery.infusion.Infusion;
import com.emoniph.witchery.infusion.infusions.creature.CreaturePower;
import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.ritual.*;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;

import alkalus.main.api.plugin.base.BasePluginWitchery;
import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.types.*;
import alkalus.main.core.util.AutoMap;
import alkalus.main.core.util.WitcheryRecipeHandlerInternal;

/**
 * 
 * This class brings together the multiple segments spread through the core of the mod.
 * Should make modification of Witchery only rely on this class for 95% of changes/additions.
 * 
 * This is the single public class to be used externally by other mods, 
 * which simply binds together the various other parts into one simple interface.
 * 
 * @author Alkalus  
 */

public class RecipeManager {

	/*
	 * Convenience classes for all the different recipe types.
	 */	
	
	/**
	 * 
	 * Use this class to queue {@link Runnable} objects for loading at the end of each load phase.
	 * {@link WitcheryExtras} will run all {@link Runnable} objects queued in the order which they are cached.
	 * It is recommended to have a sub-mod that loads {@code 'before:WitcheryExtras'}, 
	 * however all three load phases can have plugins added prior to this mod starting up. 
	 * @author Alkalus
	 *
	 */
	public static class PluginManager {

		/**
		 * This method MUST be called prior to {@link WitcheryExtras} starting it's {@code PreInit()} phase.
		* 
		 * @param basePluginWitchery - {@link BasePluginWitchery} object which you want to load after {@link WitcheryExtras} {@code PreInit()} phase.
		 * @return - {@link Boolean} which always returns true, to signify that the plugin has been queued.
		 */
		public static boolean loadPluginForPreInit(BasePluginWitchery basePluginWitchery) {
			WitcheryExtras.addEventPreInit(basePluginWitchery);			
			return true;
		}
		/**
		 * This method does not need to be called prior to {@link WitcheryExtras} starting it's {@code PreInit()} phase.
		 * This method MUST be called prior to {@link WitcheryExtras} starting it's {@code Init()} phase.
		 * 
		 * @param plugin - {@link BasePluginWitchery} object which you want to load after {@link WitcheryExtras} {@code Init()} phase.
		 * @return - {@link Boolean} which always returns true, to signify that the plugin has been queued.
		 */
		public static boolean loadPluginForInit(BasePluginWitchery plugin) {
			WitcheryExtras.addEventInit(plugin);			
			return true;
		}
		/**
		 * This method does not need to be called prior to {@link WitcheryExtras} starting it's {@code PreInit()} phase.
		 * This method does not need to be called prior to {@link WitcheryExtras} starting it's {@code Init()} phase.
		 * This method MUST be called prior to {@link WitcheryExtras} starting it's {@code PostInit()} phase.
		* 
		 * @param plugin - {@link BasePluginWitchery} object which you want to load after {@link WitcheryExtras} {@code PostInit()} phase.
		 * @return - {@link Boolean} which always returns true, to signify that the plugin has been queued.
		 */
		public static boolean loadPluginForPostInit(BasePluginWitchery plugin) {
			WitcheryExtras.addEventPostInit(plugin);			
			return true;
		}
		
	}

	public static class Distillery {
		public static synchronized boolean addRecipe(ItemStack input1, ItemStack input2, int jars, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4) {
			return WitcheryRecipeHandlerInternal.addDistilleryRecipe(input1, input2, jars, output1, output2, output3, output4);
		}
		/**
		 * This Function will remove the  {@link KettleRecipe} inputt.
		 * @param mOutput - An {@link DistilleryRecipe} for a recipe you wish to remove.
		 * @return - {@link Boolean} representing whether or not the recipe was removed.
		 */
		public static synchronized boolean removeRecipe(DistilleryRecipe mRecipe) {
			return WitcheryRecipeHandlerInternal.removeDistilleryRecipe(mRecipe);
		}
		/**
		 * Finds a {@link DistilleryRecipe} matching a broad range of inputs.
		 * @param input1 - An {@link ItemStack}, input 1.
		 * @param input2 - An {@link ItemStack}, input 2.
		 * @param jars - An {@link ItemStack}, input 3. This input slot is for Jars.
		 * @return - An {@link DistilleryRecipe} representing the closest matching recipe.
		 */
		public static synchronized DistilleryRecipe getDistillingResult(ItemStack input1, ItemStack input2, ItemStack jars){
			return Witchery_Distillery.getDistillingResult(input1, input2, jars);
		}
		/**
		 * This Function will find the first {@link DistilleryRecipe} it finds that matches the specified output.
		 * @param mOutput - An {@link ItemStack} of the Output, for a recipe you wish to find.
		 * @return - An {@link DistilleryRecipe} representing the closest matching recipe.
		 */
		public static synchronized DistilleryRecipe findRecipeForOutput(ItemStack result){
			return Witchery_Distillery.findRecipeFor(result);
		}
		/**
		 * This Function will find the first {@link DistilleryRecipe} it finds that matches the specified ingredient.
		 * @param mOutput - An {@link ItemStack} of the ingredient, for a recipe you wish to find.
		 * @return - A {@link DistilleryRecipe} representing the closest matching recipe.
		 */
		public static synchronized DistilleryRecipe findRecipeUsingIngredient(ItemStack ingredient){
			return Witchery_Distillery.findRecipeUsing(ingredient);
		}
	}

	public static class Kettle {
		public static synchronized boolean addRecipe(ItemStack output, int hatBonus, int familiarType, float powerRequired, int color, int dimension, boolean inBook, ItemStack... inputs) {
			return WitcheryRecipeHandlerInternal.addKettleRecipe(output, hatBonus, familiarType, powerRequired, color, dimension, inBook, inputs);
		}

		/**
		 * This Function will remove the first {@link KettleRecipe} it finds that matches the specified output.
		 * @param mOutput - An {@link ItemStack} of the Output, for a recipe you wish to remove.
		 * @return - {@link Boolean} representing whether or not the recipe was removed.
		 */
		public static synchronized boolean removeRecipe(ItemStack mOutput) {
			return WitcheryRecipeHandlerInternal.removeKettleRecipe(mOutput);
		}

		/**
		 * @param inputs - An {@link ItemStack} array which contains some if not all of the inputs for the recipe you're trying to find.
		 * @param output - An {@link ItemStack} of the output item for the recipe you are trying to find.
		 * @return - The {@link KettleRecipe} closest matching your search parameters.
		 */
		public static KettleRecipe findRecipeWithSomeInputsAndAnOutput(ItemStack[] inputs, ItemStack output) {		
			return Witchery_Kettle.findRecipeWithSomeInputsAndAnOutput(inputs, output);
		}

		/**
		 * @param result - An {@link ItemStack}, used to find a {@link HashMap} full of all {@link KettleRecipe}s with this output.
		 * @return - An {@link AutoMap} Which holds all matching recipes containing {@code 'result'} as an output.
		 */
		public static AutoMap<KettleRecipe> findRecipesFor(final ItemStack result) {
			return Witchery_Kettle.findRecipesFor(result);
		}
	}

	public static class CreaturePowers {
		public static synchronized boolean add(CreaturePower power) {
			return WitcheryRecipeHandlerInternal.addNewCreaturePower(power);
		}
		/**
		 * This Function will remove the specified {@link CreaturePower}.
		 * @param power - An {@link CreaturePower} you wish to remove.
		 * @return - {@link Boolean} representing whether or not the {@link CreaturePower} was removed.
		 */
		public static synchronized boolean remove(CreaturePower power) {
			return WitcheryRecipeHandlerInternal.removeCreaturePower(power);
		}

		/**
		 * @param entity - The {@link EntityLiving} to find a {@link CreaturePower} for.
		 * @return - Matching {@link CreaturePower}.
		 */
		public static synchronized CreaturePower getCreaturePower(EntityLiving entity) {
			return Witchery_CreaturePower.getCreaturePower(entity);
		}
		/**
		 * @param entity - The Entity ID to use to find a {@link CreaturePower}.
		 * @return - Matching {@link CreaturePower}.
		 */
		public static synchronized CreaturePower getCreaturePower(int entityID) {
			return Witchery_CreaturePower.getCreaturePower(entityID);		
		}
	}

	public static class RitesAndRituals {
		/**
		 * @param ritualID - Byte Value between 0-127.
		 */
		public static synchronized boolean add(int ritualID, int bookIndex, Rite rite, Sacrifice initialSacrifice, EnumSet<RitualTraits> traits, Circle... circles) {
			return WitcheryRecipeHandlerInternal.addNewRiteToRiteRegistry(ritualID, bookIndex, rite, initialSacrifice, traits, circles);
		}

		public static synchronized boolean remove(int ritualID) {		
			return WitcheryRecipeHandlerInternal.removeRiteFromRiteRegistry(ritualID);
		}
	}

	public static class Predictions {
		public static synchronized boolean add(Prediction prediction) {
			return WitcheryRecipeHandlerInternal.addNewPrediction(prediction);
		}		
		/**
		 * This Function will remove the specified {@link Prediction}.
		 * @param power - A {@link Prediction} you wish to remove.
		 * @return - {@link Boolean} representing whether or not the {@link Prediction} was removed.
		 */
		public static synchronized boolean remove(Prediction prediction) {	
			return WitcheryRecipeHandlerInternal.removePrediction(prediction);
		}
		/**
		 * @return - {@link HashTable} containing all currently stored {@link Prediction}s.
		 */
		public static synchronized Hashtable<Integer, Prediction> getPredictions() {
			return Witchery_Predictions.getPredictions();
		}
	}

	public static class Infusions {
		public static synchronized boolean add(Infusion infusion) {
			return WitcheryRecipeHandlerInternal.addNewInfusion(infusion);
		}		
		/**
		 * This Function will remove the specified {@link Infusion}.
		 * @param power - A {@link Infusion} you wish to remove.
		 * @return - {@link Boolean} representing whether or not the {@link Infusion} was removed.
		 */
		public static synchronized boolean remove(Infusion infusion) {
			return WitcheryRecipeHandlerInternal.removeInfusion(infusion);
		}
	}

}

