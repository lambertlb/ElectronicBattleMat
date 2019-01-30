package per.lambert.ebattleMat.server.serviceData;

/**
 * Session Data for a dungeon.
 * 
 * @author LLambert
 *
 */
public class DungeonSessionData {
	/**
	 * session version.
	 */
	private int version;

	/**
	 * get session version.
	 * 
	 * @return session version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * increment session version.
	 */
	public void increamentVersion() {
		if (++version == Integer.MAX_VALUE) {
			version = 1;
		}
	}

	/**
	 * Session name.
	 */
	private String sessionName;

	/**
	 * get Session name.
	 * 
	 * @return Session name.
	 */
	public String getSessionName() {
		return sessionName;
	}

	/**
	 * set Session name.
	 * 
	 * @param sessionName Session name.
	 */
	public void setSessionName(final String sessionName) {
		this.sessionName = sessionName;
	}

	/**
	 * dungeon UUID.
	 */
	private String dungeonUUID;

	/**
	 * get dungeon UUID.
	 * 
	 * @return dungeon UUID.
	 */
	public String getDungeonUUID() {
		return dungeonUUID;
	}

	/**
	 * set dungeon UUID.
	 * 
	 * @param dungeonUUID dungeon UUID.
	 */
	public void setDungeonUUID(final String dungeonUUID) {
		this.dungeonUUID = dungeonUUID;
	}

	/**
	 * session UUID.
	 */
	private String sessionUUID;

	/**
	 * get session UUID.
	 * 
	 * @return session UUID.
	 */
	public String getSessionUUID() {
		return sessionUUID;
	}

	/**
	 * set session UUID.
	 * 
	 * @param sessionUUID session UUID.
	 */
	public void setSessionUUID(final String sessionUUID) {
		this.sessionUUID = sessionUUID;
	}

	/**
	 * players in this session.
	 */
	private PogData[] players = new PogData[0];

	/**
	 * get players in this session.
	 * 
	 * @return players in this session.
	 */
	public PogData[] getPlayers() {
		return players;
	}

	/**
	 * set players in this session.
	 * 
	 * @param players players in this session.
	 */
	public void setPlayers(final PogData[] players) {
		this.players = players;
	}
	/**
	 * Level for this session.
	 */
	private DungeonSessionLevel[] sessionLevels;

	/**
	 * get Level for this session.
	 * @return Level for this session.
	 */
	public DungeonSessionLevel[] getSessionLevels() {
		return sessionLevels;
	}

	/**
	 * set Level for this session.
	 * @param sessionLevels Level for this session.
	 */
	public void setSessionLevels(final DungeonSessionLevel[] sessionLevels) {
		this.sessionLevels = sessionLevels;
	}

	/**
	 * Constructor for session data.
	 * @param newSessionName new session name
	 * @param dungeonUUID dungeon UUID
	 * @param sessionUUID session UUID
	 */
	public DungeonSessionData(final String newSessionName, final String dungeonUUID, final String sessionUUID) {
		version = 1;
		sessionName = newSessionName;
		this.dungeonUUID = dungeonUUID;
		this.sessionUUID = sessionUUID;
	}
}
