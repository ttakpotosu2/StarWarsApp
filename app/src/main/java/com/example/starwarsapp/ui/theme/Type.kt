package com.example.starwarsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.starwarsapp.R

// Set of Material typography styles to start with
val JetBrainsMono = FontFamily(
    Font(R.font.jet_brains_mono_bold, FontWeight.Bold),
    Font(R.font.jet_brains_mono_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.jet_brains_mono_extra_bold, FontWeight.ExtraBold),
    Font(R.font.jet_brains_mono_extra_bold_italic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.jet_brains_mono_extra_light, FontWeight.ExtraLight),
    Font(R.font.jet_brains_mono_extra_light_italic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.jet_brains_mono_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.jet_brains_mono_light, FontWeight.Light),
    Font(R.font.jet_brains_mono_light_italic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.jet_brains_mono_medium, FontWeight.Medium),
    Font(R.font.jet_brains_mono_medium_italic, FontWeight.Medium, FontStyle.Italic),
)