package com.aiglepub.matrixload.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun MatrixRain(stripCount: Int = 25) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "MATRIX",
            color = Color(0xffcefbe4),
            style = TextStyle(
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
        )
        Row {
            repeat(stripCount) {
                MatrixColumn(crawlSpeed = (Random.nextInt(10) * 10L) + 10,
                    yStartDelay = Random.nextInt(8) * 1000L)
            }
        }
    }
}

@Composable
fun RowScope.MatrixColumn(crawlSpeed: Long, yStartDelay: Long) {
    BoxWithConstraints(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
    ) {
        val matrixStrip = remember { Array((maxHeight / maxWidth).toInt()) { characters.random()} }
        var lettersToDraw by remember { mutableStateOf(0) }

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            repeat(lettersToDraw) {
                MatrixChar(
                    char = matrixStrip[it],
                    crawlSpeed = crawlSpeed,
                    onFinished = {
                        if(it>= matrixStrip.size * 0.6) {
                            lettersToDraw = 0
                        }
                    }
                )
            }
        }

        LaunchedEffect(Unit) {
            delay(yStartDelay)
            while (true) {
                if(lettersToDraw < matrixStrip.size) {
                    lettersToDraw += 1
                }
                if(lettersToDraw > matrixStrip.size * 0.5) {
                    matrixStrip[Random.nextInt(lettersToDraw)] = characters.random()
                }
                delay(crawlSpeed)
            }
        }
    }
    }




@Composable
fun MatrixChar(char: String, crawlSpeed: Long, onFinished: () -> Unit) {
    var textColor by remember { mutableStateOf(Color(0xffcefbe4)) }
    var startFade by remember { mutableStateOf(false) }
    val alpha by animateFloatAsState(
        targetValue = if(startFade) 0f else 1f,
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearEasing
        ),
        finishedListener = { onFinished() }
    )

    Text(text= char, color = textColor.copy(alpha = alpha))

    LaunchedEffect(Unit) {
        delay(crawlSpeed)
        textColor = Color(0xff43c728)
        startFade = true
    }
}

private val characters = listOf(
    "ジ",
    "ェ",
    "ッ",
    "ト",
    "パ",
    "Z",
    "A",
    "R",
    "Q",
    "ッ",
    "ク",
    "構",
    "成",
    "I",
    "L",
    "N",
    "K",
    "8",
    "7",
    "C",
    "6"
)