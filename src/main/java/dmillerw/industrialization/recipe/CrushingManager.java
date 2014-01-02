package dmillerw.industrialization.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class CrushingManager {

    public static final CrushingManager INSTANCE = new CrushingManager();

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
        for (String ore : OreDictionary.getOreNames()) {
            if (ore.startsWith("ore")) {
                List<ItemStack> oreBlocks = OreDictionary.getOres(ore);
                List<ItemStack> oreDusts = OreDictionary.getOres(ore.replace("ore", "dust"));

                if (oreBlocks != null && !oreBlocks.isEmpty() && oreDusts != null && !oreDusts.isEmpty()) {
                    for (ItemStack block : oreBlocks) {
                        if (oreDusts.get(0) != null && block != null) {
                            System.out.println ("adding " + block.getDisplayName() + " to " + oreDusts.get(0).getDisplayName() + " crushing recipe");
                            registerRecipe(block, oreDusts.get(0));
                        }
                    }
                }
            }
        }
    }

}
