/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.acesinc.data.json.generator.types;

import java.util.Date;

/**
 *
 * @author andrewserff
 */
public class NowType extends TypeHandler {

    public static final String TYPE_NAME = "now";
    public static final String TYPE_DISPLAY_NAME = "Now";

    //note, this can be a negative number, so you are subtracting
    private long timeToAdd = 0;

    @Override
    public void setLaunchArguments(String[] launchArguments) {
        super.setLaunchArguments(launchArguments);
        if (launchArguments.length == 0) {
            timeToAdd = 0;
        } else {
            String arg = launchArguments[0];
            String timeAmount = arg;
            boolean isNegative = false;
            if (arg.startsWith("-")) {
                //it's a negative number
                isNegative = true;
                timeAmount = arg.substring(1, arg.length());
            }

            String[] pieces = timeAmount.split("_");
            //first part is the number
            int time = Integer.parseInt(pieces[0]);
            //second part is the unit
            String unit = pieces[1];

            long multiplier = 1;
            switch (unit) {
                case "y": {
                    multiplier = 1000 * 60 * 60 * 24 * 365;
                    break;
                }
                case "d": {
                    multiplier = 1000 * 60 * 60 * 24;
                    break;
                }
                case "h": {
                    multiplier = 1000 * 60 * 60;
                    break;
                }
                case "m": {
                    multiplier = 1000 * 60;
                    break;
                }
            }
            
            timeToAdd = time * multiplier;

            if (isNegative) {
                timeToAdd = timeToAdd * -1;
            }
        }
    }

    @Override
    public Date getNextRandomValue() {
        if (timeToAdd == 0) {
            return new Date();
        } else {
            return new Date(new Date().getTime() + timeToAdd);
        }
    }

    @Override
    public String getName() {
        return TYPE_NAME;
    }
}
