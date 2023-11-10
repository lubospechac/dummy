package org.dummy.consumer;

import org.dummy.command.CommandProcessor;
import org.dummy.Queue;
import org.dummy.command.Command;

import java.util.concurrent.*;

public class Consumer {

    private final Queue queue;
    private final int readTimeout;
    private final CommandProcessor commandProcessor;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    public Consumer(Queue queue, int readTimeout, CommandProcessor commandProcessor) {
        this.queue = queue;
        this.readTimeout = readTimeout;
        this.commandProcessor = commandProcessor;
    }

    /**
     * Start consuming messages in background
     */
    public void start() {
        executorService.submit(this::consumeMessages);
        executorService.shutdown();
    }


    /**
     * Get message from queue and delegate processing
     */
    private void consumeMessages() {
        try {
            Command command = queue.get(readTimeout);
            while (command != null) {
                commandProcessor.process(command);
                command = queue.get(readTimeout);
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }


    public void waitUntilFinished() throws InterruptedException {
        executorService.awaitTermination(5, TimeUnit.SECONDS);
    }

}
