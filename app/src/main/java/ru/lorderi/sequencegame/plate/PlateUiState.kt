package ru.lorderi.sequencegame.plate

const val FIRST_PLATE = 0
const val SECOND_PLATE = 1
const val THIRD_PLATE = 2
const val FOURTH_PLATE = 3

enum class SoundThemesTypes {
    Animals,
    Piano,
}

enum class InputMode {
    Bot,
    Player,
}

data class PlateUiState(
    val record: Int = 0,
    val level: Int = 0,
    val inputMode: InputMode = InputMode.Player,
    val sound: Boolean = true,
    val delayMilliseconds: Long = 1_000,
    val highlight: Boolean = true,
    val hardMode: Boolean = false,
    val soundThemes: SoundThemesTypes = SoundThemesTypes.Animals,
)

data class Sequences(
    val playerSequence: MutableList<Int> = mutableListOf(),
    val botSequence: MutableList<Int> = mutableListOf(),
    val edited: Boolean = true,
)
