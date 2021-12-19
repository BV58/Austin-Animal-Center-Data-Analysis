
public class Animal {

	private String Time;
	private String Date; 
	private String IntakeType; 
	private String AdvancedAnimalType; 
	private String BasicAnimalType;
	private String Age; 
	private String Breed; 
	private String Color;
	private String IntakeCondition;
	
	public Animal() {
		
	}
	
	public Animal(String Time, String Date, String IntakeType, String IntakeCondition, String AdvancedAnimalType, String BasicAnimalType,  String Age, String Breed, String Color) {
		this.Time = Time;
		this.Date = Date; 
		this.IntakeType = IntakeType; 
		this.AdvancedAnimalType = AdvancedAnimalType; 
		this.BasicAnimalType = BasicAnimalType;
		this.Age = Age; 
		this.Breed = Breed; 
		this.Color = Color;
		this.IntakeCondition = IntakeCondition;
	}
	
	public String getIntakeCondition() {
		return IntakeCondition;
	}
	
	public String getTime() {
		return Time;
	}

	public String getDate() {
		return Date;
	}

	public String getIntakeType() {
		return IntakeType;
	}

	public String getAdvancedAnimalType() {
		return AdvancedAnimalType;
	}
	
	public String getBasicAnimalType()	{
		return BasicAnimalType;
	}
	
	public String getAge() {
		return Age;
	}

	public String getBreed() {
		return Breed;
	}

	public String getColor() {
		return Color;
	}
	


}
