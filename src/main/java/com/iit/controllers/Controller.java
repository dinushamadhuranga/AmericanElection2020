package com.iit.controllers;

import javafx.fxml.Initializable;

public interface Controller extends Initializable {

    //this update method is to update all controller when makes a change in any of controller
    public void update();
    //this method is used to infirm the start voting to the all controllers
    public void started();
}
