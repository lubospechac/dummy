package org.dummy.command;

public class AddCommand implements Command {

    private final Integer id;
    private final String guid;
    private final String userName;

    public AddCommand(Integer id, String guid, String userName) {
        this.id = id;
        this.guid = guid;
        this.userName = userName;
    }

    public Integer getId() {
        return id;
    }

    public String getGuid() {
        return guid;
    }

    public String getUserName() {
        return userName;
    }
}
