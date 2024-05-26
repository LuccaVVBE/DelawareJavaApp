open module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;

    requires java.persistence;
    requires java.sql;
    requires java.instrument;
    requires java.naming;
    requires java.transaction;

    requires org.hibernate.orm.core;

    requires lombok;
    requires io.github.cdimascio.dotenv.java;
    requires jbcrypt;
    requires junit;
    requires org.mockito;
    requires fontawesomefx;

    exports main;
    exports controllers;
    exports domain;
    exports enums;
    exports exceptions;
    exports interfaces;
    exports dtos;
}