package ru.lorderi.sequencegame.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.lorderi.sequencegame.viewmodel.PlatesViewModel

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
    platesViewModel: PlatesViewModel = viewModel(),
) {
    val plate by platesViewModel.uiState.collectAsState()

    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Record: ${plate.record}", modifier = Modifier.padding(bottom = 16.dp))

        Text(
            text = "Sound: ${getEnableLabel(plate.sound)}",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Delay: ${plate.delayMilliseconds}",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = "Voices: ${plate.soundThemes}", modifier = Modifier.padding(bottom = 16.dp))

        Text(
            text = "Button highlight: ${getEnableLabel(plate.highlight)}",
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(text = "Hardmode: ${getEnableLabel(plate.hardMode)}")
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "About the author:\nlorderi 2024", textAlign = TextAlign.Center)
    }
}

fun getEnableLabel(state: Boolean): String {
    val value = if (state) {
        "ON"
    } else {
        "OFF"
    }
    return value
}

@Preview(
    showSystemUi = true, showBackground = true,
    device = "id:pixel_3a"
)
@Composable
fun AboutScreenPreview() {
    AboutScreen(
        platesViewModel = PlatesViewModel()
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true, showBackground = true,
    device = "id:pixel_3a"
)
@Composable
fun AboutScreenPreviewDark() {
    AboutScreen(
        platesViewModel = PlatesViewModel()
    )
}

