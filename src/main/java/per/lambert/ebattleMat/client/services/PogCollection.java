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
package per.lambert.ebattleMat.client.services;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.JsonUtils;

import per.lambert.ebattleMat.client.event.ReasonForActionEvent;
import per.lambert.ebattleMat.client.interfaces.IDataRequester;
import per.lambert.ebattleMat.client.interfaces.IErrorInformation;
import per.lambert.ebattleMat.client.interfaces.IUserCallback;
import per.lambert.ebattleMat.client.interfaces.ReasonForAction;
import per.lambert.ebattleMat.client.services.serviceData.PogData;
import per.lambert.ebattleMat.client.services.serviceData.PogList;

/**
 * Pog collection.
 * 
 * This will hold information about a pog list.
 * 
 * @author llambert
 *
 */
public class PogCollection {
	/**
	 * load event.
	 */
	private ReasonForAction loadEvent;
	/**
	 * Map of pog data vs name.
	 */
	private Map<String, PogData> pogTemplateMap = new HashMap<String, PogData>();
	/**
	 * List of pog templates.
	 */
	private PogList pogTemplates;

	/**
	 * get array of pog templates.
	 * 
	 * @return array of pog templates
	 */
	public PogData[] getPogList() {
		return pogTemplates.getPogList();
	}

	/**
	 * Constructor.
	 * 
	 * @param eventToFireWhenLoaded event to fire when loaded
	 */
	public PogCollection(final ReasonForAction eventToFireWhenLoaded) {
		loadEvent = eventToFireWhenLoaded;
	}

	/**
	 * Find this pog based on UUID.
	 * 
	 * @param pogUUID to find
	 * @return pog data if found
	 */
	public PogData findPog(final String pogUUID) {
		return (pogTemplateMap.get(pogUUID));
	}

	/**
	 * Clear out collections.
	 */
	public void clear() {
		pogTemplateMap.clear();
	}

	/**
	 * Load in Pogs from resource file.
	 * 
	 * @param typeToLoad type to load
	 */
	public void loadFromServer(final String typeToLoad) {
		clear();
		IDataRequester dataRequester = ServiceManager.getDataRequester();
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("fileName", typeToLoad + "pogs.json");
		dataRequester.requestData("", "LOADJSONFILE", parameters, new IUserCallback() {

			@Override
			public void onSuccess(final Object sender, final Object data) {
				loadPogTemplates(data);
			}

			@Override
			public void onError(final Object sender, final IErrorInformation error) {
			}
		});
	}

	/**
	 * Got data from server fill in collections.
	 * 
	 * @param data received
	 */
	private void loadPogTemplates(final Object data) {
		pogTemplates = JsonUtils.<PogList>safeEval((String) data);
		rebuildCollections();
	}

	/**
	 * Rebuild all collections.
	 */
	private void rebuildCollections() {
		clear();
		for (PogData pogTemplate : pogTemplates.getPogList()) {
			addToCollections(pogTemplate);
		}
		ServiceManager.getEventManager().fireEvent(new ReasonForActionEvent(loadEvent, null));
	}

	/**
	 * Add information to various collections.
	 * 
	 * @param pogToAdd with data to add
	 */
	private void addToCollections(final PogData pogToAdd) {
		pogTemplateMap.put(pogToAdd.getUUID(), pogToAdd);
	}

	/**
	 * add or update this pog.
	 * 
	 * @param pog to add or update
	 */
	public void addOrUpdatePogToCommonResource(final PogData pog) {
		if (findPog(pog.getUUID()) == null) {
			pogTemplates.addPog(pog);
		}
		rebuildCollections();
	}

	/**
	 * Remove this pog.
	 * 
	 * @param pog
	 */
	public void remove(final PogData pog) {
		if (findPog(pog.getUUID()) == null) {
			return;
		}
		pogTemplates.remove(pog);
		rebuildCollections();
	}

	/**
	 * Is this pog a template.
	 * 
	 * @param pogData to check
	 * @return true if is template
	 */
	public boolean isTemplate(final PogData pogData) {
		return (pogTemplateMap.containsKey(pogData.getUUID()));
	}
}
