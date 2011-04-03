/*
 * [The "BSD license"]
 *  Copyright (c) 2011 Terence Parr
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.stringtemplate.v4.misc;

import org.stringtemplate.v4.ModelAdaptor;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

import java.util.Map;

public class MapModelAdaptor implements ModelAdaptor {
	public Object getProperty(ST self, Object o, Object property, String propertyName)
		throws STNoSuchPropertyException
	{
		Object value;
		Map map = (Map)o;
		if ( property==null ) value = map.get(STGroup.DEFAULT_KEY);
		else if ( property.equals("keys") ) value = map.keySet();
		else if ( property.equals("values") ) value = map.values();
		else if ( map.containsKey(property) ) value = map.get(property);
		else if ( map.containsKey(propertyName) ) { // if can't find the key, try toString version
			value = map.get(propertyName);
		}
		else value = map.get(STGroup.DEFAULT_KEY); // not found, use default
		if ( value == STGroup.DICT_KEY ) {
			value = property;
		}
		if ( value instanceof ST ) {
			// dup, don't alter existing template; it's a prototype
			ST st = (ST) value;
			st = st.groupThatCreatedThisInstance.createStringTemplateInternally(st);
			value = st;
		}
		return value;
	}
}
