package es.upm.bienestaremocional.data.credits

import es.upm.bienestaremocional.R

/**
 * Singleton that contains a list of all credits of the app, loaded from strings.xml
 * @see Credit
 */
object CreditContent {
    val content = listOf(
        Credit(
            nameResource = R.string.credit_author1_name,
            descriptionResource = R.string.credit_author1_description,
            importantContribution = true
        ),
        Credit(
            nameResource = R.string.credit_author2_name,
            descriptionResource = R.string.credit_author2_description,
            importantContribution = true
        ),
        Credit(
            nameResource = R.string.credit_director1_name,
            descriptionResource = R.string.credit_director1_description,
            importantContribution = true
        ),
        Credit(
            nameResource = R.string.credit_director2_name,
            descriptionResource = R.string.credit_director2_description,
            importantContribution = true
        ),
        Credit(
            nameResource = R.string.credit_graphic_design_name,
            descriptionResource = R.string.credit_graphic_design_description,
            importantContribution = false
        ),
        Credit(
            nameResource = R.string.credit_logo1_name,
            descriptionResource = R.string.credit_logo1_description,
            importantContribution = false
        ),
        Credit(
            nameResource = R.string.credit_logo2_name,
            descriptionResource = R.string.credit_logo2_description,
            importantContribution = false
        ),
        Credit(
            nameResource = R.string.credit_logo5_name,
            descriptionResource = R.string.credit_logo5_description,
            importantContribution = false
        ),
        Credit(
            nameResource = R.string.credit_logo3_name,
            descriptionResource = R.string.credit_logo3_description,
            importantContribution = false
        ),
        Credit(
            nameResource = R.string.credit_logo4_name,
            descriptionResource = R.string.credit_logo4_description,
            importantContribution = false
        ),
        Credit(
            nameResource = R.string.credit_app_logo_name,
            descriptionResource = R.string.credit_app_logo_description,
            importantContribution = false
        ),

        )
}