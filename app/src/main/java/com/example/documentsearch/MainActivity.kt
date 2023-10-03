package com.example.documentsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.documentsearch.dataClasses.Profile
import com.example.documentsearch.navbar.NavigationScreens
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class MainActivity : ComponentActivity() {
    private lateinit var onBackPressedCallback: OnBackPressedCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val systemUiController = rememberSystemUiController()
            SideEffect {
                systemUiController.setStatusBarColor(color = Color.Transparent)
            }

            // Контроллер для навигации
            val navController = rememberNavController()

            // Обработчик back жеста
            onBackPressedCallback = object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigateUp()
                }
            }
            onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

            // TODO(Пользователь должен быть получен из базы данных)
            val profile = Profile(
                fullName = "Важенин Иван Анатольевич",
                personalName = "IVazhen",
                personalInfo = "Я занимаю разработкой программного обеспечения для андроид устройств, а также увлекаюсь квантовой физикой.",
                numberPhone = "79116172604",
                email = "ivan.vazhenin34@gmail.com",
                password = "qwerty123"
            )

            // TODO(Документы должны быть получены из базы данных)
            val listDocumets = listDocumet

            // TODO(Теги должны быть получены из базы данных)
            val tags = tags

            NavigationScreens(
                navController = navController,
                profile = profile,
                listDocuments = listDocumets,
                tagsDocumentation = tags,
                tagsProfile = tags
            )
        }
    }
}
