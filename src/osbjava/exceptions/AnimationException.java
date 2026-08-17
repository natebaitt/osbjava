package osbjava.exceptions;

public class AnimationException extends RuntimeException {
    public AnimationException() {
        super("Animation parameter is out of its expected range");
    }
}
