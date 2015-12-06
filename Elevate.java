package org.usfirst.frc.team3104.robot;

import edu.wpi.first.wpilibj.command.Command;

public class Elevate extends Command{
		@Override
		protected void execute() {
			Robot.brakeDisengage.set(true);
    		Robot.brakeEngage.set(false);
    		Robot.elevateTalon.set(0.5);
		}

		@Override
		protected void initialize() {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		protected void end() {
			Robot.brakeDisengage.set(false);
    		Robot.brakeEngage.set(true);
			
		}

		@Override
		protected void interrupted() {
			// TODO Auto-generated method stub
			
		}
}
