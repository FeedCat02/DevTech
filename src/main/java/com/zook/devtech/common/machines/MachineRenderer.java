package com.zook.devtech.common.machines;

import com.zook.devtech.api.machines.IMachineRenderer;
import gregtech.client.renderer.ICubeRenderer;
import gregtech.client.renderer.texture.cube.OrientedOverlayRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineRenderer implements IMachineRenderer {

    public static final Map<String, OrientedOverlayRenderer.OverlayFace> FACE_MAP = new HashMap<>();

    public static IMachineRenderer createOrientedRenderer(String basePath, String... faces) {
        List<OrientedOverlayRenderer.OverlayFace> overlayFaces = new ArrayList<>();

        for (String face : faces) {
            OrientedOverlayRenderer.OverlayFace overlayFace = FACE_MAP.get(face.toLowerCase());
            if (overlayFace != null) {
                overlayFaces.add(overlayFace);
            }
        }
        return new MachineRenderer(new OrientedOverlayRenderer(basePath, overlayFaces.toArray(new OrientedOverlayRenderer.OverlayFace[0])));
    }

    private final ICubeRenderer renderer;

    public MachineRenderer(ICubeRenderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public ICubeRenderer getActualRenderer() {
        return renderer;
    }

    static {
        FACE_MAP.put("front", OrientedOverlayRenderer.OverlayFace.FRONT);
        FACE_MAP.put("back", OrientedOverlayRenderer.OverlayFace.BACK);
        FACE_MAP.put("top", OrientedOverlayRenderer.OverlayFace.TOP);
        FACE_MAP.put("bottom", OrientedOverlayRenderer.OverlayFace.BOTTOM);
        FACE_MAP.put("side", OrientedOverlayRenderer.OverlayFace.SIDE);

        FACE_MAP.put("f", OrientedOverlayRenderer.OverlayFace.FRONT);
        FACE_MAP.put("ba", OrientedOverlayRenderer.OverlayFace.BACK);
        FACE_MAP.put("t", OrientedOverlayRenderer.OverlayFace.TOP);
        FACE_MAP.put("bo", OrientedOverlayRenderer.OverlayFace.BOTTOM);
        FACE_MAP.put("s", OrientedOverlayRenderer.OverlayFace.SIDE);
    }
}
