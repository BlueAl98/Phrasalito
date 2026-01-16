package com.nayibit.phrasalito_data.utils

import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.entities.PhraseEntity

object Constants {

    val INITIAL_DECK = DeckEntity(
        name = "Ejemplo Deck",
        lngCode = "",
        languageName = ""
    )

    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage = "Hello", translation = "Hola", example = "Hello world", deckId = 1),
        PhraseEntity(targetLanguage = "Thank you", translation = "Gracias", example = "Thank you so much", deckId = 1),
        PhraseEntity(targetLanguage = "Goodbye", translation = "Adiós", example = "Goodbye my friend", deckId = 1),
        PhraseEntity(targetLanguage = "Please", translation = "Por favor", example = "Please come in", deckId = 1),
        PhraseEntity(targetLanguage = "Sorry", translation = "Lo siento", example = "Sorry for the delay", deckId = 1),
        PhraseEntity(targetLanguage = "Excuse me", translation = "Disculpe", example = "Excuse me, where is the bathroom?", deckId = 1)
    )

  /*
  🇬🇧 English (en → es)
       val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage = "Hello", translation = "Hola", example = "Hello world", deckId = 1),
        PhraseEntity(targetLanguage = "Thank you", translation = "Gracias", example = "Thank you so much", deckId = 1),
        PhraseEntity(targetLanguage = "Goodbye", translation = "Adiós", example = "Goodbye my friend", deckId = 1),
        PhraseEntity(targetLanguage = "Please", translation = "Por favor", example = "Please come in", deckId = 1),
        PhraseEntity(targetLanguage = "Sorry", translation = "Lo siento", example = "Sorry for the delay", deckId = 1),
        PhraseEntity(targetLanguage = "Excuse me", translation = "Disculpe", example = "Excuse me, where is the bathroom?", deckId = 1)
    )

🇫🇷 French (fr → es)
    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Bonjour", translation ="Hola", example = "Bonjour tout le monde",deckId = 1),
        PhraseEntity(targetLanguage ="Merci", translation ="Gracias", example = "Merci beaucoup", deckId = 1),
        PhraseEntity(targetLanguage ="Au revoir",translation = "Adiós", example = "Au revoir mon ami",deckId = 1),
        PhraseEntity(targetLanguage ="S'il vous plaît", translation ="Por favor", example = "S'il vous plaît entrez", deckId =1),
        PhraseEntity(targetLanguage ="Désolé", translation ="Lo siento", example = "Désolé pour le retard",deckId = 1)
    )

🇩🇪 German (de → es)
    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Hallo", translation ="Hola",  example ="Hallo Welt", deckId = 1),
        PhraseEntity(targetLanguage ="Danke", translation ="Gracias",  example ="Danke schön",deckId =  1),
        PhraseEntity(targetLanguage ="Tschüss", translation ="Adiós",  example ="Tschüss mein Freund", deckId = 1),
        PhraseEntity(targetLanguage ="Bitte", translation ="Por favor", example = "Bitte komm rein", deckId = 1),
        PhraseEntity(targetLanguage ="Entschuldigung", translation ="Lo siento",  example ="Entschuldigung für die Verspätung", deckId = 1)
    )

🇮🇹 Italian (it → es)
    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Ciao", translation ="Hola", example ="Ciao mondo", deckId = 1),
        PhraseEntity(targetLanguage ="Grazie", translation ="Gracias",example ="Grazie mille", deckId = 1),
        PhraseEntity(targetLanguage ="Arrivederci", translation ="Adiós",example = "Arrivederci amico", deckId = 1),
        PhraseEntity(targetLanguage ="Per favore", translation ="Por favor", example ="Per favore entra", deckId = 1),
        PhraseEntity(targetLanguage ="Scusa", translation ="Lo siento", example ="Scusa per il ritardo", deckId = 1)
    )

🇵🇹 Portuguese (pt → es)
    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Olá",translation = "Hola", example ="Olá mundo", deckId = 1),
        PhraseEntity(targetLanguage ="Obrigado",translation = "Gracias", example ="Muito obrigado", deckId = 1),
        PhraseEntity(targetLanguage ="Tchau",translation = "Adiós", example ="Tchau meu amigo", deckId = 1),
        PhraseEntity(targetLanguage ="Por favor",translation = "Por favor",example = "Por favor entre", deckId = 1),
        PhraseEntity(targetLanguage ="Desculpa",translation = "Lo siento", example ="Desculpa pelo atraso", deckId = 1)
    )

🇳🇱 Dutch (nl → es)
    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Hallo", translation = "Hola", example ="Hallo wereld",deckId = 1),
        PhraseEntity(targetLanguage ="Dank u", translation = "Gracias", example ="Dank u wel", deckId = 1),
        PhraseEntity(targetLanguage ="Dag", translation = "Adiós", example ="Dag mijn vriend",deckId = 1),
        PhraseEntity(targetLanguage ="Alsjeblieft",translation =  "Por favor", example ="Alsjeblieft kom binnen", deckId = 1),
        PhraseEntity(targetLanguage ="Sorry", translation = "Lo siento", example ="Sorry voor de vertraging", deckId = 1)
    )

🇸🇪 Swedish (sv → es)
    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Hej", translation ="Hola", example ="Hej världen", deckId = 1),
        PhraseEntity(targetLanguage ="Tack", translation ="Gracias",example = "Tack så mycket", deckId = 1),
        PhraseEntity(targetLanguage ="Hejdå", translation ="Adiós", example ="Hejdå min vän", deckId = 1),
        PhraseEntity(targetLanguage ="Snälla", translation ="Por favor", example ="Snälla kom in",deckId = 1),
        PhraseEntity(targetLanguage ="Förlåt", translation ="Lo siento", example ="Förlåt för förseningen", deckId = 1)
    )

🇵🇱 Polish (pl → es)
  val INITIAL_PHRASES_PL = listOf(
    PhraseEntity("Cześć", "Hola", "Cześć świecie", 1),
    PhraseEntity("Dziękuję", "Gracias", "Bardzo dziękuję", 1),
    PhraseEntity("Do widzenia", "Adiós", "Do widzenia mój przyjacielu", 1),
    PhraseEntity("Proszę", "Por favor", "Proszę wejdź", 1),
    PhraseEntity("Przepraszam", "Lo siento", "Przepraszam za spóźnienie", 1)
   )

   🇹🇷 Turkish (tr → es)
       val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Merhaba", translation ="Hola", example ="Merhaba dünya", deckId = 1),
        PhraseEntity(targetLanguage ="Teşekkürler", translation ="Gracias", example ="Çok teşekkürler", deckId = 1),
        PhraseEntity(targetLanguage ="Hoşça kal", translation ="Adiós", example ="Hoşça kal arkadaşım", deckId = 1),
        PhraseEntity(targetLanguage ="Lütfen", translation ="Por favor", example ="Lütfen içeri gir", deckId = 1),
        PhraseEntity(targetLanguage ="Üzgünüm", translation ="Lo siento",example = "Gecikme için üzgünüm", deckId = 1)
    )



 🇪🇸 Spanish (es → en)
    val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Hola", translation ="Hello", example ="Hola mundo",deckId = 1),
        PhraseEntity(targetLanguage ="Gracias", translation ="Thank you", example ="Muchas gracias", deckId = 1),
        PhraseEntity(targetLanguage ="Adiós", translation ="Goodbye", example ="Adiós amigo", deckId = 1),
        PhraseEntity(targetLanguage ="Por favor", translation ="Please", example ="Por favor entra", deckId = 1),
        PhraseEntity(targetLanguage ="Lo siento", translation ="Sorry", example ="Lo siento por el retraso", deckId = 1)
    )*/


  /*  val INITIAL_PHRASES = listOf(
        PhraseEntity(targetLanguage ="Merhaba", translation ="Hola", example ="Merhaba dünya", deckId = 1),
        PhraseEntity(targetLanguage ="Teşekkürler", translation ="Gracias", example ="Çok teşekkürler", deckId = 1),
        PhraseEntity(targetLanguage ="Hoşça kal", translation ="Adiós", example ="Hoşça kal arkadaşım", deckId = 1),
        PhraseEntity(targetLanguage ="Lütfen", translation ="Por favor", example ="Lütfen içeri gir", deckId = 1),
        PhraseEntity(targetLanguage ="Üzgünüm", translation ="Lo siento",example = "Gecikme için üzgünüm", deckId = 1)
    )*/


}