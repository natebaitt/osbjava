<div align="center">
  <img src="/media/osbjava_logo_color2.png" alt="osbjava logo" width="500">
  <h1>osbjava – an osu! storyboarding library</h1>
</div>


## Overview
*osbjava* is a Java library for programmatically creating and exporting osu! storyboards.

I originally developed this library in 2024 as a way to overcome limitations of the current storyboarding syntax. Storyboard sprites operate on a “start-to-end” system, which makes it difficult to create complex visuals such as particle generation and beat flashing. *osbjava* aims to solve this by introducing **dynamic sprites** that instead operate on a “rate-based” system, which **keeps track of sprite parameters and velocities over time**.

I also developed this library as a personal alternative to music video making. I have always wanted to accompany my music videos with more engaging “synesthetic” visuals. However, I have lacked access or experience in more sophisticated software such as Adobe After Effects or Blender. *osbjava* has since become a prominent tool in many of my music videos (see Examples).

*osbjava* is inspired by other storyboard programming tools such as [SGL](https://osu.ppy.sh/community/forums/topics/378938?n=1) (now defunct) and [storybrew](https://github.com/Damnae/storybrew).


## Features
<ul>
    <li><p><b>Traditional and dynamic sprites</b> – Animate sprites by using “start-to-end” or “rate-based” commands</p></li>
    <li><p><b>Shorthand command notation</b> – Simplify code by using shorthand commands as described in the osu! wiki</p></li>
    <li><p><b>Modular file exporting</b> - Save storyboards as binary files to reuse in more complex projects</p></li>
    <li><p><b>OOP benefits</b> – Expand library classes to create customizable or specialized sprite commands and animations</p></li>
</ul>


## Installation
### Dependencies
TBD

### Installing the library
TBD

### Installing example projects
<ol>
    <li><p>The <code>projects</code> folder contains example projects that demonstrate the various uses and functionalities of <i>osbjava</i>. Each project contains:</p>
    <ul>
        <li><p>A <code>beatmap</code> folder, which contains the beatmap and storyboard</p>
        <li><p>A <code>bin</code> folder, which contains binary data of the storyboard's segments</p>
        <li><p>Java classes that are used to generate the storyboard's segments</p>
    </ul>
    <p></p>
    <li><p>To view the storyboard:</p>
    <ul>
        <li><p><b>osu! stable:</b> Copy the <code>beatmap</code> folder into your local directory <code>C:\Users\&lt;username&gt;\AppData\Local\osu!\Songs</code>.</p>
        <li><p><b>osu! lazer:</b> Archive the <code>beatmap</code> folder into a <code>.zip</code> file and rename the extension to <code>.osz</code>. Open the file using osu! lazer to automatically import the beatmap.</p>
    </ul>
    <p></p>
    <li><p>To view only a specific segment of the storyboard:</p>
    <ol type="i">
        <li><p>Run the corresponding Java class. This will rebuild and export a new <code>.osb</code> file for the specified segment.</p>
        <li><p>Replace the existing <code>.osb</code> file in the <code>beatmaps</code> folder using the terminal prompts.</p>
        <li><p>Repeat (2) to view the storyboard.</p>
    </ol>
</ol>


## Examples
<ul>
    <li><a href="https://youtu.be/i8Ne2efT0AA">empty town</a> – TBD</li>
    <li><a href="https://youtu.be/KmEN1wlQY-U">tetration</a> – TBD</li>
</ul>


## Tutorials
TBD

## Documentation
TBD




