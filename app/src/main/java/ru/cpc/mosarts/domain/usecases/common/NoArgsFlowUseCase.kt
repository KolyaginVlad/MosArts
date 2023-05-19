package com.osinit.mycompany.domain.usecases.common

import kotlinx.coroutines.flow.Flow

/**
 * UseCase с обновляемым результатом без параметров
 *
 * @param Type тип результата
 */
abstract class NoArgsFlowUseCase<out Type> : FlowUseCase<Type, Unit>() {

    operator fun invoke() = also { invoke(Unit) }

    final override fun createFlow(args: Unit) = createFlow()

    protected abstract fun createFlow(): Flow<Result<Type>>
}