# Android Memory Game

This Android application is a memory game where players must repeat a sequence of sounds played by
the app. The game consists of multiple levels, each adding a new sound to the sequence. The project
is developed using Kotlin and incorporates ViewModel, ViewBinding, and Jetpack Compose for UI
development in a separate branch.

## Technologies Used

- Kotlin
- ViewModel
- ViewBinding
- Jetpack Compose (in development Compose branch)

## Basic Game

- The main screen displays four buttons, each representing a sound.
- The app plays a sequence of sounds with delays, highlighting the corresponding buttons.
- The user must repeat the sequence by pressing the buttons in the same order.
- Levels increase the length of the sequence.
- Incorrectly pressing a button results in a game loss.
- The top result is saved using shared preferences.

  <img src="record/new_game_record.gif" alt="Basic game" width="300">

## Free Play

- Added "Free Play" mode accessible from the menu.
- In Free Play mode, sequences are not played, and there's no level counter.

  <img src="record/free_game.png" alt="Free Play" width="300">

## Menu

- Added a menu with options for "New Game", "Free play", "Settings", "About".

  <img src="record/main_screen.png" alt="Menu" width="300">

- "New Game" launches the main game.
- "Settings" menu item with options for sound toggle, delay adjustment, disable/enable button
  highlight effect during gameplay, ability to change sound themes in settings.
- Sound can be turned on/off.
- Delay between sounds can be adjusted using a slider.

  <img src="record/settings_screen.png" alt="Settings" width="300">

- "About" displays information about the game and the author.

  <img src="record/about_screen.png" alt="About" width="300">

## Branches

- **master**: Contains the main game logic and UI using ViewBinding.
- **compose**: In development, uses Jetpack Compose for UI development.
