package by.bsuir.khimich.boolib.mviframework

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface Container<S, I, E> {
    fun CoroutineScope.subscribe(onEvent: suspend (E) -> Unit)
    val states: StateFlow<S>
    fun intent(intent: I)
}

abstract class MVIViewModel<S, I, E>(initial: S) : ViewModel(), Container<S, I, E> {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initial)
    private val _events = Channel<E>()
    private val events = _events.receiveAsFlow()

    final override val states: StateFlow<S> = _state.asStateFlow()
    final override fun intent(intent: I) {
        viewModelScope.launch { reduce(intent) }
    }

    final override fun CoroutineScope.subscribe(onEvent: suspend (E) -> Unit) {
        events.onEach { onEvent(it) }.launchIn(this)
        onSubscribe()
    }

    protected fun event(event: E) = viewModelScope.launch { _events.send(event) }
    protected fun state(block: S.() -> S) = _state.update(block)
    protected abstract fun CoroutineScope.onSubscribe()
    protected abstract suspend fun reduce(intent: I)
}
