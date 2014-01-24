package dmillerw.industrialization.core.proxy;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class ServerProxy {

    public static Fluid fluidSteam;

    public void registerRenders() {}

    public void initFluids() {
        fluidSteam = new Fluid("steam").setLuminosity(0).setDensity(-1000).setViscosity(100).setTemperature(1300).setGaseous(true);

        FluidRegistry.registerFluid(fluidSteam);
    }

}
