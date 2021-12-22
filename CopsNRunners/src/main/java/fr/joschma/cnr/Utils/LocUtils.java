package fr.joschma.cnr.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocUtils {

    public static Location stringToLoc(String loc) {
        String[] locs = loc.split("/");
        Location signLoc = new Location(Bukkit.getServer().getWorld(locs[0]), Double.parseDouble(locs[1]),
                Double.parseDouble(locs[2]), Double.parseDouble(locs[3]), Float.parseFloat(locs[4]),
                Float.parseFloat(locs[5]));
        return signLoc;
    }
    
    public static String locToString(Location loc) {
		String signLoc = loc.getWorld().getName() + "/" + loc.getX() + "/" + loc.getY() + "/" + loc.getZ() + "/" + loc.getYaw() + "/" + loc.getPitch();
		return signLoc;
	}
}

