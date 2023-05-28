package com.osinit.mycompany.domain.usecases.common

/**
 * UseCase с единичным результатом без параметров
 */
abstract class NoArgsUseCase<out Type> : UseCase<Type, Unit>() {

    suspend operator fun invoke() = invoke(Unit)

    protected abstract suspend fun run(): Result<Type>

    final override suspend fun run(args: Unit) = run()
}