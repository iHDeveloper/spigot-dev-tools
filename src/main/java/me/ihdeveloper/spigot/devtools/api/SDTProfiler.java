package me.ihdeveloper.spigot.devtools.api;

public interface SDTProfiler {

    void start(String name);

    void start(String name, boolean keep);

    void end(String name);

    void build();

    Item[] getItems();

    interface Item {
        String getName();
        boolean isUpdated();
        long getTicks();
        double getPercent();
    }

}