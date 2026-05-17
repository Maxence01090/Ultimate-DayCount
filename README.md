# Ultimate Daycount

[![Available on Modrinth](https://img.shields.io/badge/Available%20on-Modrinth-00c070?style=for-the-badge&logo=modrinth&logoColor=white)](https://modrinth.com/plugin/ultimatedaycount)

A lightweight Paper plugin that tracks the number of in-game days on your Minecraft server. Simple, fast, and fully configurable.

---

## Why Ultimate Daycount?

Ever wanted to know how many days your server has survived? Ultimate Daycount gives your players a simple way to check that — and it's smart enough to pause time when nobody's online, so the counter stays meaningful.

---

## Features

- **Day tracking** — Counts total days based on in-game world time
- **Smart pause** — Freezes the daylight cycle when no players are online (configurable)
- **Multilingual** — Full support for English and French, including all command messages
- **Auto-updating config** — Missing config keys are automatically added on startup while preserving your existing settings
- **Hot reload** — Reload the config in-game without restarting the server

---

## Commands

| Command | Description |
|---|---|
| `/daycount` | Displays the total number of days passed |
| `/dc` | Alias for `/daycount` |
| `/daycount reload` | Reloads the configuration (OP only) |

---

## Configuration

The config file is located at `plugins/UltimateDaycount/config.yml` and is generated automatically on first launch.

```yaml
# Plugin language (en or fr)
language: en

# Enable debug logs
debug: false

# Pause time when no players are online
pause-time-when-empty: true

# Messages
messages:
  en:
    format: "&6[Ultimate Daycount] &bDays passed: &a{days} day(s)"
  fr:
    format: "&6[Ultimate Daycount] &bJours passés: &a{days} jour(s)"
```

### Config options

| Key | Type | Default | Description |
|---|---|---|---|
| `language` | `string` | `en` | Plugin language (`en` or `fr`) |
| `debug` | `boolean` | `false` | Enable debug logging |
| `pause-time-when-empty` | `boolean` | `true` | Pause daylight cycle when no players are online |
| `messages.en.format` | `string` | — | Message format for English |
| `messages.fr.format` | `string` | — | Message format for French |

> The `{days}` placeholder is replaced with the actual day count. Color codes use the `&` prefix.

---

## Installation

1. Download the latest JAR from [Modrinth](https://modrinth.com/plugin/ultimatedaycount) — or compile it yourself (see [Building from Source](#building-from-source))
2. Drop it into your server's `plugins/` folder
3. Start (or restart) your server
4. Edit `plugins/UltimateDaycount/config.yml` to your liking

---

## Building from Source

**Requirements:**
- Java 21+
- Maven 3.9+

```bash
mvn clean package
```

The compiled JAR will be at `target/daycount-1.0.0.jar`.

---

## Requirements

| Dependency | Version |
|---|---|
| Paper | 1.21+ |
| Java | 21+ |

---

## Roadmap

- [x] Basic day counter using world time
- [x] Pause daylight cycle when server is empty
- [x] Multilingual support (EN / FR)
- [x] Auto-update config on missing keys
- [x] In-game config reload
- [ ] Per-world day tracking
- [ ] Scoreboard integration
- [ ] PlaceholderAPI support
- [ ] Custom date format (e.g. "Year 2, Day 45")
- [ ] Join message showing current day
- [ ] Permission-based access control

---

## License

This project is licensed under the MIT License — see [LICENSE](LICENSE) for details.
