package nl.arfie.bukkit.attributes;

import nl.arfie.bukkit.attributes.wrapper.NBTTagList;
import nl.arfie.bukkit.attributes.wrapper.CraftItemStack;
import nl.arfie.bukkit.attributes.wrapper.MinecraftItemStack;
import nl.arfie.bukkit.attributes.wrapper.NBTTagCompound;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * An AttributeList is a list containing {@link Attribute}s, and can apply these {@link Attribute}s to an ItemStack.
 * 
 * <b>Example code:</b>
 * <pre>
 * //Directly add Attributes within the constructor
 * AttributeList list = new AttributeList(new Attribute(AttributeType.MOVEMENT_SPEED,Operation.ADD_PERCENTAGE,0.5));
 * //Use add(Attribute) to add Attributes
 * list.add(new Attribute(AttributeType.ATTACK_DAMAGE,Operation.ADD_NUMBER,20.0))
 * 		.add(new Attribute().setType(AttributeType.KNOCKBACK_RESISTANCE).setOperation(Operation.ADD_PERCENTAGE).setAmount(0.75));
 * //Create an ItemStack with these Attributes
 * Bukkit.getPlayer("Arfie99").getInventory().addItem(list.apply(new ItemStack(Material.NETHER_STAR),true));
 * </pre>
 * @author Ruud Verbeek
 * @see Attribute
 */

public class AttributeList {

	List<Attribute> attributes = new ArrayList<Attribute>();
	
	/**
	 * Create an empty AttributeList
	 */
	
	public AttributeList(){}
	
	/**
	 * Create an AttributeList with the given contents.
	 * @param attributes
	 */
	
	public AttributeList(Attribute... attributes){
		for(Attribute a : attributes){
			this.attributes.add(a);
		}
	}
	
	/**
	 * Add the given attribute to the AttributeList
	 * 
	 * @param attribute
	 * @return this AttributeList, to quickly add stuff: <tt>list.add(a).add(b).add(c);</tt>
	 */
	
	public AttributeList add(Attribute attribute){
		attributes.add(attribute);
		return this;
	}
	
	/**
	 * Returns the number of attributes in this AttributeList
	 * @return the number of attributes in this AttributeList
	 */
	
	public int size(){
		return attributes.size();
	}
	
	/**
	 * Returns the attribute at the given index of the list
	 * @param index The index of the attribute
	 * @return The attribute at the given index
	 * @throws IndexOutOfBoundsException When <tt>index<0 || index>=size()</tt>
	 */
	
	public Attribute get(int index)throws IndexOutOfBoundsException{
		if(index<0 || index>=attributes.size())
			throw new IndexOutOfBoundsException();
		return attributes.get(index);
	}
	
	/**
	 * Removes the attribute at the given index from the list
	 * @param index The index of the attribute to remove.
	 * @return This list, to quickly remove stuff: <tt>list.remove(0).remove(1).remove(2);</tt>
	 * @throws IndexOutOfBoundsException When <tt>index<0 || index>=size()</tt>
	 */
	
	public AttributeList remove(int index)throws IndexOutOfBoundsException{
		if(index<0 || index>=attributes.size())
			throw new IndexOutOfBoundsException();
		attributes.remove(index);
		return this;
	}
	
	/**
	 * Applies these attributes to the given ItemStack
	 * @param original The original ItemStack to apply these attributes to
	 * @param replace Whether or not to remove the attributes that were already on the ItemStack
	 * @return A new ItemStack containing the attributes from this AttributeList
	 */
	
	public ItemStack apply(ItemStack original, boolean replace){
		try {
			MinecraftItemStack stack = CraftItemStack.asNMSCopy(original);
			NBTTagCompound tag = stack.getTag();
			NBTTagList list = replace?new NBTTagList():tag.getList("AttributeModifiers",10);
			for(Attribute attribute : attributes){
				list.add(attribute.write());
			}
			tag.set("AttributeModifiers",list);
			stack.setTag(tag);
			return CraftItemStack.asCraftMirror(stack).getStack();
		} catch (InstantiationException | IllegalAccessException ex) {
			ex.printStackTrace();
			return original;
		}
	}
	
}
