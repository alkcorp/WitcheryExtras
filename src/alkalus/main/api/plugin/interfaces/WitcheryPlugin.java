package alkalus.main.api.plugin.interfaces;

public interface WitcheryPlugin {

	public abstract String getPluginName();
	
	public abstract boolean PHASE_PRE();
	
	public abstract boolean PHASE_INIT();
	
	public abstract boolean PHASE_POST();
	
}
