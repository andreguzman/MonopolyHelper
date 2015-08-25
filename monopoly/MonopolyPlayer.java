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

	public MonopolyPlayer(String name, int startingCash, Set<MonopolyProperty> properties) {
		this.name = name;
		cash = startingCash;
		properties = new HashSet<>(properties);
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

	public boolean removeProperty(MonopolyProperty property) {
		return properties.remove(property);
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

	public boolean tradeCash(MonopolyPlayer other, int amount) {
		if (amount < 0 || getCash() - amount < 0) {
			return false;
		}

		subtractCash(amount);
		other.addCash(amount);
		return true;
	}

	public boolean tradeProperty(MonopolyPlayer other, MonopolyProperty tradeProperty) {
		if (!checkIfPlayerHasProperty(tradeProperty)
				|| other.checkIfPlayerHasProperty(tradeProperty)) {
			return false;
		}

		return removeProperty(tradeProperty) && other.addProperty(tradeProperty);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cash;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
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
		if (!(obj instanceof MonopolyPlayer)) {
			return false;
		}
		MonopolyPlayer other = (MonopolyPlayer) obj;
		if (cash != other.cash) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (properties == null) {
			if (other.properties != null) {
				return false;
			}
		} else if (!properties.equals(other.properties)) {
			return false;
		}
		return true;
	}
}