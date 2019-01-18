/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.lib.drivers;

/**
 * Add your docs here.
 */
public interface IAssisstantControlBoard {
	/* Just sit there and look pretty, will ya? */
	
	public default UnknownTruth getAmIAlive() {return null;}
	
	
	public enum UnknownTruth {
		//YES,
		NO,
		MAYBE,
		WHO_EVEN_KNOWS_AT_THIS_POINT,
		IS_THIS_THE_REAL_LIFE_IS_THIS_JUST_FANTASY,
		UNDEFINED,
		NO_SOLUTION
	}
}
