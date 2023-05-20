package com.osinit.mycompany.domain.usecases.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * UseCase с единичным результатом
 *
 * @param Type тип результата
 * @param Args аргументы UseCase
 */
abstract class UseCase<out Type, Args> {

    /**
     * Выполнить UseCase
     *
     * @param args аргументы UseCase
     * @return результат
     */
    suspend operator fun invoke(args: Args): Result<Type> {
        return withContext(Dispatchers.IO) {
            run(args)
        }
    }

    /**
     * Выполнить UseCase
     *
     * @param args аргументы
     * @return результат
     */
    abstract suspend fun run(args: Args): Result<Type>
}
