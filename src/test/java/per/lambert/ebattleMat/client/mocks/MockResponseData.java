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
	 * Get session list response for dungeon 1.
	 */
	public static final String GETSESSIONLISTRESPONSE = "{\"dungeonUUID\":\"dungeon1-template\",\"sessionNames\":[\"Session 1\"],\"sessionUUIDs\":[\"362dd584-3449-4687-aeed-d1a2ac2f10bd\"]}";
	/**
	 * Get list of monster pog templates.
	 */
	public static final String LOADMONSTERPOGTEMPLATES = "{\"pogList\":[{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Male Kobold\",\"pogImageUrl\":\"https://vignette.wikia.nocookie.net/forgottenrealms/images/f/f3/Monster_Manual_5e_-_Kobold_-_p195.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"Male-Kobold\",\"templateUUID\":\"Male-Kobold\",\"pogClass\":\"Fighter\",\"race\":\"Kobold\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"gender\":\"Female\",\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Female Kobold\",\"pogImageUrl\":\"https://pre00.deviantart.net/9204/th/pre/f/2016/172/6/a/kalbold_femalekoboldfighter_color_01_by_prodigyduck-da75cmb.png\",\"pogType\":\"MONSTER\",\"uuid\":\"Female-Kobold\",\"templateUUID\":\"Female-Kobold\",\"pogClass\":\"Fighter\",\"race\":\"Kobold\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Shaman\",\"pogImageUrl\":\"https://i.pinimg.com/originals/d6/f0/e1/d6f0e1936ff06cc4b5bef86f4f62011c.png\",\"pogType\":\"MONSTER\",\"uuid\":\"d67f547f-612d-46fa-8b0e-03b0af349182\",\"templateUUID\":\"d67f547f-612d-46fa-8b0e-03b0af349182\",\"pogClass\":\"Shaman\",\"race\":\"Orc\"}]}";
	/**
	 * Get list of room object pog templates.
	 */
	public static final String LOADROOMOBJECTPOGTEMPLATES = "{\"pogList\":[{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":23,\"dungeonLevel\":0,\"pogName\":\"Secret Door Right\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/SecretDoor.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"Secret_Door_Right\",\"templateUUID\":\"Secret_Door_Right\",\"pogClass\":\"Secretdoor\",\"race\":\"\",\"notes\":\"This is a magic \\u003cb\\u003esecret\\u003c/b\\u003e door to the evil realm of NYM!\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":27,\"dungeonLevel\":0,\"pogName\":\"Secret Door Top\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/SecretDoorTop.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"Secret_Door_Top\",\"templateUUID\":\"Secret_Door_Top\",\"pogClass\":\"Secretdoor\",\"race\":\"\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":19,\"dungeonLevel\":0,\"pogName\":\"Notes\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Notes.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"11a35391-e498-4217-90d3-125d0b2ea089\",\"templateUUID\":\"11a35391-e498-4217-90d3-125d0b2ea089\",\"pogClass\":\"Notes\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":3,\"dungeonLevel\":0,\"pogName\":\"Trap\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Trap.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"df427e70-07fd-491b-9913-5c00918b9e72\",\"templateUUID\":\"df427e70-07fd-491b-9913-5c00918b9e72\",\"pogClass\":\"Trap\",\"race\":\"\",\"notes\":\"\"}]}";
	/**
	 * Load dungeon 1 template.
	 */
	public static final String LOADDUNGEON1TEMPLATE = "{\"uuid\":\"dungeon1-template\",\"dungeonName\":\"Dungeon1\",\"dungeonLevels\":[{\"levelDrawing\":\"level1.jpg\",\"levelName\":\"Entrance\",\"gridSize\":50.0,\"gridOffsetX\":11.0,\"gridOffsetY\":11.0,\"columns\":31,\"rows\":31,\"monsters\":{\"pogList\":[{\"pogSize\":1,\"pogColumn\":4,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"8933d0b2-f6e3-4b0d-92f3-1c737b8ffb27\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\"},{\"pogSize\":1,\"pogColumn\":6,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"e3150df8-f310-446f-a86f-42a44970b475\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":7,\"pogRow\":4,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"463a7eb0-a0d8-442a-b9d3-27f925eef512\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":5,\"pogRow\":3,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"d1fe2835-d55d-48bc-892f-d9288b0d7ae4\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\",\"notes\":\"\"},{\"pogSize\":2,\"pogColumn\":8,\"pogRow\":5,\"playerFlags\":2,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Female Kobold\",\"pogImageUrl\":\"https://pre00.deviantart.net/9204/th/pre/f/2016/172/6/a/kalbold_femalekoboldfighter_color_01_by_prodigyduck-da75cmb.png\",\"pogType\":\"MONSTER\",\"uuid\":\"2daff302-5c49-437e-97f8-9f0c0710accd\",\"templateUUID\":\"Female-Kobold\",\"pogClass\":\"Fighter\",\"race\":\"Kobold\",\"notes\":\"\"}]},\"roomObjects\":{\"pogList\":[{\"pogSize\":1,\"pogColumn\":12,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":19,\"dungeonLevel\":0,\"pogName\":\"Notes\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Notes.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"93067e63-4696-4b31-8621-6e74a870767f\",\"templateUUID\":\"11a35391-e498-4217-90d3-125d0b2ea089\",\"pogClass\":\"Notes\",\"race\":\"\",\"notes\":\"Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!\"},{\"pogSize\":1,\"pogColumn\":16,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":3,\"dungeonLevel\":0,\"pogName\":\"Trap\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Trap.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"fb07199b-8c67-49c5-a97e-8faaab9bda56\",\"templateUUID\":\"df427e70-07fd-491b-9913-5c00918b9e72\",\"pogClass\":\"Trap\",\"race\":\"\",\"notes\":\"\"}]}},{\"levelDrawing\":\"level2.jpg\",\"levelName\":\"Deep Dark (2)\",\"gridSize\":50.0,\"gridOffsetX\":0.0,\"gridOffsetY\":0.0,\"columns\":39,\"rows\":22,\"monsters\":{\"pogList\":[{\"pogSize\":1,\"pogColumn\":10,\"pogRow\":3,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":1,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"5a56ca3f-f180-4aef-ae9c-7673d7beddc0\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\"}]},\"roomObjects\":{\"pogList\":[]}},{\"levelDrawing\":\"level3.jpg\",\"levelName\":\"Flooded Area (3)\",\"gridSize\":50.0,\"gridOffsetX\":0.0,\"gridOffsetY\":0.0,\"columns\":39,\"rows\":22,\"monsters\":{\"pogList\":[]},\"roomObjects\":{\"pogList\":[]}}],\"showGrid\":true}";
	/**
	 * Load session data response.
	 */
	public static final String LOADSESSIONDATARESPONSE = "{\"version\":1,\"sessionName\":\"Session 1\",\"dungeonUUID\":\"dungeon1-template\",\"sessionUUID\":\"362dd584-3449-4687-aeed-d1a2ac2f10bd\",\"players\":{\"pogList\":[{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Zorak\",\"pogImageUrl\":\"https://i.pinimg.com/236x/2b/42/95/2b4295eb2f4e3228058b0aff9625ba47.jpg\",\"pogType\":\"PLAYER\",\"uuid\":\"3bdce6d8-09a6-4af6-b362-0c375ee5cc5c\",\"templateUUID\":\"3bdce6d8-09a6-4af6-b362-0c375ee5cc5c\"},{\"pogSize\":1,\"pogColumn\":0,\"pogRow\":0,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Betty\",\"pogImageUrl\":\"https://i.pinimg.com/originals/20/58/c3/2058c3acaefdfc94bcb7f95d85230fa9.jpg\",\"pogType\":\"PLAYER\",\"uuid\":\"cae85599-b764-42f5-a286-7c046f433cc3\",\"templateUUID\":\"cae85599-b764-42f5-a286-7c046f433cc3\"}]},\"sessionLevels\":[{\"fogOfWar\":[[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true]],\"monsters\":{\"pogList\":[{\"pogSize\":1,\"pogColumn\":4,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"439db91c-a7a4-48ca-b3fa-d09492bc7ced\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\"},{\"pogSize\":1,\"pogColumn\":6,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"4980fc78-3d50-4976-ba09-0c4383eaf2a4\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":7,\"pogRow\":4,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"385cde45-93d1-4fd4-920b-75e2d8016902\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\",\"notes\":\"\"},{\"pogSize\":1,\"pogColumn\":5,\"pogRow\":3,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"f945dfdd-a7ad-44d3-895a-f5dfafdbfdb5\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\",\"notes\":\"\"},{\"pogSize\":2,\"pogColumn\":8,\"pogRow\":5,\"playerFlags\":2,\"dungeonMasterFlags\":0,\"dungeonLevel\":0,\"pogName\":\"Female Kobold\",\"pogImageUrl\":\"https://pre00.deviantart.net/9204/th/pre/f/2016/172/6/a/kalbold_femalekoboldfighter_color_01_by_prodigyduck-da75cmb.png\",\"pogType\":\"MONSTER\",\"uuid\":\"7a170bef-66f3-4875-aabf-28a289fe1552\",\"templateUUID\":\"Female-Kobold\",\"pogClass\":\"Fighter\",\"race\":\"Kobold\",\"notes\":\"\"}]},\"roomObjects\":{\"pogList\":[{\"pogSize\":1,\"pogColumn\":12,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":19,\"dungeonLevel\":0,\"pogName\":\"Notes\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Notes.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"0940762e-4901-4ff8-930d-0e32882775cc\",\"templateUUID\":\"11a35391-e498-4217-90d3-125d0b2ea089\",\"pogClass\":\"Notes\",\"race\":\"\",\"notes\":\"Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!Enter here and die!\"},{\"pogSize\":1,\"pogColumn\":16,\"pogRow\":2,\"playerFlags\":0,\"dungeonMasterFlags\":3,\"dungeonLevel\":0,\"pogName\":\"Trap\",\"pogImageUrl\":\"dungeonData/resources/roomObjects/Trap.png\",\"pogType\":\"ROOMOBJECT\",\"uuid\":\"2755943d-41c5-4917-b478-69806b4f5f21\",\"templateUUID\":\"df427e70-07fd-491b-9913-5c00918b9e72\",\"pogClass\":\"Trap\",\"race\":\"\",\"notes\":\"\"}]}},{\"fogOfWar\":[[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true]],\"monsters\":{\"pogList\":[{\"pogSize\":1,\"pogColumn\":10,\"pogRow\":3,\"playerFlags\":0,\"dungeonMasterFlags\":0,\"dungeonLevel\":1,\"pogName\":\"Orc Fighter\",\"pogImageUrl\":\"http://static4.paizo.com/image/content/PathfinderTales/PZO8500-CrisisOfFaith-Corogan.jpg\",\"pogType\":\"MONSTER\",\"uuid\":\"0a68147a-c43c-4177-adfc-f38a6777ede7\",\"templateUUID\":\"61fc86ec-2fa2-43ef-bdf7-6171047c7018\",\"pogClass\":\"Fighter\",\"race\":\"Orc\"}]},\"roomObjects\":{\"pogList\":[]}},{\"fogOfWar\":[[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true],[true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true,true]],\"monsters\":{\"pogList\":[]},\"roomObjects\":{\"pogList\":[]}}]}";

}
