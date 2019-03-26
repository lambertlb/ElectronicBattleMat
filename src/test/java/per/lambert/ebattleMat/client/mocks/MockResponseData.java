package per.lambert.ebattleMat.client.mocks;

/**
 * Hold strings of mock responses.
 * 
 * @author LLambert
 *
 */
public final class MockResponseData {
	/**
	 * Hide Constructor.
	 */
	private MockResponseData() {
	}

	/**
	 * Login response.
	 */
	public static final String LOGIN_RESPONSE = "{\"token\":355,\"error\":0}";
	/**
	 * Get dungeon list response.
	 */
	public static final String GETDUNGEONLISTRESPONSE = "{\"dungeonNames\":[\"Template Dungeon\",\"Dungeon 1\",\"Dungeon 2\"],\"dungeonUUIDS\":[\"template-dungeon\",\"dungeon1-template\",\"3c46b116-dd50-4b33-bfd8-d1a400f35292\"],\"dungeonDirectories\":[\"/dungeonData/dungeons/TemplateDungeon\",\"/dungeonData/dungeons/dungeon1\",\"/dungeonData/dungeons/dungeon2\"]}";
	/**
	 * Get session list respong for dungeon 1.
	 */
	public static final String GETSESSIONLISTRESPONSE = "{\"dungeonUUID\":\"dungeon1-template\",\"sessionNames\":[\"Session 1\"],\"sessionUUIDs\":[\"e3eb2220-2d31-4b5a-ad9f-063624ac209c\"]}";
	/**
	 * Get list of monster pog templates.
	 */
	public static final String LOADMONSTERPOGTEMPLATES = "{\"pogList\":[{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Male Kobold\",\"pogImageUrl\":\"https://vignette.wikia.nocookie.net/forgottenrealms/images/f/f3/Monster_Manual_5e_-_Kobold_-_p195.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"Male-Kobold\",\"templateUUID\":\"Male-Kobold\",\"pogClass\":\"Fighter\",\"race\":\"Kobold\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":2,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Female Kobold\",\"pogImageUrl\":\"https://pre00.deviantart.net/9204/th/pre/f/2016/172/6/a/kalbold_femalekoboldfighter_color_01_by_prodigyduck-da75cmb.png\",\"pogType\":\"MONSTER\",\"uuid\":\"Female-Kobold\",\"templateUUID\":\"Female-Kobold\",\"pogClass\":\"Fighter\",\"race\":\"Kobold\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Shaman\",\"pogImageUrl\":\"https://i.pinimg.com/originals/d6/f0/e1/d6f0e1936ff06cc4b5bef86f4f62011c.png\",\"pogType\":\"MONSTER\",\"uuid\":\"d67f547f-612d-46fa-8b0e-03b0af349182\",\"templateUUID\":\"d67f547f-612d-46fa-8b0e-03b0af349182\",\"pogClass\":\"Shaman\",\"race\":\"Orc\"}]}";
	/**
	 * Get list of room object pog templates.
	 */
	public static final String LOADROOMOBJECTPOGTEMPLATES = "{\"pogList\":[{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":23,\"dungeonLevel\":0,\"pogName\":\"Secret Door Right\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/SecretDoor.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"Secret_Door_Right\",\"templateUUID\":\"Secret_Door_Right\",\"pogClass\":\"Secretdoor\",\"race\":\"\",\"notes\":\"This is a magic \\u003cb\\u003esecret\\u003c/b\\u003e door to the evil realm of NYM!\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":27,\"dungeonLevel\":0,\"pogName\":\"Secret Door Top\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/SecretDoorTop.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"Secret_Door_Top\",\"templateUUID\":\"Secret_Door_Top\",\"pogClass\":\"Secretdoor\",\"race\":\"\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":19,\"dungeonLevel\":0,\"pogName\":\"Notes\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Notes.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"11a35391-e498-4217-90d3-125d0b2ea089\",\"templateUUID\":\"11a35391-e498-4217-90d3-125d0b2ea089\",\"pogClass\":\"Notes\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":3,\"dungeonLevel\":0,\"pogName\":\"Trap\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Trap.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"df427e70-07fd-491b-9913-5c00918b9e72\",\"templateUUID\":\"df427e70-07fd-491b-9913-5c00918b9e72\",\"pogClass\":\"Trap\",\"race\":\"\",\"notes\":\"\"}]}";
}
