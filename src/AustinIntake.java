import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Set;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

public class AustinIntake {

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<Animal> animals = new ArrayList<>(CSVtoArray("Austin_Animal_Center_Intakes.csv"));
		animalTypePieChart(animals);
		OtherAnimalTypeBarChart(animals);
		LivestockBarChart(animals);
		yearlyIntakeLineChart(animals);
		monthlyIntakeLineChart(animals);
	}

	/**
	 * @param ArraList of object type Animal, named animals
	 * Counts the number of dog, cat, other, and livestock animals
	 * Outputs pie chart based off this data
	 */
	public static void animalTypePieChart(ArrayList<Animal> animals) {
		DefaultPieDataset<String> pie = new DefaultPieDataset();
		pie.setValue("Dogs", 25);
		
		ArrayList<String> totAnimals = new ArrayList<>();
		
		for (Animal animal : animals) {
			totAnimals.add(animal.getBasicAnimalType());
		}
		
		pie.setValue("Dogs", Collections.frequency(totAnimals, "Dog"));
		pie.setValue("Cat", Collections.frequency(totAnimals, "Cat"));
		pie.setValue("Bird", Collections.frequency(totAnimals, "Bird"));
		pie.setValue("Livestock", Collections.frequency(totAnimals, "Livestock"));
		pie.setValue("Other", Collections.frequency(totAnimals, "Other"));

		JFreeChart chart = ChartFactory.createPieChart("Animal Intake Types",pie, true, true, true);

		try {

			ChartUtils.saveChartAsJPEG(new File("C:\\Users\\mrluc\\Desktop\\AnimalTypePieChart.jpeg"), chart, 600, 600);
		}catch(Exception e) {
			System.err.println("error: "+e);
		}
	}
	
	public static void OtherAnimalTypeBarChart(ArrayList<Animal> animals) throws FileNotFoundException {
		DefaultCategoryDataset bar = new DefaultCategoryDataset();
		ArrayList<String> totOtherAnimals = new ArrayList<>();

		ArrayList<String> otherAnimalsList = new ArrayList<>(CSVtoOtherArray("OtherAnimal.csv")); //all animal names, only 1 of each
		
		
		for (Animal animal : animals) {//iterate over Animals and add all Other to list
			totOtherAnimals.add(animal.getAdvancedAnimalType());
		}
		for (String other : otherAnimalsList) { //iterate over otherAnimalsList, 
			if (Collections.frequency(totOtherAnimals, other)>=25) {
			bar.addValue(Collections.frequency(totOtherAnimals, other), other, ""); //this needs to add value of other, plus check list
			}
		}
		
		JFreeChart chart = ChartFactory.createBarChart("Other Animal Intake Types","Animal Types", "Number of Animals", bar);
		try {
			ChartUtils.saveChartAsJPEG(new File("C:\\Users\\mrluc\\Desktop\\OtherAnimalTypeBarChart.jpeg"), chart, 1000, 1000);
		}catch(Exception e) {
			System.err.println("error: "+e);
		}
	}
	
	public static void LivestockBarChart(ArrayList<Animal> animals) throws FileNotFoundException { 

		DefaultCategoryDataset bar = new DefaultCategoryDataset();
		ArrayList<String> otherAnimalsList = new ArrayList<>(); 
		
		for (Animal animal : animals) {
			if(animal.getAdvancedAnimalType().equals("Livestock")) {
				otherAnimalsList.add(animal.getBreed());
			}
			
		}
		for (String other : otherAnimalsList) { 
			
			bar.addValue(Collections.frequency(otherAnimalsList, other), other, ""); 
		}
		
		JFreeChart chart = ChartFactory.createBarChart("Livestock Animal Types","Livestock", "Number of Animals", bar);
		try {
			ChartUtils.saveChartAsJPEG(new File("LiveStockBarChart.jpeg"), chart, 1000, 1000);
		}catch(Exception e) {
			System.err.println("error: "+e);
		}
	}
	
	
	public static void monthlyIntakeLineChart(ArrayList<Animal> animals) {//create an arraylist full of data that stores date 
		
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      ArrayList<String> dateData = new ArrayList<>();
	      
	      for(Animal animal : animals) {
	    	  String[] s = animal.getDate().split("/");
	    	  dateData.add(s[1]);
	      }

	      for (int i = 1; i<=12; i++) {
	    	  dataset.addValue(Collections.frequency(dateData, Integer.toString(i)), "Animal Intake by Month", Integer.toString(i));
	 
	      }
	


	      JFreeChart chart = ChartFactory.createLineChart("Animal Intake Per Month", "Month", "Total Animals", dataset);
			try {
				ChartUtils.saveChartAsJPEG(new File("C:\\\\Users\\\\mrluc\\\\Desktop\\\\Monthly Intake Line Chart.jpeg"), chart, 1000, 1000);
			}catch(Exception e) {
				System.err.println("error: "+e);
			}
	}
	public static void yearlyIntakeLineChart(ArrayList<Animal> animals) {//create an arraylist full of data that stores date /
			
	      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
	      ArrayList<String> dateData = new ArrayList<>();
	      
	      for(Animal animal : animals) {
	    	  String[] s = animal.getDate().split("/");
	    	  dateData.add(s[2]);
	      }

	      for (int i = Integer.parseInt(dateData.get(0)); i<=Integer.parseInt(dateData.get(dateData.size()-1)); i++) {
	    	  dataset.addValue(Collections.frequency(dateData, Integer.toString(i)), "Animal Intake by Year", Integer.toString(i));
	    	
	      }
	


	      JFreeChart chart = ChartFactory.createLineChart("Animal Intake Per Year", "Years", "Total Animals", dataset);
			try {
				ChartUtils.saveChartAsJPEG(new File("C:\\\\Users\\\\mrluc\\\\Desktop\\\\Yearly Intake Line Chart.jpeg"), chart, 1000, 1000);
			}catch(Exception e) {
				System.err.println("error: "+e);
			}
	}

	
	private static ArrayList<String> CSVtoOtherArray(String filename) {
		ArrayList<String> otherAnimals = new ArrayList<>();
		
		try {
			File file = new File(filename);
			Scanner in = null;
		try {
			in = new Scanner(file);
		    String[] temp = in.nextLine().split(",");
		    for (String s : temp) {
		    	otherAnimals.add(s.strip());
		    }
		}
		catch (NoSuchElementException e) {
			System.err.println("Record Error: " + e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Parse Error: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Data Error: " + e.getMessage());
		} finally {
			//in.close(); Was giving a different error since there was nothing to close
		}
		}catch (FileNotFoundException e) {
			System.err.println("File Unavailable: " + e.getMessage());
		} 
		
		return otherAnimals;	
	}

	public static ArrayList<Animal> CSVtoArray(String filename) throws FileNotFoundException {
		ArrayList<Animal> animals = new ArrayList<>();
		
		try {
			File file = new File(filename);
			Scanner in = null;
		try {
			in = new Scanner(file);
			in.nextLine();
			 
			while(in.hasNextLine()) {
		        List<String> temp = Arrays.asList(in.nextLine().split(","));
		        Animal tempAnimal = new Animal(temp.get(0), temp.get(1), temp.get(2), temp.get(3), temp.get(4), temp.get(5), temp.get(6), temp.get(7), temp.get(8));
		        animals.add(tempAnimal);
				}
		}
		catch (NoSuchElementException e) {
			System.err.println("Record Error: " + e.getMessage());
		} catch (IndexOutOfBoundsException e) {
			System.err.println("Parse Error: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Data Error: " + e.getMessage());
		} finally {
			//in.close(); Was giving a different error since there was nothing to close
		}
		}catch (FileNotFoundException e) {
			System.err.println("File Unavailable: " + e.getMessage());
		} 
		
		return animals;	}


	
	
}
