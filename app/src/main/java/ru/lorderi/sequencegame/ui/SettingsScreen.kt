package ru.lorderi.sequencegame.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lorderi.sequencegame.plate.PlateUiState
import ru.lorderi.sequencegame.plate.SoundThemesTypes
import ru.lorderi.sequencegame.ui.util.getFormattedLabel
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel
import kotlin.math.roundToInt

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    platesViewModel: PlatesViewModel = viewModel(),
) {
    val plate by platesViewModel.uiState.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .padding(all = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { platesViewModel.changeSound() }) {
            Text(text = "Sound: ${getFormattedLabel(plate.sound)}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        var sliderPosition by remember { mutableFloatStateOf(plate.delayMilliseconds.toFloat()) }

        Text(
            text = "Delay between sounds (ms): ${sliderPosition.roundToInt()}"
        )

        Slider(
            value = sliderPosition,
            valueRange = 100f..1000f,
            steps = 8,
            onValueChange = {
                sliderPosition = it
                platesViewModel.changeDelay(it.toLong())
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Voices themes")

        VoicesExposedDropdownMenuBox(platesViewModel, plate)

        Button(onClick = { platesViewModel.changeHighlight() }) {
            Text(text = "Button highlight: ${getFormattedLabel(plate.highlight)}")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { platesViewModel.changeHardMode() }) {
            Text(text = "Hardmode: ${getFormattedLabel(plate.hardMode)}")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoicesExposedDropdownMenuBox(
    platesViewModel: PlatesViewModel,
    plate: PlateUiState,
) {
    val voicesTypes = arrayOf("Animals", "Piano")
    var expanded by remember { mutableStateOf(false) }
    val indexOfTheme = when (plate.soundThemes) {
        SoundThemesTypes.Animals -> {
            0
        }

        SoundThemesTypes.Piano -> {
            1
        }
    }
    var selectedText by remember { mutableStateOf(voicesTypes[indexOfTheme]) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                voicesTypes.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            expanded = false

                            if (item == voicesTypes[0]) {
                                platesViewModel.changeTheme(SoundThemesTypes.Animals)
                            } else if (item == voicesTypes[1]) {
                                platesViewModel.changeTheme(SoundThemesTypes.Piano)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "id:pixel_3a"
)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(
        platesViewModel = PlatesViewModel()
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true,
    device = "id:pixel_3a"
)
@Composable
fun SettingsScreenPreviewDark() {
    SettingsScreen(
        platesViewModel = PlatesViewModel()
    )
}
