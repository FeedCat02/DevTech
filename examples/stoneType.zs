#loader gregtech

import mods.gregtech.StoneType;
import mods.gregtech.material.MaterialBuilder;

// first create stone material
// MUST HAVE DUST, INGOT OR GEM PROPERTY
val erebusStone = MaterialBuilder(32000, "umberstone")
    .dust(1)
    .build();

// block state of original block
val blockState = "erebus:umberstone:type=umberstone";
// create stone type
StoneType.create(12, "umberstone", "oreUmberstone", erebusStone, blockState);

