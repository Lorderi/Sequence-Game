package ru.lorderi.sequencegame.ui

import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lorderi.sequencegame.R
import ru.lorderi.sequencegame.plate.FIRST_PLATE
import ru.lorderi.sequencegame.plate.FOURTH_PLATE
import ru.lorderi.sequencegame.plate.InputMode
import ru.lorderi.sequencegame.plate.PlateUiState
import ru.lorderi.sequencegame.plate.SECOND_PLATE
import ru.lorderi.sequencegame.plate.SoundThemesTypes
import ru.lorderi.sequencegame.plate.THIRD_PLATE
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel

@Composable
fun PlayScreen(
    modifier: Modifier = Modifier,
    freePlay: Boolean,
    platesViewModel: PlatesViewModel = viewModel(),
) {
    val ANIMALS_VOICES = listOf(R.raw.bird, R.raw.cat_meow, R.raw.cow, R.raw.horse)
    val NOTE_VOICES =
        listOf(R.raw.chord_decay, R.raw.note_long, R.raw.chord_major, R.raw.note_upright)
    val context = LocalContext.current

    val plate = platesViewModel.uiState.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .padding(all = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!freePlay) {
            Spacer(modifier = Modifier.height(32.dp))
            Row {
                Text(text = "Level: ${plate.value.level}")
                Spacer(modifier = Modifier.width(32.dp))
                Text(text = "Record: ${plate.value.record}")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Starting at: ${plate.value.level}")

            Spacer(modifier = Modifier.height(32.dp))
        }

        val voices: List<Int> = when (plate.value.soundThemes) {
            SoundThemesTypes.Animals -> {
                ANIMALS_VOICES
            }

            SoundThemesTypes.Piano -> {
                NOTE_VOICES
            }
        }

        val players =
            listOf(
                MediaPlayer.create(
                    context,
                    voices[FIRST_PLATE]
                ),
                MediaPlayer.create(
                    context,
                    voices[SECOND_PLATE]
                ),
                MediaPlayer.create(
                    context,
                    voices[THIRD_PLATE]
                ),
                MediaPlayer.create(context, voices[FOURTH_PLATE])
            )

        Row {
            PlayerButton(color = 0xFFFFC0CB, text = "1") {
                val inputMode = plate.value.inputMode
                sendClick(inputMode, platesViewModel, FIRST_PLATE)
                playSound(plate.value, players, FIRST_PLATE)
            }

            Spacer(modifier = Modifier.width(16.dp))

            PlayerButton(color = 0xFFFFC65E, text = "2") {
                val inputMode = plate.value.inputMode
                sendClick(inputMode, platesViewModel, SECOND_PLATE)
                playSound(plate.value, players, SECOND_PLATE)
            }

        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            PlayerButton(color = 0xFF8EAEE8, text = "3") {
                val inputMode = plate.value.inputMode
                sendClick(inputMode, platesViewModel, THIRD_PLATE)
                playSound(plate.value, players, THIRD_PLATE)
            }

            Spacer(modifier = Modifier.width(16.dp))

            PlayerButton(color = 0xFF84EC84, text = "4") {
                val inputMode = plate.value.inputMode
                sendClick(inputMode, platesViewModel, FOURTH_PLATE)
                playSound(plate.value, players, FOURTH_PLATE)
            }
        }
    }
}

@Composable
fun PlayerButton(
    color: Long,
    text: String,
    onClick: () -> Unit,
) {
    Button(modifier = Modifier
        .height(160.dp)
        .width(160.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(color)),
        shape = RectangleShape,
        onClick = {
            onClick()
        }) {
        Text(text = text)
    }
}

fun playSound(plateUiState: PlateUiState, players: List<MediaPlayer>, numOfPlate: Int) {
    if (plateUiState.sound) {
        players[numOfPlate].seekTo(0)
        players[numOfPlate].start()
    }
}

fun sendClick(
    inputMode: InputMode,
    platesViewModel: PlatesViewModel,
    numOfPlate: Int,
) {
    if (inputMode == InputMode.Bot) {
        platesViewModel.addNumToBot(numOfPlate)
    } else if (inputMode == InputMode.Player) {
        platesViewModel.addNumToPlayer(numOfPlate)
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "id:pixel_3a"
)
@Composable
fun PlayScreenPreview() {
    PlayScreen(
        freePlay = false,
        platesViewModel = PlatesViewModel()
    )
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "id:pixel_3a"
)
@Composable
fun PlayFreeScreenPreview() {
    PlayScreen(
        freePlay = true,
        platesViewModel = PlatesViewModel()
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true,
    device = "id:pixel_3a"
)
@Composable
fun PlayScreenPreviewDark() {
    PlayScreen(
        freePlay = true,
        platesViewModel = PlatesViewModel()
    )
}