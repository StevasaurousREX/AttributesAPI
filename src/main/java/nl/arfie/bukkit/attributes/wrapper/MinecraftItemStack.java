package nl.arfie.bukkit.attributes.wrapper;

public class MinecraftItemStack extends SourceWrapper {

	private final static Class<?> clazz = loadClass("net.minecraft.server","ItemStack");
	
	public MinecraftItemStack() throws InstantiationException, IllegalAccessException{super(clazz.newInstance());}
	public MinecraftItemStack(Object instance){super(instance);}
	
	public void setTag(NBTTagCompound tag){
		try {
			clazz.getField("tag").set(instance,tag.instance);
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException ex) {
			ex.printStackTrace();
		}
	}
	
	public NBTTagCompound getTag(){
		try {
			return new NBTTagCompound(clazz.getField("tag").get(instance));
		} catch (IllegalArgumentException | IllegalAccessException
				| NoSuchFieldException | SecurityException | InstantiationException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
