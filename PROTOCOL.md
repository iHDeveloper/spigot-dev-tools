# Spigot Dev Tools Protocol
It's plugin messaging protocol that can be adapted in Minecraft versions 1.8 and above.
It aims to be compatible and flexible.

Plugin Channel Name: `Spigot|DevTools`

---

## Packet Structure
The packet format in the messaging channel should look like this:
- Type (`String`)
- Anything else depends on the type format.
