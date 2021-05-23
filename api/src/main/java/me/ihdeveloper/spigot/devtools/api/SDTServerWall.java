package me.ihdeveloper.spigot.devtools.api;

import java.util.Map;
import java.util.Set;

public interface SDTServerWall {

    void put(String name, String value);

    void remove(String name);

    void clear();

    Set<Map.Entry<String, String>> get();

}
