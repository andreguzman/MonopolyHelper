package monopoly;

import java.util.HashSet;
import java.util.Set;

public class MonopolyPlayer {
	private String name;
	private int cash;
	private Set<MonopolyProperty> properties;
	
	public MonopolyPlayer(String name) {
		this.name = name;
		cash = 2000;
		properties = new HashSet<MonopolyProperty>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getCash() {
		return cash;
	}
	
	public boolean addProperty(MonopolyProperty property) {
		return properties.add(property);
	}
	
	public boolean addProperties(MonopolyProperty addProperties) {
		return properties.add(addProperties);
	}
	
	public int addCash(int addAmount) {
		cash += addAmount;
		return cash;
	}
	
	public int subtractCash(int reduceAmount) {
		cash -= reduceAmount;
		return cash;
	}
	
	public Set<MonopolyProperty> getProperties() {
		return properties;
	}
	
	public boolean checkIfPlayerHasProperty(MonopolyProperty checkProperty) {
		return properties.contains(checkProperty);
	}
}