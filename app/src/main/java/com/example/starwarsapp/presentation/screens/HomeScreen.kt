package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.starwarsapp.presentation.navigation.Screen
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navHostController: NavHostController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerShape = CutCornerShape(bottomEnd = 50.dp),
                drawerContainerColor = BackgroundGreen,
                modifier = Modifier.border(
                    color = TextGreen,
                    width = 2.dp,
                    shape = CutCornerShape(bottomEnd = 50.dp)
                ),
                content = { DrawerContent(navHostController) }
            )
        }
    ) {
        Scaffold(
            topBar = { HomeScreenTopBar(onclick = { TODO() }) },
        ) {
            HomeScreenContent()
        }
    }
}

@Composable
fun HomeScreenContent() {
    Column(
        modifier = Modifier
            .background(BackgroundGreen)
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "All the amazing corners of the Star Wars Universe.",
            style = TextStyle(
                fontFamily = JetBrainsMono,
                fontSize = 64.sp,
                color = TextGreen
            )
        )
    }
}

@Composable
fun HomeScreenTopBar(
    onclick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp),
    ) {
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clickable { onclick() },
            tint = TextGreen,

            )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = null,
            modifier = Modifier.size(50.dp),
            tint = TextGreen
        )
    }
}

@Composable
fun DrawerContent(
    navHostController: NavHostController
) {
    val style = TextStyle(
        fontSize = 42.sp,
        fontFamily = JetBrainsMono,
        color = TextGreen
    )
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
        Text(
            text = "Films",
            style = style,
            modifier = Modifier.clickable { navHostController.navigate(Screen.FilmsScreen.route) })
        Text(text = "People", style = style)
        Text(text = "Planets", style = style)
        Text(text = "Species", style = style)
        Text(text = "Vehicles", style = style)
        Text(text = "Starships", style = style)
    }
}