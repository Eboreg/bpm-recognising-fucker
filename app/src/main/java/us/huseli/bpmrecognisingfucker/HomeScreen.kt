package us.huseli.bpmrecognisingfucker

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import us.huseli.bpmrecognisingfucker.ui.theme.BPMRecognisingFuckerTheme
import java.util.Locale
import kotlin.math.log10
import kotlin.math.max
import kotlin.random.Random

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val colors = remember {
        listOf(
            Color.Black,
            Color.Blue,
            Color.Cyan,
            Color.Red,
            Color.Green,
            Color.Magenta,
            Color.White,
            Color.Yellow,
        )
    }
    val font = remember { FontFamily(Font(R.font.comic)) }
    val italicFont = remember { FontFamily(Font(R.font.parkavenue)) }
    val bpmFont = remember { FontFamily(Font(R.font.digital7_mono)) }
    var backgroundColor by remember { mutableStateOf(colors.random()) }
    val textOnBackgroundColor =
        remember(backgroundColor) { if (backgroundColor.luminance() > 0.5f) Color.Black else Color.White }
    var buttonColor by remember { mutableStateOf(colors.filter { it != backgroundColor }.random()) }
    val buttonTextColor =
        remember(buttonColor) { if (buttonColor.luminance() > 0.5f) Color.Black else Color.White }
    var times by remember { mutableStateOf(emptyList<Long>()) }
    val bpm = remember(times) {
        if (times.size >= 2) {
            val diff = (times.last() - times.first()).toFloat()
            ((times.size - 1) / diff) * 60_000
        } else 0f
    }
    val builder = AnnotatedString.Builder(
        text = String.format(Locale.getDefault(), "%.2f", bpm).padStart(7, '0')
    )

    if (bpm < 1000) {
        builder.addStyle(
            style = SpanStyle(color = Color(0xFF008000)),
            start = 0,
            end = 3 - max(log10(bpm).toInt(), 0),
        )
    }

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .background(backgroundColor)
            .fillMaxHeight()
            .padding(40.dp)
    ) {
        Text(
            text = "BPM Recognising Fucker©®™",
            color = textOnBackgroundColor,
            fontFamily = font,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        ) {
            Text(
                text = builder.toAnnotatedString(),
                color = Color.Green,
                fontFamily = bpmFont,
                fontSize = 50.sp,
                modifier = Modifier
                    .background(Color.Black)
                    .padding(20.dp)
            )
            Text(
                text = " bpm",
                color = textOnBackgroundColor,
                fontFamily = italicFont,
                fontSize = 30.sp,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        ElevatedCard(
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(
                containerColor = buttonColor,
                contentColor = buttonColor,
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 50.dp,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            val currentTime = System.currentTimeMillis()
                            if (times.isNotEmpty() && currentTime - times.last() > 5000) {
                                times = emptyList()
                            }
                            times = times
                                .toMutableList()
                                .apply { add(System.currentTimeMillis()) }
                            backgroundColor = colors
                                .filter { it != backgroundColor }
                                .random()
                            buttonColor = colors
                                .filter { it != backgroundColor && it != buttonColor }
                                .random()
                        }
                    )
                }
                .padding(bottom = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "HIT ME",
                    color = buttonTextColor,
                    fontFamily = font,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .rotate(Random.nextFloat() * 360)
                )
            }
        }

        Button(
            onClick = { times = emptyList() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "RESET",
                fontFamily = font,
                fontWeight = FontWeight.Black,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    BPMRecognisingFuckerTheme {
        HomeScreen()
    }
}
