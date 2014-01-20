package dmillerw.industrialization.core.helper;

import cpw.mods.fml.common.FMLLog;
import dmillerw.industrialization.lib.ModInfo;

import java.util.logging.Logger;

public class CoreLogger {

	public static Logger logger;

	static {
		logger = Logger.getLogger(ModInfo.NAME);
		logger.setParent(FMLLog.getLogger());
	}
	
	public static void info(String msg) {
		logger.info(msg);
	}
	
	public static void warn(String msg) {
		logger.warning(msg);
	}

    public static void fine(String msg) {
        logger.fine(msg);
    }

}
