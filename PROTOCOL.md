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
