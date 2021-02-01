package com.lj.battle

import com.lj.battle.updater.IUpdater
import com.lj.core.gamePlay.combat.CombatContext
import com.lj.core.gamePlay.combat.timer.Time

class Battle(var combatContext: CombatContext): IUpdater {

    private var isEnemyTurn:Boolean = false
    private var accTime:Float = 0f
    private var isEndOfGame:Boolean = false
    private val enemyTurnDuration = 3f


    override suspend fun update(){
        if (isEnemyTurn){
            accTime += Time.deltaTime
            if (accTime >= enemyTurnDuration){
                accTime = 0f
                endEnemyTurn()
                beginPlayerTurn()
            }
        }
    }

    fun beginGame(){
        beginPlayerTurn()
    }

    fun beginPlayerTurn(){
        //PlayerTurnBegan.Raise();
    }

    fun endPlayerTurn(){
        //PlayerTurnEnded.Raise();
        beginEnemyTurn()
    }

    fun beginEnemyTurn(){
        //EnemyTurnBegan.Raise();
        isEnemyTurn = true
    }

    fun endEnemyTurn(){
        //EnemyTurnEnded.Raise();
        isEnemyTurn = false
    }

    fun setEndOfGame(value:Boolean){
        isEndOfGame = value
    }

    fun isEndOfGame():Boolean{
        return  isEndOfGame
    }


}