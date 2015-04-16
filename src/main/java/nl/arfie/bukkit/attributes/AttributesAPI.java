package nl.arfie.bukkit.attributes;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class AttributesAPI extends JavaPlugin{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(!cmd.getName().equalsIgnoreCase("attribute"))return true;
		if(args.length<3)return false;
		if(sender.hasPermission("attribute")&&sender instanceof Player){
			Player p = (Player) sender;
			ItemStack item = p.getItemInHand();
			if(item.getType()==Material.AIR){
				sender.sendMessage("§cYou need to hold an item in your hand to do that.");
				return false;
			}
			AttributeType type;
			Operation operation;
			double value;
			try{
				type = AttributeType.valueOf(args[0].toUpperCase());
			}catch(IllegalArgumentException ex){
				sender.sendMessage("§c"+args[0]+" is not a valid AttributeType.");
				StringBuilder types = new StringBuilder("§eAvailable AttributeTypes:");
				for(AttributeType t : AttributeType.values()){
					types.append(" ").append(t.toString());
				}
				sender.sendMessage(types.toString());
				return false;
			}
			try{
				operation = Operation.valueOf(args[1].toUpperCase());
			}catch(IllegalArgumentException ex){
				sender.sendMessage("§c"+args[0]+" is not a valid Operation.");
				StringBuilder types = new StringBuilder("§eAvailable Operations:");
				for(Operation t : Operation.values()){
					types.append(" ").append(t.toString());
				}
				sender.sendMessage(types.toString());
				return false;
			}
			try{
				value = Double.parseDouble(args[2]);
			}catch(NumberFormatException ex){
				sender.sendMessage("§c"+args[2]+" is not a valid double.");
				return false;
			}
			p.setItemInHand(Attributes.apply(item,new Attribute(type,operation,value),false));
			sender.sendMessage("§aSuccessfully added "+type.toString()+" to your "+item.getType().toString()+", operation "+operation.toString()+" with value "+value+".");
			return true;
		}
		sender.sendMessage("§cYou don't have permission to do that.");
		return true;
	}
	
}
