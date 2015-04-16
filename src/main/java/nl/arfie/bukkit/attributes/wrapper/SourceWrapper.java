package nl.arfie.bukkit.attributes.wrapper;

import org.bukkit.Bukkit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

abstract class SourceWrapper {

	protected Object instance;
	
	private static HashMap<String, Method> methods = new HashMap<String, Method>();
	
	protected static String v;
	
	static{
		String pkgName = Bukkit.getServer().getClass().getPackage().getName();
		v = "."+pkgName.substring(pkgName.lastIndexOf('.')+1)+".";
	}
	
	public SourceWrapper(Object instance){
		this.instance=instance;
	}
	
	protected static Class<?> loadClass(String start, String end){
		try {
			return Bukkit.class.getClassLoader().loadClass(start+v+end);
		} catch (ClassNotFoundException ex) {
			return null;
		}
	}
	
	protected static void declareMethod(Class<?> clazz, String name, Class<?>... parameterTypes){
		try {
			methods.put(name, clazz.getMethod(name,parameterTypes));
		} catch (NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
		}
	}
	
	protected Object invokeMethod(String name, Object... args){
		try {
			return methods.get(name).invoke(instance,args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	protected static Object invokeStaticMethod(String name, Object... args){
		try {
			return methods.get(name).invoke(null,args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
}
