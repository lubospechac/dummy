package org.dummy;

import org.dummy.command.Command;
import org.dummy.command.CommandProcessor;
import org.dummy.consumer.Consumer;
import org.dummy.db.Database;
import org.dummy.producer.Producer;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws Exception {
        // setup queue
        Queue queue = new Queue();

        // setup consumer
        Database database = Environment.setupDatabase();
        CommandProcessor commandProcessor = new CommandProcessor(database);
        Consumer consumer = new Consumer(queue, Environment.getConsumerTimeoutMs(), commandProcessor);

        // setup producer
        Stream<Command> messageSource = Environment.commands();
        Producer producer = new Producer(queue, messageSource);

        // start consumer and producer
        consumer.start();
        producer.start();

        // stop embedded DB
        consumer.waitUntilFinished();
        Environment.stopDatabase(database);
    }
}
