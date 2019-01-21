package per.lambert.ebattleMat.client.interfaces;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public enum PlayerFlag {
	DEAD("Dead"),				// 1
	IS_FEMALE("Is Female"),		// 2
	HAS_NO_SEX("Has no sex"),	// 4
	INVISIBLE("Invisible");		// 8

	private int value;
	private String name;

	private static Map<String, PlayerFlag> flagMap;
	private static int nextValue = 1;

	PlayerFlag(String flagName) {
		setPogFlag(flagName);
	}

	private void setPogFlag(String flagName) {
		value = PlayerFlag.nextValue;
		PlayerFlag.nextValue <<= 1;
		name = flagName;
		if (flagMap == null ) {
			flagMap = new LinkedHashMap<String, PlayerFlag>();
		}
		PlayerFlag.flagMap.put(flagName, this);
	}

	public int getValue() {
		return (value);
	}

	public String getName() {
		return (name);
	}

	public static Collection<PlayerFlag> getValues() {
		return (flagMap.values());
	}
}
