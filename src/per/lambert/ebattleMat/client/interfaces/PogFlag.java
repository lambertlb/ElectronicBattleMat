package per.lambert.ebattleMat.client.interfaces;

public enum PogFlag {
	TRANSPARENT,
	SHIFT_TOP,
	SHIFT_RIGHT,
	INVISIBLE,
	DEAD;

	private int value ;

	private static int nextValue = 1;
	PogFlag() {
		setPogFlag();
	}
	
	private void setPogFlag() {
		value = PogFlag.nextValue ;
		PogFlag.nextValue <<= 1;
	}

	public int getValue() {
		return(value);
	}
}
