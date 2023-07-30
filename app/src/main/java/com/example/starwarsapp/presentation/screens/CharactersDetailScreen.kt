package com.example.starwarsapp.presentation.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.example.starwarsapp.domain.models.PeopleEntity
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.viewModels.PeopleViewModel
import com.example.starwarsapp.ui.theme.BackgroundGreen
import com.example.starwarsapp.ui.theme.JetBrainsMono
import com.example.starwarsapp.ui.theme.TextGreen
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharactersDetailScreen(
    navHostController: NavHostController,
    viewModel: PeopleViewModel = hiltViewModel()
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val people = viewModel.getPeople.collectAsLazyPagingItems()

    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet(drawerShape = CutCornerShape(bottomEnd = 50.dp),
            drawerContainerColor = BackgroundGreen,
            modifier = Modifier.border(
                color = TextGreen, width = 2.dp, shape = CutCornerShape(bottomEnd = 50.dp)
            ),
            content = { DrawerContent(navHostController) })
    }) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundGreen),
            topBar = { FilmsScreenTopBar(onclick = { }) },
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
                        text = "Characters", style = TextStyle(
                            fontFamily = JetBrainsMono, fontSize = 44.sp, color = TextGreen
                        ), modifier = Modifier.padding(16.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyHorizontalStaggeredGrid(
                        rows = StaggeredGridCells.Adaptive(100.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalItemSpacing = 8.dp,
                        contentPadding = PaddingValues(8.dp)
                    ) {
                        items(
                            count = people.itemCount,
                            key = people.itemKey { it.name },
                            contentType = people.itemContentType()
                        ) { index ->
                            val data = people[index]
                            data?.let {
                                CharactersDetailCard(person = it) {

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharactersDetailCard(
    person: PeopleEntity, onItemClick: () -> Unit
) {
    val style = TextStyle(
        fontFamily = JetBrainsMono, fontSize = 20.sp, color = TextGreen
    )
    Column(
        modifier = Modifier
            .border(
                color = TextGreen, width = 2.dp, shape = CutCornerShape(bottomEnd = 10.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onItemClick() },
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = "Name: " + person.name, style = style)
        Text(text = "Date of Birth: " + person.birthYear, style = style)
        Text(
            text = "Gender: " + person.gender.replaceFirstChar {
                if (it.isLowerCase()) {
                    it.titlecase(Locale.getDefault())
                } else {
                    it.toString()
                }
            },
            style = style
        )
    }
}