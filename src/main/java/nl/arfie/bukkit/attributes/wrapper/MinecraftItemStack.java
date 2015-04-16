package nl.arfie.bukkit.attributes.wrapper;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;

public class MinecraftItemStack extends SourceWrapper {

    private final static Class<?> clazz = loadClass("net.minecraft.server", "ItemStack");
    private static Field tagField;

    static {
        try {
            tagField = clazz.getDeclaredField("tag");
            tagField.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException ex) {
            Bukkit.getLogger().info("Failed to set \"Tag\" accessible!");
        }
    }

    public MinecraftItemStack() throws InstantiationException, IllegalAccessException {
        super(clazz.newInstance());
    }

    public MinecraftItemStack(Object instance) {
        super(instance);
    }

    public void setTag(NBTTagCompound tag) {
        try {
            tagField.set(instance, tag.instance);
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException ex) {
            Bukkit.getLogger().info(instance.getClass().getName());
            ex.printStackTrace();

        }
    }

    public NBTTagCompound getTag() {
        try {
            return new NBTTagCompound(tagField.get(instance));
        } catch (IllegalArgumentException | IllegalAccessException | SecurityException | InstantiationException ex) {
            Bukkit.getLogger().info(instance.getClass().getName());
            ex.printStackTrace();
            return null;
        }
    }

}
