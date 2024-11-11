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
import kotlin.math.log10
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.random.Random

val colors =
    listOf(Color.Black, Color.Blue, Color.Cyan, Color.Red, Color.Green, Color.Magenta, Color.White, Color.Yellow)

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    val font by remember { mutableStateOf(FontFamily(Font(R.font.comic))) }
    val italicFont by remember { mutableStateOf(FontFamily(Font(R.font.parkavenue))) }
    val bpmFont by remember { mutableStateOf(FontFamily(Font(R.font.digital7_mono))) }
    var backgroundColor by remember { mutableStateOf(colors.random()) }
    val textOnBackgroundColor = if (backgroundColor.luminance() > 0.5f) Color.Black else Color.White
    var buttonColor by remember { mutableStateOf(colors.filter { it != backgroundColor }.random()) }
    val buttonTextColor = if (buttonColor.luminance() > 0.5f) Color.Black else Color.White
    var times by remember { mutableStateOf(emptyList<Long>()) }

    val bpm =
        if (times.size >= 2) {
            val diff = (times.last() - times.first()).toFloat()
            (((times.size - 1) / diff) * 60_000).roundToInt()
        } else 0

    val builder = AnnotatedString.Builder(text = bpm.toString().padStart(4, '0'))
    if (bpm < 1000) {
        builder.addStyle(
            style = SpanStyle(color = Color(0xFF008000)),
            start = 0,
            end = 3 - max(log10(bpm.toFloat()).toInt(), 0)
        )
    }

    Column(
        modifier = modifier.background(backgroundColor).padding(40.dp),
        verticalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = "BPM Recognising Fucker©®™",
            color = textOnBackgroundColor,
            fontFamily = font,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Black,
        )

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = builder.toAnnotatedString(),
                color = Color.Green,
                fontFamily = bpmFont,
                modifier = Modifier.background(Color.Black).padding(20.dp),
                fontSize = 50.sp,
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
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.0f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            val currentTime = System.currentTimeMillis()
                            if (times.isNotEmpty() && currentTime - times.last() > 5000) times = emptyList()
                            times = times.toMutableList().apply { add(System.currentTimeMillis()) }
                            backgroundColor = colors.filter { it != backgroundColor }.random()
                            buttonColor = colors.filter { it != backgroundColor && it != buttonColor }.random()
                        }
                    )
                },
            shape = CircleShape,
            colors = CardDefaults.elevatedCardColors(
                containerColor = buttonColor,
                contentColor = buttonColor,
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 50.dp,
            ),
        ) {
            Row(modifier = Modifier.fillMaxHeight().align(Alignment.CenterHorizontally)) {
                Text(
                    text = "HIT ME",
                    modifier = Modifier.align(Alignment.CenterVertically).rotate(Random.nextFloat() * 360),
                    color = buttonTextColor,
                    fontFamily = font,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Black,
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
