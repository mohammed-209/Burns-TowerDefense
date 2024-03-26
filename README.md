# Burns Tower Defense
Created by: Team ItWorkedYesterday<br/>
Team members: Alex Davis, Mohammed Saleh, Loc Nguyen, and David Yang

## Overview:
<p>
<img src ="https://user-images.githubusercontent.com/127431288/236564130-5cab9370-ba83-4a83-aa4f-82c20dcafd2a.PNG" width = 40%>
<img src="https://libgdx.com/assets/brand/logo.png" width=50%>
</p>
Burns Tower Defense is an University of the Pacific themed 2D tower defense game built in Java using the LibGDX framework. In the game, users are first tasked to build a path from the enemy spawning location and connect it to Burns Tower. When the user confirms the layout of the path, users may then use in-game currency to purchase various towers of varying abilities to defend Burns Tower from enemy tanks.

## Features Implemented & Yet to be Implemented:
### Features Implemented
**Path Building**:</br>
Though Burns Tower Defense functions similar to most 2D tower defense games, the feature that we implemented that differs from most games of this genre is the path building in which enemy characters would travel toward Burns Tower (the user's home tower)
<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236565479-4bec057a-e9c9-47c1-aef1-c599afaeaf9d.PNG" width=40%>
<p align="center">
Image above is the start of the setup phase of Burns Tower Defense
</p>
</p>
<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236565528-b1fd2921-8357-4c28-b3da-1f0eb94dc59c.PNG" width=40%>
<p align="center">
Image above is a connected path from the enemy spawn point to Burns Tower
</p>
</p>
</br>

**Speed Slider**:</br>
We included a speed slider feature that allows users to increase the gameplay speed, rather than having users sit through wave after of enemies.
<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236568554-22611a1b-2cb2-41d0-9d13-f35f51966668.PNG" width = 30%>
</p>
<p align="center">
Image above is the Speed Slider for Burns Tower Defense
</p>
</br>

**Tower Menu**</br>
We added a tower menu feature to pop-up when a placed tower on the game map is clicked on. When the tower menu is popped up, the following options are available to the user:
- **Upgrade**: Upgrades the select tower if the user has enough in-game currency to increase attack power 
- **Sell**: Sells the select tower for half it's current value and removes the tower from the game map
- **Cancel**: Close the tower menu
<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236580973-ddaba3ca-a680-44aa-803e-3a97eaeb0cd2.PNG" width = 20%>
<img src="https://user-images.githubusercontent.com/127431288/236581425-b97edfe6-0498-4fea-870c-a40bf1e6fbf8.PNG" width = 20%>
<img src ="https://user-images.githubusercontent.com/127431288/236581458-c4f861c3-75a2-4028-af95-79d1a4347218.PNG" width = 20%>
</p>
<p align="center">
Images from left to right: Popped up Tower Menu. Level 1 Bullet Tower. Upgraded (Level 2) Bullet Tower
</p>

### Features yet to be implemented
**Abilities**:</br>
Due to time constraints, we were unable to implement the **Abilities** portion of Burns Tower Defense.</br>

<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236582727-ebd47b5f-47e4-4f14-8d2a-b7c81952ca09.PNG" width=40%>
<img src="https://user-images.githubusercontent.com/127431288/236582751-dbf75816-2fde-4165-9c67-afd86637a343.PNG" width = 13%>
</p></br>

The **Abilities** feature of the game was intended to assist the user in effecting the enemy characters by doing the following: </br>
- Freeze: Temporarily stopping all enemy character movement</br>
- Bomb: The user would place a bomb on the game map and upon the bomb's denotation, all enemies present on the game map would be removed.</br>
- Boost: A temporary upgrade of all attack towers for a limited duration.</br>


## How to Build/Use Burns Tower Defense
### How to build:
Burns Tower Defense was built with the libGDX framework. It's recommended to follow this documentation:</br>
- [Build libGDX project](https://libgdx.com/wiki/start/setup)


### How to use Burns Tower Defense
- Clone the repository or download and extract the ZIP file
- Import the project into your preferred development environment, run, debug and package it

## How to Play the Game
When the user selects the **Play** option, the user will be present with the game screen and enter the **Setup Phase** of the game.<br/>
### Setup Phase:
During the **Setup Phase**, the user must complete the following tasks before the game can be played:
- [ ] User is required to build a connected path from enemy spawn point to Burns Tower.<br/>
**Note:**<br/>
- The user is presented with an in-game currency of $3500 to build the connected path.
- Each purchase of a path tile is $50.
- The path cannot overlap.
- User can select **Reset** to restart the path building 
- User can select **Back** to undo the previous laid tile
- [ ] Once a connected path has been made, the user selects **Confirm** to end Setup Phase.

### Game Phase:
Now that the user has entered the **Game Phase**, the following options will be presented:<br/>
- Various towers to purchase with in-game currency left over from building the path.
- A **Spawn Next Wave** button, when selected, will send the next wave of enemies onto the map.

# Customer Statement of Requirements
The motivation driving the creation of Burns Tower Defense is to create a 2D Tower Defence that functions similar to other tower defense games but offers the option for users to build the enemy path in the direction they choose. </br>
<p align="center">
<img src="https://cdn.cloudflare.steamstatic.com/steam/apps/3590/0000008164.1920x1080.jpg?t=1615390608" align="center" width=50%>
</p>
<p align="center">Image above is from the game: Plants vs Zombies</p>
Plants vs. Zombies is a popular tower defense game that restricts enemy movement to five predetermined lanes. Despite this limitation, the game still adheres to the typical tower defense format, utilizing plant-themed towers to fend off different types of enemy zombies and protect the player's house.</br>

</br><p align="center">
<img src="https://cdn.akamai.steamstatic.com/steam/apps/246420/ss_502205852350bfd8d64070adf4c38f2ffdf86772.1920x1080.jpg?t=1672337706" width=50%>
</p>
<p align="center">Image above is from the game: Kingdom Rush</p>
Like Plants vs. Zombies, the popular tower defense game Kingdom Rush introduced new features to the genre, but still limited players to selecting the path that enemy characters would travel on the map. In addition, Kingdom Rush also restricted the locations where towers could be placed to engage enemy characters approaching the player's home base.</br></br>

With the option of building enemy lanes of travel not present for users that play Plants vs Zombies and Kingdom Rush; Burns Tower Defense fulfills that option and presents more possibilities to users.</br></br>

# Functional Requirements
<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236598805-5388665f-1885-4cb5-978d-13ba2c35f392.PNG" width = 60%>
</p>
<p align="center">Image above is the Use Case diagram for the Main Menu of Burns Tower Defense</p>

### Main Menu
Use Case: User wants to normal/creative play 
<ol>
<li> Program displays main menu.</br>
<li> User selects **Play**
<li> Program brings user to normal/creative play mode
</ol>

### Settings
Use Case: User wants to adjust Music and SFX levels
<ol>
<li> Program displays main menu.</br>
<li> User selects **Settings**
<li> Program produces a pop-up window with sliders for Music ans SFX volume
<li> User adjusts the sliders as desired
<li> User selects **Back** in pop-window
<li> Program removes pop-window and returns user to main screen
</ol>

<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236599526-edb5d8a7-de25-4c9f-8afe-fd411aa6ce77.PNG" width =50%>
</p>
<p align="center">Image above is the Use Case diagram Upgrading/Selling a placed attack tower in Burns Tower Defense</p>

### Upgrade Tower
Use Case: User wants to upgrade tower
<ol>
<li> User clicks on tower on the map
<li> Program produced a pop-up tower menu window with the options:</br>
- Upgrade</br>
- Sell</br>
- Cancel</br>
<li> User clicks on Upgrade
<li> Program checks if user has enough funds for upgrade</br>
- Program does nothing if user lacks funds for upgrade
<li> If user has enough funds, program upgrades selected tower
<li> Program removes pop-up menu window
</ol>

### Sell/Remove Tower
Use Case: User wants to sell/remove tower
<ol>
<li> User clicks on tower on the map
<li> Program produced a pop-up tower menu window with the options:</br>
- Upgrade</br>
- Sell</br>
- Cancel</br>
<li> User clicks on Sell
<li> Program returns half of the value of the selected tower to user's in-game currency
<li> Program removes selected tower and pop-up menu window
</ol>

# Class Diagram and Interface Specification
<p align="center">
  <img src="https://user-images.githubusercontent.com/127431288/236594742-af14811d-2661-445c-9b55-e31a0cf1ba14.PNG" width=40%>
</p>
<p>Above image is the initial UML Diagram for Burns Tower Defense</p>

<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236597808-0da409af-0968-4495-9901-7bb2fbca982c.PNG" width = 40%>
<img src="https://user-images.githubusercontent.com/127431288/236597809-7175d79d-fe69-4410-9dd2-60a74f34fad9.PNG" width = 40%>
<img src="https://user-images.githubusercontent.com/127431288/236597815-eaff172e-1ada-4bfe-808c-307dc0e408f7.PNG" width = 30%>
<img src="https://user-images.githubusercontent.com/127431288/236597819-3366b452-1b75-4d37-b1fa-51296ea8679d.PNG" width = 50%>
<img src="https://user-images.githubusercontent.com/127431288/236597828-1cde6542-59b1-4313-af17-73212cecb6ef.PNG" width = 40%>
<img src="https://user-images.githubusercontent.com/127431288/236597832-ca9acf39-c9c2-4de4-b836-c232dd1abd74.PNG" width = 40%>
</p>
<p align="center">Above images are the sections of the UML for the latest verison of Burns Tower Defense</p>

### Omissions
- From the initial UML diagram to its latest version, we were unable to implement the drag and drop **Abilities** due to time constraints.

### Additions
- The addition of the **Projectile** and Projectile subclasses was cruical to have as we increased the number of **Tower** subclasses to the game. The various Tower subclasses required Projectile subclasses to differ from one another in terms of range, damage output, and cost.
- We created the **GameUI** class in which users would have a designated class that they'd directly interact with when placing a Tower subclass onto the game map.
- We created an **UpgradeScreen** class which would allow for users to increase the default stats of Tower subclasses, along with present the option of increasing Burns Tower's health and a means to generate additional in-game currency.

# Interaction Diagrams
<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236590348-b3f83d79-9fc2-4482-87e6-3be145da9c50.png" width=40%>
</p>
<p align="center">
Image above is the complete sequence diagram for Burns Tower Defense
</p>

### Single Responsibility Principle (SRP)
<p align="center">
<img src="https://user-images.githubusercontent.com/127431288/236591399-2c477f11-8266-48f2-88d4-bc058b533260.PNG" width=30%>
<img src ="https://user-images.githubusercontent.com/127431288/236591406-38bf0f74-aefe-45f9-949e-2ebb6408a1e5.PNG" width=30%>
</p>
<p align="center">Above images showcase the importance of the class, Level, and it's responsibility for the game</p>
Our use of SRP was to have the game be driven through one, in this instance, the Level class. As shown in the above sequence diagrams, component classes like Tower, Enemy, and Projectile are funneled toward Level to pass the instances and attributes of their occurance in the game. The responsibility of the class Level is to receive game information selected by the user, and then tell the Map class what instances and attributes of game components to render onto the game map. By having Level adhere to SRP, a clear separation of responsibilities and functions is established for more organized code.

### Open-Closed Principle (OCP)
<p align="center">
  <img src="https://user-images.githubusercontent.com/127431288/236593180-dfc8edc0-6dd9-4fad-93d8-0874f555fab8.PNG" width = 40%>
</p>
<p align="center">Above image shows the importance of the class, GameUI, and it's responsibility for the game's class components (Tower, Enemy, and Projectile classes)</p></br>
Throughout the game, the component classes (i.e. Tower subclasses, Enmey subclasses, and Projectile subclasses) relied heavily on OCP to create new instances of them on the game map. But their occurances on the game map were facilitated through the user interacting with the GameUI class. During this interaction, DraggingButton subclasses would be dragged onto the game map, and then instances of Tower subclass would be generated on the map if the location was not occupied nor a part of the the path. This can also be applied to the Projectile class and its subclasses, in which the various subclasses extend from the Projectile base class, but are unique in how they effect enemy characters.

# References with Annotations
**[LibGDX](https://libgdx.com/)**
- Looking into LibGDX as a more capable graphics library as we are worried about the efficiency of ACM.

**[Head First Object Oriented - Analysis & 
](https://www.oreilly.com/library-access/?next=/library/view/head-first-object-oriented/0596008678/)**
- Referenced for the use case and design principles

**[Game Dev Academy](https://gamedevacademy.org/best-tower-defense-tutorials/)**
- Tutorials for making tower defense games.

**[Software Engineering](https://www.ece.rutgers.edu/~marsic/books/SE/book-SE_marsic.pdf)**
- Reference for design principles

**[JRebel.com](https://www.jrebel.com/blog/solid-principles-in-java)**
- Overview of different design principles

**[LibGDX](https://libgdx.com/assets/brand/logo.png)**
- Used LibGDX logo image

**[Plants vs Zombies](https://cdn.cloudflare.steamstatic.com/steam/apps/3590/0000008164.1920x1080.jpg?t=1615390608)**
- Used Plants vs Zombies image

**[Kingdom Rush](https://cdn.akamai.steamstatic.com/steam/apps/246420/ss_502205852350bfd8d64070adf4c38f2ffdf86772.1920x1080.jpg?t=1672337706)**
- Used Kingdom Rush image
