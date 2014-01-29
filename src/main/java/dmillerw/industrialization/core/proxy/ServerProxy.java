package dmillerw.industrialization.core.proxy;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Dylan Miller on 1/1/14
 */
public class ServerProxy {

    public static Fluid fluidSteam;

    public void registerRenders() {}

    public void registerFluids() {
        if (!FluidRegistry.isFluidRegistered("steam")) {
            Fluid steam = new Fluid("steam").setLuminosity(0).setDensity(-1000).setViscosity(100).setTemperature(1300).setGaseous(true);
            FluidRegistry.registerFluid(steam);
        }
        
        fluidSteam = FluidRegistry.getFluid("steam");
    }

}
