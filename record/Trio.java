public record Trio<T,S,U>(T fi , S se , U th) { 
        public String toString() { 
            return "("+fi+","+se+","+th+")";
        }
    }