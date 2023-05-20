package ru.cpc.mosarts.domain.models

data class UserAnswer(
	val questionId: Long,
	val textAnswer: String? = null,
	val optionsAnswer:  ArrayList<String>? = null,
	val drawingAnswer: String? = null //что-то там
)