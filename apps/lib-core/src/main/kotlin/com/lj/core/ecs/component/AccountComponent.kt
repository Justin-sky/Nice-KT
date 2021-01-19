package com.lj.core.ecs.component

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.core.ecs.Component

@JsonTypeName("account")
class AccountComponent : Component() {
    var name:String = "ab"
    var money:Int = 100

    fun changeName(name:String){
        this.name = name
        this.addUpdateJson("components.AccountComponent.name",name)
    }
}