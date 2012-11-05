package me.blockcat.head;

import java.util.HashMap;

import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.NBTTagCompound;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Head extends JavaPlugin{

	HashMap<Player, Block> blocks = new HashMap<Player, Block>();
	 public static Permission permission = null;

	@Override
	public void onEnable() {

		this.setupPermissions();
	}

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String string, String[] args) {
		if (!(cs instanceof Player)) {
			return false;
		}
		Player player = (Player) cs;
		if (cmd.getName().equalsIgnoreCase("name") && hasPermissions(player, "header.change")) {
			if (player.getItemInHand().getTypeId() == 397) {
				if (args.length >= 1) {

					CraftItemStack c = new CraftItemStack(player.getItemInHand());
					NBTTagCompound nbt = new NBTTagCompound();

					if (c.getHandle().hasTag()) {
						nbt = c.getHandle().getTag();
					}

					nbt.setString("SkullOwner", args[0]);
					c.getHandle().tag = nbt;
					player.setItemInHand(c);
					player.sendMessage(ChatColor.GREEN + "The head has been changed to: " + ChatColor.AQUA + args[0]);
				} else {
					player.sendMessage(ChatColor.RED + "Please give a name!");	
				}
			}else {
				player.sendMessage(ChatColor.RED + "This item is not a head!");
			}
		} 

		return true;
	}

	
	public boolean hasPermissions(Player player, String node) {
		if (player.isOp()) {
			return true;
		} else {
			return permission.has(player, node);
		}
	}
	
	private boolean setupPermissions()
    {
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
        if (permissionProvider != null) {
            permission = permissionProvider.getProvider();
        }
        return (permission != null);
    }

}
