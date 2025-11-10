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
        PhraseEntity(targetLanguage = "Excuse me", translation = "Disculpe", example = "Excuse me, where is the bathroom?", deckId = 1),
        PhraseEntity(targetLanguage = "Yes", translation = "Sí", example = "Yes, I agree", deckId = 1),
        PhraseEntity(targetLanguage = "No", translation = "No", example = "No, thank you", deckId = 1),
        PhraseEntity(targetLanguage = "How are you?", translation = "¿Cómo estás?", example = "Hey, how are you?", deckId = 1),
        PhraseEntity(targetLanguage = "I'm fine", translation = "Estoy bien", example = "I'm fine, thanks for asking", deckId = 1),
        PhraseEntity(targetLanguage = "What's your name?", translation = "¿Cómo te llamas?", example = "Hi, what's your name?", deckId = 1),
        PhraseEntity(targetLanguage = "Nice to meet you", translation = "Mucho gusto", example = "Nice to meet you too!", deckId = 1),
        PhraseEntity(targetLanguage = "I don’t understand", translation = "No entiendo", example = "Sorry, I don’t understand", deckId = 1),
        PhraseEntity(targetLanguage = "Can you help me?", translation = "¿Puedes ayudarme?", example = "Can you help me, please?", deckId = 1),
        PhraseEntity(targetLanguage = "How much is this?", translation = "¿Cuánto cuesta esto?", example = "How much is this shirt?", deckId = 1),
        PhraseEntity(targetLanguage = "Where is the bathroom?", translation = "¿Dónde está el baño?", example = "Excuse me, where is the bathroom?", deckId = 1),
        PhraseEntity(targetLanguage = "I love you", translation = "Te amo", example = "I love you so much", deckId = 1),
        PhraseEntity(targetLanguage = "Good morning", translation = "Buenos días", example = "Good morning everyone", deckId = 1),
        PhraseEntity(targetLanguage = "Good night", translation = "Buenas noches", example = "Good night, sleep well", deckId = 1)
    )

}