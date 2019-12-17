package alkalus.main.core.types;

import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.List;

import com.emoniph.witchery.brewing.action.BrewAction;
import com.emoniph.witchery.brewing.action.BrewActionRitualRecipe;
import com.emoniph.witchery.crafting.DistilleryRecipes;
import com.emoniph.witchery.crafting.DistilleryRecipes.DistilleryRecipe;

import alkalus.main.core.util.ReflectionUtils;
import net.minecraft.item.ItemStack;

public class Witchery_Cauldron {

	private static final Class mBrewingRegistryClass;
	private static final Field mBrewingRegistryInstanceField;
	
	private static final Object mBrewingRegistryInstance;
	
	private final static Hashtable mBrewingIngredients;
	
	static {
		mBrewingRegistryClass = ReflectionUtils.getClass("com.emoniph.witchery.brewing.WitcheryBrewRegistry");
		mBrewingRegistryInstanceField = ReflectionUtils.getField(mBrewingRegistryClass, "INSTANCE");		
		mBrewingRegistryInstance = ReflectionUtils.getFieldValue(mBrewingRegistryInstanceField);
		Field aBrewingIngrediants = ReflectionUtils.getField(mBrewingRegistryClass, "ingredients");
		mBrewingIngredients = (Hashtable) ReflectionUtils.getFieldValue(aBrewingIngrediants, mBrewingRegistryInstance);
	}
	
	public static List<BrewActionRitualRecipe> getBrewingRecipes() {
		return (List<BrewActionRitualRecipe>) ReflectionUtils.invokeNonVoid(mBrewingRegistryInstance, ReflectionUtils.getMethod(mBrewingRegistryClass, "getBrewingRecipes", new Class[] {}));
	}
	
	private BrewAction removeBrewingRecipe(BrewAction ingredient) {
		if (!Witchery_Cauldron.mBrewingIngredients.containsKey(ingredient.ITEM_KEY)) {
			Witchery_Cauldron.mBrewingIngredients.put(ingredient.ITEM_KEY, ingredient);
			Class c = ReflectionUtils.getClass("com.emoniph.witchery.brewing.action.BrewActionRitualRecipe");
			if (c.isInstance(ingredient)) {
				getBrewingRecipes().add((BrewActionRitualRecipe) ingredient);
			}
		}
		return ingredient;
	}
	
	public static synchronized DistilleryRecipe getDistillingResult(ItemStack input1, ItemStack intput2, ItemStack jars){
		return DistilleryRecipes.instance().getDistillingResult(input1, intput2, jars);
	}

	public static synchronized DistilleryRecipe findRecipeFor(ItemStack result){
		return DistilleryRecipes.instance().findRecipeFor(result);
	}

	public static synchronized DistilleryRecipe findRecipeUsing(ItemStack ingredient){
		return DistilleryRecipes.instance().findRecipeUsing(ingredient);
	}

}
