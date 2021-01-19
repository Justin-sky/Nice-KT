package com.lj.core.ecs.component

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.core.ecs.Component

@JsonTypeName("team")
class TeamComponent: Component() {
    var name:String = "team1"
}