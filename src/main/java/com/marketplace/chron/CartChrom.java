package com.marketplace.chron;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import com.marketplace.v1.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Named;


import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by evacalla on 13/12/2019
 **/

@Named("CartChrom")
@Scope("prototype")
public class CartChrom extends UntypedActor {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartChrom.class);

    private final CartService cartService;

    @Inject
    public CartChrom(CartService cartService) {
        this.cartService = cartService;
    }

    public static class Schedule {
        private final Date date;

        public Schedule(Date date) {
            this.date = date;
        }

        public Date getDate() {
            return date;
        }
    }

    @Override
    public void preStart() throws Exception { super.preStart(); }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Schedule) {
            Schedule schedule = (Schedule) message;
            Schedule newMsg = new Schedule(new Date());
            LOGGER.info("CartChrom ran on: {}", schedule.getDate());
            getContext().system().scheduler().scheduleOnce(Duration.create(5, TimeUnit.MINUTES),
                    () -> {
                        cartService.process();
                        getSelf().tell(newMsg, ActorRef.noSender());
                    }, getContext().system().dispatcher());
        } else {
            unhandled(message);
        }
    }

}
