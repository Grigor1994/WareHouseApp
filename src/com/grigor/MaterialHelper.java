package com.grigor;

import java.util.Map;

public class MaterialHelper {

    private static final Map<Class<? extends Material>, Integer> counts =
            Map.of(
                    Bolt.class, 5,
                    Iron.class, 30,
                    Copper.class, 40);

    public static int getMaxCountInWareHouse(Class<? extends Material> materialType) {
        return counts.get(materialType);
    }

}
