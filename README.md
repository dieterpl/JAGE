# JAGE
Just Another Gameboy Emulator

---
## Desription

JAGE is a work-in-progress project to create a functioning Gameboy emulator in java.
The structure of the modified z80 processor, used in the Gameboy, is represented in a very verbose way.
The goal of the project is NOT to make a very fast emulator, but to layout the internal in an easy to understand way, that is 
compliant with object oriented language paradigms of java. 
For that reason the opcodes and corresponding instructions are stored in the 'instructions.yml' file. Which are then dynamically loaded to
and executed. This approach enables the commands to be very general and clear. It also helps with debugging as it makes tracing the executed code straight forward.

--- 

In addition to the main window this emulator features debugging tools like a sprite view, a memory view and a disassembler.
Although these are still very much work-in-progess.

-- 

## Progress

So far the emulator is able to run the 1. test from the emulator test set found here:
http://gbdev.gg8.se/files/roms/blargg-gb-tests/
In the issue tab of this github one can find the still missing function for the project.

![main](https://github.com/dieterpl/jage/blob/readme/picture/main.png?raw=true)

![sprite](https://github.com/dieterpl/jage/blob/readme/picture/sprite.png?raw=true)

![memory](https://github.com/dieterpl/jage/blob/readme/picture/memory.png?raw=true)
## Documents & Reading

As the Gameboy is a very well researched device a lot of documentation exists some of it is really excellent:

- Gameboy CPU Manual: http://marc.rawer.de/Gameboy/Docs/GBCPUman.pdf
- Gameboy test roms: http://gbdev.gg8.se/files/roms/blargg-gb-tests/
- A curated list of awesome Game Boy: https://github.com/gbdev/awesome-gbdev



