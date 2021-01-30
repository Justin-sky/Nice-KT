package com.lj.gamePlay.helper


class GameTimer(maxTime: Float) {
    private var _maxTime:Float = maxTime
    private var _time:Float = 0f
    private var _onFinish: (() -> Unit)? = null

    var maxTime:Float
        get() {
            return _maxTime
        }
        set(value) {
            _maxTime = value
        }

    val isFinished:Boolean
        get() = this._time >= this._maxTime

    val isRunning:Boolean
        get() = this._time < this._maxTime

    val time:Float
        get() = this._time


    fun reset(){
        this._time = 0f
    }

    fun updateAsFinish(delta:Float, onFinish:(() -> Unit)? = null): GameTimer {
        if(!isFinished){
            this._time += delta
            if (onFinish != null) _onFinish = onFinish
            if (isFinished) _onFinish?.invoke()
        }
        return this
    }

    fun updateAsRepeat(delta:Float, onRepeat:(() -> Unit)? = null){
        this._time += delta
        if (onRepeat != _onFinish) _onFinish = onRepeat
        while (_time >= _maxTime){
            _time -= _maxTime
            _onFinish?.invoke()
        }
    }

    fun onFinish(onFinish:(() -> Unit)? = null){
        _onFinish = onFinish
    }

    fun onRepeat(onRepeat: (() -> Unit)? = null){
        _onFinish = onRepeat
    }

}