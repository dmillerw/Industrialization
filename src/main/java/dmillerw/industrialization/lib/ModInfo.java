package dmillerw.industrialization.lib;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class ModInfo {

    public static final String ID = "industrialization";
    public static final String NAME = "Industrialization";

    public static final String CHANNEL = "indust";

    public static final String MAJOR_VERSION = "@MAJOR@";
    public static final String MINOR_VERSION = "@MINOR@";
    public static final String REVISION_VERSION = "@REVIS@";
    public static final String BUILD = "@BUILD@";

    public static final String VERSION = MAJOR_VERSION + "." + MINOR_VERSION + "." + REVISION_VERSION + "." + BUILD;

    public static final String BASE_PACKAGE = "dmillerw." + ID;

    public static final String SERVER_PROXY = BASE_PACKAGE + ".core.proxy.ServerProxy";
    public static final String CLIENT_PROXY = BASE_PACKAGE + ".core.proxy.ClientProxy";

    public static final String SERVER_PACKET_PROXY = BASE_PACKAGE + ".core.proxy.ServerPacketProxy";
    public static final String CLIENT_PACKET_PROXY = BASE_PACKAGE + ".core.proxy.ClientPacketProxy";

    public static final String RESOURCE_PREFIX = ID.toLowerCase() + ":";

}
