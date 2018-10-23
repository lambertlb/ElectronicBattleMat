package per.lambert.ebattleMat.client.interfaces;

/**
 * Defines values for all errors that can occur in the ZFS application
 *
 */
public enum DungeonServerError {
	GenericError(-1),
	Succsess(0),
	Undefined1(1);
			
	 private int value;
	 
	DungeonServerError(int value) {
		 this.value = value;
	 }
	 
	 public int getErrorValue() {
		 return value;
	 }

	 public static DungeonServerError fromInt(int value) {
		 for(DungeonServerError error : DungeonServerError.values()){
			 if(error.getErrorValue() == value) {
				 return error;
			 }
		 }
		 return DungeonServerError.GenericError;
	 }
}
