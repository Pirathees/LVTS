
package javaanpr;

import javaanpr.gui.windows.LostVehicleTrackingSystem;

public class Main {

    public static void main(String[] args) throws Exception {
        
       LostVehicleTrackingSystem mainView = new LostVehicleTrackingSystem();
        mainView.setResizable(false);
        mainView.setVisible(true);
    }
}
