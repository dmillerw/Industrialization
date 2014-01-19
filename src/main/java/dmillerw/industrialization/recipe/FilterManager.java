package dmillerw.industrialization.recipe;

import dmillerw.industrialization.core.ore.OreHandler;
import dmillerw.industrialization.core.ore.OreWrapper;
import dmillerw.industrialization.util.UtilStack;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class FilterManager {

    public static final FilterManager INSTANCE = new FilterManager();

    public Set<FilterRecipe> recipes = new HashSet<FilterRecipe>();

    public void registerRecipe(ItemStack input, ItemStack output) {
        registerRecipe(new FilterRecipe(input, output));
    }

    public void registerRecipe(FilterRecipe recipe) {
        recipes.add(recipe);
    }

    public FilterRecipe getRecipeFor(ItemStack input) {
        for (FilterRecipe recipe : recipes) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public void initializeRecipes() {
        /* ORE DICTIONARY HANDLING */
        for (OreWrapper ore : OreHandler.INSTANCE.getRegisteredOres()) {
            if (ore.getOreBlocks() != null && ore.getOreBlocks().length > 0 && ore.getGrinding() != null && ore.getDust() != null) {
                registerRecipe(ore.getGrinding(), UtilStack.resize(ore.getDust(), 2));
            }
        }
    }

}
