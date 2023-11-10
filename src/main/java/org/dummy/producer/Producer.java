package org.dummy.producer;

import org.dummy.Queue;
import org.dummy.command.Command;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Producer{

    private final Queue queue;
    private final Stream<Command> source;

    public Producer(Queue queue, Stream<Command> source) {
        this.queue = queue;
        this.source = source;
    }

    /**
     * start putting commands to queue in background
     */
    public void start() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(() -> source.forEach(queue::put));
        executorService.shutdown();
    }
}
