package com.grigor;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        WareHouse wareHouse1 = new WareHouse();
        WareHouse wareHouse2 = new WareHouse();

        Bolt bolt1 = new Bolt("Bolt1");
        Iron iron2 = new Iron("Iron2");
        wareHouse1.addMaterial(bolt1);
        wareHouse1.addMaterial(new Bolt("Bol2"));
        wareHouse1.addMaterial(new Iron("Iron1"));
        wareHouse1.addMaterial(iron2);
        wareHouse1.addMaterial(new Iron("Iron3"));
        wareHouse1.addMaterial(new Copper("Copper1"));

        Iron iron4 = new Iron("Iron4");
        wareHouse2.addMaterial(iron4);
        wareHouse2.addMaterial(new Iron("Iron5"));
        wareHouse2.addMaterial(new Copper("Copper2"));

        System.out.println(wareHouse1);
        System.out.println(wareHouse2);

        wareHouse1.transferMaterialsToOtherWareHouse(List.of(bolt1, iron2), wareHouse2);

        System.out.println(wareHouse1);
        System.out.println(wareHouse2);

        wareHouse2.transferMaterialsToOtherWareHouse(List.of(bolt1, iron4), wareHouse1);

        System.out.println(wareHouse1);
        System.out.println(wareHouse2);

        wareHouse2.removeMaterial(iron2);

        System.out.println(wareHouse2);
    }
}
