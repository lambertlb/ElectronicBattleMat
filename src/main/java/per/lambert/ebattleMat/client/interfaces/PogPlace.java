/*
 * Copyright (C) 2019 Leon Lambert.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package per.lambert.ebattleMat.client.interfaces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Enumeration of where Pogs can reside on server.
 * 
 * @author LLambert
 *
 */
public final class PogPlace extends FlagBit {
	/**
	 * Next available value.
	 */
	private static int nextValue = 0;
	/**
	 * Resides in Common resource area available to all dungeons.
	 */
	public static final PogPlace COMMON_RESOURCE = new PogPlace("Common Resource");
	/**
	 * Resides in dungeon specific resource area.
	 */
	public static final PogPlace DUNGEON_RESOURCE = new PogPlace("Dungeon Resource");
	/**
	 * Resides in dungeon instance area.
	 */
	public static final PogPlace DUNGEON_INSTANCE = new PogPlace("Dungeon Instance");
	/**
	 * Resides in Session common resource area.
	 */
	public static final PogPlace SESSION_RESOURCE = new PogPlace("Session Resource");
	/**
	 * Resides in Session level instance area.
	 */
	public static final PogPlace SESSION_INSTANCE = new PogPlace("Session Instance");
	/**
	 * Invalid place.
	 */
	public static final PogPlace INVALID = new PogPlace("Invalid Place");
	

	/**
	 * Map of names vs flag bit.
	 */
	private static Map<String, FlagBit> nameMap;

	/**
	 * expose to sub-classes.
	 * 
	 * @return map of names
	 */
	protected static Map<String, FlagBit> getNameMap() {
		return nameMap;
	}

	/**
	 * Value vs flag bit.
	 */
	private static Map<Integer, FlagBit> valueMap;

	/**
	 * Constructor.
	 * 
	 * @param flagName flag name
	 */
	private PogPlace(final String flagName) {
		super(flagName, nextValue);
		++nextValue;
		if (nameMap == null) {
			nameMap = new LinkedHashMap<String, FlagBit>();
			valueMap = new LinkedHashMap<Integer, FlagBit>();
		}
		nameMap.put(flagName, (FlagBit) this);
		valueMap.put(getValue(), (FlagBit) this);
	}

	/**
	 * Get value for this name.
	 * 
	 * @param name to get
	 * @return flag bit
	 */
	public static FlagBit valueOf(final String name) {
		return (nameMap.get(name));
	}

	/**
	 * Get flag bit for this ordinal.
	 * 
	 * @param ordinal to get
	 * @return flag bit
	 */
	public static FlagBit valueOf(final int ordinal) {
		return (valueMap.get(ordinal));
	}

	/**
	 * Get collection of flags.
	 * 
	 * @return collection of flags.
	 */
	public static Collection<FlagBit> getValues() {
		ArrayList<FlagBit> list = new ArrayList<FlagBit>();
		for (FlagBit flagBit : getNameMap().values()) {
			list.add(flagBit);
		}
		return (list);
	}
}
