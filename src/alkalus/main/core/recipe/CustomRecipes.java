package alkalus.main.core.recipe;

import alkalus.main.core.recipe.fixes.GarlicRecipes;

public class CustomRecipes implements Runnable {

	@Override
	public void run() {
		
		//Fixes recipes that hardcode Witchery Garlic types.
		new GarlicRecipes();
		
	}

	
	
}
