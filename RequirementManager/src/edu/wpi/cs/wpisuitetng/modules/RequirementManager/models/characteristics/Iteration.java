package edu.wpi.cs.wpisuitetng.modules.RequirementManager.models.characteristics;

/**
 * An iteration in a project. Requirements can be assigned to an iteration.
 * @author Gabriel McCormick
 */
public class Iteration {

	private String name;
	
	public Iteration(String name)
	{
		this.name = name;
		if(name.trim().length() == 0) this.name = "Backlog";
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}
	
	public boolean equals(Iteration that){
		if (this.name == that.getName())
			return true;
		else 
			return false;
	}
}
