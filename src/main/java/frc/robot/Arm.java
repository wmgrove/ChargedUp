package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxAnalogSensor;
import com.revrobotics.SparkMaxLimitSwitch;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.motorcontrol.Spark;

public class Arm {
    private static Arm self = null;

    //Motors
    private CANSparkMax elevationMotor = new CANSparkMax(Utilities.kElevation, MotorType.kBrushless);
    private CANSparkMax extensionMotor = new CANSparkMax(Utilities.kExtension, MotorType.kBrushless);
    private Spark slideMotor = new Spark(Utilities.kSlide);

    //PIDs
    private PIDController slideController = Utilities.pidInitializer(Utilities.kSlidePID);
    private PIDController elevationController = Utilities.pidInitializer(Utilities.kElevationPID);
    private PIDController extensionController = Utilities.pidInitializer(Utilities.kExtensionPID);

    //Control
    private SlewRateLimiter elevationAccel = new SlewRateLimiter(Utilities.kElevationAccel);
    private SlewRateLimiter extensionAccel = new SlewRateLimiter(Utilities.kExtensionAccel);

    private boolean autoSlide = false;
    private boolean autoElevate = false;
    private boolean autoExtend = false;

    private double armSpeed = 0;
    private double extensionSpeed = 0;
    private double slideSpeed = 0;

    //Sense
    private SparkMaxLimitSwitch elevationForwardLimit = elevationMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    private SparkMaxLimitSwitch elevationReverseLimit = elevationMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    private SparkMaxAnalogSensor elevationPosition = elevationMotor.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute);
    private SparkMaxLimitSwitch extensionNearLimit = extensionMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    private SparkMaxLimitSwitch extensionFarLimit = extensionMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    private SparkMaxAnalogSensor extensionPosition = extensionMotor.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute);
    private DigitalInput slideLeftLimit = new DigitalInput(Utilities.kLeftLimitDIO);
    private DigitalInput slideRightLimit = new DigitalInput(Utilities.kRightLimitDIO);
    private AnalogPotentiometer slidePosition = new AnalogPotentiometer(Utilities.kSlidePot);

    //Measurement
    private double extensionOrigin = 0;
    private double slideOrigin = 0;
    private double elevationOrigin = 0;

    public static Arm getArm () {
        if (self == null) {
            self = new Arm();
        }
        return self;
    }

    private Arm () {}

    public void run () {
        //If we get user input, listen to it
        if (armSpeed != 0 ) {
            autoElevate = false;
        }
        //If we lack user input and we aren't running automatically, maintain position
        else if (!autoElevate) {
            autoElevate(elevationAngle());
        }
        //This runs automatically or to maintain position
        if (autoElevate) {
            //Clamp added as protective measure, adjust limit as needed
            armSpeed = Utilities.clamp(elevationController.calculate(elevationAngle()),1);
        }
        elevationMotor.set(elevationAccel.calculate(armSpeed));
        //Reset armSpeed to prevent potential "speed memory" (should be unnecessary)
        armSpeed = 0;
        
        //If we get user input, listen to it
        if (extensionSpeed != 0 ) {
            autoExtend = false;
        }
        //Check bound here so autoExtend can be corrected
        bound();
        if (autoExtend) {
            //Clamp added as protective measure, adjust limit as needed
            extensionSpeed = Utilities.clamp(extensionController.calculate(extensionInches()),1); //TODO: Fix limit
        }
        extensionMotor.set(extensionAccel.calculate(extensionSpeed));
        //Reset extensionSpeed to prevent potential "speed memory" (should be unnecessary)
        extensionSpeed = 0;

        //If we get user input, listen to it
        if (slideSpeed != 0 ) {
            autoSlide = false;
        }
        /* Disabled until slide position is readable, otherwise slide will run constinuously
        else if (autoSlide) {
            //Clamp added as protective measure, adjust limit as needed
            slideSpeed = Utilities.clamp(slideController.calculate(slideInches()),1); //TODO: Fix limit
        }
        */
        slideMotor.set(slideSpeed);
        //Reset slideSpeed to prevent potential "speed memory" (should be unnecessary)
        slideSpeed = 0;
    }

    public void elevate (double speed) {
        armSpeed = speed;
    }

    public void extend (double speed) {
        extensionSpeed = speed;
    }

    public void slide (double speed) {
        slideSpeed = speed;
    }

    public void autoExtend (double target) {
        extensionController.setSetpoint(target);
        autoExtend = true;
    }

    public void autoElevate (double target) {
        elevationController.setSetpoint(target);
        autoElevate = true;
    }

    private void bound () {
        double newExtension = extensionInches();
        if (Math.sin(elevationRadians()) * newExtension > Utilities.kHeightLimit) {
            newExtension = 
                Math.sqrt(Math.pow(Math.cos(elevationRadians()) * newExtension, 2)
                    + Math.pow(Utilities.kHeightLimit, 2));
        }
        if (elevationRadians() < Math.PI / 2 && Math.cos(elevationRadians()) * newExtension > Utilities.kReachLimit) {
            newExtension = 
                Math.sqrt(Math.pow(Math.sin(elevationRadians()) * newExtension, 2)
                    + Math.pow(Utilities.kReachLimit, 2));
        }
        autoExtend(newExtension);
    }

    private double extensionInches () {
        double inchesRaw = extensionMotor.getEncoder().getPosition();
        return (inchesRaw - extensionOrigin) * Utilities.kExtensionInchesPerCount;
    }

    private double slideInches () {
        return 0.0; //TODO
    }

    private double elevationAngle () {
        double angleRaw = elevationMotor.getEncoder().getPosition();
        return (angleRaw - elevationOrigin) * Utilities.kElevationCountsPerDegree;
    }

    private double elevationRadians () {
        return (elevationAngle() * Math.PI / 180) + (Math.PI / 2);
    }
}
