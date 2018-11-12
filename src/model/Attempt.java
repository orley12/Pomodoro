package model;

public class Attempt {

    private String mMessage;

    private int mRemainingSeconds;

    private AttemptKind mAttemptKind;

    public Attempt(AttemptKind attemptKind, String message) {
        mMessage = message;
        mAttemptKind = attemptKind;
        mRemainingSeconds = mAttemptKind.getTotalSeconds();
    }

    public String getMessage() {
        return mMessage;
    }

    public int getRemainingSeconds() {
        return mRemainingSeconds;
    }

    public AttemptKind getAttemptKind() {
        return mAttemptKind;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void tick() {
        mRemainingSeconds--;
    }

    @Override
    public String toString() {
        return "Attempt{" +
                "mMessage='" + mMessage + '\'' +
                ", mRemainingSeconds=" + mRemainingSeconds +
                ", mAttemptKind=" + mAttemptKind +
                '}';
    }

    public void save() {
        System.out.printf("Saving: " + toString(),this);
    }
}
