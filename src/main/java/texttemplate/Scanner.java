package texttemplate;

class Scanner {
    private String text;
    private int ptr;
    private StringBuilder buffer;

    public Scanner(String text) {
        this.text = text;
        this.ptr = 0;
        this.buffer = new StringBuilder();
    }

    public boolean isEof() {
        return this.text.length() <= this.ptr;
    }

    public char charAt(int index) {
        try {
            return this.text.charAt(index);
        } catch (IndexOutOfBoundsException e) {
            return '\0';
        }
    }

    /**
     * Incr current ptr and returns current charactor.
     * 
     * @return Charactor in current position
     */
    public char next() {
        var c = this.charAt(this.ptr);
        this.incr();
        return c;
    }

    /**
     * Returns current charactor.
     * 
     * Almost same as next(), but this method doesn't incr current ptr
     * 
     * @return Charactor in current position
     */
    public char peek() {
        return this.charAt(this.ptr);
    }

    public void incr() {
        this.ptr++;
    }

    public void push(char c) {
        this.buffer.append(c);
    }

    /**
     * Pop string from buffer and clear it.
     * 
     * @return String which was stored in the buffer
     */
    public String pop() {
        var s = this.buffer.toString();
        this.buffer.delete(0, this.buffer.length());
        return s;
    }
}
