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
package per.lambert.ebattleMat.client.services.serviceData;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * List of pogs.
 * @author LLambert
 *
 */
public class PogList extends JavaScriptObject {
	/**
	 * Constructor.
	 */
	protected PogList() {
	}

	/**
	 * List of pogs.
	 * @return list of pogs.
	 */
	public final native PogData[] getPogList() /*-{
		if (this.pogList === undefined) {
			this.pogList = [];
		}
		return this.pogList;
	}-*/;

	/**
	 * Add or update pog.
	 * @param pog to add
	 */
	public final void addOrUpdate(final PogData pog) {
		PogData[] list = getPogList();
		for (PogData pogInList : list) {
			if (pogInList.isEqual(pog)) {
				pogInList.fullUpdate(pog);
				return;
			}
		}
		addPog(pog);
	}
	/**
	 * Add pog to list.
	 * @param pogToAdd pog to add
	 */
	public final native void addPog(PogData pogToAdd) /*-{
		if (this.pogList === undefined) {
			this.pogList = [];
		}
		this.pogList.push(pogToAdd);
	}-*/;
}
