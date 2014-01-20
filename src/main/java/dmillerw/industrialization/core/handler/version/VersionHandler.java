package dmillerw.industrialization.core.handler.version;

import dmillerw.industrialization.core.helper.CoreLogger;
import dmillerw.industrialization.lib.ModInfo;
import net.minecraft.crash.CallableMinecraftVersion;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

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

    private String activeMCVersion;
    private String activeModVersion = ModInfo.VERSION;

    private boolean shouldSuppress = false;
    private boolean newVersion = false;
    private boolean checkComplete = false;

    /* Version check data */
    private String mcVersion;
    private String modVersion;
    private String description;

    private boolean critical;

    public VersionHandler(String checkURL) {
        this.checkURL = checkURL;
        this.activeMCVersion = new CallableMinecraftVersion(null).minecraftVersion();
    }

    public void suppress() {
        this.shouldSuppress = true;
    }

    public void runVersionCheck() {
        ThreadVersionCheck thread = new ThreadVersionCheck();
        thread.start();
    }

    public String getModVersion() {
        return modVersion;
    }

    public String getMcVersion() {
        return mcVersion;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCritical() {
        return critical;
    }

    private class ThreadVersionCheck extends Thread {

        private ThreadVersionCheck() {

        }

        public void run() {
            try {
                URL versionFile = new URL(VersionHandler.this.checkURL);
                BufferedReader reader = new BufferedReader(new InputStreamReader(versionFile.openStream()));
                VersionHandler.this.modVersion = reader.readLine();
                VersionHandler.this.mcVersion = reader.readLine();
                VersionHandler.this.description = reader.readLine();
                VersionHandler.this.critical = Boolean.parseBoolean(reader.readLine());
                reader.close();

                if (VersionHandler.beforeTargetVersion(VersionHandler.this.activeModVersion, VersionHandler.this.modVersion)) {
                    CoreLogger.info("An newer version of " + ModInfo.NAME + " is available: " + VersionHandler.this.modVersion);
                    VersionHandler.this.newVersion = true;

                    if (VersionHandler.this.critical) {
                        CoreLogger.info("This is a critical update and should be downloaded as soon as possible. It will not be possible to suppress this notification");
                        VersionHandler.this.shouldSuppress = false;
                    }
                    if (VersionHandler.beforeTargetVersion(VersionHandler.this.activeMCVersion, VersionHandler.this.mcVersion)) {
                        CoreLogger.info("This update is for Minecraft " + VersionHandler.this.mcVersion + ".");
                    }
                }
            } catch (Exception e) {
                CoreLogger.warn("Failed to run version check: " + e.getMessage());
            }
            VersionHandler.this.checkComplete = true;
        }
    }

}
