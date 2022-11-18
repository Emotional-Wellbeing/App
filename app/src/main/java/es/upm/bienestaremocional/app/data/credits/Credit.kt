package es.upm.bienestaremocional.app.data.credits

data class Credit(
    val nameResource: Int,
    val descriptionResource: Int,
    val importantContribution : Boolean = true
)