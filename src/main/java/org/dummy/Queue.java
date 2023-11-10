package org.dummy;

import org.dummy.command.Command;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Queue {

    BlockingQueue<Command> queue = new LinkedBlockingQueue<>();

    public void put(Command command) {
        queue.add(command);
    }

    public Command get(int millis) throws InterruptedException {
        return queue.poll(millis, TimeUnit.MILLISECONDS);
    }
}
