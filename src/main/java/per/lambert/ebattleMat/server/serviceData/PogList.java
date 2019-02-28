package per.lambert.ebattleMat.server.serviceData;

/**
 * List of pogs.
 * 
 * @author LLambert
 *
 */
public class PogList {
	/**
	 * List of pogs.
	 */
	private PogData[] pogList;

	/**
	 * Get list of pogs.
	 * 
	 * @return list of pogs.
	 */
	public PogData[] getPogList() {
		return pogList;
	}

	/**
	 * Set list of pogs.
	 * 
	 * @param pogList list of pogs.
	 */
	public void setPogList(final PogData[] pogList) {
		this.pogList = pogList;
	}

	/**
	 * Constructor.
	 */
	public PogList() {
		this(0);
	}

	/**
	 * Constructor.
	 * 
	 * @param startingSize starting size.
	 */
	public PogList(final int startingSize) {
		pogList = new PogData[startingSize];
	}

	/**
	 * Update pog if it exists ot add it.
	 * 
	 * @param pog with data.
	 */
	public void addOrUpdate(final PogData pog) {
		for (PogData pogInList : pogList) {
			if (pogInList.equals(pog)) {
				pogInList.fullUpdate(pog);
				return;
			}
		}
		addPog(pog);
	}

	/**
	 * Add new pog to list.
	 * 
	 * @param pog to add
	 */
	public void addPog(final PogData pog) {
		PogData[] newList = new PogData[pogList.length + 1];
		for (int i = 0; i < pogList.length; ++i) {
			newList[i] = pogList[i];
		}
		newList[pogList.length] = pog;
		pogList = newList;
	}

	/**
	 * {@inheritDoc}
	 */
	public PogList clone() {
		PogList newList = new PogList(pogList.length);
		for (int i = 0; i < pogList.length; ++i) {
			newList.pogList[i] = pogList[i].clone();
		}
		return (newList);
	}
}
