package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CutCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.navigation.Screen
import com.example.starwarsapp.presentation.viewModels.PeopleListCard
import com.example.starwarsapp.presentation.viewModels.PeopleViewModel
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PeopleScreen(
    navHostController: NavHostController,
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val people = viewModel.getPeople.collectAsLazyPagingItems()

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
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGreen),
            topBar = { PeopleScreenTopBar {} },
        ) {
            Column(
                modifier = Modifier
                    .background(BackgroundGreen)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.layoutModifiers()
                ) {
                    Text(
                        text = "People",
                        style = TextStyle(
                            fontFamily = JetBrainsMono,
                            fontSize = 44.sp,
                            color = TextGreen
                        ),
                        modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyColumn(
                        Modifier.padding(16.dp)
                    ) {
                        items(
                            count = people.itemCount,
                            key = people.itemKey { it.name },
                            contentType = people.itemContentType()
                        ) { index ->
                            val data = people[index]
                            data?.let {
                                PeopleListCard(
                                    people = it,
                                    onClick = {
                                        navHostController.navigate(
                                            Screen.FilmDetailScreen.route + "/${data.name}"
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PeopleScreenTopBar(
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