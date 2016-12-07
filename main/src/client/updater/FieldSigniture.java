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

import client.updater.asm.assembly.Mask;

public class FieldSigniture {
    public String[] arguments;
    public String returnType;
    public String bytecodePattern;
    public Mask[] masks;
    public boolean regex = false;

    public FieldSigniture(String returnType, String[] arguments, String pattern) {
        this.returnType = returnType;
        this.arguments = arguments;
        bytecodePattern = pattern;
    }

    public FieldSigniture(String returnType, String[] arguments) {
        this.returnType = returnType;
        this.arguments = arguments;
    }

    public FieldSigniture(String returnType, String[] arguments, Mask... masks) {
        this.returnType = returnType;
        this.arguments = arguments;
        this.masks = masks;
    }

    public FieldSigniture(String returnType, String[] arguments, String pattern, boolean regex) {
        this.returnType = returnType;
        this.arguments = arguments;
        bytecodePattern = pattern;
        this.regex = true;

    }
}

