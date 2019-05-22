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
 * Gender enumeration.
 * 
 * @author LLambert
 *
 */
public final class Gender {
	/**
	 * Next available value.
	 */
	private static int nextValue = 0;
	/**
	 * Pog is Male.
	 */
	public static final Gender MALE = new Gender("Male"); // 0
	/**
	 * Pog is female.
	 */
	public static final Gender FERMALE = new Gender("Female"); // 1
	/**
	 * Has no gender.
	 */
	public static final Gender NEUTRAL = new Gender("Neutral"); // 2
	/**
	 * Map of names vs gender.
	 */
	private static Map<String, Gender> nameMap;

	/**
	 * Value of item.
	 */
	private int value;

	/**
	 * Get value.
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Name of item.
	 */
	private String name;

	/**
	 * Get name.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * expose to sub-classes.
	 * 
	 * @return map of names
	 */
	protected static Map<String, Gender> getNameMap() {
		return nameMap;
	}

	/**
	 * Value vs name.
	 */
	private static Map<Integer, Gender> valueMap;

	/**
	 * Constructor.
	 * 
	 * @param genderName gender name
	 */
	private Gender(final String genderName) {
		this.value = nextValue++;
		name = genderName;
		if (nameMap == null) {
			nameMap = new LinkedHashMap<String, Gender>();
			valueMap = new LinkedHashMap<Integer, Gender>();
		}
		nameMap.put(genderName, this);
		valueMap.put(getValue(), this);
	}

	/**
	 * Get value for this name.
	 * 
	 * @param name to get
	 * @return gender
	 */
	public static Gender valueOf(final String name) {
		if (name == null || name.isEmpty()) {
			return (MALE);
		}
		return (nameMap.get(name));
	}

	/**
	 * Get gender this ordinal.
	 * 
	 * @param ordinal to get
	 * @return gender
	 */
	public static Gender valueOf(final int ordinal) {
		if (ordinal < 0 || ordinal >= nextValue) {
			return (MALE);
		}
		return (valueMap.get(ordinal));
	}

	/**
	 * Get collection of genders.
	 * 
	 * @return collection of genders.
	 */
	public static Collection<Gender> getValues() {
		ArrayList<Gender> list = new ArrayList<Gender>();
		for (Gender gender : getNameMap().values()) {
			list.add(gender);
		}
		return (list);
	}
}
