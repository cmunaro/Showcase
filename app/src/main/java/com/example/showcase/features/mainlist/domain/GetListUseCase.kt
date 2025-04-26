package com.example.showcase.features.mainlist.domain

import com.example.showcase.features.mainlist.domain.model.Media

interface GetListUseCase {
    suspend operator fun invoke(): List<Media>
}

class GetListUseCaseImpl(
    private val repository: ListRepository
): GetListUseCase {
    override suspend fun invoke() = repository.getList()
}
