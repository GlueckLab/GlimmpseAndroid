/*
 * Mobile - Android, User Interface for the GLIMMPSE Software System.  Allows
 * users to perform power, sample size calculations. 
 * 
 * Copyright (C) 2010 Regents of the University of Colorado.  
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package edu.ucdenver.bios.glimmpseandroid.application;

public enum SolvingForEnum {
	/** The POWER. */
    POWER("Power"),

    /** The SAMPLE SIZE. */
    SAMPLE_SIZE("Sample Size"),

    /** The DETECTABLE DIFFERENCE. */
    DETECTABLE_DIFFERENCE("Detectable Difference");

    /** The id. */
    private final String id;

    /**
     * Instantiates a new solution type enum.
     *
     * @param id
     *            the id
     */
    SolvingForEnum(final String id) {
        this.id = id;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Parses the id.
     *
     * @param id
     *            the id
     * @return the solution type enum
     */
    public static SolvingForEnum parseId(final String id) {
    	SolvingForEnum soulutionTypeEnum = null;
        for (SolvingForEnum b : SolvingForEnum.values()) {
            if (id.equalsIgnoreCase(b.id)) {
                soulutionTypeEnum = b;
            }
        }
        return soulutionTypeEnum;
    }
}
