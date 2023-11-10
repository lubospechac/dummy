package org.dummy.command;


import org.dummy.db.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CommandProcessor {

    private final Logger log = LoggerFactory.getLogger(CommandProcessor.class);

    private final Database database;

    public CommandProcessor(Database database) {
        this.database = database;
    }

    public void process(Command command) {
        log.info("Processing command {}", command.getClass().getSimpleName());

        if (command instanceof AddCommand) {
            processAddCommand((AddCommand) command);

        } else if (command instanceof DeleteAllCommand) {
            processDeleteAllCommand((DeleteAllCommand) command);

        } else if (command instanceof PrintAllCommand) {
            processPrintAllCommand((PrintAllCommand) command);

        } else {
            throw new IllegalArgumentException("Unsupported command type: " + command.getClass().getName());
        }
    }


    private void processAddCommand(AddCommand command) {
        try {
            int count = database.addUser(command.getId(), command.getGuid(), command.getUserName());
            if (count == 1) {
                log.info("User created\n");
            }
        } catch (Exception e) {
            log.error("Error processing command {}", command.getClass().getSimpleName(), e);
        }
    }


    private void processDeleteAllCommand(DeleteAllCommand command) {
        try {
            int count = database.deleteAllUsers();
            log.info("Deleted {} users\n", count);
        } catch (Exception e) {
            log.error("Error processing command {}", command.getClass().getSimpleName(), e);
        }
    }


    private void processPrintAllCommand(PrintAllCommand command) {
        try {
            List<String[]> users = database.findAllUsers();

            if (users.isEmpty()) {
                log.info("No users found\n");
                return;
            }

            // PKs are usually hidden from end users, so I'm not printing it
            for (int i = 0; i < users.size(); i++) {
                String[] user = users.get(i);
                if (i + 1 == users.size()) { // last
                    log.info("{}\t{}\n", user[1], user[2]);
                } else {
                    log.info("{}\t{}", user[1], user[2]);
                }
            }

        } catch (Exception e) {
            log.error("Error processing command {}", command.getClass().getSimpleName(), e);
        }
    }
}

