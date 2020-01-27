package alkalus.main.core.entities;

import java.lang.reflect.Field;

import com.emoniph.witchery.predictions.Prediction;

import alkalus.main.api.RecipeManager;
import alkalus.main.asm.AsmConfig;
import alkalus.main.core.WitcheryExtras;
import alkalus.main.core.util.ReflectionUtils;

public class BabaYagaHandler {
	
	private static final Field mSelfFulfillmentProbabilityPerSec;
	private static final double DEFAULT_CHANCE = 0.05D;
	
	static {
		mSelfFulfillmentProbabilityPerSec = ReflectionUtils.getField(ReflectionUtils.getClass("com.emoniph.witchery.predictions.Prediction"), "selfFulfillmentProbabilityPerSec");
	}

	public static void adjustPredictions() {
		if (AsmConfig.chanceBabaYagaBad != DEFAULT_CHANCE || AsmConfig.chanceBabaYagaGood != DEFAULT_CHANCE) {
			if (AsmConfig.chanceBabaYagaGood != DEFAULT_CHANCE) {
				Prediction aBabaYagaGood = RecipeManager.Predictions.getPrediction(10);
				ReflectionUtils.setField(aBabaYagaGood, mSelfFulfillmentProbabilityPerSec, AsmConfig.chanceBabaYagaGood);		
				WitcheryExtras.log(0, "Adjusted chance of spawning a 'Good' Baba Yaga from "+DEFAULT_CHANCE+"% to "+AsmConfig.chanceBabaYagaGood+"%.");
			}
			if (AsmConfig.chanceBabaYagaBad != DEFAULT_CHANCE) {
				Prediction aBabaYagaBad = RecipeManager.Predictions.getPrediction(11);
				ReflectionUtils.setField(aBabaYagaBad, mSelfFulfillmentProbabilityPerSec, AsmConfig.chanceBabaYagaBad);		
				WitcheryExtras.log(0, "Adjusted chance of spawning a 'Bad' Baba Yaga from "+DEFAULT_CHANCE+"% to "+AsmConfig.chanceBabaYagaBad+"%.");		
			}
		}			
	}

}
