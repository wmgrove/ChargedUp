package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Driver {

    static Driver self;

    XboxController controller = new XboxController(Utilities.kDriverPort);
    
    enum DriverState {ARCADE, TANK}
    DriverState controlMode = DriverState.ARCADE;

    Drivebase drivebase = Drivebase.getDrivebase();
    Gripper gripper = Gripper.getGripper();

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
     * Periodic code during teleop
     */
    public void run () {
        switch (controlMode){
            case ARCADE: 
                drivebase.arcade(getLeftY(), getRightX());
                break;
            case TANK:
                drivebase.tank(getLeftY(), getRightY());
                break;
        }
        
        if (c_ConeMode() != c_CubeMode()) {
            if (c_ConeMode()) {
                gripper.coneMode();
            }
            if (c_CubeMode()) {
                gripper.cubeMode();
            }
        }
    }

    /**
     * Periodic code during autonomous
     */
    public void auto () {

    }

    /**
     * Periodic code at all times
     */
    public void idle () {

    }

    /**
     * Public code for testing
     */
    public void test () {

    }


    /**
     * Provides information about the state of the left stick with a
     * pre-applied deadband.
     * 
     * @return The X value of the left stick with deadband applied
     */
    @SuppressWarnings("unused")
    private double getLeftX() {
        return Utilities.deadband(controller.getLeftX(), Utilities.kDriverDeadband);
    }

    /**
     * Provides information about the state of the left stick with a
     * pre-applied deadband.
     * 
     * @return The Y value of the left stick with deadband applied
     */
    private double getLeftY() {
        return Utilities.deadband(controller.getLeftY(), Utilities.kDriverDeadband);
    }

    /**
     * Provides information about the state of the right stick with a
     * pre-applied deadband.
     * 
     * @return The X value of the right stick with deadband applied
     */
    private double getRightX() {
        return Utilities.deadband(controller.getRightX(), Utilities.kDriverDeadband);
    }

    /**
     * Provides information about the state of the right stick with a
     * pre-applied deadband.
     * 
     * @return The Y value of the right stick with deadband applied
     */
    private double getRightY() {
        return Utilities.deadband(controller.getRightY(), Utilities.kDriverDeadband);
    }

    private boolean c_ConeMode() {
        return controller.getLeftTriggerAxis() > 0.2;
    }

    private boolean c_CubeMode() {
        return controller.getRightTriggerAxis() > 0.2;
    }
}
