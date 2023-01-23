package com.pashacabu.testapp.ui.movieslist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pashacabu.testapp.ui.components.pulllist.PullList

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadMovies()
    }

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            PullList(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                loadingState = viewModel.loading,
                indicator = {
                    val animation = rememberInfiniteTransition()
                    val alpha by animation.animateFloat(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(),
                            repeatMode = RepeatMode.Reverse
                        )
                    )
                    Text(
                        text = "Loading...",
                        modifier = Modifier.graphicsLayer { this.alpha = alpha })
                },
                onlyRefresh = viewModel.refreshOnly,
                topAction = { viewModel.loadMovies(reload = true) },
                bottomAction = { viewModel.loadMovies() }
            ) {
                val state = rememberLazyGridState()
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    this.items(viewModel.movies.value) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .padding(8.dp)
                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(8.dp)
                        ) {
                            it.let {
                                it.name?.let { name ->
                                    Text(
                                        text = name,
                                        modifier = Modifier.align(Alignment.TopStart),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                                it.year?.let { year ->
                                    Text(
                                        text = year.toString(),
                                        modifier = Modifier.align(Alignment.TopEnd),
                                        fontWeight = FontWeight.Bold,
                                        color = Color.DarkGray
                                    )
                                }
                            }

                        }
                    }
                }
//                LazyColumn(Modifier.fillMaxWidth()) {
//                    this.items(viewModel.movies.value) {
//                        Box(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .aspectRatio(1f)
//                                .padding(8.dp)
//                                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
//                                .border(
//                                    width = 1.dp,
//                                    color = Color.LightGray,
//                                    shape = RoundedCornerShape(8.dp)
//                                )
//                                .padding(8.dp)
//                        ) {
//                            it.let {
//                                it.name?.let { name ->
//                                    Text(
//                                        text = name,
//                                        modifier = Modifier.align(Alignment.TopStart),
//                                        fontWeight = FontWeight.Bold,
//                                        color = Color.Black
//                                    )
//                                }
//                                it.year?.let { year ->
//                                    Text(
//                                        text = year.toString(),
//                                        modifier = Modifier.align(Alignment.TopEnd),
//                                        fontWeight = FontWeight.Bold,
//                                        color = Color.DarkGray
//                                    )
//                                }
//                            }
//
//                        }
//                    }
//                }
            }
            if (viewModel.searchEnabled.value) {
                Box(modifier = Modifier.background(Color.White)) {
                    TextField(
                        value = viewModel.searchField.collectAsState().value,
                        onValueChange = {
                            viewModel.updateSearchField(it)
                            viewModel.loading.value
                        },
                        singleLine = true,
                        placeholder = {
                            Text(text = "Input movie name to look for")
                        },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Search",
                                tint = Color.Red,
                                modifier = Modifier.clickable {
                                    viewModel.enableSearch(false)
                                }
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = !viewModel.searchEnabled.value,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .fillMaxWidth(0.15f)
                .aspectRatio(1f),
            enter = slideInVertically(animationSpec = spring()) { -it } + fadeIn(),
            exit = slideOutVertically(animationSpec = spring()) { -it } + fadeOut()
        ) {
            Button(
                modifier = Modifier.fillMaxSize(),
                onClick = { viewModel.enableSearch(true) },
                shape = CircleShape,
                colors = ButtonDefaults.outlinedButtonColors(
                    backgroundColor = Color.LightGray
                ),
                border = BorderStroke(width = 1.dp, color = Color.DarkGray)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.Red
                )
            }
        }
    }

}