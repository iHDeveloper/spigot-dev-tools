# Spigot Dev Tools Protocol
It's plugin messaging protocol that can be adapted in Minecraft versions 1.8 and above.
It aims to be compatible and flexible.

Plugin Channel Name: `Spigot|DevTools`

---

## Packet Structure
The packet format in the messaging channel should look like this:
- Type (`String`)
- Anything else depends on the type format.

--

## Server-bound Messages

### Discovery (Special)
The goal of this message is to let the user know that this server supports SDT Client.
It includes the major, and minor data of the protocol.
It can be used to provide the ability of auto connect feature in the future.

**Note:** It can be turned off by the server-side. This message is sent when the player joins the server.

**Note:** This message is sent when the player joins the server.

**Enabled** by default!

Name: `discovery`
```java
byte major = in.readByte();
byte minor = in.readByte();
```

### Hello
This means that the server acknowledged you and authorized you to listen to the hidden game data.

**Note:** This message has no data

### TPS
The server is broadcasting the TPS every 30 seconds.

Name: `tps`
```java
double recentTPS = new double[3];
for (int i = 0; i < 2; i++)
    recentTPS[i] = in.readDouble();
```

### Profiler
The server is broadcasting the calculated Profiler every 1 second.

Name: `profiler`
```java
int length = in.readInt();
int totalTicks = in.readLong();
int totalMilliseconds = in.readLong();
for (int i = 0; i < length; i++) {
    String name = in.readUTF();
    boolean updated = in.readBoolean();
    long ticks = in.readLong();
    long ms = in.readLong();
    double percent = in.readDouble();
}
```

### Server Wall Put
Put information on the server wall.

- When the player joins
- When something on the wall changes

Name: `server-wall-put`
```java
int length = in.readInt();

for (int i = 0; i < length; i++) {
    String name = in.readUTF();
    String value = in.readUTF();
}
```

### Server Wall Remove
Remove certain information from the server wall.

Name: `server-wall-remove`
```java
String name = in.readUTF();
```

### Server Wall Clear
Clears the server wall data.

Name: `server-wall-clear`
```java
/* Empty */
```

### Watcher Put
The server is giving you a read-only state to watch. Each state has a unique key.
If there are two states with the same key, Then the old state gets replaces with the new state.

Name: `watcher-put`
```java
String key = in.readUTF();
String value = in.readUTF();
```

### Watcher Remove
The server is telling you to remove a state from the watcher.
If the key of the state doesn't exist then no need to remove anything.

Name: `watcher-remove`
```java
String key = in.readUTF();
```

### Logger Message
The server is informing you about a log that has been issued.

Name: `logger-message`
```java
byte type = in.readByte();
/**
 * 1 = DEBUG
 * 2 = ERROR
 * 3 = WARNING
 * 4 = INFO
*/

String message = in.readUTF();
```

### Logger Chunk
The server is sending a cached chunk of logs

Name: `logger-chunk`
```java
int length = in.readInt();

for (int i = 0; i < length; i++) {
    byte type = in.readByte();
    String message = in.readUTF();
}
```

## Client-bound Messages

### Hello
The goal of this packet to let the server acknowledge that this client has the mod.
It also means that the client is ready to receive data to enable the tools.

In this case, the server has two actions to deal with this,
If the server authorized the client. Then, the client will receive data immediately,
If not, the client will get kicked from the server.

Name: `hello`

**Notes:**
- This packet can be sent at anytime.
- This packet should be sent after the player login successfully!
- The server will accept connections with the same major and same or higher minor.

```java
/* Protocol version that the client understands */
out.writeByte(major);
out.writeByte(minor);
```
