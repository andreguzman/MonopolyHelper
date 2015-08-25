package monopoly;

public class MonopolyProperty implements Comparable<MonopolyProperty> {
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
	public int compareTo(MonopolyProperty o) {
		return this.value - o.value;
	}

	@Override
	public String toString() {
		return String.format("Name: %s, Value: %d, Group: %s, Houses: %d", name, value, group, houses);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MonopolyProperty)) {
			return false;
		}
		MonopolyProperty other = (MonopolyProperty) obj;
		if (group != other.group) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (value != other.value) {
			return false;
		}
		return true;
	}
}