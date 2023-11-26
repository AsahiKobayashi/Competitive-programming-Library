
    public <T extends Comparable<T>> int LowCountClosed(List<T> A , T key) { 
        return upperbound(A, key); 
    }

    public <T extends Comparable<T>> int LowCountOpen(List<T> A , T key) { 
        return lowerbound(A, key); 
    }

    public <T extends Comparable<T>> int HighCountClosed(List<T> A , T key) { 
        return A.size() - lowerbound(A, key); 
    }

    public <T extends Comparable<T>> int HighCountOpen(List<T> A , T key) { 
        return A.size() - upperbound(A, key); 
    }
    // [)
    public <T extends Comparable<T>> int CountClosedOpen(List<T> A, T a, T b) {
        return lowerbound(A, b) - lowerbound(A, a);
    }
    // []
    public <T extends Comparable<T>> int CountClosedClosed(List<T> A, T a, T b) {
        return upperbound(A, b) - lowerbound(A, a);
    }
    // (]
    public <T extends Comparable<T>> int CountOpenClosed(List<T> A, T a, T b) {
        return upperbound(A, b) - upperbound(A, a);
    }
    // ()
    public <T extends Comparable<T>> int CountOpenOpen(List<T> A, T a, T b) {
        return lowerbound(A, b) - upperbound(A, a);
    }

    private <T extends Comparable<T>> int lowerbound(List<T> A, T key) {
        int left = 0 , right = A.size();
        while (left < right) {
            int mid = (left + right) / 2;
            if (A.get(mid).compareTo(key) < 0) left = mid + 1;
            else right = mid;
        }
        return right;
    }

    private <T extends Comparable<T>> int upperbound(List<T> A, T key) { 
        int left = 0 , right = A.size();
        while (left < right) {
            int mid = (left + right) / 2;
            if (A.get(mid).compareTo(key) <= 0) left = mid + 1;
            else right = mid;
        }
        return right;
    }
