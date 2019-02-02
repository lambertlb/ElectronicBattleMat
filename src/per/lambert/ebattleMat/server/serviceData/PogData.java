package per.lambert.ebattleMat.server.serviceData;

/**
 * Pog data server side.
 * 
 * @author LLambert
 *
 */
public class PogData {
	/**
	 * Size of pog in grid squares.
	 */
	private int pogSize;
	/**
	 * Column pog is in grid.
	 */
	private int pogColumn;
	/**
	 * Row pog is in grid.
	 */
	private int pogRow;
	/**
	 * Flag bits for player.
	 */
	private int playerFlags;
	/**
	 * Flag bits for dungeon master.
	 */
	private int dungeonMasterFlags;
	/**
	 * Level of dungeon pog is on.
	 */
	private int dungeonLevel;
	/**
	 * Name of pog.
	 */
	private String pogName;
	/**
	 * URL of pog image.
	 */
	private String pogImageUrl;
	/**
	 * Type of Pog.
	 */
	private String pogType;
	/**
	 * UUID of pog instance.
	 */
	private String uuid;
	/**
	 * UUID of template this pog was created from.
	 */
	private String templateUUID;
	/**
	 * Class of pog.
	 */
	private String pogClass;
	/**
	 * Race of pog.
	 */
	private String race;

	/**
	 * Constructor.
	 */
	public PogData() {
	}

	/**
	 * Constructor.
	 * 
	 * @param pogData to clone
	 */
	public PogData(final PogData pogData) {
		pogSize = pogData.pogSize;
		copyData(pogData);
	}

	/**
	 * Copy data from a different pog.
	 * 
	 * @param pogData with data to copy.
	 */
	private void copyData(final PogData pogData) {
		pogColumn = pogData.pogColumn;
		pogRow = pogData.pogRow;
		playerFlags = pogData.playerFlags;
		dungeonMasterFlags = pogData.dungeonMasterFlags;
		dungeonLevel = pogData.dungeonLevel;
		pogName = pogData.pogName;
		pogImageUrl = pogData.pogImageUrl;
		pogType = pogData.pogType;
		uuid = pogData.uuid;
		templateUUID = pogData.templateUUID;
		pogClass = pogData.pogClass;
		race = pogData.race;
	}

	/**
	 * Update pog with new data.
	 * @param withUpdates with updates
	 */
	public void updatePog(final PogData withUpdates) {
		this.pogColumn = withUpdates.pogColumn;
		this.pogRow = withUpdates.pogRow;
		dungeonLevel = withUpdates.dungeonLevel;
	}

	/**
	 * {@inheritDoc}
	 */
	public PogData clone() {
		PogData clone = new PogData();
		clone.copyData(this);
		return (clone);
	}

	/**
	 * Is pog this type?
	 * 
	 * @param typeToCheck type to check
	 * @return true if is
	 */
	public boolean isType(final String typeToCheck) {
		return (pogType.equals(typeToCheck));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object toCompare) {
		if (toCompare instanceof PogData) {
			return (equals((PogData) toCompare));
		}
		return (false);
	}

	/**
	 * Are these the same?
	 * 
	 * @param toCompare to compare
	 * @return true if same uuid
	 */
	public boolean equals(final PogData toCompare) {
		if (toCompare == null) {
			return (false);
		}
		return (uuid.equals(toCompare.uuid));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return uuid.hashCode();
	}
}
