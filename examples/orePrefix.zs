#loader gregtech

import mods.gregtech.ore.OrePrefix;
import mods.gregtech.material.IMaterialPredicate;

val curvedPlate as OrePrefix = OrePrefix.registerOrePrefix("plateCurved", 1);
//val curvedPlate = OrePrefix.registerOrePrefix("plateCurved", 1, "curvedPlate");
//val curvedPlate = OrePrefix.registerOrePrefix("plateCurved", 1, "curvedPlate", 0);

curvedPlate.setGenerationPredicate(IMaterialPredicate.isIngot);
curvedPlate.createMaterialItem();