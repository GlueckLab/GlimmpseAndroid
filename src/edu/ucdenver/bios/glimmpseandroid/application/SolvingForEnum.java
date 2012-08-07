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
