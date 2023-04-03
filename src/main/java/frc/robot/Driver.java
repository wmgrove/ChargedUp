package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Driver {

    static Driver self;

    XboxController controller = new XboxController(Utilities.DRIVERPORT);
    
    enum DriverState {ARCADE, TANK}
    DriverState controlMode = DriverState.ARCADE;
    Drivebase drivebase = Drivebase.getDrivebase();

    /**
     * Provides singleton for the driver
     * 
     * @return Driver singleton
     */
    public static Driver getDriver () {
        if (self == null) {
            self = new Driver();
        }
        return self;
    }

    /**
     * Constructor
     */
    private Driver() {}

    /**
     * Periodic code
     */
    public void run() {
        switch (controlMode){
            case ARCADE: 
                drivebase.arcade(getLeftStick()[1], getRightStick()[0]);
                break;
            case TANK:
                drivebase.tank(getLeftStick()[1], getRightStick()[1]);
                break;
        }
    }

    /**
     * Provides information about the state of the left stick with a
     * pre-applied deadband.
     * 
     * @return An array of 2 elements, where the X-axis is at index 0, and the
     * Y-axis is at index 1.
     */
    private double[] getLeftStick() {
        double[] output = 
            {Utilities.deadband(controller.getLeftX(), Utilities.DRIVERDEADBAND),
            Utilities.deadband(controller.getLeftY(), Utilities.DRIVERDEADBAND)};
        return output;
    }

    /**
     * Provides information about the state of the rightt stick with a pre-applied deadband.
     * 
     * @return An array of 2 elements, where the X-axis is at index 0, and the
     * Y-axis is at index 1.
     */
    private double[] getRightStick() {
        double[] output = 
            {Utilities.deadband(controller.getRightX(), Utilities.DRIVERDEADBAND),
            Utilities.deadband(controller.getRightY(), Utilities.DRIVERDEADBAND)};
        return output;
    }
}
