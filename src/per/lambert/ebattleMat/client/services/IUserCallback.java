package per.lambert.ebattleMat.client.services;

import per.lambert.ebattleMat.client.interfaces.IErrorInformation;

/**
 * @author LLambert
 * Use this interface to implement a callback from any asynchronous operation
 */
public interface IUserCallback {
	/**
	 * Called if error occurred
	 * @param sender The original caller
	 * @param error Information about the error
	 */
	void	onError(Object sender, IErrorInformation error);
	/**
	 * Called when request succeeds
	 * @param sender The original caller
	 * @param data Data returned from execution
	 */
	void	onSuccess(Object sender, Object data);
}
