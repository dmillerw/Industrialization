package dmillerw.industrialization.core.handler.version;

import dmillerw.industrialization.core.helper.CoreLogger;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.crash.CallableMinecraftVersion;

import java.net.URL;
import java.util.Properties;

/**
 * Created by Dylan Miller on 1/19/14
 */
public class VersionHandler {

    public static boolean beforeTargetVersion(String version, String target) {
        try {
            String[] versionTokens = version.trim().split("\\.");
            String[] targetTokens = target.trim().split("\\.");

            for (int i = 0; i < versionTokens.length; i++) {
                if (versionTokens[i].startsWith("a")) {
                    return false;
                }
                if (versionTokens[i].startsWith("b")) {
                    if (targetTokens[i].startsWith("b")) {
                        versionTokens[i] = versionTokens[i].substring(1);
                        targetTokens[i] = targetTokens[i].substring(1);
                    } else {
                        return true;
                    }
                }
                if ((targetTokens[i].startsWith("a")) || (targetTokens[i].startsWith("b"))) {
                    return false;
                }
                int v = Integer.valueOf(versionTokens[i]).intValue();
                int t = Integer.valueOf(targetTokens[i]).intValue();

                if (v < t)
                    return true;
                if (v > t)
                    return false;
            }
        } catch (Throwable t) {
        }
        return false;
    }

    public static boolean afterTargetVersion(String version, String target) {
        return !beforeTargetVersion(target, version);
    }

    private final String checkURL;

    /* Current data */
    private String activeMCVersion;
    private String activeModVersion = ModInfo.VERSION;

    /* Version check data */
    private String mcVersion;
    private String modVersion;

    public VersionHandler(String checkURL) {
        this.checkURL = checkURL;
        this.activeMCVersion = new CallableMinecraftVersion(null).minecraftVersion();
    }

    public void runVersionCheck() {
        ThreadVersionCheck thread = new ThreadVersionCheck();
        thread.start();
    }

    private class ThreadVersionCheck extends Thread {

        private ThreadVersionCheck() {

        }

        public void run() {
            if (VersionHandler.this.activeModVersion.contains("@")) {
                CoreLogger.info("Development version of Industrialization found. Skipping version check.");
                return;
            }

            try {
                URL versionFile = new URL(VersionHandler.this.checkURL);
                Properties properties = new Properties();
                properties.load(versionFile.openStream());

                VersionHandler.this.mcVersion = properties.getProperty("minecraft_version");
                VersionHandler.this.modVersion = properties.getProperty("mod_version");

                if (VersionHandler.beforeTargetVersion(VersionHandler.this.activeModVersion, VersionHandler.this.modVersion)) {
                    CoreLogger.info("An newer version of " + ModInfo.NAME + " is available: " + VersionHandler.this.modVersion);

                    if (VersionHandler.beforeTargetVersion(VersionHandler.this.activeMCVersion, VersionHandler.this.mcVersion)) {
                        CoreLogger.info("This update is for Minecraft " + VersionHandler.this.mcVersion + ".");
                    }
                }
            } catch (Exception e) {
                CoreLogger.warn("Failed to run version check: " + e.getMessage());
            }
        }
    }

}
