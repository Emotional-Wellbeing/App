package es.upm.bienestaremocional.app.data.credits

/**
 * Class to store Credit info
 * @param nameResource : Int pointer to string resource that contains the name of the Author
 * @param descriptionResource : Int pointer to string resource thatcontains the contribution
 * of the Author
 * @param importantContribution : Boolean used for classification at render (important people
 * are showed first)
 * @see CreditContent
 */
data class Credit(
    val nameResource: Int,
    val descriptionResource: Int,
    val importantContribution : Boolean = true
)