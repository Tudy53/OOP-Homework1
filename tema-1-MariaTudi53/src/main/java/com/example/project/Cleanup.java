package com.example.project;

import javax.xml.crypto.Data;

public class Cleanup extends Command {

    @Override
    void doIt() {
        Database.cleanup();
    }
}
