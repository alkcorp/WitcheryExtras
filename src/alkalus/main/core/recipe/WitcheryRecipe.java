package alkalus.main.core.recipe;

import java.util.*;

import com.emoniph.witchery.crafting.DistilleryRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;
import com.emoniph.witchery.crafting.KettleRecipes;
import com.emoniph.witchery.crafting.KettleRecipes.KettleRecipe;
import com.emoniph.witchery.infusion.Infusion;
import com.emoniph.witchery.infusion.infusions.creature.CreaturePower;
import com.emoniph.witchery.predictions.Prediction;
import com.emoniph.witchery.predictions.PredictionManager;
import com.emoniph.witchery.ritual.*;
import com.emoniph.witchery.ritual.RiteRegistry.Ritual;

import net.minecraft.item.ItemStack;

import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.util.ReflectionUtils;

public class WitcheryRecipe {

	public static synchronized boolean addDistilleryRecipe(ItemStack input1, ItemStack input2, int jars, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4) {
		if (DistilleryRecipes.instance().addRecipe(input1, input2, jars, output1, output2, output3, output4) != null) {
			return true;
		}
		return false;
	}

	public static synchronized boolean removeDistilleryRecipe(DistilleryRecipe mRecipe) {
		
		if (mRecipe == null) {
			WitcheryExtras.log(2, "Null Distillery Recipe parsed into removeDistilleryRecipe(). Please check all DistilleryRecipes are valid before calling this function.");
			return false;
		}
		
		ArrayList<DistilleryRecipe> recipesOld = new ArrayList<DistilleryRecipe>();
		ArrayList<DistilleryRecipe> recipesNew = new ArrayList<DistilleryRecipe>();		
		recipesOld = DistilleryRecipes.instance().recipes;
		final int sizeOld = recipesOld.size();
		DistilleryRecipe toRemove = mRecipe;
		for (DistilleryRecipe n : recipesOld) {
			if (n != toRemove) {
				recipesNew.add(n);
			}
			else {
				WitcheryExtras.log(0, "Removing Distillery recipe: "+n.getDescription());
			}
		}
		final int sizeNew = recipesNew.size();		
		try {
			if (sizeNew < sizeOld) {
				ReflectionUtils.setFinal(DistilleryRecipes.instance(), "recipes", recipesNew);
				return true;				
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		WitcheryExtras.log(0, "Failed to remove Distillery recipe: "+mRecipe.getDescription());
		return false;
	}

	public static synchronized boolean addKettleRecipe(ItemStack output, int hatBonus, int familiarType, float powerRequired, int color, int dimension, boolean inBook, ItemStack... inputs) {
		if (KettleRecipes.instance().addRecipe(output, hatBonus, familiarType, powerRequired, color, dimension, inBook, inputs) != null) {
			return true;
		}
		return false;
	}

	public static synchronized boolean removeKettleRecipe(ItemStack mOutput) {
		
		if (mOutput == null) {
			WitcheryExtras.log(2, "Null Kettle Recipe parsed into removeKettleRecipe(). Please check all ItemStacks are valid before calling this function.");
			return false;
		}
		
		ArrayList<KettleRecipe> recipesOld = new ArrayList<KettleRecipe>();
		ArrayList<KettleRecipe> recipesNew = new ArrayList<KettleRecipe>();		
		recipesOld = KettleRecipes.instance().recipes;
		final int sizeOld = recipesOld.size();
		KettleRecipe toRemove = KettleRecipes.instance().findRecipeFor(mOutput);
		for (KettleRecipe n : recipesOld) {
			if (n != toRemove) {
				recipesNew.add(n);
			}
			else {
				WitcheryExtras.log(0, "Removing Kettle recipe for output: "+n.output.getDisplayName()+". "+n.getDescription());
			}
		}
		final int sizeNew = recipesNew.size();		
		try {
			if (sizeNew < sizeOld) {
				ReflectionUtils.setFinal(KettleRecipes.instance(), "recipes", recipesNew);
				return true;				
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		WitcheryExtras.log(0, "Failed to remove Kettle recipe for "+mOutput.getDisplayName());
		return false;
	}

	public static synchronized boolean addNewCreaturePower(CreaturePower power) {
		try {
			CreaturePower.Registry.instance().add(power);
			return true;			
		}
		catch (NullPointerException e) {
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	public static synchronized boolean removeCreaturePower(CreaturePower power) {
		
		if (power == null) {
			WitcheryExtras.log(2, "Null CreaturePower parsed into removeCreaturePower(). Please check all CreaturePowers are valid before calling this function.");
			return false;
		}
		
		ArrayList<CreaturePower> recipesOld = new ArrayList<CreaturePower>();
		ArrayList<CreaturePower> recipesNew = new ArrayList<CreaturePower>();		
		try {
			recipesOld = (ArrayList<CreaturePower>) ReflectionUtils.getField(CreaturePower.Registry.class, "registry").get(CreaturePower.Registry.instance());
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e1) {
			e1.printStackTrace();
			WitcheryExtras.log(2, "Failed to get CreaturePower Registry from Witchery. Unable to process removal of CreaturePower with ID: "+power.getCreaturePowerID()+".");
			return false;
		}
		final int sizeOld = recipesOld.size();
		CreaturePower toRemove = power;
		for (CreaturePower n : recipesOld) {
			if (n != toRemove) {
				recipesNew.add(n);
			}
			else {
				WitcheryExtras.log(0, "Removing CreaturePower: "+n.getCreaturePowerID()+".");
			}
		}
		final int sizeNew = recipesNew.size();		
		try {
			if (sizeNew < sizeOld) {
				ReflectionUtils.setFinal(CreaturePower.Registry.instance(), "registry", recipesNew);
				return true;				
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		WitcheryExtras.log(0, "Failed to remove CreaturePower: "+power.getCreaturePowerID()+".");
		return false;
	}

	public static synchronized boolean addNewRiteToRiteRegistry(int ritualID, int bookIndex, Rite rite, Sacrifice initialSacrifice, EnumSet<RitualTraits> traits, Circle... circles) {
		if (ritualID > Byte.MAX_VALUE) {
			WitcheryExtras.log(1, "RitualID for Rite: "+rite.toString()+" has an ID greater than "+Byte.MAX_VALUE+". Found ID: "+ritualID+".");
			return false;
		}
		if (RiteRegistry.addRecipe((byte)ritualID, bookIndex, rite, initialSacrifice, traits, circles) != null) {
			return true;
		}
		return false;
	}

	public static synchronized boolean removeRiteFromRiteRegistry(int ritualID) {		
		if (ritualID < Byte.MIN_VALUE || ritualID > Byte.MAX_VALUE) {
			WitcheryExtras.log(2, "Failed to remove Rite, ID exceeded range of a byte. Found: "+ritualID+", Expected: "+Byte.MIN_VALUE+"-"+Byte.MAX_VALUE+".");
			return false;
		}		
		List<Ritual> recipesOld = new ArrayList<Ritual>();
		List<Ritual> recipesNew = new ArrayList<Ritual>();		
		recipesOld = RiteRegistry.instance().getRituals();
		final int sizeOld = recipesOld.size();
		Ritual toRemove = RiteRegistry.instance().getRitual((byte) ritualID);
		if (toRemove != null) {
			for (Ritual n : recipesOld) {
				if (n != toRemove) {
					recipesNew.add(n);
				}
				else {
					WitcheryExtras.log(0, "Removing Rite: "+n.getDescription());
				}
			}
		}
		final int sizeNew = recipesNew.size();		
		try {
			if (sizeNew < sizeOld) {
				ReflectionUtils.setFinal(RiteRegistry.instance(), "rituals", recipesNew);
				return true;				
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		WitcheryExtras.log(0, "Failed to remove Rite: "+(toRemove != null ? toRemove.getLocalizedName()+" | "+toRemove.getRitualID()+" | "+toRemove.getDescription() : ritualID));
		return false;
	}

	public static synchronized boolean addNewPrediction(Prediction prediction) {
		try {
			PredictionManager.instance().addPrediction(prediction);
			return true;			
		}
		catch (NullPointerException e) {
			return false;
		}
	}
	
	public static synchronized boolean removePrediction(Prediction prediction) {	
		
		if (prediction == null) {
			WitcheryExtras.log(2, "Null Prediction parsed into removePrediction(). Please check all Predictions are valid before calling this function.");
			return false;
		}
					
		Hashtable<Integer, Prediction> predictionsOld = new Hashtable<Integer, Prediction>();
		Hashtable<Integer, Prediction> predictionsNew = new Hashtable<Integer, Prediction>();	
		try {
			predictionsOld = (Hashtable<Integer, Prediction>) ReflectionUtils.getField(PredictionManager.class, "predictions").get(PredictionManager.instance());
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e1) {
			e1.printStackTrace();
			WitcheryExtras.log(2, "Failed to get Prediction Registry from Witchery. Unable to process removal of Prediction with ID: "+prediction.predictionID+".");
			return false;
		}
		final int sizeOld = predictionsOld.size();
		int mIndexpredictions = 0;
		Prediction toRemove = prediction;
		if (toRemove != null) {
			for (Prediction n : predictionsOld.values()) {
				if (n != toRemove) {
					predictionsNew.put(mIndexpredictions++, n);
				}
				else {
					WitcheryExtras.log(0, "Removing Prediction: "+n.getTranslationKey()+" | "+n.predictionID);
				}
			}
		}
		final int sizeNew = predictionsNew.size();		
		try {
			if (sizeNew < sizeOld) {
				ReflectionUtils.setFinal(PredictionManager.instance(), "predictions", predictionsNew);
				return true;				
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		WitcheryExtras.log(0, "Failed to remove Prediction: "+(toRemove != null ? toRemove.getTranslationKey()+" | "+toRemove.predictionID+" | " : prediction.predictionID));
		return false;
	}

	public static synchronized boolean addNewInfusion(Infusion infusion) {
		try {
			Infusion.Registry.instance().add(infusion);
			return true;			
		}
		catch (NullPointerException e) {
			return false;
		}
	}
	
	public static synchronized boolean removeInfusion(Infusion infusion) {
		
		if (infusion == null) {
			WitcheryExtras.log(2, "Null Infusion parsed into removeInfusion(). Please check all Infusions are valid before calling this function.");
			return false;
		}
		
		ArrayList<Infusion> InfusionsOld = new ArrayList<Infusion>();
		ArrayList<Infusion> InfusionsNew = new ArrayList<Infusion>();	
		try {
			InfusionsOld = (ArrayList<Infusion>) ReflectionUtils.getField(Infusion.Registry.class, "registry").get(Infusion.Registry.instance());
		}
		catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException e1) {
			e1.printStackTrace();
			WitcheryExtras.log(2, "Failed to get Infusion Registry from Witchery. Unable to process removal of Infusion with ID: "+infusion.infusionID);
			return false;
		}
		final int sizeOld = InfusionsOld.size();
		int mIndexInfusions = 0;
		Infusion toRemove = infusion;
		if (toRemove != null) {
			for (Infusion n : InfusionsOld) {
				if (n != toRemove) {
					InfusionsNew.add(mIndexInfusions++, n);
				}
				else {
					WitcheryExtras.log(0, "Removing Infusion: "+n.toString()+" | "+n.infusionID);
				}
			}
		}
		final int sizeNew = InfusionsNew.size();		
		try {
			if (sizeNew < sizeOld) {
				ReflectionUtils.setFinal(Infusion.Registry.instance(), "registry", InfusionsNew);
				return true;				
			}			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		WitcheryExtras.log(1, "Failed to remove Infusion: "+(toRemove != null ? toRemove.toString()+" | "+toRemove.infusionID+" | " : infusion.infusionID));
		return false;
	}

}
