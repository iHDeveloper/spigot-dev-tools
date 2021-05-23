package me.ihdeveloper.spigot.devtools.test;

import me.ihdeveloper.spigot.devtools.api.DevTools;

public class ProfileTesterTask implements Runnable {

    private final String name;
    private int operationSize;
    private int something = 100;

    public ProfileTesterTask(String name, int operationSize) {
        this.name = name;
        this.operationSize = operationSize;
    }

    @Override
    public void run() {
        DevTools.profileStart(name, true);

        int currentSize = 0;
        while (currentSize < operationSize) {
            something *= 5;
            something += 5;
            something <<= 5;

            currentSize++;
        }

        DevTools.profileEnd(name);
    }

}
