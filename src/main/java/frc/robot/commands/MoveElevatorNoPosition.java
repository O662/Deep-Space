// /*----------------------------------------------------------------------------*/
// /* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
// /* Open Source Software - may be modified and shared by FRC teams. The code   */
// /* must be accompanied by the FIRST BSD license file in the root directory of */
// /* the project.                                                               */
// /*----------------------------------------------------------------------------*/

// package frc.robot.commands;

// import edu.wpi.first.wpilibj.command.Command;
// import frc.robot.Robot;
// import frc.robot.RobotPreferences;

// public class MoveElevatorNoPosition extends Command {
//   double s;
//   boolean goodPlace;
//   public MoveElevatorNoPosition(double speed) {
//     // Use requires() here to declare subsystem dependencies
//     // eg. requires(chassis);
//     goodPlace = false;
//     s = speed;
//     requires(Robot.elevator);
//   }

//   // Called just before this Command runs the first time
//   @Override
//   protected void initialize() {
//   }

//   // Called repeatedly when this Command is scheduled to run
//   @Override
//   protected void execute() {
//     if(Robot.elevator.getTranslateHeight() >= 44 && Robot.hatchPlacer.getPusher() ==false){
//       goodPlace = false;
//       Robot.elevator.moveElevator(0);
//       Robot.hatchPlacer.togglePusher();
//     }

//     else if(Robot.elevator.getTranslateHeight() < 44){
//       goodPlace = false;
//        Robot.elevator.moveElevator(s);
//     }
//     else if(Robot.elevator.getTranslateHeight() >= 44 && Robot.hatchPlacer.getPusher() == true){
//       goodPlace = true;
//       Robot.elevator.moveElevator(s);
//     }
    
//     System.out.println("Moving @ "+s);
//   }

//   // Make this return true when this Command no longer needs to run execute()
//   @Override
//   protected boolean isFinished() {
//     if(s == 0 && Robot.elevator.getTranslateHeight() < 44){
//       return true;
//     }
//     if(s == 0 && goodPlace == true){
//       return true;
//     }
//    // if(Robot.elevator.getLaser()){
//     //  return true;
//     //}
//     return false;
//   }

//   // Called once after isFinished returns true
//   @Override
//   protected void end() {
//     Robot.elevator.endElevator();
//    // Robot.elevator.zeroEncoder();
//   }

//   // Called when another command which requires one or more of the same
//   // subsystems is scheduled to run
//   @Override
//   protected void interrupted() {
//   }
// }
