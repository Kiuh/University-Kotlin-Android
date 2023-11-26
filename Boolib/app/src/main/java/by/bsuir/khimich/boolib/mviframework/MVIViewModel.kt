package by.bsuir.khimich.boolib.mviframework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface Container<S, I, E> {
    fun CoroutineScope.subscribe(onEvent: suspend (E) -> Unit)
    val states: StateFlow<S>
    fun intent(intent: I)
}

abstract class MVIViewModel<S, I, E>(initial: S) : ViewModel(), Container<S, I, E> {

    private val _states: MutableStateFlow<S> = MutableStateFlow(initial)
    private val _events = Channel<E>(Channel.UNLIMITED)

    final override val states = _states.asStateFlow()
    final override fun intent(intent: I): Unit = run { viewModelScope.launch { reduce(intent) } }
    final override fun CoroutineScope.subscribe(onEvent: suspend (E) -> Unit) {
        launch { for (event in _events) onEvent(event) }
        onSubscribe()
    }

    protected fun event(event: E) = viewModelScope.launch { _events.send(event) }
    protected fun state(block: S.() -> S) = _states.update { block.invoke(_states.value) }
    protected open fun CoroutineScope.onSubscribe() = Unit
    protected abstract suspend fun reduce(intent: I)
}

