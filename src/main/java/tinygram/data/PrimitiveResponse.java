package tinygram.data;

public class PrimitiveResponse {
    public static class String {
        public java.lang.String value;
    
        public String(java.lang.String value) {
            this.value = value;
        }
    }

    public static class Int {
        public int value;
    
        public Int(int value) {
            this.value = value;
        }
    }

    public static class Boolean {
        public boolean value;
    
        public Boolean(boolean value) {
            this.value = value;
        }
    }
}
