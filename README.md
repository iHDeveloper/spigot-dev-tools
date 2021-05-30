[![](https://jitpack.io/v/iHDeveloper/spigot-dev-tools.svg)](https://jitpack.io/#iHDeveloper/spigot-dev-tools)
# Spigot Dev Tools
Lightweight spigot library to provide developer tools to monitor/debug the Minecraft game world.

**Note:** This project is in the alpha state.
If you found any bug/glitch, open an issue in the [issue tracker](https://github.com/iHDeveloper/spigot-dev-tools/issues).

## 🌃⠀Showcase
[[Old] Youtube](https://youtu.be/BXlPDq4DVlw)

### Server Wall
![Server Wall](./img/server-wall.png)
Global information about the server
```java
DevTools.pin("Max Players", "20");
DevTools.unpin("Max Players");
```

### Profiler
![Profiler](./img/profiler.png)
Overview of the performance of certain operations in the server
```java
DevTools.profileStart("Regions");
/* Regions calculation */
DevTools.profileEnd("Regions");
```

### Logger
![Logger](./img/logger.png)
Records events that occur in the server
```java
DevTools.logger().info("This server is using the test plugin of §eSpigot Dev Tools!");
DevTools.logger().info("§ePlugin By §c@iHDeveloper");
DevTools.logger().info("Example of info log message!");
DevTools.logger().warn("Example of warn log message!");
DevTools.logger().err("Example of error log message!");
DevTools.logger().debug("Example of debug log message!");
DevTools.logger().debug("Example of debug log message!");
```

### Watcher
![Watcher](./img/watcher.png)
Read-Only table to watch multiple values (different for each player)
```java
DevTools.watch("Winner", winnerName);
DevTools.unwatch("Winner"); /* Broadcasted to all online players ONLY! */

DevTools.watch(player, "Region", regionName);
DevTools.unwatch(player, "Region");
```

### TPS
![TPS](./img/tps.png)
Overview of the server cycle performance (aka TPS).
```java
/* Enabled by default! */
```

## 📦⠀Modules
- `~` (aka root) is Spigot plugin to host the custom plugin messaging channel
- `~/legacy-mod` is forge mod (1.8.9) for providing graphical developer tools

## 🗓⠀Protocol
You can view the protocol structure and design [here](./PROTOCOL.md).

## 🗄⠀Download
### Plugin
You can download the main plugin from [here](https://github.com/iHDeveloper/spigot-dev-tools/releases/tag/v0.3-alpha)
### API
- Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependency>
    <groupId>me.ihdeveloper</groupId>
    <artifactId>spigot-dev-tools</artifactId>
    <version>v0.3-alpha</version>
    <scope>provided</scope> <!-- The API is already included with the plugin -->
</dependency>
```

- Groovy
```groovy
repositories {
  maven { url 'https://jitpack.io' }
}

dependencies {
  compileOnly 'me.ihdeveloper:spigot-dev-tools:v0.3-alpha';
}
```

- Kotlin DSL
```kotlin
repositories {
  maven { url = uri("https://jitpack.io") }
}

dependencies {
  compileOnly("me.ihdeveloper:spigot-dev-tools:v0.3-alpha")
}
```

- Scratch
  If you don't want to use the options above, then you can download the API from [here](https://github.com/iHDeveloper/spigot-dev-tools/releases/tag/v0.3-alpha)

### Dependency
Add this to your `plugin.yml` to load the plugin before your plugin.
```yml
depend:
  - SpigotDevTools
```

## 👨‍💻⠀Credits
This project is made⠀by @iHDeveloper
