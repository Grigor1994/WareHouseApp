package com.grigor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class WareHouse {

    private static final Object sharedLock = new Object();

    private final Object lock = new Object();

    private final Map<Class<? extends Material>, Set<Material>> materials;

    public WareHouse() {
        materials = new ConcurrentHashMap<>();
    }

    public void addMaterial(Material material) {
        Set<Material> materials = getMaterialSetByType(material.getClass());

        synchronized (lock) {
            int size = materials.size();
            int maxCount = MaterialHelper.getMaxCountInWareHouse(material.getClass());
            if (size == maxCount) {
                throw new MaterialSpaceException("Count of `" + material.getClass().getSimpleName() + "` can be maximum " + maxCount);
            }

            materials.add(material);
        }
    }

    public boolean removeMaterial(Material material) {
        Set<Material> materials = getMaterialSetByType(material.getClass());

        synchronized (lock) {
            return materials.remove(material);
        }
    }

    public void transferMaterialsToOtherWareHouse(List<? extends Material> materials, WareHouse wareHouse) {
        if (wareHouse == this) {
            throw new IllegalArgumentException();
        }

        Map<Class<? extends Material>, List<Material>> materialsByType =
                materials.stream().collect(Collectors.groupingBy(Material::getClass));

        synchronized (sharedLock) {

            synchronized (this.lock) {
                synchronized (wareHouse.lock) {
                    materialsByType.forEach((type, list) -> {
                        Set<Material> currentMaterialsSet = this.getMaterialSetByType(type);
                        Set<Material> materialsSet = wareHouse.getMaterialSetByType(type);

                        int currentSize = materialsSet.size();
                        int maxCount = MaterialHelper.getMaxCountInWareHouse(type);
                        int emptyCapacity = maxCount - currentSize;

                        int needAdd = Math.min(emptyCapacity, list.size());

                        int listIndex = 0;
                        for (int i = 0; i < needAdd && listIndex<list.size(); i++) {
                            Material material = list.get(listIndex);
                            boolean deleted = currentMaterialsSet.remove(material);
                            if (deleted) {
                                materialsSet.add(material);
                            } else {
                                i--;
                            }
                            listIndex++;
                        }
                    });
                }
            }
        }
    }

    private Set<Material> getMaterialSetByType(Class<? extends Material> aClass) {
        return this.materials.computeIfAbsent(aClass, k -> new HashSet<>());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        this.materials.forEach((type, materials) -> {
            stringBuilder.append(type.getSimpleName()).append(" - ").append(materials.size()).append(" ");

            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");

            for (Material material : materials) {
                stringJoiner.add(material.getName());
            }

            stringBuilder.append(stringJoiner.toString());
            stringBuilder.append("\n");
        });

        return stringBuilder.toString();
    }
}
