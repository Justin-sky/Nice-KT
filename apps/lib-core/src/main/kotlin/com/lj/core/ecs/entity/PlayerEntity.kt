package com.lj.core.ecs.entity

import com.fasterxml.jackson.annotation.JsonTypeName
import com.lj.core.ecs.Entity

@JsonTypeName("player")
class PlayerEntity: Entity() {
    override var docName: String = "user"


}