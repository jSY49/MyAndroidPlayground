package com.example.apod.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.apod.Navigation.NasaDestination
import com.example.apod.Navigation.bottomNavItems
import com.example.apod.ui.theme.mainColor
import com.example.apod.ui.theme.nav_bar
import com.example.apod.ui.theme.selected_icon
import com.example.apod.ui.theme.unselected_icon

@Composable
fun MyNasaExplorerApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            val navBackSet by navController.currentBackStackEntryAsState()  // 화면이 바뀌면 값이 갱신됨
            val currentRoute = navBackSet?.destination?.route

            NavigationBar(
                containerColor = nav_bar,
            ) { //화면 하단에 있는 탭 바 자체를 그리는 컴포넌트.
                bottomNavItems.forEach { destination ->
                    NavigationBarItem(
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painterResource(id = destination.icon),
                                contentDescription = destination.label,
                                tint = if (currentRoute == destination.route) selected_icon else unselected_icon
                            )
                        },
                        label = {
                            Text(
                                text = destination.label,
                                color = if (currentRoute == destination.route) selected_icon else unselected_icon,
                                fontWeight = if (currentRoute == destination.route) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }

        }
    ) { innerPadding ->
        NavHost(    // 실제 화면 콘텐츠가 바뀌는 영역
            navController = navController,
            startDestination = NasaDestination.Apod.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            //이 route 일 때 이 컴포저블 보여줘 - 매핑
            composable(NasaDestination.Apod.route) { ApodScreen() }
            composable(NasaDestination.Asteroids.route) { AsteroidScreen() }
            composable(NasaDestination.Iss.route) { IssScreen() }
        }
    }
}


@Preview(name = "Phone", device = "spec:width=360dp,height=800dp")
@Preview(name = "Phone_flip", device = "spec:width=360dp,height=880dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
@Composable
fun MyNasaExplorerAppPreview() {
    MyNasaExplorerApp()
}