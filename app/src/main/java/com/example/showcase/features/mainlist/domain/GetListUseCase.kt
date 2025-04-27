package com.example.showcase.features.mainlist.domain

import com.example.showcase.features.mainlist.domain.model.Media

interface GetListUseCase {
    suspend operator fun invoke(): Result<List<Media>>
}

class GetListUseCaseImpl(
    private val repository: MediaRepository
): GetListUseCase {
    override suspend fun invoke() = repository.getList()
}
