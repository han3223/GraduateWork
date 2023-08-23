package com.example.documentsearch

import android.graphics.Bitmap
import android.graphics.BlendMode
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.graphics.applyCanvas

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Scaffold(
                bottomBar = { CustomNavbar() },
                content = { padding -> // We have to pass the scaffold inner padding to our content. That's why we use Box.
                    Box(modifier = Modifier.padding(padding)) {
                        /* Add code later */
                    }
                },
            )
        }
    }
}



@Composable
fun CustomNavbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Box(
            modifier = Modifier
                .weight(0.2f)
        ) {
            Image(
                colorFilter =  ColorFilter.tint(Color.Red, androidx.compose.ui.graphics.BlendMode.SrcIn),
                painter = painterResource(id = R.drawable.add_scientific_articles_focused),
                contentScale = ContentScale.FillWidth,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth().border(2.dp, Color.Blue, MaterialTheme.shapes.small)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(0.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.page_white),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
        ) {
            Image(
                colorFilter =  ColorFilter.tint(Color.Red, androidx.compose.ui.graphics.BlendMode.SrcIn),
                painter = painterResource(id = R.drawable.messanger),
                contentScale = ContentScale.FillWidth,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(0.dp, 30.dp, 25.dp, 0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.message_white),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White

                )
            }
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.scientific_supervisor),
                contentScale = ContentScale.FillWidth,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(25.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add_user_white),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
        Box(
            modifier = Modifier
                .weight(0.2f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentScale = ContentScale.FillWidth,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(),
                colorFilter = ColorFilter.tint(Color.Blue)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(0.dp, 30.dp, 0.dp, 0.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.profile_white),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        }
    }
}
