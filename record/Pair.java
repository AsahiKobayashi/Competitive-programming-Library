public record Pair<T,S> (T fi , S se) { 
        public String toString() { 
            return "("+fi+","+se+")";

        }
    }