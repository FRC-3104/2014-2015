package org.usfirst.frc.team3104.robot;

import edu.wpi.first.wpilibj.command.Command;

public class Strafe extends Command{
		@Override
		protected void execute() {
			Robot.drive.mecanumDrive_Cartesian(1, 0, 0, 0);
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
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void interrupted() {
			// TODO Auto-generated method stub
			
		}
}
