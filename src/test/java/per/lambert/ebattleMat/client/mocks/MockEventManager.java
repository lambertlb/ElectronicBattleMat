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

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.GwtEvent.Type;
import com.google.gwt.event.shared.HandlerRegistration;

import per.lambert.ebattleMat.client.interfaces.IEventManager;

/**
 * Mock event manager.
 * @author LLambert
 *
 */
public class MockEventManager implements IEventManager {

	/**
	 * Callback for unit test.
	 */
	private EventManagerTestCallback eventManagerTestCallback;
	/**
	 * get Callback for unit test.
	 * @return Callback for unit test.
	 */
	public EventManagerTestCallback getEventManagerTestCallback() {
		return eventManagerTestCallback;
	}

	/**
	 * Set Callback for unit test.
	 * @param eventManagerTestCallback Callback for unit test.
	 */
	public void setEventManagerTestCallback(final EventManagerTestCallback eventManagerTestCallback) {
		this.eventManagerTestCallback = eventManagerTestCallback;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <H extends EventHandler> HandlerRegistration addHandler(final Type<H> eventType, final H handler) {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireEvent(final GwtEvent<?> event) {
		if (eventManagerTestCallback != null) {
			eventManagerTestCallback.onEvent(event);
		}
	}
}
