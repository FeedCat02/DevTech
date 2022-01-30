package com.zook.devtech.common.items;

import com.zook.devtech.DevTech;
import gregtech.api.items.metaitem.StandardMetaItem;

public class DTMetaItem extends StandardMetaItem {

    public static final DTMetaItem META_ITEM = new DTMetaItem();

    static {
        META_ITEM.setRegistryName(DevTech.MODID, "meta_item");
    }

    @Override
    public void registerModels() {
        super.registerModels();
    }

    // TODO set model path
}
