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
