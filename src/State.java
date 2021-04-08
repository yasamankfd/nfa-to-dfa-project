public class State {
    public String name = "";
    public State forOne = null;
    public State forZero = null;
    public boolean isfinal = false;
    public int index;
    public State nextForOne() { return forOne; }

    public State nextForZero()
    {
        return forZero;
    }
}
