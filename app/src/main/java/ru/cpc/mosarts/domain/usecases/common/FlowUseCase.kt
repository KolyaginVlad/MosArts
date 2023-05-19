package com.osinit.mycompany.domain.usecases.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn

/**
 * UseCase с обновляемым результатом
 *
 * @param Type тип результата
 * @param Args тип аргументов UseCase
 * @property arguments аргументы UseCase
 * @property latestFlow поток ответа
 */
abstract class FlowUseCase<out Type, Args> {

    private val arguments = MutableSharedFlow<Args>(replay = 1)
    @kotlinx.coroutines.ExperimentalCoroutinesApi
    private val latestFlow by lazy {
        arguments.flatMapLatest {
            createFlow(it).flowOn(Dispatchers.IO)
        }
    }

    /**
     * Запустить/перезапустить UseCase
     *
     * @param args аргументы
     */
    operator fun invoke(args: Args) : FlowUseCase<Type, Args> {
        arguments.tryEmit(args)
        return this
    }

    /**
     * Подписаться на результаты выполнения useCase
     *
     * @return поток результатов
     */
    fun observe() = latestFlow

    /**
     * Выполнить UseCase в потоке результатов
     *
     * @param args аргументы
     * @return поток результатов
     */
    protected abstract fun createFlow(args: Args): Flow<Result<Type>>
}