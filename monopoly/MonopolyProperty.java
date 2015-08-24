package monopoly;

public class MonopolyProperty {
	private final String name;
	private final int value;
	private final int houses;
	private final ColorGroup group;
	
	public MonopolyProperty(String name, int value, int houses, ColorGroup group) {
		this.name = name;
		this.value = value;
		this.houses = houses;
		this.group = group;
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	public int getHouses() {
		return houses;
	}

	public ColorGroup getGroup() {
		return group;
	}

	public enum ColorGroup {
		DARK_PURPLE, LIGHT_BLUE, PURPLE, ORANGE, RED, YELLOW, GREEN, DARK_BLUE
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s, Value: %d, Group: %s, Houses: %d", name, value, group, houses);
	}
}