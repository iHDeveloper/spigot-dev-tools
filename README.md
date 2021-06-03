[![](https://jitpack.io/v/iHDeveloper/spigot-dev-tools.svg)](https://jitpack.io/#iHDeveloper/spigot-dev-tools)
# Spigot Dev Tools
Lightweight spigot library to provide developer tools to monitor/debug the Minecraft game world.

**Note:** This project is in the alpha state.
If you found any bug/glitch, open an issue in the [issue tracker](https://github.com/iHDeveloper/spigot-dev-tools/issues).

|Minecraft Version | Plugin | Minecraft Mod |
|------------------|--------|---------------|
| **1.8.x** (Legacy) | ✓ | ✓ |
| **1.16.x** | ✓ (not tested) | Not Supported |

## Example

```java
/* Pin a global info about the maximum number of players */
DevTools.pin("Max Players", "20");

/* Profile a selected region of your code */
DevTools.profileStart("Regions");
/* Regions calculation/processing */
DevTools.profileEnd("Regions");

/* Log information about certain events in the server */
DevTools.logger().info("Example of info log message!");
DevTools.logger().warn("Example of warn log message!");
DevTools.logger().err("Example of error log message!");
DevTools.logger().debug("Example of debug log message!");

/* Watch a temporary information for everyone or certain player */
DevTools.watch("Winner", winnerName);
DevTools.watch(player, "Region", regionName);
```

## 🌃⠀Screenshots
![Logger](./img/logger.png)
More screenshots? click [here](./SCREENSHOTS.md)

(Old) Youtube Video? click [here](https://youtu.be/BXlPDq4DVlw)


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
