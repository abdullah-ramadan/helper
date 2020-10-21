module helper {
  requires spring.boot.autoconfigure;
  requires spring.boot;
  requires javafx.graphics;
  requires spring.context;
  requires javafx.fxml;
  requires spring.core;
  requires spring.beans;
  requires javafx.controls;
  requires poi;
  requires poi.ooxml;
  requires org.slf4j;
  requires lombok;

  opens com.circle.bulk.helper;
  opens com.circle.bulk.helper.gui;
  opens com.circle.bulk.helper.controller;
  opens com.circle.bulk.helper.service;
}
