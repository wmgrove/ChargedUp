package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class Sensors {

    static Sensors self;

    AHRS navx = new AHRS(SPI.Port.kMXP);

    public static Sensors getSensors () {
        if (self == null) {
            self = new Sensors();
        }
        return self;
    }

    private Sensors () {}

    public double getYaw () {
        return navx.getYaw();
    }
}
