package frc.robot.subsystems.vision;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * Wrapper class for getting and setting Limelight NetworkTable values.
 * 
 */

public class Limelight extends Subsystem implements IVisionTarget{
	private NetworkTableInstance table = null;
	private static Limelight mInstance;

	private IVisionTarget[] mTargets = new IVisionTarget[10];

	private PeriodicIO mPeriodicIO = new PeriodicIO();

	private LightMode mLightMode;
	private CameraMode mCameraMode;
	private StreamMode mStreamMode;
	private Target mTargetSelected;
	
	
	
	public static Limelight getInstance() {
		if (mInstance == null) {
            mInstance = new Limelight();
        }
        return mInstance;
    }
	
	public Limelight() {
		//Set up table and game targets
		table = NetworkTableInstance.getDefault();
		mTargets[Target.HATCH.ordinal()] = new HatchTarget();

		//Set default values
		mTargetSelected = Target.HATCH;
		mLightMode = LightMode.DEFAULT;
		mCameraMode = CameraMode.VISION;
		mStreamMode = StreamMode.SECONDARY;
	}

	public enum Target {
		HATCH,
		BALL;
		
		protected static Target[] targets = Target.values();
	}

	public enum LightMode {
		DEFAULT,
		ON,
		BLINK,
		OFF;

		protected static LightMode[] lightModes = LightMode.values();
	}

	public enum CameraMode {
		VISION, 
		DRIVER;

		protected static CameraMode[] cameraModes = CameraMode.values();
	}
	
	public enum StreamMode {
		SIDE_BY_SIDE,
		MAIN,
		SECONDARY;

		protected static StreamMode[] streamModes = StreamMode.values();
	}

	public boolean hasTarget() {return mPeriodicIO.hasTarget;}

	public double getTx() {return mPeriodicIO.tx;}

	public double getTy() {return mPeriodicIO.ty;}

	public double getTa() {return mPeriodicIO.ta;}

	public double getTs() {return mPeriodicIO.ts;}

	public void setLedMode(LightMode mode) {mLightMode = mode;}

	public void setCameraMode(CameraMode mode) {mCameraMode = mode;}

	public void setPipeline(Target target) {mTargetSelected = target;}

	public Target getTargetSelected() {return mTargetSelected;}
	
	public void setSteam(StreamMode mode) {mStreamMode = mode;}

	private NetworkTableEntry getValue(String key) {return table.getTable("limelight").getEntry(key);}

	@Override
	public IVisionTarget getTargetType() {return mTargets[mTargetSelected.ordinal()];}
	@Override
	public double getDistance() {return mTargets[mTargetSelected.ordinal()].getDistance();}
	@Override
	public double getHeightAngle() {return mTargets[mTargetSelected.ordinal()].getHeightAngle();}
	@Override
	public double getOffsetAngle() {return mTargets[mTargetSelected.ordinal()].getOffsetAngle();}
	@Override
	public double getSidewaysAngle() {return mTargets[mTargetSelected.ordinal()].getSidewaysAngle();}

	
	public boolean checkSystem() {
		return false;
	}

	
	public void outputTelemetry() {
		//post to smart dashboard periodically
        SmartDashboard.putBoolean("Limeligh Target Aquired", hasTarget());
        SmartDashboard.putNumber("Limelight X", getTx());
        SmartDashboard.putNumber("Limelight Y", getTy());
        SmartDashboard.putNumber("Limelight S", getTs());
		SmartDashboard.putNumber("Limelight Area", getTa());
	}

	
	public void stop() {

	}
	
	
    public synchronized void readPeriodicInputs() {
		mPeriodicIO.hasTarget = getValue("tv").getDouble(0) == 1;
		mPeriodicIO.tx = getValue("tx").getDouble(0.00);
		mPeriodicIO.ty = getValue("ty").getDouble(0.00);
		mPeriodicIO.ta = getValue("ta").getDouble(0.00);
		mPeriodicIO.ts = getValue("ts").getDouble(0.00);
		mPeriodicIO.targetSelected = Target.targets[(int) getValue("pipeline").getDouble(0)];
		mPeriodicIO.cameraMode = CameraMode.cameraModes[(int) getValue("camMode").getDouble(0)];
		mPeriodicIO.streamMode = StreamMode.streamModes[(int) getValue("stream").getDouble(0)];
		mPeriodicIO.lightMode = LightMode.lightModes[(int) getValue("ledMode").getDouble(0)];
	}

	
	public synchronized void writePeriodicOutputs() {
		if(mPeriodicIO.targetSelected != mTargetSelected) getValue("pipeline").setNumber(mTargetSelected.ordinal());
		if(mPeriodicIO.lightMode != mLightMode) getValue("ledMode").setNumber(mLightMode.ordinal());
		if(mPeriodicIO.streamMode != mStreamMode) getValue("stream").setNumber(mStreamMode.ordinal());
		if(mPeriodicIO.cameraMode!= mCameraMode) getValue("camMode").setNumber(mCameraMode.ordinal());
		
	}

	public static class PeriodicIO {
		//Inputs
		public boolean hasTarget;
		public double tx;
		public double ty;
		public double ta;
		public double ts;
		public Target targetSelected;
		public StreamMode streamMode;
		public LightMode lightMode;
		public CameraMode cameraMode;
	}

	@Override
	protected void initDefaultCommand() {

	}

	
}
