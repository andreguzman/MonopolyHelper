package monopoly;

import java.util.Set;

public class MonopolyBank extends MonopolyPlayer {
	public static final String BANK_NAME = "Bank";

	public MonopolyBank(Set<MonopolyProperty> allProperties) {
		super(BANK_NAME, Integer.MAX_VALUE, allProperties);
	}


	@Override
	public int addCash(int addCash) {
		return 0;
	}

	@Override
	public int subtractCash(int removeCash) {
		return 0;
	}

	public boolean tradeCash(MonopolyPlayer other, int amount) {
		return false;
	}

	public boolean tradeProperty(MonopolyPlayer other, MonopolyProperty tradeProperty) {
		return false;
	}
}
