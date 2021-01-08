package com.iit.mainroom;

import com.iit.controllers.Controller;
import com.iit.controllers.AdminLoginController;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.HashMap;

public class AmericanElectionControlRoom{

    //to store all of the controllers and reuse without creating new objects and
    private HashMap<String , Controller> controllerHashMap = new HashMap<String, Controller>();

    // to store the login controller which is created by the start method
    private AdminLoginController adminLoginController;

    //use to get and add controllers
    public enum controllerTypes {
        ADMINMENUCONTROLLER  , ADMINLOGINCONTROLLER  , HOMECONTROLLER , MANAGEBALLOTSCONTROLLER , MANAGECANDIDATECONTROLLER , VOTINGCONTROLLER;
    }

    //add the relevent controller
    public void addController(controllerTypes type , Controller controllerImlp) {

        switch (type){
            case ADMINLOGINCONTROLLER:
                controllerHashMap.put("adminlogincontroller" , controllerImlp);
            case ADMINMENUCONTROLLER:
                controllerHashMap.put("adminmenucontroller" , controllerImlp);
            case HOMECONTROLLER:
                controllerHashMap.put("homecontroller" , controllerImlp);
            case MANAGEBALLOTSCONTROLLER:
                controllerHashMap.put("manageballotscontroller" , controllerImlp);
            case MANAGECANDIDATECONTROLLER:
                controllerHashMap.put("managecandidatecontroller" , controllerImlp);
            case VOTINGCONTROLLER:
                controllerHashMap.put("votingcontroller" , controllerImlp);
        }
    }

    //return the relevent controller
    public Controller getController(controllerTypes type) {

        switch (type){
            case ADMINLOGINCONTROLLER:
                return controllerHashMap.get("adminlogincontroller");
            case ADMINMENUCONTROLLER:
                return controllerHashMap.get("adminmenucontroller" );
            case HOMECONTROLLER:
                return controllerHashMap.get("homecontroller");
            case MANAGEBALLOTSCONTROLLER:
                return controllerHashMap.get("manageballotscontroller");
            case MANAGECANDIDATECONTROLLER:
                return controllerHashMap.get("managecandidatecontroller");
            case VOTINGCONTROLLER:
                return controllerHashMap.get("votingcontroller");
            default:
                return null;
        }
    }

    //to remove controllers from the hashmap
    public void removeController(Controller controllerImlp) {
        controllerHashMap.remove(controllerImlp);
    }

    //to execute update method of all of the controllers in the hashmap
    public void notifyControllersUpdate(){
        for (Controller controller :
                controllerHashMap.values()) {
            controller.update();
        }
    };

    //to execte start menthod of all of the controllers in the hashmap
    public void notifyControllersStarted(){
        for (Controller controller :
                controllerHashMap.values()) {
            controller.started();
        }
    };

    //set the adminlogincontroller
    public void setAdminLoginController(AdminLoginController adminLoginController) {
        this.adminLoginController = adminLoginController;
    }

    //return the adminloginlogin controller
    public AdminLoginController getAdmniLoginController() {
        return adminLoginController;
    }
}
