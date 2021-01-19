package com.lj.msg

import com.lj.core.common.ServiceHandlerAnnotation
import com.lj.core.ecs.DBEntityManager
import com.lj.core.ecs.EntityFactory
import com.lj.core.ecs.component.AccountComponent
import com.lj.core.ecs.component.BagComponent
import com.lj.core.ecs.component.Card
import com.lj.core.ecs.component.TeamComponent
import com.lj.core.ecs.entity.PlayerEntity
import com.lj.core.net.Opcode
import com.lj.core.service.Msg
import com.lj.core.service.handler.BaseHandler
import com.lj.proto.Login
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kt.crypto.decodeBase64
import kt.crypto.encodeBase64
import kt.scaffold.tools.logger.Logger

@ServiceHandlerAnnotation(opcode = Opcode.MSG_C2G_LoginGate)
class LoginGateHandler: BaseHandler() {
    override fun process(msg: Msg,handler: Handler<AsyncResult<Msg>>) {
        GlobalScope.launch {
            //解析消息
            var rev = msg.base64Msg.decodeBase64()
            var c2g = Login.C2G_LoginGate.parseFrom(rev)
            Logger.debug("gateid: ${c2g.gateId}, key: ${c2g.key}")

            //返回消息
            var builder = Login.G2C_LoginGate.newBuilder()
            builder.message = "Login Gate Success .."
            builder.playerId = 991
            builder.error = 0
            var buf = builder.build().toByteArray()

            val resResp = msg.copy()
            msg.msgId = Opcode.MSG_G2C_LoginGate
            msg.base64Msg = buf.encodeBase64()

            //登录到服务器后，发用户ID返回
            msg.userId = 991

            //从数据库查询玩家信息

            var user = DBEntityManager.findEntityAwait(991)
            if(user == null){
                val entity = EntityFactory.create<PlayerEntity>(991)

                entity.addComponent<AccountComponent>()
                entity.addComponent<BagComponent>()

                val bag = entity.getComponent<BagComponent>() as BagComponent
                bag.addCard(Card(1,"card1",1,1))
                bag.addCard(Card(2,"card2",2,2))

                DBEntityManager.saveEntity2DBAwait(entity)

                user =  DBEntityManager.findEntityAwait(991)
            }else{
                val bag = user.getComponent<BagComponent>() as BagComponent
                bag.addCard(Card(3,"card3",3,3))
              //  DBEntityManager.updateEntity2DBAwait(user)

                //add
               // user.addComponent<TeamComponent>(true)
               // DBEntityManager.updateComponent2DBAwait(user)

                //remove
                user.removeComponent<TeamComponent>()
                DBEntityManager.removeComponent2DBAwait(user)

            }
            Logger.debug(user.toString())


            handler.handle(Future.succeededFuture(msg))
        }

    }
}
