package fr.joschma.cnr.GUI;

import java.util.Arrays;

import com.cryptomorin.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.joschma.cnr.CopsNRunners;
import fr.joschma.cnr.Arena.Arena;

public class ArenaGui {

    CopsNRunners pl;
    Inventory inv;

    public ArenaGui(CopsNRunners pl) {
        super();
        this.pl = pl;
    }

    public void openGui(Player p, Arena a) {
        setUpArenaGui(a);
        p.openInventory(inv);
    }

    public void setUpArenaGui(Arena a) {
        inv = Bukkit.createInventory(null, 45, a.getName());

        inv.setItem(10,
                createIT(XMaterial.NAME_TAG.parseMaterial(), ChatColor.GRAY + "Arena name: " + ChatColor.WHITE + a.getName(), ""));

        for (int i = 0; i < 8; i++) {
            inv.setItem(i, createIT(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), " ", ""));
        }
        for (int i = 8; i < 36; i = i + 8) {
            inv.setItem(i, createIT(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), " ", ""));
            i++;
            inv.setItem(i, createIT(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), " ", ""));
        }
        for (int i = 36; i < 45; i++) {
            inv.setItem(i, createIT(XMaterial.BLACK_STAINED_GLASS_PANE.parseMaterial(), " ", ""));
        }

        ItemStack[] its = {createIT(XMaterial.OAK_DOOR.parseMaterial(), "Lobby Spawn", isSet(a.getLobbySpawn())),
                createIT(XMaterial.BLUE_CONCRETE.parseMaterial(), ChatColor.GREEN + "Add" + ChatColor.GRAY + " Cops Spawn", isSet(a.getCopsSpawns().size()), ChatColor.GRAY + "You have set "
                        + ChatColor.GOLD + a.getCopsSpawns().size() + ChatColor.GRAY + " spawns"),
                createIT(XMaterial.YELLOW_CONCRETE.parseMaterial(), ChatColor.GREEN + "Add" + ChatColor.GRAY + " Runners Spawn", isSet(a.getRunnersSpawns().size()), ChatColor.GRAY + "You have set "
                        + ChatColor.GOLD + a.getRunnersSpawns().size() + ChatColor.GRAY + " spawns"),
                createIT(XMaterial.IRON_DOOR.parseMaterial(), "End Spawn", isSet(a.getEndSpawn())),
                createIT(XMaterial.OAK_SIGN.parseMaterial(), ChatColor.GREEN + "Add" + ChatColor.GRAY + " Sign", isSet(a.getSigns().size()), ChatColor.GRAY + "You have link "
                        + ChatColor.GOLD + a.getSigns().size() + ChatColor.GRAY + " to this arenas"),
                createIT(XMaterial.PAPER.parseMaterial(), "Finished", isSet(a.isFinished()))};

        for (int i = 19; i < 25; i++) {
            if (i != 24) {
                inv.setItem(i, its[i - 19]);
            } else {
                inv.setItem(i + 1, its[i - 19]);
            }
        }

        inv.setItem(29, createIT(XMaterial.BLUE_CONCRETE_POWDER.parseMaterial(), ChatColor.RED + "Remove" + ChatColor.GRAY + " Cops Spawn", isSet(a.getCopsSpawns().size()), ChatColor.GRAY + "You have set "
                + ChatColor.GOLD + a.getCopsSpawns().size() + ChatColor.GRAY + " spawns"));
        inv.setItem(30, createIT(XMaterial.YELLOW_CONCRETE_POWDER.parseMaterial(), ChatColor.RED + "Remove" + ChatColor.GRAY + " Runners Spawn", isSet(a.getRunnersSpawns().size()), ChatColor.GRAY + "You have set "
                + ChatColor.GOLD + a.getRunnersSpawns().size() + ChatColor.GRAY + " spawns"));

        inv.setItem(32, createIT(XMaterial.DARK_OAK_SIGN.parseMaterial(),  ChatColor.RED + "Remove" + ChatColor.GRAY + " Sign", isSet(a.getSigns().size()), ChatColor.GRAY + "You have link "
                + ChatColor.GOLD + a.getSigns().size() + ChatColor.GRAY + " to this arenas"));
    }

    public String isSet(int i) {
        if (i > 0) {
            return ChatColor.GREEN + "Is set";
        } else {
            return ChatColor.RED + "Not set";
        }
    }

    public String isSet(boolean bool) {
        if (bool) {
            return ChatColor.GREEN + "Is finished";
        } else {
            return ChatColor.RED + "Not finished";
        }
    }

    public String isSet(Object obj) {
        if (obj != null) {
            return ChatColor.GREEN + "Is set";
        } else {
            return ChatColor.RED + "Not set";
        }
    }

    public ItemStack createIT(Material ma, String displayName, String... lore) {
        ItemStack it = new ItemStack(ma);
        ItemMeta im = it.getItemMeta();

        if (displayName != null) {
            im.setDisplayName(displayName);
        }

        if (lore != null) {
            im.setLore(Arrays.asList(lore));
        }

        it.setItemMeta(im);
        return it;
    }
}
