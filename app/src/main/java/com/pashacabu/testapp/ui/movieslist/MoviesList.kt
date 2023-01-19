package com.pashacabu.testapp.ui.movieslist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import timber.log.Timber

@Composable
fun MoviesList(
    modifier: Modifier = Modifier,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .border(color = Color.Red, width = 1.dp)
        ) {
            this.items(viewModel.movies.value.movies) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(3 / 2f)
                        .padding(8.dp)
                        .background(color = Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp)
                ) {
                    it?.let {
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
        Button(
            onClick = {viewModel.loadMovies()}, modifier = Modifier
                .fillMaxWidth()
                .width(25.dp)
        ) {
            Text(text = "Get movies")
        }
    }

}