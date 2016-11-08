# lgtvchannels

Simple editor to edit channels in LG TV that allows reordering of channels. I wrote it as after connecting my TV to Satellite it has found 1700 channels. Most of them useless. The code was written for (LG TV 43UF680V-ZA).

The editor is a Java-written tool. If you feel you want to join to have some fun with code - feel free to pull the repo and write tests :). Or other stuff.

Project:
 - can be easily imported to Eclipse
 - uses Maven for solving dependancies.

From API perspecitve:
 - uses XML/DOM to process files (it includes demo file)
 - uses Swing as GUI
 - uses basic file access (NIO2)
 - has structure for testing.
  
It would be nice to:
 - add options for editing parameters of individual channels
 - use to a greater extent design patterns (to make make it more GUI independent)
   and in the long term put it online.
 - add some XPath features, revisit different options of XML handling.
 - add Localization
 - wrap it into Swing

![Alt text](/2016-11-08 21_33_48-.png?raw=true "Optional Title")

