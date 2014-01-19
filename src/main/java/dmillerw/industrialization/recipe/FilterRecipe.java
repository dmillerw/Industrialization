package dmillerw.industrialization.recipe;

import net.minecraft.item.ItemStack;

/**
 * Created by Dylan Miller on 1/2/14
 */
public class FilterRecipe {

    private final ItemStack input;
    private final ItemStack output;

    public FilterRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return this.input.copy();
    }

    public ItemStack getOutput() {
        return this.output.copy();
    }

    public boolean matches(ItemStack input) {
        return this.input.isItemEqual(input);
    }

}
