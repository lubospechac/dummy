package org.dummy;

import org.dummy.command.AddCommand;
import org.dummy.command.Command;
import org.dummy.command.DeleteAllCommand;
import org.dummy.command.PrintAllCommand;
import org.dummy.db.Database;

import java.util.stream.Stream;

public class Environment {

    private static final String DB_URL = "jdbc:derby:dummy";
    private static final int CONSUMER_TIMEOUT_MS = 500;

    public static Database setupDatabase() throws Exception {
        Database database = new Database(DB_URL);
        database.initDatabase();
        return database;
    }

    public static void stopDatabase(Database database) throws Exception {
        database.stopDatabase();
    }

    public static Stream<Command> commands() {
        return Stream.of(
                new AddCommand(1, "a1", "Robert"),
                new AddCommand(2, "a2", "Martin"),
                new PrintAllCommand(),
                new DeleteAllCommand(),
                new PrintAllCommand());
    }

    public static int getConsumerTimeoutMs() {
        return CONSUMER_TIMEOUT_MS;
    }
}
