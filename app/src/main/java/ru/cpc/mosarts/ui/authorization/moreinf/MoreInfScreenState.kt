package ru.cpc.mosarts.ui.authorization.moreinf

import ru.cpc.mosarts.utils.base.State

data class MoreInfScreenState(
    val age: String = "",
    val phoneNumber: String = "+",
    val name: String = "",
    val surname: String = "",
    val avatar: String = "",// изменить
    val fatherName: String = "",
    val isLoading: Boolean = false,
    val nameError: Boolean = false,
    val surnameError: Boolean = false,
) : State()