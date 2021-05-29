package me.ihdeveloper.spigot.devtools.api;

import java.util.Collection;

public interface Logger {

    void info(String message);

    void warn(String message);

    void err(String message);

    void debug(String message);

    void sendCache(SDTContainer container);

    void setMaximumCacheSize(int max);

}
