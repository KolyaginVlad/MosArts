package ru.cpc.mosarts.ui.test.testSelect

import ru.cpc.mosarts.domain.models.NamesOfTest
import ru.cpc.mosarts.utils.base.State


data class TestSelectScreenState(
	val test: NamesOfTest? = null
) : State()