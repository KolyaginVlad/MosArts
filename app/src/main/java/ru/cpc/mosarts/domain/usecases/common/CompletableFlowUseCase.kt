package com.osinit.mycompany.domain.usecases.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * UseCase с отменой подписки при завршении
 *
 * @param Type тип результата
 * @param Args аргументы UseCase
 *
 */
abstract class CompletableFlowUseCase<out Type, Args> {

    /**
     * Запустить UseCase
     *
     * @param args аргументы
     */
    operator fun invoke(args: Args): Flow<Result<Type>> {
        return createFlow(args).flowOn(Dispatchers.IO)
    }

    /**
     * Выполнить UseCase в потоке результатов
     *
     * @param args аргументы
     * @return поток результатов
     */
    protected abstract fun createFlow(args: Args): Flow<Result<Type>>
}