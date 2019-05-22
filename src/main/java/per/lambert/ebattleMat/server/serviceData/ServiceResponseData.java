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
package per.lambert.ebattleMat.server.serviceData;

/**
 * Response data for service request.
 * 
 * @author LLambert
 *
 */
public class ServiceResponseData {
	/**
	 * Error if there was one.
	 */
	private int error;

	/**
	 * Constructor.
	 * 
	 * @param error response error.
	 */
	protected ServiceResponseData(final int error) {
		this.error = error;
	}

	/**
	 * Constructor.
	 */
	protected ServiceResponseData() {
		this.error = 0;
	}

	/**
	 * Get error.
	 * 
	 * @return error
	 */
	public int getError() {
		return error;
	}

	/**
	 * Set error.
	 * 
	 * @param error to set
	 */
	public void setError(final int error) {
		this.error = error;
	}

}
