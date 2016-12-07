/******************************************************
 * Created by Marneus901                                *
 * � 2012-2014                                          *
 * **************************************************** *
 * Access to this source is unauthorized without prior  *
 * authorization from its appropriate author(s).        *
 * You are not permitted to release, nor distribute this* 
 * work without appropriate author(s) authorization.    *
 ********************************************************/
package client.updater;

import org.objectweb.asm.tree.FieldNode;

public class FieldDescription {
	private int modifier;
	private String type;
	public boolean found;

	public FieldDescription(int modifier, String type) {
		this.modifier = modifier;
		this.type = type;
		found = false;
	}

	public boolean isField(FieldNode f) {
		if (f.access == modifier) {
			if (type.equals("?") || (type.equals("[") && f.desc.startsWith("[")) || type.equals(f.desc)) {
				found = true;
				return true;
			}
		}
		return false;
	}
}
