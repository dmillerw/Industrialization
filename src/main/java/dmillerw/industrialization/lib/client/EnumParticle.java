package dmillerw.industrialization.lib.client;

import net.minecraft.world.World;

public enum EnumParticle {

    HUGE_EXPLOSION("hugeexplosion"),
    LARGE_EXPLOSION("largeexplode"),
    SMALL_EXPLOSION("explode"),
    FIREWORK_SPARK("fireworksSpawk"),
    BUBBLE("bubble"),
    SUSPEND("suspended"),
    DEPTH_SUSPEND("depthsuspend"),
    TOWN_AURA("townaura"),
    CRITICAL("crit"),
    CRITICAL_MAGIC("magicCrit"),
    SMOKE("smoke"),
    MOB_SPELL("mobSpell"),
    MOB_SPELL_AMBIENT("mobSpellAmbient"),
    SPELL("spell"),
    SPELL_INSTANT("instantSpell"),
    WITCH_MAGIC("witchMagic"),
    NOTE("note"),
    ENDER("portal"),
    ENCHANT_GLYPH("enchantmenttable"),
    FLAME("flame"),
    LAVA("lava"),
    FOOTSTEP("footstep"),
    SPLASH("splash"),
    LARGE_SMOKE("largesmoke"),
    CLOUD("cloud"),
    REDSTONE("reddust"),
    SNOWBALL_BREAK("snowballpoof"),
    DRIP_WATER("dripWater"),
    DRIP_LAVA("dripLava"),
    SLIME("slime"),
    HEART("heart"),
    VILLAGER_ANGRY("angryVillager"),
    VILLAGER_HAPPY("happyVillager");
    
    private String particle;
    
    private EnumParticle(String particle) {
        this.particle = particle;
    }
    
    public void display(World world, double x, double y, double z, double vx, double vy, double vz) {
        world.spawnParticle(this.particle, x, y, z, vx, vy, vz);
    }
    
}
