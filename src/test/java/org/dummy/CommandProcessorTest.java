package org.dummy;

import org.dummy.command.*;
import org.dummy.db.Database;
import org.junit.jupiter.api.*;

class CommandProcessorTest {

    Database database = new Database("jdbc:derby:junit");
    CommandProcessor commandProcessor = new CommandProcessor(database);

    @BeforeEach
    public void init() throws Exception {
        database.initDatabase();
    }

    @AfterEach
    public void cleanup() throws Exception {
        database.stopDatabase();
    }

    @Test
    void processCommand() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> commandProcessor.process(new Command() {}));
        try {
            commandProcessor.process(new PrintAllCommand());
            commandProcessor.process(new AddCommand(1, "x", "y"));
            Assertions.assertEquals(1, database.findAllUsers().size());
            commandProcessor.process(new PrintAllCommand());
            commandProcessor.process(new DeleteAllCommand());
            Assertions.assertTrue(database.findAllUsers().isEmpty());
            commandProcessor.process(new PrintAllCommand());
        } catch (Exception ex) {
            Assertions.fail(ex);
        }
    }
}