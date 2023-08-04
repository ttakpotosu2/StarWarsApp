package com.example.starwarsapp.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.starwarsapp.presentation.ui.theme.BackgroundGreen
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import kotlinx.coroutines.launch

@Composable
fun NavScreen() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = CutCornerShape(bottomEnd = 50.dp),
                drawerContainerColor = BackgroundGreen,
                modifier = Modifier.border(
                    color = TextGreen,
                    width = 2.dp,
                    shape = CutCornerShape(bottomEnd = 50.dp)
                ),
                content = { DrawerContent(navController, drawerState) }
            )
        },drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(BackgroundGreen)
                        .padding(16.dp),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                if (drawerState.isClosed) {
                                    coroutineScope.launch { drawerState.open() }
                                } else {
                                    coroutineScope.launch { drawerState.close() }
                                }
                            },
                        tint = TextGreen,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
                NavGraph(navHostController = navController)
            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavHostController,
    drawerState: DrawerState
) {
    val style = TextStyle(
        fontSize = 42.sp,
        fontFamily = JetBrainsMono,
        color = TextGreen
    )
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .width(500.dp)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Where do you want to start?", style = TextStyle(
                fontSize = 50.sp,
                fontFamily = JetBrainsMono,
                color = TextGreen
            )
        )
        Spacer(modifier = Modifier.height(50.dp))
        navScreenItems.forEach { item ->
            NavigationDrawerItem(
                label = { Text(text = item.label, style = style) },
                selected = false,
                onClick = {
                    navController.navigate(item.route, navOptions {
                        this.launchSingleTop = true
                        this.restoreState = true
                    })
                    scope.launch { drawerState.close() }
                },
                colors = NavigationDrawerItemDefaults.colors(
                    unselectedContainerColor = Color.Transparent
                )
            )
        }
    }
}