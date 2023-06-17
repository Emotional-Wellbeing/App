package es.upm.bienestaremocional.ui.component.text

import androidx.compose.ui.text.AnnotatedString

data class LinkTextData(
    val text: String,
    val tag: String? = null,
    val annotation: String? = null,
    val onClick: ((AnnotatedString.Range<String>) -> Unit)? = null,
)