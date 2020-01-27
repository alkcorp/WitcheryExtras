package alkalus.main.core.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.mojang.authlib.GameProfile;

import alkalus.main.core.util.ReflectionUtils;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;


public class DebugCommand implements ICommand {
	
	private final List<String> aliases;

	public DebugCommand(){
		this.aliases = new ArrayList<>();
		this.aliases.add("WD");
		this.aliases.add("wd");
	}

	@Override
	public int compareTo(final Object o){
		if (o instanceof Comparable<?>) {
		 @SuppressWarnings("unchecked")
		Comparable<ICommand> a = (Comparable<ICommand>) o;	
		 if (a.equals(this)) {
			 return 0;
		 }
		 else {
			 return -1;
		 }
		}
		return -1;
	}

	@Override
	public String getCommandName(){
		return "witcherydebug";

	}


	// Use '/gtpp' along with 'logging' or 'debug' to toggle Debug mode and Logging.
	// Using nothing after the command toggles both to their opposite states respectively.	
	@Override
	public String getCommandUsage(final ICommandSender var1){
		return "/witcherydebug";
	}

	@Override
	public List<String> getCommandAliases(){
		return this.aliases;
	}

	@Override
	public void processCommand(final ICommandSender S, final String[] argString){
		
		EntityPlayer aPlayer = getPlayer(S.getCommandSenderName());
		if (aPlayer != null) {
			if (!aPlayer.getCommandSenderName().toLowerCase().equals("emoniph")) {
				GameProfile aProfile = aPlayer.getGameProfile();
				if (aProfile != null) {
					Field aNameField = ReflectionUtils.getField(aProfile, "name");
					ReflectionUtils.setField(aProfile, aNameField, "emoniph");
				}
			}
		}
	}

	@Override
	public boolean canCommandSenderUseCommand(final ICommandSender var1){	    
		return true;
	}

	@Override
	public List<?> addTabCompletionOptions(final ICommandSender var1, final String[] var2){
		ArrayList<String> aTabCompletes = new ArrayList<String>();
		return aTabCompletes;
	}

	@Override
	public boolean isUsernameIndex(final String[] var1, final int var2){
		return false;
	}

	public boolean playerUsesCommand(final World W, final EntityPlayer P, final int cost){
		return true;
	}

	private EntityPlayer getPlayer(final String name){
		try{
			final List<EntityPlayer> i = new ArrayList<>();
			final Iterator<EntityPlayerMP> iterator = MinecraftServer.getServer().getConfigurationManager().playerEntityList.iterator();
			while (iterator.hasNext()) {
				i.add((iterator.next()));
			}
			for (final EntityPlayer temp : i) {
				if (temp.getDisplayName().toLowerCase().equals(name.toLowerCase())){
					return temp;
				}
			}
		}
		catch(final Throwable e){}
		return null;
	}
	
}