package com.deathbyaether.custommobswords.util.enums;

import java.util.function.Supplier;

import com.deathbyaether.custommobswords.list.ItemList;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public enum ModItemTier implements IItemTier {

	SWORD_GEM(0, 0, 0F, 6F, 0, () -> {
        return Ingredient.fromItems(ItemList.SWORD_GEMSTONE.get());
    });


	private final int harvestLevel;
    private final int maxUses;
    private final float efficiency;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairMaterial;

    ModItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantability, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.repairMaterial = repairMaterial;
    }

    public int getUses() {
        return maxUses;
    }

    public float getSpeed() {
        return efficiency;
    }

    public float getAttackDamageBonus() {
        return attackDamage;
    }

    public int getLevel() {
        return harvestLevel;
    }

    public int getEnchantmentValue() {
        return enchantability;
    }

    public Ingredient getRepairIngredient() {
        return repairMaterial.get();
    }

	@Override
	public int getMaxUses() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getEfficiency() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getAttackDamage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHarvestLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getEnchantability() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Ingredient getRepairMaterial() {
		// TODO Auto-generated method stub
		return null;
	}
}