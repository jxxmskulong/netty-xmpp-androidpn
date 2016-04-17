/**
 * Copyright 2012 José Martínez
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.xmpp.push.androidpn.server.xmpp.xml;

/**
 * Interface which provides a standard method for accessing the
 * {@link XMLElement} from an object which encapsulates one.
 */
public interface HasXML {
	
	/**
	 * Gets the XML element from this object.
	 * 
	 * @return the XML element
	 */
	XMLElement getXML();
	
	@Override
	String toString();
	
}
