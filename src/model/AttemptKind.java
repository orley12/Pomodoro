package model;

public enum AttemptKind {

    FOCUS(25 * 60, "Focus time"),
    BREAK(5 * 60, "Break time");

    private int mTotalSeconds;

    private String mAttemptTitle;

    AttemptKind(int totalSeconds , String attemptTitle) {
        mTotalSeconds = totalSeconds;
        mAttemptTitle = attemptTitle;
    }

    public int getTotalSeconds() {
        return mTotalSeconds;
    }

    public String getAttemptName() {
        return mAttemptTitle;
    }
}
