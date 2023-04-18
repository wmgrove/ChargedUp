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
    static Arm self = null;

    //Motors
    CANSparkMax elevationMotor = new CANSparkMax(Utilities.kElevation, MotorType.kBrushless);
    CANSparkMax extensionMotor = new CANSparkMax(Utilities.kExtension, MotorType.kBrushless);
    Spark slideMotor = new Spark(Utilities.kSlide);

    //PIDs
    PIDController slideController = Utilities.pidInitializer(Utilities.kSlidePID);
    PIDController elevationController = Utilities.pidInitializer(Utilities.kElevationPID);
    PIDController extensionController = Utilities.pidInitializer(Utilities.kExtensionPID);

    //Control
    SlewRateLimiter elevationAccel = new SlewRateLimiter(Utilities.kElevationAccel);
    SlewRateLimiter extensionAccel = new SlewRateLimiter(Utilities.kExtensionAccel);

    boolean autoSlide = false;
    boolean autoElevate = false;
    boolean autoExtend = false;

    //Sense
    SparkMaxLimitSwitch elevationForwardLimit = elevationMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    SparkMaxLimitSwitch elevationReverseLimit = elevationMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    SparkMaxAnalogSensor elevationPosition = elevationMotor.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute);
    SparkMaxLimitSwitch extensionNearLimit = extensionMotor.getForwardLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    SparkMaxLimitSwitch extensionFarLimit = extensionMotor.getReverseLimitSwitch(SparkMaxLimitSwitch.Type.kNormallyOpen);
    SparkMaxAnalogSensor extensionPosition = extensionMotor.getAnalog(SparkMaxAnalogSensor.Mode.kAbsolute);
    DigitalInput slideLeftLimit = new DigitalInput(Utilities.kLeftLimitDIO);
    DigitalInput slideRightLimit = new DigitalInput(Utilities.kRightLimitDIO);
    AnalogPotentiometer slidePosition = new AnalogPotentiometer(Utilities.kSlidePot);

    //Measurement
    double extensionOrigin = 0;
    double slideOrigin = 0;
    double elevationOrigin = 0;

    double extensionTarget = 0;
    double slideTarget = 0;
    double elevationTarget = 0;

    public static Arm getArm () {
        if (self == null) {
            self = new Arm();
        }
        return self;
    }

    private Arm () {}

    private void bound () {
    }

    public void autoExtend (double target) {

    }
}
