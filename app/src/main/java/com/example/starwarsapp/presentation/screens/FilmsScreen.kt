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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.presentation.FilmsListCard
import com.example.starwarsapp.presentation.viewModels.FilmsViewModel
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun FilmsScreen(
    viewModel: FilmsViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
//  val scope = rememberCoroutineScope()

    val film = viewModel.getFilms.collectAsLazyPagingItems()

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
                content = { DrawerContent() }
            )
        }
    ) {
        Scaffold(
            topBar = { FilmsScreenTopBar(onclick = { TODO() }) },
        ) {
            val scroll = rememberScrollState(0)

            Column(
                modifier = Modifier
                    .padding(26.dp)
                    .clip(CutCornerShape(bottomEnd = 50.dp))
                    .background(BackgroundGreen)
                    .fillMaxSize()
                    .border(
                        color = TextGreen,
                        width = 2.dp,
                        shape = CutCornerShape(bottomEnd = 50.dp)
                    )
                    .verticalScroll(scroll),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Films",
                    style = TextStyle(
                        fontFamily = JetBrainsMono,
                        fontSize = 44.sp,
                        color = TextGreen
                    )
                )
                Spacer(modifier = Modifier.height(20.dp))
                LazyColumn(
                    Modifier.padding(16.dp)
                ) {
                    items(
                        count = film.itemCount,
                        key = film.itemKey { it.title },
                        contentType = film.itemContentType()
                    ) {index ->
                        val data = film[index]
                        data?.let { FilmsListCard(film = it, onClick = {}) }
                    }
                }
            }
        }
    }
}


@Composable
fun FilmsScreenTopBar(
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
    }
}

//@Composable
//fun FilmsScreenContent(
//    films: FilmsEntity
//) {
//    Column(
//        modifier = Modifier
//            .padding(26.dp)
//            .clip(CutCornerShape(bottomEnd = 50.dp))
//            .background(BackgroundGreen)
//            .fillMaxSize()
//            .border(
//                color = TextGreen,
//                width = 2.dp,
//                shape = CutCornerShape(bottomEnd = 50.dp)
//            ),
//        verticalArrangement = Arrangement.Bottom
//    ) {
//        Text(
//            text = "Films",
//            style = TextStyle(
//                fontFamily = JetBrainsMono,
//                fontSize = 44.sp,
//                color = TextGreen
//            )
//        )
//        Spacer(modifier = Modifier.height(20.dp))
//        Text(text = films.title)
//    }
//}


