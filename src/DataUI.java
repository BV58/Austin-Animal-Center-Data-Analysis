import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Panel;
import java.awt.ScrollPane;

public class DataUI {

	private JFrame frmAustinAnimalIntake;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DataUI window = new DataUI();
					window.frmAustinAnimalIntake.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws FileNotFoundException 
	 */
	public DataUI() throws FileNotFoundException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws FileNotFoundException 
	 */
	private void initialize() throws FileNotFoundException {
		ArrayList<Animal> animals = new ArrayList<>(CSVtoArray("Austin_Animal_Center_Intakes.csv"));
		frmAustinAnimalIntake = new JFrame();
		frmAustinAnimalIntake.setTitle("Austin Animal Center Intake Data");
		frmAustinAnimalIntake.setBounds(100, 100, 965, 654);
		frmAustinAnimalIntake.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAustinAnimalIntake.getContentPane().setLayout(null);
		
		ChartPanel chartPanel = new ChartPanel(null);
		chartPanel.setBounds(0, 0, 939, 535);
		frmAustinAnimalIntake.getContentPane().add(chartPanel);
		
		JButton btnChart_1 = new JButton("Animal Type Pie Chart");
		btnChart_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chartPanel.setChart(animalTypePieChart(animals));

			}
		});
		btnChart_1.setBounds(10, 569, 159, 35);
		frmAustinAnimalIntake.getContentPane().add(btnChart_1);
		
		JButton btnChart_2 = new JButton("Other Animal Type Bar Chart");
		btnChart_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chartPanel.setChart(otherAnimalTypeBarChart(animals));

			}
		});
		btnChart_2.setBounds(179, 569, 196, 35);
		frmAustinAnimalIntake.getContentPane().add(btnChart_2);
		
		JButton btnChart_3 = new JButton("Livestock Bar Chart");
		btnChart_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chartPanel.setChart(livestockBarChart(animals));
			}
		});
		btnChart_3.setBounds(385, 569, 167, 35);
		frmAustinAnimalIntake.getContentPane().add(btnChart_3);
		
		JButton btnChart_4 = new JButton("Monthly Intake Line Chart");
		btnChart_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chartPanel.setChart(monthlyIntakeLineChart(animals));
			}
		});
		btnChart_4.setBounds(562, 569, 195, 35);
		frmAustinAnimalIntake.getContentPane().add(btnChart_4);
		
		JButton btnChart_5 = new JButton("Yearly Intake Line Chart");
		btnChart_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chartPanel.setChart(yearlyIntakeLineChart(animals));

			}
		});
		btnChart_5.setBounds(767, 569, 172, 35);
		frmAustinAnimalIntake.getContentPane().add(btnChart_5);
		
		

	}
	public static JFreeChart animalTypePieChart(ArrayList<Animal> animals) {
		DefaultPieDataset<String> pie = new DefaultPieDataset();
		
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

		return chart;
	}
	
	public static JFreeChart otherAnimalTypeBarChart(ArrayList<Animal> animals) {
		
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
		return chart;
	}
	
	public static JFreeChart livestockBarChart(ArrayList<Animal> animals) { 

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
		return chart;
	}
	
	public static JFreeChart monthlyIntakeLineChart(ArrayList<Animal> animals) {//create an arraylist full of data that stores date 
		
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
	      return chart;
	}
	
	public static JFreeChart yearlyIntakeLineChart(ArrayList<Animal> animals) {//create an arraylist full of data that stores date /
			
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
	      return chart;
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

