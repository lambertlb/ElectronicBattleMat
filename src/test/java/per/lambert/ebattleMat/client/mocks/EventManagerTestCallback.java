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
package per.lambert.ebattleMat.client.mocks;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Callback to unit test when eent is fired from event manager.
 * @author LLambert
 *
 */
public interface EventManagerTestCallback {

	/**
	 * Tell unit test about event.
	 * @param event that fired
	 */
	void onEvent(GwtEvent<?> event);
}
