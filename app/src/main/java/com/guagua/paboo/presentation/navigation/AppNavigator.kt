package com.guagua.paboo.presentation.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AppNavigator {

    val getCurrentScreenFlow: Flow<Screen?>

    fun navigate(screen: Screen)

    fun popBackStack()
}

class AppNavigatorImpl(
    private val navController: NavController,
    private val gson: Gson
) : AppNavigator {

    override val getCurrentScreenFlow: Flow<Screen?> =
        navController.currentBackStackEntryFlow.map { navBackStackEntry ->
            navBackStackEntry.destination.route?.let { route ->
                Screen::class.sealedSubclasses
                    .map { it.objectInstance }
                    .find { it?.path == route }
            }
        }

    override fun navigate(screen: Screen) {
        navController.navigate(getPathWithArguments(screen))
    }

    override fun popBackStack() {
        navController.popBackStack()
    }

    private fun getPathWithArguments(screen: Screen): String {
        var path = screen.path
        getArgumentsMap(screen).forEach { (key, value) ->
            path = path.replace("{$key}", value)
        }
        return path
    }

    private fun getArgumentsMap(screen: Screen): Map<String, String> {
        val json = gson.toJson(screen)
        return gson.fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
    }
}