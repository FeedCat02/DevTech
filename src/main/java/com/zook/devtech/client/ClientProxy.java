package com.zook.devtech.client;

import com.zook.devtech.DevTech;
import com.zook.devtech.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = DevTech.MODID, value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
}
