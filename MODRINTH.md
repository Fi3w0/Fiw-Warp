# Fiw Warp

**Lightweight, server-side teleportation for Fabric, NeoForge and Forge.**

Fiw Warp gives your players homes and your server warps, with a fair, configurable warmup so
teleporting isn't an instant escape button — and an optional cooldown to stop spam. It's
server-side only, registers no networking, and ships no client code, so it stays out of the way
and keeps your server light.

| Minecraft | Loaders | Java |
|---|---|---|
| 26.3, 26.1.2 | Fabric, NeoForge | 25 |
| 1.21.11, 1.21.8, 1.21.1 | Fabric, NeoForge | 21 |
| 1.20.1 | Fabric, Forge | 17 |

## ✨ Features

- 🏠 **Multiple named homes** per player (configurable limit)
- 🌐 **Warps** — admin-only by default, or let players create their own with a per-player limit
- ✅ **Warp confirmation** — clickable Accept/Deny prompt before a `/warp` teleport starts
  (optional)
- ⏳ **Warmup teleport** — stand still for a few seconds (default 5); moving, getting hit,
  attacking, or breaking a block cancels it (all toggleable)
- ❄️ **Separate cooldowns** for `/home`, `/warp`, and `/back` (off by default)
- ⚡ **Instant teleport for admins** via permission level
- 🎆 **Particles + sound** on teleport, with an extra vanish burst and a short cosmetic
  Blindness effect on instant teleports — or turn any of it off
- 💬 **Every message customizable** — reword anything the mod says, right in the config
- 🪶 **Tiny footprint**, plain-JSON storage, no Architectury dependency

## 🎮 Commands

- `/home [name]`, `/sethome [name]`, `/delhome [name]`, `/edithome <name> <newname>`, `/homes`
- `/warp [name]`, `/warp accept`, `/warp deny`, `/warps`
- `/back`
- **Warp management:** `/setwarp <name>`, `/delwarp <name>`, `/editwarp <name> <newname>` —
  admin-only by default, or open to everyone (own warps only) if configured
- **Admin:** `/fiwwarp reload`

## ⚙️ Configuration

Everything is configurable in `config/fiwwarp/config.json` — warmup time, per-command cooldowns,
what cancels a teleport, home/warp limits, cross-dimension toggle, particle/blindness effects,
warp confirmation, admin permission level, every message the mod sends, and more. Edit it and
run `/fiwwarp reload`.

**Mix and match the systems.** Each of the **home**, **warp**, and **back** systems can be turned
on or off independently — e.g. run a homes-only SMP by setting `enable_warps` and `enable_back`
to `false`. Disabled systems hide their commands entirely.

## 📦 Requirements

- **Fabric:** Fabric API + Fabric Language Kotlin
- **NeoForge / Forge:** Kotlin for Forge for the matching Minecraft version (6.4+ on 26.3, 6.2+ on 26.1.2, 6.x on 1.21.11, 5.10 or a newer 5.x on 1.21.8 and 1.21.1, 4.12 or a newer 4.x on 1.20.1)

## 🧩 Compatibility

Server-side only. Clients do not need the mod installed to join a server running it.
