package org.dummy;

import org.dummy.db.Database;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseSetupTest {

    Database database = new Database("jdbc:derby:junit");

    @Test
    @Order(1)
    void initDatabase() {
        try {
            database.initDatabase();
        } catch (Exception e) {
            fail("Unable to init database", e);
        }
    }


    @Test
    @Order(2)
    void testCanWrite() throws Exception {
        assertEquals(1, database.addUser(1, "a", "b"));
        assertEquals(1, database.deleteAllUsers());
    }


    @Test
    @Order(3)
    void stopDatabase() {
        try {
            database.stopDatabase();
        } catch (Exception e) {
            fail("Unable to stop database", e);
        }
    }
}