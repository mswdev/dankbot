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

public class MethodDescription {
	public String[] arguments;
	public String returnType;

	public MethodDescription(String ret, String[] params) {
		returnType = ret;
		arguments = params;
	}
}
