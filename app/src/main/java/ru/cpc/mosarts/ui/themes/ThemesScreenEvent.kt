package ru.cpc.mosarts.ui.themes

import ru.cpc.mosarts.utils.base.Event

sealed class ThemesScreenEvent : Event(){
    object Error: ThemesScreenEvent()
    object GoToMap : ThemesScreenEvent()
}