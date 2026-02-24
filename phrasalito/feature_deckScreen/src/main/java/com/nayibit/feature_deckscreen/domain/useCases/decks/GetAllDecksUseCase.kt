package com.nayibit.feature_deckscreen.domain.useCases.decks

import com.nayibit.feature_deckscreen.domain.model.Deck
import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllDecksUseCase @Inject
    constructor(private val repository: DeckRepository){

    operator fun invoke(id: Int): Flow<Result<List<Deck>, DatabaseError>> {
        return repository.getAllDecks(id)
    }

 }