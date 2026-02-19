package com.nayibit.feature_deckscreen.domain.useCases.decks

import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDecksUseCase @Inject
    constructor(private val repository: DeckRepository){

    operator fun invoke(): Flow<Resource<List<Deck>>> {
        return repository.getAllDecks()
    }

 }