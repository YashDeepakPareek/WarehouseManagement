package com.warehouse.control;

import com.warehouse.model.DBSQL;

public class ItemManagment {
    DBSQL db;

    public ItemManagment(DBSQL db) { this.db = db; }

    public Boolean AddItem(
        double height,
        double width,
        double depth,
        double volume,
        String description,
        String location,
        int quantity,
        String category
    ) {
        try {
            description = (description.isEmpty() ? null : description);
            location = (location.isEmpty() ? null : location);
            category = (category.isEmpty() ? null : category);
            return db.insertItem(description, height, width, depth, volume, location, quantity, category);
        }
        catch (Exception e) {
            System.out.print("Error in ItemManagment:\n" + e);
            return false;
        }
    }
}
