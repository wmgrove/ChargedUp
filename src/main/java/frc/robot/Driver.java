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
                drivebase.arcade(getLeftY(), getRightX());
                break;
            case TANK:
                drivebase.tank(getLeftY(), getRightY());
                break;
        }
    }

    /**
     * Provides information about the state of the left stick with a
     * pre-applied deadband.
     * 
     * @return The X value of the left stick with deadband applied
     */
    private double getLeftX() {
        return Utilities.deadband(controller.getLeftX(), Utilities.DRIVERDEADBAND);
    }

    /**
     * Provides information about the state of the left stick with a
     * pre-applied deadband.
     * 
     * @return The Y value of the left stick with deadband applied
     */
    private double getLeftY() {
        return Utilities.deadband(controller.getLeftY(), Utilities.DRIVERDEADBAND);
    }

    /**
     * Provides information about the state of the right stick with a
     * pre-applied deadband.
     * 
     * @return The X value of the right stick with deadband applied
     */
    private double getRightX() {
        return Utilities.deadband(controller.getRightX(), Utilities.DRIVERDEADBAND);
    }

    /**
     * Provides information about the state of the right stick with a
     * pre-applied deadband.
     * 
     * @return The Y value of the right stick with deadband applied
     */
    private double getRightY() {
        return Utilities.deadband(controller.getRightY(), Utilities.DRIVERDEADBAND);
    }
}
