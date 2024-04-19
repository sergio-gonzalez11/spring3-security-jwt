package sg.security.api.constant;

public final class Errors {

    public static final String ERROR_ASSIGNMENT_NOT_PERSON_ID = "Person id not found.";

    public static final String ERROR_ASSIGNMENT_NOT_FUNCTION_ID = "Function id not found.";

    public static final String ERROR_ASSIGNMENT_NOT_GROUP_ID = "Group id not found.";

    public static final String ERROR_ASSIGNMENT_EXITS = "Assignment function and person exits";

    public static final String ERROR_ASSIGNMENT_NOT_EXITS = "Assignment function and person not exists";

    public static final String ERROR_ASSIGNMENT_GROUP_EXITS = "Assignment group and person exits";

    public static final String ERROR_ASSIGNMENT_GROUP_NOT_EXISTS = "Assignment group and person not exists";

    public static final String ERROR_ASSIGNMENT_STATE_NOT_CORRECT = "Assigment State is not correct.";

    public static final String ERROR_ASSIGNMENT_GROUP_WITHOUT_FUNCTION = "All group functions are assigned to the person";

    public static final String ERROR_FUNCTION_NOT_ACTIVE = "Function not active.";

    public static final String ERROR_ASSIGNMENT_ALREADY_APPROVED_DENIED = "Assignment already approved/denied.";

    public static final String ERROR_GROUP_NOT_ACTIVE = "Group not active.";

    public static final String ERROR_GROUP_ASSIGNMENT_NOT_PENDING = "Group assignment not pending.";

    public static final String PERSON_OU_NULL_ERROR = "Organizational unit is null";

    public static final String ERROR_NOTIFICATION_TYPE = "Notification type isn´t allowed.";

    public static final String ERROR_ACCESS_POLICY_DATE_LAST_REFRESH_NULL = "Access policy dateLastRefresh is null";

    public static final String ERROR_NOTIFICATION_NOT_FOUND = "Notification not found.";

    public static final String ERROR_NOTIFICATION_PERSON_NOT_FOUND = "Notification Person not found.";

    public static final String ERROR_SEGREGATIONS_ALREADY_RESOLVED = "Segregation already resolved.";

    public static final String ERROR_CRITICALITY_ALREADY_RESOLVED = "Criticality already resolved.";

    private Errors() {
        throw new UnsupportedOperationException("Not instantiable class!");
    }

}
