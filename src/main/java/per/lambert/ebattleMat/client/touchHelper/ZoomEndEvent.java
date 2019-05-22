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
package per.lambert.ebattleMat.client.touchHelper;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.TouchEvent;
import com.google.gwt.event.shared.GwtEvent;

/**
 * Event for handling zoom end.
 * 
 * @author LLambert
 *
 */
public class ZoomEndEvent extends GwtEvent<ZoomEndHandler> {


	/**
	 * Element that was targeted.
	 */
	private Element targetElement;

	/**
	 * Get target element.
	 * 
	 * @return target element.
	 */
	public Element getTargetElement() {
		return targetElement;
	}

	/**
	 * Type of event.
	 */
	private static Type<ZoomEndHandler> eventType = new Type<ZoomEndHandler>();

	/**
	 * Get event type.
	 * 
	 * @return event type
	 */
	public static Type<ZoomEndHandler> getType() {
		return eventType;
	}

	/**
	 * Constructor.
	 * 
	 * @param targetElement element that was targeted.
	 */
	@SuppressWarnings("rawtypes")
	public ZoomEndEvent(final TouchEvent targetElement) {
		this.targetElement = TouchHelper.computeTargetElement(targetElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Type<ZoomEndHandler> getAssociatedType() {
		return eventType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void dispatch(final ZoomEndHandler handler) {
		handler.onZoomEnd(this);
	}

}
