package ru.cpc.mosarts.domain.models

enum class Difficulty {
	Easy,
	Medium,
	Hard
}

enum class NamesOfTest {
	//TextText,
	TextOptions,
	MusicImage,
	//VideoMusic,
}

data class TestParams(
	val name:NamesOfTest,
	val difficulty: Difficulty
)
