package com.example.starwarsapp.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.starwarsapp.presentation.VehiclesStates
import com.example.starwarsapp.presentation.layoutModifiers
import com.example.starwarsapp.presentation.ui.theme.JetBrainsMono
import com.example.starwarsapp.presentation.ui.theme.TextGreen
import com.example.starwarsapp.presentation.viewModels.VehicleViewModel

@Composable
fun VehicleDetailScreen(
    viewModel: VehicleViewModel = hiltViewModel()
) {
    when (val vehicles = viewModel.vehicle.value) {
        is VehiclesStates.Success -> {
            Column(
                modifier = Modifier.layoutModifiers().fillMaxSize()
            ) {
                val style = TextStyle(
                    fontFamily = JetBrainsMono,
                    fontSize = 20.sp,
                    color = TextGreen
                )
                Text(
                    text = vehicles.data.vehicle.name,
                    style = TextStyle(
                        fontFamily = JetBrainsMono,
                        fontSize = 44.sp,
                        color = TextGreen
                    ),
                    modifier = Modifier.padding(top = 16.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(text = "Name: " + vehicles.data.vehicle.name, style = style)
                    Text(text = "Model: " + vehicles.data.vehicle.model, style = style)
                    Text(text = "Manufacturer: " + vehicles.data.vehicle.manufacturer, style = style)
                    Text(text = "Length: " + vehicles.data.vehicle.length, style = style)
                    Text(text = "Max Speed: " + vehicles.data.vehicle.maxAtmospheringSpeed, style = style)
                    Text(text = "Crew: " + vehicles.data.vehicle.crew, style = style)
                    Text(text = "Cargo Capacity: " + vehicles.data.vehicle.cargoCapacity, style = style)
                    Text(text = "Consumables: " + vehicles.data.vehicle.consumables, style = style)
                    Text(text = "Passengers: " + vehicles.data.vehicle.passengers, style = style)
                }
            }
        }
        is VehiclesStates.Loading -> {
            CircularProgressIndicator(
                color = TextGreen,
                modifier = Modifier.size(150.dp)
            )
        }
    }
}