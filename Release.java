package org.usfirst.frc.team3104.robot;

import edu.wpi.first.wpilibj.command.Command;

public class Release extends Command{
		@Override
		protected void execute() {
	    	Robot.pneumaticRelease.set(true);
	    	Robot.pneumaticTrigger.set(false);
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
