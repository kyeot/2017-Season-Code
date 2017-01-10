package org.usfirst.frc.team2783.robot.vision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionData {

	private NetworkTable sizeDataTable;
	private NetworkTable contourDataTable;
	
	private ImageSize imageSize;
	
	// enum that has image sizes to assign to images received with NetworkTables
	public enum ImageSize {
		R640x480 (640, 480),
		R320x240 (320, 240),
		R160x120 (160, 120);
		
		private final double x;
		private final double y;
		
		ImageSize(double x, double y){
			this.x = x;
			this.y = y;
		}
		
		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

	}
	
	// Getting NetworkTable values
	public VisionData(){
		sizeDataTable = NetworkTable.getTable("GRIP/tapeTrackingImageSize");
		contourDataTable = NetworkTable.getTable("GRIP/tapeTrackingCountours");
	}
	
	public ImageSize getImageSize(){
		
		String xSizeString = sizeDataTable.getString("x", "None");
		String ySizeString = sizeDataTable.getString("y", "None");
		
		// Assign imageSize to enum value by using NetworkTable values
		if(xSizeString == "640" && ySizeString == "480"){
			imageSize = ImageSize.R640x480;
		} else if(xSizeString == "320" && ySizeString == "240"){
			imageSize = ImageSize.R320x240;
		} else if(xSizeString == "160" && ySizeString == "120"){
			imageSize = ImageSize.R160x120;
		} else {
			imageSize = null;
		}
		
		return imageSize;
		
	}
	
	public ArrayList<Contour> getContours(){
		
		ArrayList<Contour> contourArray = new ArrayList<>();
		
		// Putting image values from NetworkTables into arrays 
		double[] contourArea = contourDataTable.getNumberArray("area", new double[0]);
		double[] contourCenterX = contourDataTable.getNumberArray("centerX", new double[0]);
		double[] contourCenterY = contourDataTable.getNumberArray("centerY", new double[0]);
		double[] contourWidth = contourDataTable.getNumberArray("width", new double[0]);
		double[] contourHeight = contourDataTable.getNumberArray("height", new double[0]);
		double[] contourSolidity = contourDataTable.getNumberArray("solidity", new double[0]);
		
		// Using image data arrays to assign one value from each array to a Contour; putting those into an ArrayList
		if(contourArea.length > 0) {
			for(int i=0; i >= contourArea.length; i++){
				contourArray.add(new Contour(
						contourArea[i], contourCenterX[i], 
						contourCenterY[i], contourWidth[i], 
						contourHeight[i], contourSolidity[i]));
			}
		}
		
		return contourArray;

	}
	
	public ArrayList<Contour> getGoals(){
		
		ArrayList<Contour> contourFilterArray = getContours();
		ArrayList<Contour> goalArray = new ArrayList<>();
		
		// TODO: Filter array based upon aspect ratio (and min area?) of contours
		goalArray = contourFilterArray;
		
		/*
		// Filtering getContours by Aspect Ratio and putting them into an ArrayList
		for(int i = 0; i >= contourFilterArray.size(); i++){
			double aspectRatio = contourFilterArray.get(i).getWidth()/contourFilterArray.get(i).getHeight();
			if(aspectRatio < 3 && aspectRatio > .25){
				goalArray.add(contourFilterArray.get(i));
			}
		}
		*/
			
		return goalArray;
		
	}
	
	public Contour getLargestGoal(){
		
		ArrayList<Contour> sortedGoals = new ArrayList<>();
		
		// Sort getGoals ArrayList by area, highest to lowest
		sortedGoals = getGoals();
		Collections.sort(sortedGoals, 
			new Comparator<Contour>() {
				@Override
				public int compare(Contour cont1, Contour cont2) {
					if (cont1.getArea() < cont2.getArea()) {
						return -1;
					} else if (cont1.getArea() > cont2.getArea()) {
						return 1;
					} else {
						return 0;
					}
				}
			}
		);
		
		// Return largest
		//TODO: get 0 or length - 1
		return sortedGoals.get(0);
		
	}
	
	public double getDistanceToGoal(){
		
		double resX = imageSize.x;
		
		//Using getLargestGoal width and the image size x value, return distance to goal
		return (20*resX)/(2*(getLargestGoal().getWidth())*Math.tan(1.16937));
		
	}

}
