package dmillerw.industrialization.recipe;

import dmillerw.industrialization.core.ore.OreHandler;
import dmillerw.industrialization.core.ore.OreWrapper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import java.util.HashSet;
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
        registerRecipe(new ItemStack(Block.stone), new ItemStack(Block.cobblestone));
        registerRecipe(new ItemStack(Block.cobblestone), new ItemStack(Block.gravel));
        registerRecipe(new ItemStack(Block.gravel), new ItemStack(Block.sand));

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
