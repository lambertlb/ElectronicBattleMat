package per.lambert.ebattleMat.client.interfaces;

/**
 * Base class for managing flag bits.
 * 
 * This will simulate an enumeration but has the advantage of being sub-classed.
 * 
 * @author LLambert
 *
 */
public abstract class FlagBits {
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
	 * Constructor.
	 * 
	 * @param flagName of value
	 * @param value of ordinal
	 */
	protected FlagBits(final String flagName, final int value) {
		this.value = value;
		name = flagName;
	}
}
