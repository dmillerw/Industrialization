package dmillerw.industrialization.recipe;

import dmillerw.industrialization.core.ore.OreHandler;
import dmillerw.industrialization.core.ore.OreWrapper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class CrushingManager {

    public static final CrushingManager INSTANCE = new CrushingManager();

    @ForgeSubscribe
    public void onOreDictionaryRegistration(OreDictionary.OreRegisterEvent event) {
        if (event.Name.startsWith("dust")) { // Found a dust, so assume it has an attached ore block, and generate a crushed Item for it
            String oreTag = event.Name.replace("dust", "ore");
            ItemStack oreBlock = OreDictionary.getOres(oreTag).get(0);

            if (oreBlock != null) {

            }
        }
    }

    public Set<CrushingRecipe> recipes = new HashSet<CrushingRecipe>();

    public void registerRecipe(ItemStack input, ItemStack output) {
        registerRecipe(new CrushingRecipe(input, output));
    }

    public void registerRecipe(CrushingRecipe recipe) {
        recipes.add(recipe);
    }

    public CrushingRecipe getRecipeFor(ItemStack input) {
        for (CrushingRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public void initializeRecipes() {
        /* NORMAL RECIPES */

        /* ORE DICTIONARY HANDLING */
        for (OreWrapper ore : OreHandler.INSTANCE.getRegisteredOres()) {
            if (ore.getOreBlocks() != null && ore.getOreBlocks().length > 0 && ore.getGrinding() != null && ore.getDust() != null) {
                for (ItemStack block : ore.getOreBlocks()) {
                    registerRecipe(block, ore.getGrinding());
                }
            }
        }
    }

}
